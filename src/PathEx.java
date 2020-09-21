import java.nio.file.Path;
import java.nio.file.Paths;

public class PathEx {
    public static void main(String[] args) {
           new PathEx().createPath("..");
    }

    public void createPath(String path) {
        Path newPath = Paths.get(path);
        System.out.println("Path is created: " + newPath.toString());
        System.out.println("Absolute path is " + newPath.toAbsolutePath());
        System.out.println("getFileName " + newPath.getFileName());
        System.out.println("getNameCount "+ newPath.getNameCount());
        System.out.println("getRoot " + newPath.getRoot());
        System.out.println("PathToFile" + newPath.toFile());
    }
}
