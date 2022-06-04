import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String args[]) {
        try {
            Socket socket = new Socket("localhost", 4422);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the Msg : ");
            String msg = scanner.nextLine();

            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF(msg);

            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("C:\\image.jpg"));
            byte[] buffer = new byte[1024];
            int count;
            InputStream in = socket.getInputStream();
            while ((count = in.read(buffer)) >= 0) {
                out.write(buffer,0,count);
            }
            dataOutputStream.close();
            socket.close();
        }catch (Exception e){
            System.out.println(e);
        }

    }
}