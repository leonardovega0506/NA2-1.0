package mx.com.upiicsa.na2.NA210.controller;

import mx.com.upiicsa.na2.NA210.model.entity.RenunciaTrabajadorModel;
import mx.com.upiicsa.na2.NA210.service.implementation.pdf.PdfServiceRenuncia;
import mx.com.upiicsa.na2.NA210.service.implementation.pdf.PdfServiceRenunciaRespuesta;
import mx.com.upiicsa.na2.NA210.service.interfaces.IRenunciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/na2/renuncia/")
public class RenunciaController {

    @Autowired
    private IRenunciaService renunciaService;

    @Autowired
    private PdfServiceRenuncia sPdfRenuncia;

    @Autowired
    private PdfServiceRenunciaRespuesta sPdfRespuesta;

    @GetMapping("{id_trabajador}")
    public ResponseEntity<?> traerRenuncia(@PathVariable(value = "id_trabajador") long id_trabajador){
        return new ResponseEntity<>(renunciaService.findRenuncia(id_trabajador), HttpStatus.OK);
    }


    @GetMapping("/na2/renuncia/pdf/{idRenuncia}")
    public void downloadSolicutdPdf(@PathVariable Long idRenuncia, HttpServletResponse response){
        try{
            Path file = Paths.get(sPdfRenuncia.generatePlacesPdf(idRenuncia).getAbsolutePath());
            if (Files.exists(file)){
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition", "attachment; filename"+ file.getFileName());
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @GetMapping("/na2/renuncia/pdf/{idRenuncia}/respuesta")
    public void downloadRespuestaPdf(@PathVariable Long idRenuncia, HttpServletResponse response){
        try{
            Path file = Paths.get(sPdfRespuesta.generatePlacesPdf(idRenuncia).getAbsolutePath());
            if (Files.exists(file)){
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition", "attachment; filename"+ file.getFileName());
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("{id_trabajador}")
    public ResponseEntity<?> crearRenuncia(@PathVariable Long id_trabajador, @RequestBody RenunciaTrabajadorModel renunciaDTO){
        return new ResponseEntity<>(renunciaService.createRenuncia(id_trabajador,renunciaDTO),HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN') OR  hasRole('GERENTE')")
    @PutMapping("{id_renuncia}")
    public ResponseEntity<?> actualizarRenuncia(@PathVariable(value = "id_trabajador") long id_trabajador,@PathVariable(value = "id_renuncia") long id_Renuncia, @Valid @RequestBody RenunciaTrabajadorModel renunciaDTO){
        renunciaService.updateRenuncia(id_trabajador,renunciaDTO);
        return new ResponseEntity<>("Actualizada", HttpStatus.NO_CONTENT);
    }
}
