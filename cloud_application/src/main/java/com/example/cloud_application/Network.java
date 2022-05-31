package com.example.cloud_application;

import java.io.*;
import java.net.Socket;

public class Network {
    private final int port;

    private DataInputStream is;
    private DataOutputStream os;


    public Network(int port) throws IOException {
        this.port = port;
        Socket clientToServer = new Socket("localhost", 8189);
        is = new DataInputStream(clientToServer.getInputStream());
        os = new DataOutputStream(clientToServer.getOutputStream());

    }

    public String readString() throws IOException {
        return is.readUTF();
    }

    public int readInt () throws IOException {
        return is.readInt();
    }
    public void writeMessage (String message) throws IOException {
        os.writeUTF(message);
        os.flush();
    }

    public DataInputStream getIs() {
        return is;
    }

    public DataOutputStream getOs() {
        return os;
    }
}
