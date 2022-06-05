package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

public class BufferExample {


    public static void main(String[] args) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(5);
        buf.put((byte) 'a');
        buf.put((byte) 'b');

        buf.flip();
        Path initialPath = Path.of(System.getProperty("user.home"));
        System.out.println(changePath(String.valueOf(initialPath), "Documents"));
        Path to = initialPath.resolve("text.txt");
        System.out.println(String.valueOf(Files.readString(to)));

    }

    public static Path changePath(String dir, String toDir) {
        Path path = Path.of(dir);
        return (path.resolve(toDir));

    }
}

