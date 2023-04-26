package mx.com.upiicsa.na2.NA210.controller;

import mx.com.upiicsa.na2.NA210.model.entity.RenunciaTrabajadorModel;
import mx.com.upiicsa.na2.NA210.model.entity.RetradoTrabajadorModel;
import mx.com.upiicsa.na2.NA210.service.interfaces.IRetardoTrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:4200")
public class RetardoController {

    @Autowired
    private IRetardoTrabajadorService sRetardo;


    //Falta version-trabajador
    @GetMapping("/na2/trabajadores/{id_trabajador}/retardos/")
    public ResponseEntity<?> listarRetardosTrabajador(@PathVariable(value = "id_trabajador") long id_trabajador){
        return new ResponseEntity<>(sRetardo.findRetardosTrabajadorID(id_trabajador),HttpStatus.OK);
    }


    @GetMapping("/na2/retardos/{id_retardo}")
    public ResponseEntity<?> obtenerRetardoByID(@PathVariable(value = "id_retardo") long id_retardo){
        return new ResponseEntity<>(sRetardo.findRetardoById(id_retardo), HttpStatus.OK);
    }


    @GetMapping("/na2/retardos/")
    public ResponseEntity<?> listarRetardosTrabajador(){
        return new ResponseEntity<>(sRetardo.findAllRetardos(),HttpStatus.OK);
    }


    @GetMapping("/na2/retardos/fecha")
    public ResponseEntity<?> listarRetardoDate(@RequestParam String fecha){
        return new ResponseEntity<>(sRetardo.findARetardosByDate(LocalDate.parse(fecha)),HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN') OR  hasRole('GERENTE')")
    @PostMapping("/na2/trabajadores/{id_trabajador}/retardos/")
    public ResponseEntity<?> generarRetardo(@PathVariable(value = "id_trabajador") long id_trabajador, @Valid @RequestBody RetradoTrabajadorModel retardoTrabajadorDTO){
        return new ResponseEntity<>(sRetardo.createRetardo(id_trabajador,retardoTrabajadorDTO),HttpStatus.CREATED);
    }
}
