package mx.com.upiicsa.na2.NA210.controller;

import mx.com.upiicsa.na2.NA210.model.entity.RenunciaTrabajadorModel;
import mx.com.upiicsa.na2.NA210.service.interfaces.IRenunciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/na2/renuncia/")
public class RenunciaController {

    @Autowired
    private IRenunciaService renunciaService;

    @GetMapping("{id_trabajador}")
    public ResponseEntity<?> traerRenuncia(@PathVariable(value = "id_trabajador") long id_trabajador){
        return new ResponseEntity<>(renunciaService.findRenuncia(id_trabajador), HttpStatus.OK);
    }

    @PostMapping("{idTrabajador}")
    public ResponseEntity<?> crearRenuncia(@PathVariable(value = "id_trabajador") long id_trabajador, @Valid @RequestBody RenunciaTrabajadorModel renunciaDTO){
        return new ResponseEntity<>(renunciaService.createRenuncia(id_trabajador,renunciaDTO),HttpStatus.CREATED);
    }

    @PutMapping("{id_renuncia}")
    public ResponseEntity<?> actualizarRenuncia(@PathVariable(value = "id_trabajador") long id_trabajador,@PathVariable(value = "id_renuncia") long id_Renuncia, @Valid @RequestBody RenunciaTrabajadorModel renunciaDTO){
        renunciaService.updateRenuncia(id_trabajador,renunciaDTO);
        return new ResponseEntity<>("Actualizada", HttpStatus.NO_CONTENT);
    }
}
