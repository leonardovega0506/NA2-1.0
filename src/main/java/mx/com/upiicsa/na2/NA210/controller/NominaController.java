package mx.com.upiicsa.na2.NA210.controller;

import mx.com.upiicsa.na2.NA210.model.entity.NominaTrabajadorModel;
import mx.com.upiicsa.na2.NA210.service.interfaces.INominaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
public class NominaController {

    @Autowired
    private INominaService sNomina;

    @GetMapping("/na2/trabajadores/{id_trabajador}/nominas/")
    private  ResponseEntity<?> listarNominasTrabajadorID(@PathVariable(value = "id_trabajador") long id_trabajador){
        return new ResponseEntity<>(sNomina.findAllNominasTrabajador(id_trabajador),HttpStatus.OK);
    }

    @GetMapping("/na2/trabajadores/{id_trabajador}/nominas/{id_nomina}")
    public ResponseEntity<?> obtenerNomina(@PathVariable(value = "id_trabajador") long id_trabajador, @PathVariable(value = "id_nomina") long id_nomina){
        return new ResponseEntity<>(sNomina.findNominaByID(id_trabajador,id_nomina), HttpStatus.OK);
    }
    @GetMapping("/na2/nominas/")
    public ResponseEntity<?> listarnominas(){
        return new ResponseEntity<>(sNomina.findAllNominas(),HttpStatus.OK);
    }

    @PostMapping("/na2/trabajadores/{id_trabajador}/nominas/")
    public ResponseEntity<?> crearNomina(@PathVariable(value = "id_trabajador") long id_trabajador, @Valid @RequestBody NominaTrabajadorModel nominaDTO){
        return new ResponseEntity<>(sNomina.createNomina(id_trabajador,nominaDTO),HttpStatus.CREATED);
    }
    @PutMapping("/na2/trabajadores/{id_trabajador}/nominas")
    public ResponseEntity<?> actualizarNomina(@PathVariable(value = "id_trabajador") long id_trabajador, long id_nomina,@Valid @RequestBody NominaTrabajadorModel nominaDTO){
         sNomina.updateNomina(id_trabajador,nominaDTO);
        return new ResponseEntity<>("Actualizada", HttpStatus.NO_CONTENT);
    }
}