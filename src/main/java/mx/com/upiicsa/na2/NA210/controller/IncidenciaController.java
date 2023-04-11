package mx.com.upiicsa.na2.NA210.controller;

import mx.com.snreh.dto.IncidenciaDTO;
import mx.com.snreh.service.interfaces.IncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/snrhe/trabajadores/{id_trabajador}/incidencias/")
public class IncidenciaController {
    @Autowired
    private IncidenciaService sIncidencia;

    @GetMapping
    public List<IncidenciaDTO> listarIncidencias(@PathVariable(value = "id_trabajador") Long id_trabajador){
        return sIncidencia.findAllInciencias(id_trabajador);
    }

    @GetMapping("{id_incidencia}")
    public ResponseEntity<IncidenciaDTO> obtenerIncidencia(@PathVariable(value = "id_trabajador") Long id_trabajador, @PathVariable(value = "id_incidencia") long id_incidencia){
        IncidenciaDTO incidenciaDTO = sIncidencia.findIncidencia(id_trabajador,id_incidencia);
        return new ResponseEntity<>(incidenciaDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IncidenciaDTO> registrarIncidencia(@PathVariable(value = "id_trabajador") Long id_trabajador, @Valid @RequestBody IncidenciaDTO incidenciaDTO){
        return new ResponseEntity<>(sIncidencia.createIncidencia(id_trabajador,incidenciaDTO),HttpStatus.CREATED);
    }


}