import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**Implement my own FileVisitor that will count size of each directory
 * in specified path. Files.size(dir) won’t return true directory size,
 * but only size of its file descriptor.
 * To measure real size of dir, we have to walk directory tree and
 * sum sizes of all its members 
 */

public class CountDirSize_WalkFileTree {

    public static void main(String[] args) throws IOException {

        //compare two different results of file size - native Files.size() vs. Files.walkFielTree() -- which needs a FileVisitor param
        Path path = Paths.get("C:\\Users\\NancyPC\\Desktop\\icon folder");
        calculateSizeNatively(path);
        walkFileTreeForSize(path);
    }

    private static void walkFileTreeForSize(Path path) throws IOException {
        FileSizeCalculator calculator = new FileSizeCalculator();
        Files.walkFileTree(path,calculator);
    }

    private static void calculateSizeNatively(Path path) throws IOException {
        long fileSizeNative =   Files.size(path); // use this method for dir size
        System.out.println("Native count file size is " + fileSizeNative);
    }

    // inner class extends SimpleFileVisitor<Path> class to calculate file size
    public static class FileSizeCalculator extends SimpleFileVisitor<Path>  {
        // override 3 of 4 methods from FileVisitor class
        private long currentDirSize;
        private Queue<Long> sizeOfParents = Collections.asLifoQueue(new LinkedList<>());
        private int counterDir = 0;
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
           // b4 each directory entry, add the currentDirSize to the queue
            counterDir++;

            System.out.println("PRE - current Dir Size " + currentDirSize + " at counterDir of " + counterDir + " directory name is " + dir.getFileName().toString() );

            sizeOfParents.add(currentDirSize);  // add the newly calculated currentDirSize value to the queue, LIFO operation

            System.out.printf("PRE - sizeOfParent has %d elements%n", sizeOfParents.size() );
            // reset currentDirSize for a new dir
            currentDirSize = 0;
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            // for each file within the subDir,
            // add up currentDirSize thru all files in the dir
            currentDirSize += attrs.size();
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            // once a subdir has been traversed, show currentDirSize result
            System.out.println("POST - The current directory size b4 add remove is " + currentDirSize);
            long removeResult = sizeOfParents.remove();
            currentDirSize += removeResult;   // add previously saved long number to the currentDirSize, LIFO operation.
            System.out.println("removeResult is  "+ removeResult);
            System.out.println("POST - The current directory size2 is " + currentDirSize);

            return FileVisitResult.CONTINUE;

        }
    }
}
