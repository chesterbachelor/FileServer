import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        try {
            ServerSocket server_socket = new ServerSocket(4422);
            System.out.println("Created socket");
            File myFile = new File("C:\\image.jpg");

            Socket socket = server_socket.accept();
            System.out.println("Accepted socket");
            int count = 0;
            byte[] buffer = new byte[1024];

            OutputStream out = socket.getOutputStream();
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(myFile));
            while ((count = in.read(buffer)) > 0) {
                out.write(buffer, 0, count);
                out.flush();
            }
            System.out.println("Sent file");
            socket.close();
        } catch (Exception ex) {
            System.out.println("Error");
        }
    }
}
