package com.crestech.common.api;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    public static String zipFolder(String sourceFolder, String zipFilePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(zipFilePath);
        ZipOutputStream zos = new ZipOutputStream(fos);
        File folder = new File(sourceFolder);
        zipFiles(folder, folder.getName(), zos);
        zos.close();
        fos.close();
        return zipFilePath;
    }

    private static void zipFiles(File folder, String parentFolder, ZipOutputStream zos) throws IOException {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                zipFiles(file, parentFolder + "/" + file.getName(), zos);
                continue;
            }
            FileInputStream fis = new FileInputStream(file);
            zos.putNextEntry(new ZipEntry(parentFolder + "/" + file.getName()));

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }

            zos.closeEntry();
            fis.close();
        }
    }
}
