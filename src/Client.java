import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String args[]) {
        try {
            Socket socket = new Socket("localhost", 4422);
            String fileName = readFileName();
            sendFileName(socket, fileName);

            FileSearchResult result = receiveFileSearchResult(socket);

            System.out.println(result.fileStatus);
            if (result.fileStatus != FileSearchResult.Status.FILE_FOUND) {
                System.exit(1);
            }
            System.out.println(result.fileLength);

            receiveAndSaveFile(socket,"D:\\image11.jpg");

            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private static FileSearchResult receiveFileSearchResult(Socket socket) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        FileSearchResult result = (FileSearchResult) ois.readObject();

        return result;
    }

    private static String readFileName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter file name : ");
        String msg = scanner.nextLine();

        scanner.close();
        return msg;
    }

    static void receiveAndSaveFile(Socket socket,String outputFilePath) throws Exception {
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outputFilePath));
        byte[] buffer = new byte[1024];
        int count;
        InputStream in = socket.getInputStream();
        while ((count = in.read(buffer)) >= 0) {
            out.write(buffer, 0, count);
        }
        out.flush();
        System.out.println("File Received");

        out.close();
        in.close();
    }

    static void sendFileName(Socket socket, String fileName) throws Exception {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(fileName);
    }
}