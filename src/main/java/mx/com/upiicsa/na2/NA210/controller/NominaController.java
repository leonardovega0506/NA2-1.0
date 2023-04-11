package mx.com.upiicsa.na2.NA210.controller;


import mx.com.snreh.dto.NominaDTO;
import mx.com.snreh.service.interfaces.NominaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
public class NominaController {

    @Autowired
    private NominaService sNomina;

    @GetMapping("/snrhe/trabajadores/{id_trabajador}/nominas/")
    private List<NominaDTO> listarNominasTrabajadorID(@PathVariable(value = "id_trabajador") long id_trabajador){
        return sNomina.findAllNominasTrabajador(id_trabajador);
    }

    @GetMapping("/snrhe/trabajadores/{id_trabajador}/nominas/{id_nomina}")
    public ResponseEntity<NominaDTO> obtenerNomina(@PathVariable(value = "id_trabajador") long id_trabajador, @PathVariable(value = "id_nomina") long id_nomina){
        NominaDTO nominaDTO = sNomina.findNominaByID(id_trabajador,id_nomina);
        return new ResponseEntity<>(nominaDTO, HttpStatus.OK);
    }
    @GetMapping("/snrhe/nominas/")
    public List<NominaDTO> listarnominas(){
        return sNomina.findAllNominas();
    }

    @PostMapping("/snrhe/trabajadores/{id_trabajador}/nominas/")
    public ResponseEntity<NominaDTO> crearNomina(@PathVariable(value = "id_trabajador") long id_trabajador, @Valid @RequestBody NominaDTO nominaDTO){
        return new ResponseEntity<>(sNomina.createNomina(id_trabajador,nominaDTO),HttpStatus.CREATED);
    }
    @PutMapping("/snrhe/trabajadores/{id_trabajador}/nominas/{id_nomina}")
    public ResponseEntity<NominaDTO> actualizarNomina(@PathVariable(value = "id_trabajador") long id_trabajador,@PathVariable(value = "id_nomina") long id_nomina,@Valid @RequestBody NominaDTO nominaDTO){
        NominaDTO noominaActualiza = sNomina.updateNomina(id_trabajador,id_nomina,nominaDTO);
        return new ResponseEntity<>(noominaActualiza, HttpStatus.NO_CONTENT);
    }
}