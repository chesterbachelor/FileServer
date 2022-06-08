import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Directory not chosen");
            System.exit(1);
        }
        List<File> paths = FolderValidator.getValidPaths(args);
        if (paths.isEmpty()) {
            System.out.println("All selected paths are incorrect, finishing.");
            System.exit(1);
        }
        try {
            int portNum = 5000;
            ServerSocket server_socket = new ServerSocket(4422);
            while (true) {
                System.out.println("Awaiting connection");
                Socket socket = server_socket.accept();
                new FileStreamer(paths, portNum).start();
                sendPortNumberToClient(socket, portNum++);
                if (portNum > 5100) portNum = 5000;
                socket.close();
            }

        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }

    static void sendPortNumberToClient(Socket socket, int portNum) throws Exception {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeInt(portNum);
        System.out.println("Client connected to port " + portNum);
    }
}

