package mx.com.upiicsa.na2.NA210.service.implementation.pdf;

import mx.com.upiicsa.na2.NA210.model.entity.VacacionModel;
import mx.com.upiicsa.na2.NA210.service.interfaces.IVacacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.Optional;

@Service
public class PdfServiceRespuesta {
    private static final String PDF_RESOURCES = "/pdf-resources/";

    @Autowired
    private SpringTemplateEngine springTemplateEngine;


    @Autowired
    private IVacacionesService sVacaciones;


    public File generatePlacesPdf(Long idVacacion) throws Exception{
        Context context = getContextPlaceListPdf(idVacacion);
        String html = loadAndFillTemplate(context);
        String xhtml = convertToXhtml(html);
        return renderPlaceListPdf(xhtml);
    }
    private String convertToXhtml(String html) throws UnsupportedEncodingException {
        Tidy tidy = new Tidy();
        tidy.setXHTML(true);
        tidy.setIndentContent(true);
        tidy.setPrintBodyOnly(true);
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setSmartIndent(true);
        tidy.setShowWarnings(false);
        tidy.setQuiet(true);
        tidy.setTidyMark(false);

        Document htmlDOM = tidy.parseDOM(new ByteArrayInputStream(html.getBytes()), null);

        OutputStream out = new ByteArrayOutputStream();
        tidy.pprint(htmlDOM, out);
        return out.toString();
    }
    private File renderPlaceListPdf(String html) throws Exception {
        File file = File.createTempFile("respuesta", ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();
        return file;
    }
    private Context getContextPlaceListPdf(Long idVacacion) {
        Optional<VacacionModel> oVacacion = sVacaciones.findVacaciones(idVacacion);
        Context context = new Context();
        context.setVariable("vacacion",oVacacion.get() );
        return context;
    }
    private String loadAndFillTemplate(Context context) {
        return springTemplateEngine.process("respuestaSolicitudPDF", context);
    }
}
