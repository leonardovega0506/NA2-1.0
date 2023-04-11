package mx.com.upiicsa.na2.NA210.controller;

import mx.com.snreh.dto.RetardoTrabajadorDTO;
import mx.com.snreh.service.interfaces.RetardoTrabajadorService;
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
    private RetardoTrabajadorService sRetardo;

    @GetMapping("/snrhe/trabajadores/{id_trabajador}/retardos/")
    public List<RetardoTrabajadorDTO> listarRetardosTrabajador(@PathVariable(value = "id_trabajador") long id_trabajador){
        return sRetardo.findRetardosTrabajadorID(id_trabajador);
    }
    @GetMapping("/snrhe/trabajadores/{id_trabajador}/retardos/{id_retardo}")
    public ResponseEntity<RetardoTrabajadorDTO> obtenerRetardoByID(@PathVariable(value = "id_trabajador")long id_trabajador, @PathVariable(value = "id_retardo") long id_retardo){
        RetardoTrabajadorDTO retardoTrabajadorDTO = sRetardo.findRetardoById(id_trabajador,id_retardo);
        return new ResponseEntity<>(retardoTrabajadorDTO, HttpStatus.OK);
    }

    @GetMapping("/snrhe/retardos/")
    public List<RetardoTrabajadorDTO> listarRetardosTrabajador(){
        return sRetardo.findAllRetardos();
    }

    @PostMapping("/snrhe/trabajadores/{id_trabajador}/retardos/")
    public ResponseEntity<RetardoTrabajadorDTO> generarRetardo(@PathVariable(value = "id_trabajador") long id_trabajador, @Valid @RequestBody RetardoTrabajadorDTO retardoTrabajadorDTO){
        return new ResponseEntity<>(sRetardo.createRetardo(id_trabajador,retardoTrabajadorDTO),HttpStatus.CREATED);
    }
}
