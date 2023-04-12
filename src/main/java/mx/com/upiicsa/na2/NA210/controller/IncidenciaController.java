package mx.com.upiicsa.na2.NA210.controller;


import mx.com.upiicsa.na2.NA210.model.entity.IncidenciaModel;
import mx.com.upiicsa.na2.NA210.service.interfaces.IIncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/na2/")
public class IncidenciaController {
    @Autowired
    private IIncidenciaService sIncidencia;

    @GetMapping("/trabajadores/{id_trabajador}/incidencias")
    public ResponseEntity<?> listarIncidencias(@PathVariable(value = "id_trabajador") Long id_trabajador){
        return new ResponseEntity<>(sIncidencia.findAllInciencias(id_trabajador),HttpStatus.OK);
    }

    @GetMapping("trabajadores/{idTrabajador}/incidencias{id_incidencia}")
    public ResponseEntity<?> obtenerIncidencia(@PathVariable(value = "id_trabajador") Long id_trabajador, @PathVariable(value = "id_incidencia") long id_incidencia){
        return new ResponseEntity<>(sIncidencia.findIncidencia(id_trabajador,id_incidencia), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> registrarIncidencia(@PathVariable(value = "id_trabajador") Long id_trabajador, @Valid @RequestBody IncidenciaModel incidenciaDTO){
        return new ResponseEntity<>(sIncidencia.createIncidencia(id_trabajador,incidenciaDTO),HttpStatus.CREATED);
    }


}