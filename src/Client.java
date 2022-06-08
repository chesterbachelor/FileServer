import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Client {
    public static void main(String args[]) {
        try {
            int portNum = connectingToReceivePortNumber();

            Socket socket = new Socket("localhost",portNum);
            System.out.println("Connected to port - " + socket.getLocalPort());
            System.out.println("Connected to port - " + socket.getPort());
            String fileName = readFileName();
            sendFileName(socket, fileName);

            FileSearchResult result = receiveFileSearchResult(socket);

            System.out.println(result.fileStatus);
            if (result.fileStatus != FileSearchResult.Status.FILE_FOUND) {
                System.exit(1);
            }
            System.out.println(result.fileLength);

            receiveAndSaveFile(socket,"D:\\" + fileName);

            socket.close();
        } catch (SocketException e) {
            System.out.println("Socket already taken");
        }catch (Exception exception){
            System.out.println(exception);
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
    static int connectingToReceivePortNumber() throws Exception
    {
        Socket socket1 = new Socket("localhost", 4422);
        DataInputStream dis = new DataInputStream(socket1.getInputStream());
        int portNum = dis.readInt();
        System.out.println(portNum);
        socket1.close();
        return portNum;

    }
}