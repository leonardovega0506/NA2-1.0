package mx.com.upiicsa.na2.NA210.controller;

import mx.com.snreh.dto.VacacionesDTO;
import mx.com.snreh.service.interfaces.VacacionesService;
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
    private VacacionesService sVacaciones;

    @GetMapping("/snrhe/trabajadores/{id_trabajador}/vacaciones/")
    public List<VacacionesDTO> listarVacacionesTrabajador(@PathVariable(value = "id_trabajador") long id_trabajador){
        return sVacaciones.findAllVacacionesTrabajador(id_trabajador);
    }

    @GetMapping("/snrhe/trabajadores/{id_trabajador}/vacaciones/{id_vacaciones}")
    public ResponseEntity<VacacionesDTO> obtenerVacacion(@PathVariable(value = "id_trabajador") long id_trabajador, @PathVariable(value = "id_vacaciones") long id_vacaciones){
        VacacionesDTO vacacionesDTO = sVacaciones.findVacaciones(id_trabajador,id_vacaciones);
        return new ResponseEntity<>(vacacionesDTO, HttpStatus.OK);
    }
    @GetMapping("/snrhe/vacaciones/")
    public List<VacacionesDTO> listarVacaciones(){
        return sVacaciones.findAllVacaciones();
    }

    @PostMapping("/snrhe/trabajadores/{id_trabajador}/vacaciones/")
    public ResponseEntity<VacacionesDTO> generarVacaciones(@PathVariable(value = "id_trabajador") long id_trabajador, @Valid @RequestBody VacacionesDTO vacacionesDTO){
        return new ResponseEntity<>(sVacaciones.createVacacion(id_trabajador,vacacionesDTO),HttpStatus.CREATED);
    }

    @PutMapping("/snrhe/trabajadores/{id_trabajador}/vacaciones/{id_vacaciones}")
    public ResponseEntity<VacacionesDTO> actualizarVacaciones(@PathVariable(value = "id_trabajador") long id_trabajador,@PathVariable(value = "id_vacaciones") long id_vacaciones,@Valid @RequestBody VacacionesDTO vacacionesDTO){
        VacacionesDTO vacacionActualizada = sVacaciones.updateVacaciones(id_trabajador,id_vacaciones,vacacionesDTO);
        return new ResponseEntity<>(vacacionActualizada,HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/snrhe/trabajadores/{id_trabajador}/vacaciones/{id_vacaciones}")
    public ResponseEntity<String> borrarVacaciones(@PathVariable(value = "id_trabajador") long id_trabajador,@PathVariable(value = "id_vacaciones") long id_vacaciones){
        sVacaciones.deleteVacaciones(id_trabajador,id_vacaciones);
        return new ResponseEntity<>("Vacaciones eliminadas con exito",HttpStatus.NO_CONTENT);
    }
}