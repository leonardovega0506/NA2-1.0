package mx.com.upiicsa.na2.NA210.controller;

import mx.com.snreh.dto.RenunciaDTO;
import mx.com.snreh.service.interfaces.RenunciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/snrhe/trabajadores/{id_trabajador}/renuncia/")
public class RenunciaController {

    @Autowired
    private RenunciaService renunciaService;

    @GetMapping("{id_renuncia}")
    public ResponseEntity<RenunciaDTO> traerRenuncia(@PathVariable(value = "id_trabajador") long id_trabajador, @PathVariable(value = "id_renuncia") long id_renuncia){
        RenunciaDTO renunciaDTO = renunciaService.findRenuncia(id_trabajador,id_renuncia);
        return new ResponseEntity<>(renunciaDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RenunciaDTO> crearRenuncia(@PathVariable(value = "id_trabajador") long id_trabajador, @Valid @RequestBody RenunciaDTO renunciaDTO){
        return new ResponseEntity<>(renunciaService.createRenuncia(id_trabajador,renunciaDTO),HttpStatus.CREATED);
    }

    @PutMapping("{id_renuncia}")
    public ResponseEntity<RenunciaDTO> actualizarRenuncia(@PathVariable(value = "id_trabajador") long id_trabajador,@PathVariable(value = "id_renuncia") long id_Renuncia, @Valid @RequestBody RenunciaDTO renunciaDTO){
        RenunciaDTO renunciaActualizada = renunciaService.updateRenuncua(id_trabajador,id_Renuncia,renunciaDTO);
        return new ResponseEntity<>(renunciaActualizada, HttpStatus.NO_CONTENT);
    }
}
