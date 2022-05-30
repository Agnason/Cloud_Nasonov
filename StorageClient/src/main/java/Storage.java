import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Storage {
    private static File file;

    public Storage(File file) {
        this.file = new File("Storage/File/");
    }

    public static void main(String[] args) {

        Path path = Path.of("Storage/File");

        try (DirectoryStream<Path> files = Files.newDirectoryStream(path)) {
            for (Path p: files)
                System.out.println(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
