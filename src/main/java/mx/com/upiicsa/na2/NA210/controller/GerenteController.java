package mx.com.upiicsa.na2.NA210.controller;


import mx.com.upiicsa.na2.NA210.model.auth.UsuarioModel;
import mx.com.upiicsa.na2.NA210.model.entity.GerenteModel;
import mx.com.upiicsa.na2.NA210.repository.IGerenteRepository;
import mx.com.upiicsa.na2.NA210.repository.IUsuarioRepository;
import mx.com.upiicsa.na2.NA210.response.GerenteRespuesta;
import mx.com.upiicsa.na2.NA210.service.interfaces.IGerenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/na2/gerentes")
@CrossOrigin(origins = "http://localhost:4200")
public class GerenteController {

    @Autowired
    private IGerenteService sGerente;

    @Autowired
    private IUsuarioRepository iUsuario;

    @Autowired
    private IGerenteRepository iGerente;

    //Ya está
    @PreAuthorize("hasRole('ADMIN') OR  hasRole('GERENTE')")
    @GetMapping
    public GerenteRespuesta listarGerentes(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false)int numeroPagina,
            @RequestParam(value = "pageSize",defaultValue = "10", required = false) int sizePagina){
        return sGerente.obtenerGerentes(numeroPagina,sizePagina);
    }

    //Ya está
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerGerenteByNumero_Gerente(@PathVariable(name = "id") long numero_gerente){
        return new ResponseEntity<>(sGerente.obtenerGerenteByID(numero_gerente),HttpStatus.OK);
    }

    //Ya está
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GerenteModel> crearGerentes(@RequestBody GerenteModel gerenteDTO, @RequestParam long idUsuario){
        Optional<UsuarioModel> oUsuario = iUsuario.findById(idUsuario);
        gerenteDTO.setUsuario(oUsuario.get());
        return new ResponseEntity<>(sGerente.crearGerente(gerenteDTO), HttpStatus.CREATED);
    }

    @GetMapping("/idUsuario/{idUsuario}")
    public ResponseEntity<?> obtenerGerenteByIdUsuario(@PathVariable Long idUsuario){
        Optional<GerenteModel> oGerente = iGerente.findByUsuario_IdUsuario(idUsuario);
        GerenteModel gerenteTraido = oGerente.get();
        return new ResponseEntity<>(gerenteTraido,HttpStatus.OK);
    }

    //Ya está
    @PreAuthorize("hasRole('ADMIN') OR  hasRole('GERENTE')")
    @PutMapping
    public ResponseEntity<?> actualizarGerente(@Valid @RequestBody GerenteModel gerenteDTO){
        sGerente.actualizarGerente(gerenteDTO);
        return new ResponseEntity<>("Actualizado",HttpStatus.NO_CONTENT);
    }

    //Ya está
    @PreAuthorize("hasRole('ADMIN') OR  hasRole('GERENTE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarGerente(@PathVariable(name = "id") long numero_gerente){
        sGerente.eliminarGerente(numero_gerente);
        return new ResponseEntity<>("Trabajador eliminado con exito",HttpStatus.NO_CONTENT);
    }
}