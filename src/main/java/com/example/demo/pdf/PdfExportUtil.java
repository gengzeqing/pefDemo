package com.example.demo.pdf;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.css.media.MediaDeviceDescription;
import com.itextpdf.html2pdf.css.media.MediaType;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.font.FontProvider;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

@Slf4j
public class PdfExportUtil {

    public PdfExportUtil() {
    }

    private static void exportToOutputStream(OutputStream outputStream, String content) {
        try {
            PdfWriter writer = new PdfWriter(outputStream, (new WriterProperties()).setFullCompressionMode(true));
            PdfDocument pdfDocument = new PdfDocument(writer);
            PageSize pageSize = (new PageSize(1095.0F, 1000.0F)).rotate();
            pdfDocument.setDefaultPageSize(pageSize);
            ConverterProperties converterProperties = new ConverterProperties();
            FontProvider fp = new FontProvider();
            fp.addStandardPdfFonts();
            byte[] fontByte = IOUtils.toByteArray(PdfExportUtils.class.getResource("/fonts/msyh.ttf").openStream());
            fp.addFont(fontByte);
            converterProperties.setFontProvider(fp);
            MediaDeviceDescription mediaDeviceDescription = new MediaDeviceDescription(MediaType.SCREEN);
            mediaDeviceDescription.setWidth(pageSize.getWidth());
            converterProperties.setMediaDeviceDescription(mediaDeviceDescription);
            HtmlConverter.convertToPdf(content, pdfDocument, converterProperties);
        } catch (IOException var9) {
            var9.printStackTrace();
        }

    }

    public static void exportToFileOutputStream(OutputStream outputStream, String content) {
        exportToOutputStream(outputStream, content);
    }

    public static void exportToFile(String filePath, String content) {
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(filePath);
            exportToOutputStream(outputStream, content);
            outputStream.flush();
        } catch (IOException var11) {
            var11.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception var12) {
                log.error(var12.getMessage(), var12);
                throw new RuntimeException(var12);
            }

        }

    }
}
