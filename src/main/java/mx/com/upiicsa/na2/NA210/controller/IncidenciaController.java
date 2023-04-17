package mx.com.upiicsa.na2.NA210.controller;


import mx.com.upiicsa.na2.NA210.model.entity.IncidenciaModel;
import mx.com.upiicsa.na2.NA210.service.implementation.pdf.PdfServiceIncidencia;
import mx.com.upiicsa.na2.NA210.service.interfaces.IIncidenciaService;
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
import java.util.List;

@RestController
@RequestMapping("/na2/")
public class IncidenciaController {
    @Autowired
    private IIncidenciaService sIncidencia;

    @Autowired
    private PdfServiceIncidencia sPdfIncidencia;

    @GetMapping("/trabajadores/{id_trabajador}/incidencias")
    public ResponseEntity<?> listarIncidencias(@PathVariable(value = "id_trabajador") Long id_trabajador){
        return new ResponseEntity<>(sIncidencia.findAllInciencias(id_trabajador),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR  hasRole('GERENTE')")
    @GetMapping("/na2/trabajador/{id_trabajador}/incidencia/pdf/{id_incidencia}")
    public void downloadSolicutdPdf(@PathVariable Long id_trabajador,@PathVariable Long id_incidencia, HttpServletResponse response){
        try{
            Path file = Paths.get(sPdfIncidencia.generatePlacesPdf(id_trabajador,id_incidencia).getAbsolutePath());
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

    @GetMapping("trabajadores/{idTrabajador}/incidencias{id_incidencia}")
    public ResponseEntity<?> obtenerIncidencia(@PathVariable(value = "id_trabajador") Long id_trabajador, @PathVariable(value = "id_incidencia") long id_incidencia){
        return new ResponseEntity<>(sIncidencia.findIncidencia(id_trabajador,id_incidencia), HttpStatus.OK);
    }

    @PostMapping("{id_trabajador}")
    public ResponseEntity<?> registrarIncidencia(@PathVariable(value = "id_trabajador") Long id_trabajador, @RequestBody IncidenciaModel incidenciaDTO){
        return new ResponseEntity<>(sIncidencia.createIncidencia(id_trabajador,incidenciaDTO),HttpStatus.CREATED);
    }


}