package com.tekleo.pdfscan;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class ReadTextFromPdfMain {
    public static void main(String[] args) throws Exception {
        PdfReader reader = new PdfReader("/Users/leo/Downloads/Sample 2017 YE.pdf");
        String textFromPage = PdfTextExtractor.getTextFromPage(reader, 1);
        System.out.println(textFromPage);
        reader.close();
    }
}