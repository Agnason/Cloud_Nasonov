package nio;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class NioServer {
    private String serverDir;
    private ServerSocketChannel server;
    private Selector selector;
    private String file;

    public NioServer() throws IOException {
        server = ServerSocketChannel.open();
        selector = Selector.open();
        server.bind(new InetSocketAddress(8189));
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void start() throws IOException {
        while (server.isOpen()) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    handleAccept();
                }
                if (key.isReadable()) {
                    handleRead(key);
                }

                iterator.remove();
            }

        }
    }

    private void handleRead(SelectionKey key) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(1024);
        SocketChannel channel = (SocketChannel) key.channel();
        StringBuilder s = new StringBuilder();
        while (channel.isOpen()) {
            int read = channel.read(buf);
            if (read < 0) {
                channel.close();
                return;
            }
            if (read == 0) {
                break;
            }
            buf.flip();
            while (buf.hasRemaining()) {
                s.append((char) buf.get());
            }
            buf.clear();

        }
        s.append("-> ");

        byte[] message = s.toString().getBytes(StandardCharsets.UTF_8);
// команда -ls- в виде байтового массива
        byte[] ls = new byte[]{108, 115, 13, 10, 45, 62, 32};

        String command = String.valueOf(s);
        System.out.println(s);

        byte[] cat = new byte[]{99, 97, 116, 13, 10, 45, 62, 32};
        // [99, 100, 13, 10, 45, 62, 32] - cd

        if (Arrays.equals(message, ls)) {
            sendListOfFiles(System.getProperty("user.home"), channel);
        }
        if (command.startsWith("cat")) {
            String[] token = command.split(" ", 2);
            file = token[1];
            Path init = Path.of("C:/Users/anasonov/");
            Path ini2 = init.resolve(token[1]);
            byte[] bytes = Files.readAllBytes(init);
            channel.write(ByteBuffer.wrap((bytes)));
        }
        if (command.startsWith("cd")) {
            Path initialPath = Path.of(System.getProperty("user.home"));
            String[] token = command.split(" ", 2);
            Path qqq = initialPath.resolve(token[1]);
            channel.write(ByteBuffer.wrap((String.valueOf(qqq) + "\r\n").getBytes(StandardCharsets.UTF_8)));
        } else {
            channel.write(ByteBuffer.wrap(message));
        }
    }
//        for (SelectionKey selectedKey : selector.keys()) {
//
//            if (selectedKey.isValid() && selectedKey.channel() instanceof SocketChannel sc) {
//                sc.write(ByteBuffer.wrap(message));
//            }
//
//        }



    private void handleAccept() throws IOException {
        SocketChannel channel = server.accept();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        channel.write(ByteBuffer.wrap("Welcome in Mike terminal!\r\n".getBytes(StandardCharsets.UTF_8)));

    }

    private List<String> getFiles(String dir) {
        String[] list = new File(dir).list();
        assert list != null;
        return Arrays.asList(list);
    }

    private void sendListOfFiles(String dir, SocketChannel channel) throws IOException {
        Path listOfFiles = Path.of(dir);
        channel.write(ByteBuffer.wrap((("Содержимое папки: " + listOfFiles + "\r\n").getBytes(StandardCharsets.UTF_8))));
        List<String> files = getFiles(dir);
        StringBuilder ls = new StringBuilder("\r\n");
        for (String file : files) {
            ls.append(file).append("\r\n");
        }
        ls.append("-> ");
        byte[] bytes = ls.toString().getBytes(StandardCharsets.UTF_8);
        channel.write(ByteBuffer.wrap(bytes));
    }

    public Path changePath(String dir, String toDir) {
        Path path = Path.of(dir);
        return (path.resolve(toDir));
    }
}
