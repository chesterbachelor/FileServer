import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String args[]) {
        try {
            Socket socket = new Socket("localhost", 4422);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter file name : ");
            String msg = scanner.nextLine();

            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF(msg);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            FileSearchResult result = (FileSearchResult) ois.readObject();

            System.out.println(result.fileStatus);
            if(result.fileStatus!= FileSearchResult.Status.FILE_FOUND){
                System.exit(1);}

            System.out.println(result.fileLength);

            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("D:\\image132.jpg"));
            byte[] buffer = new byte[1024];
            int count;
            InputStream in = socket.getInputStream();
            while ((count = in.read(buffer)) >= 0) {
                out.write(buffer,0,count);
                out.flush();
            }
            System.out.println("File Received");
            in.close();
            out.close();
            ois.close();
            dataOutputStream.close();
            socket.close();
        }catch (Exception e){
            System.out.println(e);
        }

    }
}