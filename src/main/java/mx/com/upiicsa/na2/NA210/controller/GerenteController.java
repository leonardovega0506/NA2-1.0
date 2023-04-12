package mx.com.upiicsa.na2.NA210.controller;


import mx.com.upiicsa.na2.NA210.model.entity.GerenteModel;
import mx.com.upiicsa.na2.NA210.response.GerenteRespuesta;
import mx.com.upiicsa.na2.NA210.service.interfaces.IGerenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/na2/gerentes")
public class GerenteController {

    @Autowired
    private IGerenteService sGerente;

    @GetMapping
    public GerenteRespuesta listarGerentes(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false)int numeroPagina,
            @RequestParam(value = "pageSize",defaultValue = "10", required = false) int sizePagina){
        return sGerente.obtenerGerentes(numeroPagina,sizePagina);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerGerenteByNumero_Gerente(@PathVariable(name = "id") long numero_gerente){
        return new ResponseEntity<>(sGerente.obtenerGerenteByID(numero_gerente),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<GerenteModel> crearGerentes(@Valid @RequestBody GerenteModel gerenteDTO){
        return new ResponseEntity<>(sGerente.crearGerente(gerenteDTO), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<?> actualizarGerente(@Valid @RequestBody GerenteModel gerenteDTO){
        sGerente.actualizarGerente(gerenteDTO);
        return new ResponseEntity<>("Actualizado",HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarGerente(@PathVariable(name = "id") long numero_gerente){
        sGerente.eliminarGerente(numero_gerente);
        return new ResponseEntity<>("Trabajador eliminado con exito",HttpStatus.NO_CONTENT);
    }
}