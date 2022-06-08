import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class FileStreamer extends Thread {
    private final List<File> paths;
    private final int portNum;

    FileStreamer(List<File> paths, int portNum) {
        this.paths = paths;
        this.portNum = portNum;
    }

    public void run() {
        try {
            ServerSocket individualClientServer = new ServerSocket(portNum);
            Socket socket = individualClientServer.accept();
            String fileName = receiveFilePath(socket);

            List<File> locations = FileFinder.getFileLocations(fileName, paths);

            FileSearchResult result = fileSearching(socket, locations);
            if (result.fileStatus != FileSearchResult.Status.FILE_FOUND) {
                System.out.println("Error " + result.fileStatus + "\nProgram closing.");
                individualClientServer.close();
                return;
            }
            sendFile(socket, locations.get(0).getAbsolutePath());

            socket.close();
            individualClientServer.close();

        } catch (Exception ex) {
            System.out.println("Error");
        }
    }

    static void sendFile(Socket socket, String path) throws Exception {
        int count = 0;
        byte[] buffer = new byte[1024];
        OutputStream out = socket.getOutputStream();
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
        while ((count = in.read(buffer)) >= 0) {
            out.write(buffer, 0, count);
        }
        out.flush();
        in.close();
        out.close();
    }

    static FileSearchResult fileSearching(Socket socket, List<File> locations) throws Exception {
        FileSearchResult result = new FileSearchResult(locations);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(result);
        return result;
    }

    static String receiveFilePath(Socket socket) throws Exception {
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        return dis.readUTF();

    }

}


