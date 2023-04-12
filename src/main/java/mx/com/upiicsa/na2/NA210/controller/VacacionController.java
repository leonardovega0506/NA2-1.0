package mx.com.upiicsa.na2.NA210.controller;

import mx.com.upiicsa.na2.NA210.model.entity.VacacionModel;
import mx.com.upiicsa.na2.NA210.service.interfaces.IVacacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
public class VacacionController {

    @Autowired
    private IVacacionesService sVacaciones;

    @GetMapping("/na2/trabajadores/{id_trabajador}/vacaciones/")
    public ResponseEntity<?> listarVacacionesTrabajador(@PathVariable(value = "id_trabajador") long id_trabajador){
        return new ResponseEntity<>(sVacaciones.findAllVacacionesTrabajador(id_trabajador),HttpStatus.OK);
    }

    @GetMapping("/na2/trabajadores/{id_trabajador}/vacaciones/{id_vacaciones}")
    public ResponseEntity<?> obtenerVacacion(@PathVariable(value = "id_vacaciones") long id_vacaciones){
        return new ResponseEntity<>(sVacaciones.findVacaciones(id_vacaciones), HttpStatus.OK);
    }
    @GetMapping("/na2/vacaciones/")
    public ResponseEntity<?> listarVacaciones(){
        return new ResponseEntity<>(sVacaciones.findAllVacaciones(),HttpStatus.OK);
    }

    @PostMapping("/na2/trabajadores/{id_trabajador}/vacaciones/")
    public ResponseEntity<?> generarVacaciones(@PathVariable(value = "id_trabajador") long id_trabajador, @Valid @RequestBody VacacionModel vacacionesDTO){
        return new ResponseEntity<>(sVacaciones.createVacacion(id_trabajador,vacacionesDTO),HttpStatus.CREATED);
    }

    @PutMapping("/na2/vacaciones/{id_vacaciones}")
    public ResponseEntity<?> actualizarVacaciones(@Valid @RequestBody VacacionModel vacacionesDTO){
        sVacaciones.updateVacaciones(vacacionesDTO);
        return new ResponseEntity<>("vacacionActualizada",HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/na2/trabajadores/{id_trabajador}/vacaciones/{id_vacaciones}")
    public ResponseEntity<String> borrarVacaciones(@PathVariable(value = "id_vacaciones") long id_vacaciones){
        sVacaciones.deleteVacaciones(id_vacaciones);
        return new ResponseEntity<>("Vacaciones eliminadas con exito",HttpStatus.NO_CONTENT);
    }
}