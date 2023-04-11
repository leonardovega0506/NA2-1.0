package mx.com.upiicsa.na2.NA210.controller;

import mx.com.snreh.dto.TrabajadorDTO;
import mx.com.snreh.response.TrabajadorRespuesta;
import mx.com.snreh.service.interfaces.TrabajadorService;
import mx.com.snreh.util.ConstantesGlobales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("snrhe/trabajadores")
public class TrabajadorController {
    @Autowired
    private TrabajadorService sTrabajador;

    @GetMapping
    public TrabajadorRespuesta  listarTrabajadores(
            @RequestParam(value = "pageNo",defaultValue = ConstantesGlobales.NUMERO_PAGINA_DEFECTO,required = false)int numeroPagina,
            @RequestParam(value = "pageSize",defaultValue = ConstantesGlobales.MEDIDA_PAGINA_DEFECTO, required = false) int sizePagina){
        return sTrabajador.obtenerTrabajadores(numeroPagina,sizePagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrabajadorDTO> obtenerTrabajadorByNumero_Trabajador(@PathVariable(name = "id") long numero_trabajador){
        return ResponseEntity.ok(sTrabajador.obtenerTrabajadorById(numero_trabajador));
    }

    @PostMapping
    public ResponseEntity<TrabajadorDTO> crearTrabajador(@Valid @RequestBody TrabajadorDTO trabajadorDTO){
        return new ResponseEntity<>(sTrabajador.crearTrabajador(trabajadorDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrabajadorDTO> actaulizarTrabajador(@Valid @RequestBody TrabajadorDTO trabajadorDTO,@PathVariable(name = "id")long numero_trabajador){
        TrabajadorDTO trabajadroRespuesta = sTrabajador.actualizarTrabajador(trabajadorDTO,numero_trabajador);
        return new ResponseEntity<>(trabajadroRespuesta,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTrabajador(@PathVariable(name = "id") long numero_trabajador){
        sTrabajador.eliminarTrabajador(numero_trabajador);
        return new ResponseEntity<>("Trabajador eliminado con exito",HttpStatus.OK);
    }
}