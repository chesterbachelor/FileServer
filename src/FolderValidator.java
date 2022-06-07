import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderValidator {

    public static List<File> getValidPaths(String[] path) {
        List<File> validPaths = new ArrayList<>();
        for (String s : path) {
            File file = new File(s);
            if (!file.exists()){
                System.out.println("Selected path does not exits - " + s);
                continue;}
            if (!file.isDirectory()){
                System.out.println("Path you gave is not directory - " + s );
                continue;}
            validPaths.add(file);
        }
        return validPaths;
    }
}