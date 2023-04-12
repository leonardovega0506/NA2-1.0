package mx.com.upiicsa.na2.NA210.controller;

import mx.com.upiicsa.na2.NA210.model.entity.TareaModel;
import mx.com.upiicsa.na2.NA210.model.entity.TurnosModel;
import mx.com.upiicsa.na2.NA210.service.interfaces.ITurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/na2/turnos")
public class TurnosController {

    @Autowired
    private ITurnoService sTurnos;

    @GetMapping
    public ResponseEntity<?> listarTurnos() {
        return new ResponseEntity<>(sTurnos.findAllTurnos(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTurno(@PathVariable(name = "id") long id_turno) {
        return new ResponseEntity(sTurnos.findTurnoById(id_turno),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> generarTurno(@RequestBody TurnosModel turnosDTO) {
        return new ResponseEntity<>(sTurnos.createTurno(turnosDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTurno( @Valid @RequestBody TurnosModel turnosDTO) {
        sTurnos.updateTurno(turnosDTO);
        return new ResponseEntity<>("Actualizado", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTurni(@PathVariable(name = "id") long id_turno) {
        sTurnos.deleteTurno(id_turno);
        return new ResponseEntity<>("Turno eliminado con exito", HttpStatus.NO_CONTENT);
    }
}
