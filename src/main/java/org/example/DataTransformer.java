package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.example.utils.FileCompressor;
import org.example.utils.FileFormatter;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataTransformer {
    public static void main(String[] args) throws Exception {
        String url = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";
        String pdfPath = FileFormatter.downloadPdf(url);

        if (pdfPath != null) {
            processPdf(pdfPath);
        } else {
            System.out.println("Não foi possivel fazer o download.");
        }
    }

    private static void processPdf(String pdfPath) throws IOException {
        System.out.println("Compactando arquivo... ");
        File pdfFile = new File(pdfPath);
        try (FileInputStream fileInputStream = new FileInputStream(pdfFile); PDDocument document = PDDocument.load(fileInputStream)) {
            String pdfText = extractTextFromPDF(document);
            Map<String, String> abbreviationsMap = extractAbbreviations(pdfText);

            List<String> csvFilePaths = extractDataAndGenerateCsv(document, abbreviationsMap);

            compressFiles(csvFilePaths);
            deleteFile(csvFilePaths.get(0));
        }
    }

    private static String extractTextFromPDF(PDDocument document) throws IOException {
        PDFTextStripper stripper = new PDFTextStripper();
        return stripper.getText(document);
    }

    private static Map<String, String> extractAbbreviations(String pdfText) {
        Map<String, String> abbreviationsMap = new HashMap<>();

        Pattern pattern = Pattern.compile("Legenda[\\s\\S]*?(OD):\\s*([\\w\\s.]+)[\\s\\S]*?(AMB):\\s*([\\w\\s.]+)");
        Matcher matcher = pattern.matcher(pdfText);

        if (matcher.find()) {
            abbreviationsMap.put("OD", cleanText(matcher.group(2)));
            abbreviationsMap.put("AMB", cleanText(matcher.group(4)));
        }

        return abbreviationsMap;
    }

    private static String cleanText(String text) {
        return text.trim().replace("\r", " ").replace("\n", " ").replace("\u00A0", " ");
    }

    private static List<String> extractDataAndGenerateCsv(PDDocument document, Map<String, String> abbreviationsMap) throws IOException {
        SpreadsheetExtractionAlgorithm extractorAlgorithm = new SpreadsheetExtractionAlgorithm();
        ObjectExtractor objectExtractor = new ObjectExtractor(document);
        PageIterator pageIterator = objectExtractor.extract();

        String csvFilePath = System.getProperty("user.home") + "/Downloads/extracted_file.csv";
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFilePath), StandardCharsets.UTF_8))) {

            while (pageIterator.hasNext()) {
                Page page = pageIterator.next();
                List<Table> tables = extractorAlgorithm.extract(page);

                for (Table table : tables) {
                    for (List<RectangularTextContainer> row : table.getRows()) {
                        StringBuilder rowData = new StringBuilder();

                        for (RectangularTextContainer cell : row) {
                            String cellText = cleanText(cell.getText());
                            for (Map.Entry<String, String> entry : abbreviationsMap.entrySet()) {
                                if (cellText.contains(entry.getKey())) {
                                    cellText = cellText.replace(entry.getKey(), entry.getValue()).trim();
                                }
                            }
                            rowData.append(cellText).append(",");
                        }

                        if (rowData.length() > 0) {
                            rowData.setLength(rowData.length() - 1);
                            writer.write(rowData.toString());
                            writer.newLine();
                        }
                    }
                }
            }
        }

        return Collections.singletonList(csvFilePath);
    }

    private static void compressFiles(List<String> filesToCompress) {
        if (!filesToCompress.isEmpty()) {
            String zipFilePath = System.getProperty("user.home") + "/Downloads/Teste_Adriano_Xavier.zip";
            FileCompressor.compressFilesToZip(zipFilePath, filesToCompress.toArray(new String[0]));
        }
    }

    private static void deleteFile(String filePath) {
        File fileToDelete = new File(filePath);
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }
}
