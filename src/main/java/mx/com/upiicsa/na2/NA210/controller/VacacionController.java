package mx.com.upiicsa.na2.NA210.controller;

import mx.com.upiicsa.na2.NA210.model.entity.VacacionModel;
import mx.com.upiicsa.na2.NA210.service.implementation.pdf.PdfServiceRespuesta;
import mx.com.upiicsa.na2.NA210.service.implementation.pdf.PdfServiceSolicitudVacaciones;
import mx.com.upiicsa.na2.NA210.service.interfaces.IVacacionesService;
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
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:4200")
public class VacacionController {

    @Autowired
    private IVacacionesService sVacaciones;

    @Autowired
    private PdfServiceSolicitudVacaciones sPdfVacacionesS;

    @Autowired
    private PdfServiceRespuesta sPdfRespuesta;

    //Falta version-trabajador
    @GetMapping("/na2/trabajadores/{id_trabajador}/vacaciones/")
    public ResponseEntity<?> listarVacacionesTrabajador(@PathVariable(value = "id_trabajador") long id_trabajador){
        return new ResponseEntity<>(sVacaciones.findAllVacacionesTrabajador(id_trabajador),HttpStatus.OK);
    }

    @GetMapping("/na2/trabajadores/{id_trabajador}/vacaciones/{id_vacaciones}")
    public ResponseEntity<?> obtenerVacacion(@PathVariable(value = "id_vacaciones") long id_vacaciones){
        return new ResponseEntity<>(sVacaciones.findVacaciones(id_vacaciones), HttpStatus.OK);
    }

    @GetMapping("/na2/vacaciones/")
    public ResponseEntity<?> listarVacaciones(){
        return new ResponseEntity<>(sVacaciones.findAllVacaciones(),HttpStatus.OK);
    }


    @GetMapping("/na2/vacacion/pdf/{idVacacion}")
    public void downloadSolicutdPdf(@PathVariable Long idVacacion, HttpServletResponse response){
        try{
            Path file = Paths.get(sPdfVacacionesS.generatePlacesPdf(idVacacion).getAbsolutePath());
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

    //Falta- version trabajador
    @GetMapping("/na2/vacacion/pdf/{idVacacion}/respuesta")
    public void downloadRespuestaSolicutdPdf(@PathVariable Long idVacacion, HttpServletResponse response){
        try{
            Path file = Paths.get(sPdfRespuesta.generatePlacesPdf(idVacacion).getAbsolutePath());
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

    @GetMapping("/na2/vacacion/fecha")
    public ResponseEntity<?> listarVacacionesFecha(@RequestParam String fecha){
        return new ResponseEntity<>(sVacaciones.findVacacionesByDate(LocalDate.parse(fecha)),HttpStatus.OK);
    }

    //Falta version trabajador
    @PostMapping("/na2/trabajadores/{id_trabajador}/vacaciones/")
    public ResponseEntity<?> generarVacaciones(@PathVariable(value = "id_trabajador") long id_trabajador, @Valid @RequestBody VacacionModel vacacionesDTO){
        return new ResponseEntity<>(sVacaciones.createVacacion(id_trabajador,vacacionesDTO),HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') OR  hasRole('GERENTE')")
    @PutMapping("/na2/vacaciones/{id_vacaciones}")
    public ResponseEntity<?> actualizarVacaciones(@Valid @RequestBody VacacionModel vacacionesDTO){
        sVacaciones.updateVacaciones(vacacionesDTO);
        return new ResponseEntity<>("vacacionActualizada",HttpStatus.NO_CONTENT);
    }

}