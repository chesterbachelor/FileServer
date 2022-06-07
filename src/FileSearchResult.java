import java.io.File;
import java.io.Serializable;
import java.util.Formatter;
import java.util.List;

public class FileSearchResult implements Serializable {
    Status fileStatus;
    String fileLength;
    FileSearchResult(List<File> results) {

        if (results.isEmpty())
            fileStatus = Status.FILE_NOT_FOUND;
        if (results.size() > 1)
            fileStatus = Status.MANY_FILES_FOUND;
        if(results.size()==1) {
            fileStatus = Status.FILE_FOUND;
            File requestedFile = new File(results.get(0).getAbsolutePath());
            fileLength = new Formatter().format("The size of the selected file is: %d bytes\n", requestedFile.length()).toString();
        }
    }

    public enum Status {
        FILE_FOUND,
        FILE_NOT_FOUND,
        MANY_FILES_FOUND
    }

}
