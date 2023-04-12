package mx.com.upiicsa.na2.NA210.controller;

import mx.com.upiicsa.na2.NA210.model.entity.RenunciaTrabajadorModel;
import mx.com.upiicsa.na2.NA210.model.entity.RetradoTrabajadorModel;
import mx.com.upiicsa.na2.NA210.service.interfaces.IRetardoTrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
public class RetardoController {

    @Autowired
    private IRetardoTrabajadorService sRetardo;

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

    @PostMapping("/na2/trabajadores/{id_trabajador}/retardos/")
    public ResponseEntity<?> generarRetardo(@PathVariable(value = "id_trabajador") long id_trabajador, @Valid @RequestBody RetradoTrabajadorModel retardoTrabajadorDTO){
        return new ResponseEntity<>(sRetardo.createRetardo(id_trabajador,retardoTrabajadorDTO),HttpStatus.CREATED);
    }
}
