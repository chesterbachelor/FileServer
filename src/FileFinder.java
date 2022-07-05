import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileFinder {

    public static List<File> getFileLocations(String fileName, List<File> paths) {
        return paths.stream()
                .map(File::listFiles)
                .filter(Objects::nonNull)
                .flatMap(Arrays::stream)
                .filter(file -> file.getName().equalsIgnoreCase(fileName))
                .toList();
    }
}
