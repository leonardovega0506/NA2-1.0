package mx.com.upiicsa.na2.NA210.controller;

import lombok.extern.slf4j.Slf4j;
import mx.com.upiicsa.na2.NA210.model.entity.NominaTrabajadorModel;
import mx.com.upiicsa.na2.NA210.model.entity.TrabajadorModel;
import mx.com.upiicsa.na2.NA210.repository.INominaRepository;
import mx.com.upiicsa.na2.NA210.repository.ITrabajadorRepository;
import mx.com.upiicsa.na2.NA210.service.implementation.NominaServiceImpl;
import mx.com.upiicsa.na2.NA210.service.implementation.pdf.PdfServiceActaNominas;
import mx.com.upiicsa.na2.NA210.service.implementation.pdf.PdfServiceNomina;
import mx.com.upiicsa.na2.NA210.service.implementation.pdf.PdfServiceNominaById;
import mx.com.upiicsa.na2.NA210.service.interfaces.INominaService;
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
import java.util.Optional;

@RestController
@RequestMapping
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class NominaController {

    @Autowired
    private INominaService sNomina;

    @Autowired
    private PdfServiceNomina sPDFNomina;

    @Autowired
    private ITrabajadorRepository iTrabajadorRepository;

    @Autowired
    private PdfServiceNominaById sPdfDetalle;

    @Autowired
    private INominaRepository iNomina;

    @Autowired
    private PdfServiceActaNominas sPDFFechas;


    @GetMapping("/na2/trabajadores/{id_trabajador}/nominas/{id_nomina}")
    public ResponseEntity<?> obtenerNomina(@PathVariable(value = "id_trabajador") long id_trabajador, @PathVariable(value = "id_nomina") long id_nomina){
        return new ResponseEntity<>(sNomina.findNominaByID(id_trabajador,id_nomina), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR  hasRole('GERENTE')")
    @GetMapping("/na2/nominas/")
    public ResponseEntity<?> listarnominas(){
        return new ResponseEntity<>(sNomina.findAllNominas(),HttpStatus.OK);
    }

    //Falta version -trabajador
    @GetMapping("/na2/nomina/pdf/{id_trabajador}")
    public void downloadPdf(@PathVariable(value = "id_trabajador") Long id_trabajador,HttpServletResponse response){
        try{
            Path file = Paths.get(sPDFNomina.generatePlacesPdf(id_trabajador).getAbsolutePath());
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
    @GetMapping("/na2/nominas/trabajadores/{id}")
    public ResponseEntity<?> listarTrabajador(@PathVariable Long id){
        log.info("El id del trabajador {}" ,id);
        Optional<TrabajadorModel> oTrabajador = iTrabajadorRepository.findById(id);
        TrabajadorModel rabajador = oTrabajador.get();
        List<NominaTrabajadorModel> listanominas = sNomina.findAllNominasTrabajador(rabajador.getId());
        return new ResponseEntity<>(listanominas,HttpStatus.OK);
    }

    @GetMapping("/na2/nomina/pdf/{id_trabajador}/idNomina/{idNomina}")
    public void downloadNominaDetallePdf(@PathVariable(value = "id_trabajador") Long id_trabajador,@PathVariable(value = "idNomina") Long idNomina,HttpServletResponse response){
        try{
            Path file = Paths.get(sPdfDetalle.generatePlacesPdf(id_trabajador,idNomina).getAbsolutePath());
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

    @GetMapping("/na2/nomina/pdf/")
    public void downloadNominasFechaPdf(@RequestParam String fecha, HttpServletResponse response){
        try{
            LocalDate fechaString = LocalDate.parse(fecha);
            Path file = Paths.get(sPDFFechas.generatePlacesPdf(fechaString).getAbsolutePath());
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

    @GetMapping("/na2/nomina/fecha")
    public ResponseEntity<?> listarNominasByDate(@RequestParam String fecha){
        return new ResponseEntity<>(sNomina.findNominasByDate(LocalDate.parse(fecha)),HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN') OR  hasRole('GERENTE')")
    @PostMapping("/na2/trabajadores/{id_trabajador}/nominas/")
    public ResponseEntity<?> crearNomina(@PathVariable(value = "id_trabajador") long id_trabajador){
        return new ResponseEntity<>(sNomina.createNomina(id_trabajador),HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') OR  hasRole('GERENTE')")
    @PutMapping("/na2/trabajadores/{id_trabajador}/nominas")
    public ResponseEntity<?> actualizarNomina(@PathVariable(value = "id_trabajador") long id_trabajador, long id_nomina,@Valid @RequestBody NominaTrabajadorModel nominaDTO){
         sNomina.updateNomina(id_trabajador,nominaDTO);
        return new ResponseEntity<>("Actualizada", HttpStatus.NO_CONTENT);
    }
}