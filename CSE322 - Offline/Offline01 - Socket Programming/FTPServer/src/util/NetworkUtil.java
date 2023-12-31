package util;

import server._CONFIG_;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class NetworkUtil {
    private final Socket socket;
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;

    public NetworkUtil(String s, int port) throws Exception {
        this.socket = new Socket(s, port);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public NetworkUtil(Socket s) throws IOException {
        this.socket = s;
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public Object read() throws IOException, ClassNotFoundException {
        return ois.readUnshared();
    }

    public void setTimeout() throws SocketException {
        socket.setSoTimeout(_CONFIG_.SOCKET_TIMEOUT);
    }

    public void resetTimeout() throws SocketException {
        socket.setSoTimeout(0);
    }

    public void write(Object o) throws IOException {
        oos.writeUnshared(o);
        oos.reset();    // Clear the cache
    }

    public void closeConnection() throws IOException {
        ois.close();
        oos.close();
    }
}
