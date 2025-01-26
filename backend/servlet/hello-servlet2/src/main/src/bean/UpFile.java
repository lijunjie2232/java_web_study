package bean;

import com.alibaba.fastjson2.annotation.JSONField;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpFile implements Serializable {
    private File file;
    private boolean dir;
    private long fileSize = 0;
    private long lastModified;

    public UpFile(File file) {
        this.file = file;
        this.dir = file.isDirectory();
        if (!this.dir)
            this.fileSize = file.length();
        this.lastModified = file.lastModified();
    }

    @JSONField(name = "modify")
    public long getLastModified() {
//        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(this.lastModified));
        return this.lastModified;
    }

    @JSONField(name = "name")
    public String getFile() {
        return file.getName();
    }

    public File fileObj() {
        return file;
    }

    public void renameFile(String filePath) {
        File newfile = new File(filePath);
        this.file.renameTo(newfile);
        this.file = newfile;
    }

    public void deleteFile() {
        this.file.delete();
    }

    public void copyFile(String filePath) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        File newFile = new File(filePath);
        if (this.dir) {
            newFile.mkdirs();
            File[] dirFiles = this.file.listFiles();
            for (File dirFile : dirFiles) {
                UpFile targetFile = this.getClass().getConstructor(File.class).newInstance(dirFile);
                targetFile.copyFile(new File(newFile, dirFile.getName()).getAbsolutePath());
            }
        } else
            try (InputStream input = Files.newInputStream(this.file.toPath())) {
                try (OutputStream output = new FileOutputStream(newFile, false)) {
                    byte[] cache = new byte[128];
                    for (int len = input.read(cache); len != -1; len = input.read(cache)) {
                        output.write(cache, 0, len);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @JSONField(name = "dir")
    public boolean getDir() {
        return dir;
    }

    @JSONField(name = "size")
    public long getFileSize() {
        return fileSize;
    }

    @Override
    public String toString() {
        return this.file.toString();
    }
}
