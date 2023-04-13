package mx.com.upiicsa.na2.NA210.service.implementation.pdf;

import mx.com.upiicsa.na2.NA210.model.entity.IncidenciaModel;
import mx.com.upiicsa.na2.NA210.model.entity.VacacionModel;
import mx.com.upiicsa.na2.NA210.service.interfaces.IIncidenciaService;
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
public class PdfServiceIncidencia {
    private static final String PDF_RESOURCES = "/pdf-resources/";

    @Autowired
    private SpringTemplateEngine springTemplateEngine;


    @Autowired
    private IIncidenciaService sInidencia;


    public File generatePlacesPdf(Long idTrabajador,Long idIncidencia) throws Exception{
        Context context = getContextPlaceListPdf(idTrabajador,idIncidencia);
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
        File file = File.createTempFile("incidencia", ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();
        return file;
    }
    private Context getContextPlaceListPdf(Long idTrabajador,Long idIncidencia) {
        Optional<IncidenciaModel> oIncidencia = sInidencia.findIncidencia(idTrabajador,idIncidencia);
        Context context = new Context();
        context.setVariable("incidencia",oIncidencia.get() );
        return context;
    }
    private String loadAndFillTemplate(Context context) {
        return springTemplateEngine.process("IncidenciaPDF", context);
    }
}
