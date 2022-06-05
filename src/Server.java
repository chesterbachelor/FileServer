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
        List<String> paths = FolderValidator.getValidPaths(args);

        try {
            ServerSocket server_socket = new ServerSocket(4422);
            System.out.println("Created socket");

            Socket socket = server_socket.accept();
            System.out.println("Accepted socket");

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String filePath = dis.readUTF();
                        
            int count = 0;
            byte[] buffer = new byte[1024];
            OutputStream out = socket.getOutputStream();
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath));
            while ((count = in.read(buffer)) >= 0) {
                out.write(buffer, 0, count);
                out.flush();
            }
            System.out.println("Sent file");
            socket.close();
            dis.close();
            in.close();
            out.close();

        } catch (Exception ex) {
            System.out.println("Error");
        }
    }
}
