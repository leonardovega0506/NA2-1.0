package mx.com.upiicsa.na2.NA210.controller;

import mx.com.upiicsa.na2.NA210.model.entity.RenunciaTrabajadorModel;
import mx.com.upiicsa.na2.NA210.model.entity.TrabajadorModel;
import mx.com.upiicsa.na2.NA210.repository.IRenunciaRepository;
import mx.com.upiicsa.na2.NA210.service.implementation.pdf.PdfServiceRenuncia;
import mx.com.upiicsa.na2.NA210.service.implementation.pdf.PdfServiceRenunciaRespuesta;
import mx.com.upiicsa.na2.NA210.service.interfaces.IRenunciaService;
import mx.com.upiicsa.na2.NA210.service.interfaces.ITrabajadorService;
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
import java.util.Optional;

@RestController
@RequestMapping("/na2/renuncia/")
@CrossOrigin(origins = "http://localhost:4200")
public class RenunciaController {

    @Autowired
    private IRenunciaService renunciaService;

    @Autowired
    private IRenunciaRepository iRenuncia;
    @Autowired
    private PdfServiceRenuncia sPdfRenuncia;

    @Autowired
    private ITrabajadorService sTrabajador;

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

    @PutMapping("{id_trabajador}")
    public ResponseEntity<?> actualizarRenuncia(@PathVariable Long id_trabajador, @RequestBody RenunciaTrabajadorModel renunciaTrabajadorModel){
        renunciaService.updateRenuncia(id_trabajador,renunciaTrabajadorModel);
        return new ResponseEntity<>("Actualizado",HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN') OR  hasRole('GERENTE')")
    @PutMapping("aceptar/{idRenuncia}")
    public ResponseEntity<?> aceptarRenuncia(@PathVariable Long idRenuncia){
        Optional<RenunciaTrabajadorModel> oRenuncia = iRenuncia.findById(idRenuncia);
        RenunciaTrabajadorModel renunciaTrabajadorModel = oRenuncia.get();
        renunciaTrabajadorModel.setEstatusRenuncia("Aceptada");
        Optional<TrabajadorModel> oTrabajador = sTrabajador.obtenerTrabajadorById(renunciaTrabajadorModel.getTrabajadorModel().getId());
        TrabajadorModel trabajadorModel = oTrabajador.get();
        trabajadorModel.setEstatus("Despedido");
        iRenuncia.save(renunciaTrabajadorModel);
        sTrabajador.actualizarTrabajador(trabajadorModel);
        return new ResponseEntity<>("Renuncia Aceptada",HttpStatus.OK);
    }
}
