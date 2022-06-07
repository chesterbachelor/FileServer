import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileFinder {

    public static File findFile(String fileName, File directory) {
        File[] list = directory.listFiles();

        for (File file : list)
            if (file.getName().equalsIgnoreCase(fileName))
                return file;
        return null;
    }
    public static List<File> getFileLocations(String fileName,List<File> paths) {

        List<File> locations = new ArrayList<>();
        for (File path : paths)
            if (FileFinder.findFile(fileName, path) != null)
                locations.add(new File(path + "\\" + fileName));
        return locations;
    }
}
