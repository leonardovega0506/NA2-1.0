package mx.com.upiicsa.na2.NA210.controller;

import mx.com.snreh.dto.TareaDTO;
import mx.com.snreh.service.interfaces.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
public class TareaController {

    @Autowired
    private TareaService sTarea;

    @GetMapping("/snrhe/trabajadores/{id_trabajador}/tareas/")
    public List<TareaDTO> listarTareasTrabajador(@PathVariable(value = "id_trabajador") long id_trabajador){
        return sTarea.findTareasTrabajador(id_trabajador);
    }
    @GetMapping("/snrhe/tareas/")
    public List<TareaDTO> listarTareasTrabajador(){
        return sTarea.findAllTareas();
    }
    @GetMapping("/snrhe/trabajadores/{id_trabajador}/tareas/{id_tarea}")
    public ResponseEntity<TareaDTO> obtenerTareaByID(@PathVariable(value = "id_trabajador") long id_trabajador,@PathVariable(value = "id_tarea") long id_tarea){
        TareaDTO tareaDTO = sTarea.findTareaByID(id_trabajador,id_tarea);
        return new ResponseEntity<>(tareaDTO, HttpStatus.OK);
    }

    @PostMapping("/snrhe/trabajadores/{id_trabajador}/tareas/")
    public ResponseEntity<TareaDTO> asignarTarea(@PathVariable(value = "id_trabajador") long id_trabajador, @Valid @RequestBody TareaDTO tareaDTO){
        return new ResponseEntity<>(sTarea.createTarea(id_trabajador,tareaDTO),HttpStatus.CREATED);
    }

    @PutMapping("/snrhe/trabajadores/{id_trabajador}/tareas/{id_tarea}")
    public ResponseEntity<TareaDTO> actualizarTarea(@PathVariable(value = "id_trabajador") long id_trabajador,@PathVariable(value = "id_tarea") long id_tarea,@Valid @RequestBody TareaDTO tareaDTO){
        TareaDTO tareaActualizada = sTarea.updateTarea(id_trabajador,id_tarea,tareaDTO);
        return new ResponseEntity<>(tareaActualizada,HttpStatus.NO_CONTENT);
    }
}