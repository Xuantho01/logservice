package com.vpbank.logservice.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileUtils {

    private static File rootDirectory;
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    public static File getDirectory() {
        String folderName = UUID.randomUUID().toString();
        File directory = new File(rootDirectory + "/" + folderName);
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory;
    }

    public static void removeDirectory(File directory) {
        if (directory != null) {
            if (rootDirectory.getPath().equals(directory.getPath())) {
                LOGGER.error("Cannot remove root directory {}", directory.getAbsolutePath());
                return;
            }
            new Thread(() -> {
                try {
                    org.apache.commons.io.FileUtils.deleteDirectory(directory);
                    LOGGER.info("remove directory {}", directory.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }

    public static File convertMultipartFile2File(MultipartFile multiFile, File directory) throws IOException {
        String fileName = multiFile.getOriginalFilename();
        File file = new File(directory + "/" + fileName);
        try {
            multiFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            file.deleteOnExit();
            throw e;
        }
        return file;
    }

    public static File convertMultipartFile2File(MultipartFile multiFile, File directory, String fileName) throws IOException {
        File file = new File(directory + "/" + fileName);
        try {
            multiFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            file.deleteOnExit();
            throw e;
        }
        return file;
    }

    public static void setRootPath(String rootPath) {
        rootDirectory = new File(rootPath);
        try {
            if (!rootDirectory.exists()) {
                LOGGER.info("Folder {} not exist", rootPath);
                rootDirectory.mkdir();
                if (rootDirectory.exists()) {
                    LOGGER.info("Create folder success");
                } else {
                    LOGGER.info("Create folder failed ");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Create  folder {} error", rootPath);
            LOGGER.error("Create  folder {} error", e);
        }

    }


    public static List<String> getFileList(String folderPath) {
        File f = new File(folderPath);
        if (f.exists()) {
            return Arrays.asList(Objects.requireNonNull(f.list()));
        } else {
            return Collections.singletonList(folderPath + " not exists");
        }
    }
}
