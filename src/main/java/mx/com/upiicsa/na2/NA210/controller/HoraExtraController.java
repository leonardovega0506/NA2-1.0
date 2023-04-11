package mx.com.upiicsa.na2.NA210.controller;

import mx.com.snreh.dto.HoraExtraDTO;

import mx.com.snreh.service.interfaces.HoraExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
public class HoraExtraController {

    @Autowired
    private HoraExtraService sHora;

    @GetMapping("/snrhe/trabajadores/{id_trabajador}/horaExtra/")
    public List<HoraExtraDTO> listarHorasExtraTrabajadorID(@PathVariable(value = "id_trabajador") long id_trabajador){
        return sHora.findAllHoraExtraTrabajadorID(id_trabajador);
    }

    @GetMapping("/snrhe/horaExtra/")
    public List<HoraExtraDTO> listarHorasExtra(){
        return sHora.findAllHorasExtra();
    }

    @GetMapping("/snrhe/trabajadores/{id_trabajador}/horaExtra/{id_horaExtra}")
    public ResponseEntity<HoraExtraDTO> obtenerHoraExtra(@PathVariable(value = "id_trabajador") long id_trabajador,@PathVariable(value = "id_horaExtra") long id_horaExtra){
        HoraExtraDTO horaExtraDTO = sHora.findHoraExtra(id_trabajador,id_horaExtra);

        return new ResponseEntity<>(horaExtraDTO, HttpStatus.OK);
    }
    @PostMapping("/snrhe/trabajadores/{id_trabajador}/horaExtra/")
    public ResponseEntity<HoraExtraDTO> registrarHoraExtra(@PathVariable(value = "id_trabajador")  long id_trabajador, @Valid @RequestBody HoraExtraDTO horaExtraDTO){
        return new ResponseEntity<>(sHora.createHoraExtra(id_trabajador,horaExtraDTO),HttpStatus.CREATED);
    }

}