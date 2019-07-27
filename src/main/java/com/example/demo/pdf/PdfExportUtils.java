package com.example.demo.pdf;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.impl.BlockCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.DefaultCssApplierFactory;
import com.itextpdf.html2pdf.css.media.MediaDeviceDescription;
import com.itextpdf.html2pdf.css.media.MediaType;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.IStylesContainer;
import com.itextpdf.io.font.otf.GlyphLine;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.splitting.ISplitCharacters;
import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class PdfExportUtils {

    public static class BlockPCssApplier extends BlockCssApplier {
        @Override
        public void apply(ProcessorContext context, IStylesContainer stylesContainer, ITagWorker tagWorker){
            Map<String, String> cssStyles = stylesContainer.getStyles();
            super.apply(context, stylesContainer,tagWorker);
            IPropertyContainer element = tagWorker.getElementResult();
            String align = cssStyles.get("text-align");
            if(element != null){
                if(align != null && align.equals("justify")){
                    element.setProperty(61, 0.75F);
                }
                String wordBreak = cssStyles.get("word-break");
                if(wordBreak != null && wordBreak.equals("normal")){
                    element.setProperty(62, new DefaultSplitCharacters());
                }
                /*if (cssStyles.get("font-weight") != null) {
                    element.setProperty(8, true);
                }*/
            }
        }
    }

    private static  class DefaultSplitCharacters implements ISplitCharacters {
        static List<Integer> charCodeList = Arrays.asList(12298/*《*/, 12299/*》*/, 12290/*。*/, 12289/*、*/);
        public DefaultSplitCharacters() {
        }
        @Override
        public boolean isSplitCharacter(GlyphLine text, int glyphPos) {
            if (!text.get(glyphPos).hasValidUnicode()) {
                return false;
            } else {
                boolean cur = isSplit(text.get(glyphPos).getUnicode());
                boolean next = isSplit(text.get(text.end - 1 == glyphPos ? glyphPos : glyphPos + 1).getUnicode());
//                System.out.print("\n" +  text.get(glyphPos)+ "---" + text.get(glyphPos).getUnicode() + "---" + cur);
                return cur && next;
            }
        }

        public boolean isSplit(int charCode){
            return !charCodeList.contains(charCode) && (charCode <= 32 || charCode == 45 || charCode == 8208 || charCode >= 8194 && charCode <= 8203 || charCode >= 11904 && charCode < 55200 || charCode >= 63744 && charCode < 64256 || charCode >= 65072 && charCode < 65104 || charCode >= 65377 && charCode < 65440);
        }
    }

    public PdfExportUtils() {
    }

    public static void exportToFile(String filePath, String content) {
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(filePath);
            PdfExportUtils.exportToOutputStream(outputStream, content);
            outputStream.flush();
        } catch (IOException var11) {
            var11.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception var12) {
                throw new RuntimeException(var12);
            }

        }

    }


    public static void exportToFileOutputStream(OutputStream outputStream, String content) {
        exportToOutputStream(outputStream, content);
    }

    private static void exportToOutputStream(OutputStream outputStream, String content) {
        try {
            PdfWriter writer = new PdfWriter(outputStream, (new WriterProperties()).setFullCompressionMode(true));
            PdfDocument pdfDocument = new PdfDocument(writer);
            PageSize pageSize = (PageSize.A4);
            pdfDocument.setDefaultPageSize(pageSize);
            ConverterProperties converterProperties = new ConverterProperties();
            FontProvider fp = new FontProvider();
            fp.addStandardPdfFonts();
            byte[] fontByte = IOUtils.toByteArray(PdfExportUtil.class.getResource("/fonts/msyh.ttf").openStream());
            fp.addFont(fontByte);
            converterProperties.setFontProvider(fp);
            MediaDeviceDescription mediaDeviceDescription = new MediaDeviceDescription(MediaType.SCREEN);
            mediaDeviceDescription.setWidth(pageSize.getWidth());
            converterProperties.setMediaDeviceDescription(mediaDeviceDescription);
            converterProperties.setCssApplierFactory(new DefaultCssApplierFactory() {
                @Override
                public ICssApplier getCustomCssApplier(IElementNode tag) {
                    if (tag.name().equals("p")) {
                        return new BlockPCssApplier();
                    }
                    return null;
                }
            });
            HtmlConverter.convertToPdf(content, pdfDocument, converterProperties);
        } catch (IOException var9) {
            var9.printStackTrace();
        }

    }




}