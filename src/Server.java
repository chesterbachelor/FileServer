import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Directory not chosen");
            System.exit(1);
        }
        List<File> paths = FolderValidator.getValidPaths(args);
        if(paths.isEmpty()){
            System.out.println("All selected paths are incorrect, finishing.");
            System.exit(1);}


        try {
            ServerSocket server_socket = new ServerSocket(4422);
            System.out.println("Created socket");

            Socket socket = server_socket.accept();
            System.out.println("Accepted socket");

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String fileName= dis.readUTF();

            List<File> locations = FileFinder.getFileLocations(fileName,paths);

            FileSearchResult result = new FileSearchResult(locations);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(result);

            if(result.fileStatus!= FileSearchResult.Status.FILE_FOUND) {
                System.out.println("Error " + result.fileStatus + "\nProgram closing.");
                System.exit(1);
            }


            int count = 0;
            byte[] buffer = new byte[1024];
            OutputStream out = socket.getOutputStream();
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(locations.get(0)));
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
