package mx.com.upiicsa.na2.NA210.controller;

import mx.com.upiicsa.na2.NA210.model.entity.QuejasAclaracionesModel;
import mx.com.upiicsa.na2.NA210.service.interfaces.IQuejaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping()
@CrossOrigin(origins = "http://localhost:4200")
public class QuejasController {
    @Autowired
    private IQuejaService sQueja;

    //Falta- version trabajador
    @GetMapping("/na2/trabajadores/{id_trabajador}/quejas/")
    public ResponseEntity<?> listarQuejasTrabajador(@PathVariable(value = "id_trabajador") long id_trabajador){
        return new ResponseEntity<>(sQueja.findAllQuejasTrabajador(id_trabajador),HttpStatus.OK);
    }


    //Ya está
    @GetMapping("/na2/quejas/")
    public ResponseEntity<?> listarQuejas(){
        return new ResponseEntity<>(sQueja.findAllQuejas(),HttpStatus.OK);
    }
    @GetMapping("/na2/quejas/{id_queja}")
    public ResponseEntity<?> obtenerQuejaByID(@PathVariable(value = "id_queja") long id_queja){
        return new ResponseEntity<>(sQueja.findQueja(id_queja), HttpStatus.OK);
    }

    //Ya está
    @GetMapping("/na2/quejas/fecha")
    public ResponseEntity<?> listarQuejasFecha(@RequestParam String fecha){
        return new ResponseEntity<>(sQueja.findQuejasDate(LocalDate.parse(fecha)),HttpStatus.OK);
    }

    //Ya está
    @PostMapping("/na2/trabajadores/{id_trabajador}/quejas/")
    public ResponseEntity<?> crearQueja(@PathVariable(value = "id_trabajador") long id_trabajador, @RequestBody QuejasAclaracionesModel quejasDTO){
        return new ResponseEntity<>(sQueja.createQueja(id_trabajador,quejasDTO),HttpStatus.CREATED);
    }

    //Ya está
    @PreAuthorize("hasRole('ADMIN') OR  hasRole('GERENTE')")
    @PutMapping("/na2/trabajadores/{id_trabajador}/quejas")
    public ResponseEntity<?> actualizarQueja(@PathVariable(value = "id_trabajador") long id_trabajador,@Valid @RequestBody QuejasAclaracionesModel quejasDTO){
         sQueja.updateQueja(id_trabajador,quejasDTO);
        return new ResponseEntity<>("Actualizada",HttpStatus.NO_CONTENT);
    }

    //Ya está
    @PreAuthorize("hasRole('ADMIN') OR  hasRole('GERENTE')")
    @DeleteMapping("/na2/trabajadores/{id_trabajador}/quejas/{id_queja}")
    public ResponseEntity<?> eliminarQueja(@PathVariable(value = "id_queja") long id_queja){
        sQueja.eliminarQueja(id_queja);
        return new ResponseEntity<>("Queja eliminada Correctamente",HttpStatus.NO_CONTENT);
    }
}