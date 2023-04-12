package mx.com.upiicsa.na2.NA210.controller;

import mx.com.upiicsa.na2.NA210.model.entity.EventoModel;
import mx.com.upiicsa.na2.NA210.service.interfaces.IEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
public class EventoController {

    @Autowired
    private IEventoService sEvento;

    @GetMapping("/na2/gerentes/{id_gerente}/eventos/")
    public ResponseEntity<?> listarEventosGerenteId(@PathVariable(value = "id_gerente") long id_gerente){
        return new ResponseEntity<>(sEvento.findAllEventosGerenteID(id_gerente),HttpStatus.OK);
    }
    @GetMapping("/na2/eventos/{id_evento}")
    public ResponseEntity<?> obtenerEventoById(@PathVariable(value = "id_evento") long id_evento){
        return new ResponseEntity<>(sEvento.findEvento(id_evento), HttpStatus.OK);
    }
    @GetMapping("/na2/eventos/")
    public  ResponseEntity<?> listarEventos(){
        return new ResponseEntity<>(sEvento.findAllEventos(),HttpStatus.OK);
    }

    @PostMapping("/na2/gerentes/{id_gerente}/eventos/")
    public ResponseEntity<EventoModel> crearEvento(@PathVariable(value = "id_gerente")long id_gerente,@Valid @RequestBody EventoModel eventoDTO){
        return new ResponseEntity<>(sEvento.createEvento(id_gerente,eventoDTO),HttpStatus.CREATED);
    }
    @PutMapping("/na2/gerentes/{id_gerente}/eventos/")
    public ResponseEntity<?> actualizarEvento(@PathVariable(value = "id_gerente") long id_gerente, @PathVariable("id_evento") long id_evento, @Valid @RequestBody EventoModel eventoDTO){
         sEvento.updateEvento(id_gerente, eventoDTO);
        return new ResponseEntity<>("eventoActualizado",HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/na2/eventos/{id_evento}")
    public ResponseEntity<?> borrarEvento(@PathVariable("id_evento") long id_evento){
        sEvento.deleteEvento( id_evento);
        return new ResponseEntity<>("Comentario eliminado con exito",HttpStatus.NO_CONTENT);
    }

}