package mx.com.upiicsa.na2.NA210.controller;

import mx.com.snreh.dto.TurnosDTO;
import mx.com.snreh.respuesta.TurnosRespuesta;
import mx.com.snreh.service.interfaces.TurnoService;
import mx.com.snreh.util.ConstantesGlobales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/snrhe/turnos")
public class TurnosController {

    @Autowired
    private TurnoService sTurnos;

    @GetMapping
    public TurnosRespuesta listarTurnos(@RequestParam(value = "pageNo",defaultValue = ConstantesGlobales.NUMERO_PAGINA_DEFECTO,required = false)int numeroPagina,
                                        @RequestParam(value = "pageSize",defaultValue = ConstantesGlobales.MEDIDA_PAGINA_DEFECTO,required = false) int sizePagina){
        return sTurnos.findAllTurnos(numeroPagina,sizePagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnosDTO> obtenerTurno(@PathVariable(name = "id") long id_turno){
        return ResponseEntity.ok(sTurnos.findTurnoBy(id_turno));
    }

    @PostMapping
    public ResponseEntity<TurnosDTO> generarTurno(@RequestBody TurnosDTO turnosDTO){
        return new ResponseEntity<>(sTurnos.createTurno(turnosDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurnosDTO> actualizarTurno(@PathVariable(name = "id") long id_turno,@Valid @RequestBody TurnosDTO turnosDTO){
        TurnosDTO turnoRespuesta = sTurnos.updateTurno(id_turno,turnosDTO);
        return new ResponseEntity<>(turnoRespuesta,HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurni(@PathVariable(name = "id") long id_turno){
        sTurnos.deleteTurno(id_turno);
        return new ResponseEntity<>("Turno eliminado con exito",HttpStatus.NO_CONTENT);
    }
}
