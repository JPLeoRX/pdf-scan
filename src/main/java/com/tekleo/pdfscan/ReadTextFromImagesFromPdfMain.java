package com.tekleo.pdfscan;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadTextFromImagesFromPdfMain {
    public static void main(String[] args) throws Exception {
        PDDocument document = PDDocument.load(new File("/Users/leo/Downloads/Sample YEs/2015 YE- Calgary.pdf"));
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        List<BufferedImage> images = new ArrayList<>(document.getNumberOfPages());
        for (int page = 0; page < document.getNumberOfPages(); ++page)
            images.add(pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB));
        document.close();

        Tesseract instance = new Tesseract();
        instance.setDatapath("/Users/leo/Downloads/tes4j_datasets");
        String text = instance.doOCR(images.get(0));
        System.out.println(text);
    }
}