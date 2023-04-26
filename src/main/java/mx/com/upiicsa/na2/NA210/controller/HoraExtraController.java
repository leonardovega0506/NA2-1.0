package mx.com.upiicsa.na2.NA210.controller;

import mx.com.upiicsa.na2.NA210.model.entity.HoraExtraModel;
import mx.com.upiicsa.na2.NA210.service.interfaces.IHoraExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:4200")
public class HoraExtraController {

    @Autowired
    private IHoraExtraService sHora;

    //Falta Version Trabajador
    @GetMapping("/na2/trabajadores/{id_trabajador}/horaExtra/")
    public ResponseEntity<?> listarHorasExtraTrabajadorID(@PathVariable(value = "id_trabajador") long id_trabajador) {
        return new ResponseEntity<>(sHora.findAllHoraExtraTrabajadorID(id_trabajador), HttpStatus.OK);
    }

    @GetMapping("/na2/horaExtra/")
    public ResponseEntity<?> listarHorasExtra() {
        return new ResponseEntity<>(sHora.findAllHorasExtra(), HttpStatus.OK);
    }

    //Falta- version trabajador
    @GetMapping("/na2/trabajadores/{id_trabajador}/horaExtra/{id_horaExtra}")
    public ResponseEntity<?> obtenerHoraExtra(@PathVariable(value = "id_trabajador") long id_trabajador, @PathVariable(value = "id_horaExtra") long id_horaExtra) {
        return new ResponseEntity<>(sHora.findHoraExtra(id_trabajador, id_horaExtra), HttpStatus.OK);
    }


    @GetMapping("na2/horaExtra/fecha")
    public ResponseEntity<?> listarHorasFecha(@RequestParam String fecha){
        return new ResponseEntity<>(sHora.findHoraExtraByDate(LocalDate.parse(fecha)),HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN') OR  hasRole('GERENTE')")
    @PostMapping("/na2/trabajadores/{id_trabajador}/horaExtra/")
    public ResponseEntity<?> registrarHoraExtra(@PathVariable(value = "id_trabajador")  long id_trabajador, @Valid @RequestBody HoraExtraModel horaExtraDTO){
        return new ResponseEntity<>(sHora.createHoraExtra(id_trabajador,horaExtraDTO),HttpStatus.CREATED);
    }

}