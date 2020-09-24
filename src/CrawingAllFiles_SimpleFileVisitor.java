import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CrawingAllFiles_SimpleFileVisitor extends SimpleFileVisitor<Path> {

    private Path myPath;
    static private int regFileCounter =0;
    static private int symboFileCounter = 0;
    CrawingAllFiles_SimpleFileVisitor(Path thePath) {
        myPath = thePath;
    }
    // do nothing
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }
    // check file attrs.
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if(attrs.isRegularFile()) regFileCounter++;
        if(attrs.isSymbolicLink()) symboFileCounter++;
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        if (myPath.equals(dir) ) {
            System.out.println("regular files are " + regFileCounter +"\nSymbolic links are " + symboFileCounter);
            return FileVisitResult.TERMINATE;
        }
        return FileVisitResult.CONTINUE;

    }

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("C:\\Users\\NancyPC\\Desktop\\pdf books");
        CrawingAllFiles_SimpleFileVisitor crawler = new CrawingAllFiles_SimpleFileVisitor(path);
        Files.walkFileTree(path,crawler);
    }
}
