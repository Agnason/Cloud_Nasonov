import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class ChatHandler implements Runnable {
    //lesson01//
    private String serverDir = "storageServer/filesServer";
    //lesson01//
    private DataInputStream is;
    private DataOutputStream os;

    public ChatHandler(Socket socket) throws IOException {
        is = new DataInputStream(socket.getInputStream());
        os = new DataOutputStream(socket.getOutputStream());
        System.out.println("Client accepted");

        List<String> files = getFiles(serverDir);
        for (String file : files) {
            os.writeUTF(file);
        }
        os.flush();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = is.readUTF();
                System.out.println("received " + msg);
                os.writeUTF(msg);
                os.flush();
            }

        } catch (Exception e) {
            System.err.println("Connection was broken");
        }
    }

    //lesson01//
    private List<String> getFiles(String dir) {
        String[] list = new File(dir).list();
        assert list != null;
        return Arrays.asList(list);
    }
    //lesson01//
}
