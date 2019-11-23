package com.tekleo.pdfscan;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BuildPdfFromImagesMain {
    private static final String PATH_IMAGES = "/Users/leo/Downloads/leo";
    private static final String PATH_OUT = "/Users/leo/Downloads/eva_project_sorted.pdf";

    public static void main(String[] args) throws Exception {
        // Find images and sort them
        List<String> imageFilePathList = Files.walk(Paths.get(PATH_IMAGES)).filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());
        imageFilePathList.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return prep(o1).compareTo(prep(o2));
            }

            private String prep(String original) {
                return original.replaceAll("Screenshot_", "").replaceAll("_Adobe Acrobat", "").replaceAll("_", "").replaceAll("-", "");
            }
        });

        // Debug images
        imageFilePathList.forEach(System.out::println);

        // Create new document
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(PATH_OUT));
        writer.open();
        document.open();

        // We will insert images to the table
        PdfPTable table = new PdfPTable(3);

        // For each row
        for (int row = 0; row < imageFilePathList.size() / 3; row++) {
            // And for each col
            for (int col = 0; col < 3; col++) {
                // Get image
                String path = imageFilePathList.get(row * (3 + col));
                Image image = Image.getInstance(path);

                // Wrap it into a table cell
                PdfPCell cell = new PdfPCell();
                cell.addElement(image);

                // Add cell to the table
                table.addCell(cell);
            }
        }

        // Add table to the document
        document.add(table);

        // Finish up
        document.close();
        writer.close();
    }
}
