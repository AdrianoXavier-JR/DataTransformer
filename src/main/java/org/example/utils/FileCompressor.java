package org.example.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileCompressor {

    public static void compressFilesToZip(String zipFilePath, String[] filesToCompress) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(zipFilePath);
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {

            for (String filePath : filesToCompress) {
                File file = new File(filePath);

                if (file.exists() && file.isFile()) {
                    addFileToZip(file, zipOutputStream);
                } else {
                    System.out.println("Arquivo não encontrado: " + filePath);
                }
            }
            System.out.println("Arquivo CSV compactado com sucesso: " + zipFilePath);

        } catch (IOException e) {
            System.out.println("Erro para compactar arquivo: " + e.getMessage());
        }
    }

    private static void addFileToZip(File file, ZipOutputStream zipOutputStream) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zipOutputStream.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fileInputStream.read(buffer)) >= 0) {
                zipOutputStream.write(buffer, 0, length);
            }
        }
    }
}
