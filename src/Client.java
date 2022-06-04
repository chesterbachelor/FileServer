import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;

public class Client {
    public static void main(String args[]) {

        try {
            Socket socket = new Socket("localhost", 4422);
            FileOutputStream fos = new FileOutputStream("C:/image.jpg");
            BufferedOutputStream out = new BufferedOutputStream(fos);
            byte[] buffer = new byte[1024];
            int count;
            InputStream in = socket.getInputStream();
            while ((count = in.read(buffer)) > 0) {
                fos.write(buffer,0,count);
            }
            fos.close();
            socket.close();
        }catch (Exception e){
            System.out.println(e);
        }

    }
}