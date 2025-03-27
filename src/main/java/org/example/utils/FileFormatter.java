package org.example.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileFormatter {

    public static String downloadPdf(String url) {
        try {
            System.out.println("Conectando ao site...");
            Document document = Jsoup.connect(url).get();

            File downloadDirectory = new File("downloads");
            if (!downloadDirectory.exists()) {
                downloadDirectory.mkdir();
            }

            System.out.println("Analisando links disponiveis...");
            for (Element link : document.select("a[href]")) {
                String linkHref = link.attr("href");
                String linkText = link.text();

                if (linkText.contains("Anexo I") && linkHref.endsWith(".pdf")) {
                    String baseUrl = "https://www.gov.br";
                    String fullUrl = linkHref.startsWith("/") ? baseUrl + linkHref : linkHref;
                    String outputPath = System.getProperty("user.home") + "/Downloads/" + extractFileName(linkHref);

                    return downloadFile(fullUrl, outputPath);
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao acessar website: " + e.getMessage());
        }

        return null;
    }

    private static String extractFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    private static String downloadFile(String fileUrl, String outputPath) {
        try {
            System.out.println("Tentando baixar arquivo: " + fileUrl);
            URL urlToDownload = new URL(fileUrl);
            File pdfFile = new File(outputPath);
            Files.copy(urlToDownload.openStream(), pdfFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Arquivo baixado com sucesso: " + pdfFile.getAbsolutePath());
            return pdfFile.getAbsolutePath(); // Return the path of the downloaded PDF
        } catch (IOException e) {
            System.out.println("Erro ao baixar arquivo: " + fileUrl + " - Mensagem: " + e.getMessage());
        }

        return null;
    }
}
