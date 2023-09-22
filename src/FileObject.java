import java.io.File;

public class FileObject {

    private long size; //

    private File file; //

    public FileObject(long size, File file) {
        this.size = size;
        this.file = file;
    }

    public File getFile() {
        return file;
    }

//    public void setFile(File file) {
//        this.file = file;
//    }

    public long getSize() {
        return size;
    }

//    public void setSize(long size) {
//        this.size = size;
//    }
}
