import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.EnumSet;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * There are two situations:
 * 1. source files are all files, use Files.copy() to copy to target Path
 * 2. source path has directories, use Files.walkFileTree() to copy all source to target path
 */

public class CopyDirAndFiles {

    public static void main(String[] args) throws IOException {
        // these 3 booleans are determined by console input
        boolean recursive = false;   // only directory source needs to recursive using WalkFileTree method, if just a file path, no recursive is needed
        boolean prompt = false;
        boolean preserve = false;

        // define flag command [-rip]
        int argIndex = 0;
        while (args.length > argIndex) {
            String arg = args[argIndex]; // start from the args[0];
            // only define those flag args - starting with a hyphen "-"
            if (!arg.startsWith("-")) break; // get out of the while loop
            // if the arg only contains "-", that is not right, give out hint
            if (arg.length() < 2) {
                checkHintForHowToWriteCommandLine();
            }
            for (int i = 1; i < arg.length(); i++) {// loop thru each char of the arg except the first char "-"
                char c = arg.charAt(i);
                switch (c) {
                    case 'r':
                        recursive = true;
                        break;
                    case 'i':
                        prompt = true;
                        break;
                    case 'p':
                        preserve = true;
                        break;
                    // other inputs are wrong, show hints
                    default:
                        checkHintForHowToWriteCommandLine();
                }
            }
            // increase the argIndex;
            argIndex++; // since only flag arg start with "-", so this while loop will break after the flag arg
        }

        // define source args as those following the flag arg, define target argument as the last argument
        int remainingNumOfArgs = args.length - argIndex;
        // remainingNumOfArgs should be at least two, one for source path, one for target path
        if (remainingNumOfArgs < 2) {
            checkHintForHowToWriteCommandLine();
        }
        // the remaining args are all Paths, the last one is the target Path
        Path[] allSourcePaths = new Path[remainingNumOfArgs - 1];
        // add all source Path to "allSourcePaths"
        int i = 0;
        while (remainingNumOfArgs > 1) { // loop thru all paths till the last one
            allSourcePaths[i++] = Paths.get(args[argIndex++]);
            // reduce remainingNumOfArgs by one
            remainingNumOfArgs--;
        }
        // get path for target
        Path target = Paths.get(args[argIndex]); // argIndex should be the last one
        // NOW is the time to copy each source file to target path
        // check target path is dir or file, if dir, append source path to target dir path, if file, keep the target path as is
        boolean targetIsDir = Files.isDirectory(target);
        for (int j = 0; j < allSourcePaths.length; j++) {
            Path targetRevised = (targetIsDir) ? target.resolve(allSourcePaths[j].getFileName()) : target;
            if (recursive) { // later in else, set "recursive" must be true for allSourcePath[i] to be a directory,
                // use Files.walkFileTree() to map source paths to target path
                // FOLLOW_LINKS option to keeps track of directories visited so that cycles can be detected.
                EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
                // instantiate the inner class TreeCopier
                TreeCopier visitor = new TreeCopier(preserve, prompt, allSourcePaths[j], targetRevised);
                Files.walkFileTree(allSourcePaths[j], options, Integer.MAX_VALUE, visitor);
            } else {
                // not recursive so source path can't be directoy
                if (Files.isDirectory(allSourcePaths[j])) {
                    System.err.format("%s: is a dirctory, please includ r in the command flag", allSourcePaths[i]);
                    continue;
                }
                copyFile(allSourcePaths[i], targetRevised, prompt, preserve);
            }
        }
    }

    private static void checkHintForHowToWriteCommandLine() {
        System.err.println("java CopyDirAndFiles [-ip] source... target      if the source path is a file");
        System.err.println("java CopyDirAndFiels -r [-ip] source-dir... target        if the source path has directory");
        System.exit(-1);
    }

    static boolean okayToOverride(Path target) {
        // Note: System.console() will be null if the main() did not ask to have input from condole
        // which is to define args[0], args[1] etc.
        String answer = System.console().readLine("overwrite %s (yes/no)? ", target);
        return (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes"));

    }

    static void copyFile(Path source, Path target, boolean prompt, boolean preserve) {
        CopyOption[] options = (preserve) ? new CopyOption[]{StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING} : new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
        if (!prompt || Files.notExists(target) || okayToOverride(target)) {// okayToOverride is forced to be called when prompt is true, so that first two conditions are not met, therefore the last condition is been asked to be tested out
            try {
                Files.copy(source, target, options);
            } catch (IOException e) {
                System.err.format("unable to copy %s for %s%n", source, e);
            }
        }
    }

    // create an inner class TreeCopier (FileVisitor subclass) that copy a file tree
    static class TreeCopier implements FileVisitor<Path> {
        private final boolean preserve;
        private final boolean prompt;
        private final Path source;
        private final Path target;

        public TreeCopier(boolean preserve, boolean prompt, Path source, Path target) {
            this.preserve = preserve;
            this.prompt = prompt;
            this.source = source;
            this.target = target;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            // copy directory -
            // if preserve is true, then copy_attributes, otherwise, copyOption is null
            CopyOption[] copyOptions = (preserve) ? new CopyOption[]{StandardCopyOption.COPY_ATTRIBUTES} : new CopyOption[0];
            // This program construct the new target path as follows:
            // truncate(relativize) the dir path, then append(resolve) it to the target path.
            // e.g: dir is "C:\Users\NancyPC\Desktop\icon folder", then this is appened to "C:\Users\NancyPC\Desktop\icon folder 2"
            // so the newdir is "C:\Users\NancyPC\Desktop\icon folder 2\icon folder"
            Path newdir = target.resolve(source.relativize(dir));
            // When visit each directory, copy each directory to the target path
            try {
                Files.copy(dir, newdir, copyOptions);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return FileVisitResult.CONTINUE;
        }

        // when visit each file, copy each file to target Path
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            copyFile(file, target.resolve(source.relativize(file)), prompt, preserve);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            if (exc instanceof FileSystemLoopException) {
                System.err.println("cycle detected: " + file);
            } else {
                System.err.format("Unable to copy: %s: %s%n", file, exc);
            }
            return CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
            // after visit all directory, fix up the modification time of the directory
            if (exc == null && preserve) { // no IO Exception
                // set the lastModificationTime to the new File path
                Path newDir = target.resolve(source.relativize(dir));
                try {
                    FileTime time = Files.getLastModifiedTime(dir);
                    Files.setLastModifiedTime(newDir, time);
                } catch (IOException e) {
                    System.err.format("Unable to copy all attributes to %s: %s%n", newDir, e); // fileModifiedTime is one of attributes
                }
            }
            return FileVisitResult.CONTINUE;
        }
    }
}
