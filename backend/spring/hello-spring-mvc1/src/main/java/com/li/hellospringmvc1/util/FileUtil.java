package com.li.hellospringmvc1.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class FileUtil {

    // static method for write MultipartFile file to tmp dir
    public static void multipartFileWriter(MultipartFile file, File tmpDir, boolean overwrite) throws IOException {
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }
        File tmpFile = new File(tmpDir, Objects.requireNonNull(file.getOriginalFilename()));
        if (!tmpFile.exists() || overwrite) {
            file.transferTo(tmpFile);
        }
    }
}
