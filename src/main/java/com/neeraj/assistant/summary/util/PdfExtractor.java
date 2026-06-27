package com.neeraj.assistant.summary.util;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import com.neeraj.assistant.summary.exception.PdfExtractionException;

@Component
public class PdfExtractor {

    public String extractText(File file) {

        try (PDDocument document = Loader.loadPDF(file)) {

            PDFTextStripper stripper = new PDFTextStripper();

            return stripper.getText(document);

        } catch (IOException e) {

            throw new PdfExtractionException(
                    "Failed to extract text from PDF", e);

        }
    }
}