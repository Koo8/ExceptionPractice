import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

/**
 * A file crawler - search fileName in the startDir path to see if it is there
 * Another approach is -
 * Instead of implementing FileVisitor we can choose to extend SimpleFileVisitor
 * and override only methods of our need.
 */

public class FileSearchAPI_FileVisitor implements FileVisitor<Path> {
    private String fileName;
    private Path startDir;
    private static int countNumOfDirB4FoundFile = 0;

    // Constructor
    public FileSearchAPI_FileVisitor(String fileName, Path startDir) {
        this.fileName = fileName;
        this.startDir = startDir;
    }

    // called when encounter a new dir in the file tree
    // NOTE: When doing recursive copy, do the dir copy in preVisitDirectory
    // and then do the files copy in visitFiles method.
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        // we could skip some specific directories here.
        // no discrimination in this program
        return CONTINUE;
    }

    // called when encounter a file -- main actions happen here -- check file attributes and compare
    // with our criteria for result
    // We can also from File Attributes to check file created time, last modified time
    // or last accessed times etc.
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        // get file name from the path
        String _fileName = file.getFileName().toString();
        // if found file, terminate the search, otherwise continue the search
        if (fileName.equals(_fileName)) {
            System.out.println("File found: " + file.toString());
            return TERMINATE;
        }
        return CONTINUE;
    }

    // called when a specific file is not accessible to the JVM by locked(sync)or permission issue
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("Can't access the file " + file.toString());
        return CONTINUE;
    }
    //called when a dir has been fully traversed
    // NOTE: When doing recursive delete of files and directory, do the directory
    //  delete in postVisitDirectory. This is to ensure that the directory is deleted
    // after all the files are deleted.
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        boolean doneSearchPath = Files.isSameFile(dir, startDir);
        countNumOfDirB4FoundFile++;
        // if doneSearchPath is false - means only finished a subdirectory, there are more subdir to traverse for fileName
        if (doneSearchPath) {
            // done the whole search but not found the fileName, otherwise the 2nd method has terminated the program
            System.out.println("File " + fileName + " not found in the path " + startDir.toString());
            return TERMINATE;
        }
        return CONTINUE;
    }

    public static void main(String[] args) throws IOException {
        String fileN = "Java Cook Book.pdf";
        Path path = Paths.get("C:\\Users\\NancyPC\\Desktop"/*\\pdf books"*/);
        FileSearchAPI_FileVisitor fileCrawler = new FileSearchAPI_FileVisitor(fileN, path);
        Files.walkFileTree(path, fileCrawler);
        System.out.println(countNumOfDirB4FoundFile + " dirs have been entered");
    }
}
