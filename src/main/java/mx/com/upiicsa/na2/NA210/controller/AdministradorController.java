package mx.com.upiicsa.na2.NA210.controller;

import mx.com.upiicsa.na2.NA210.model.entity.AdministradorModel;
import mx.com.upiicsa.na2.NA210.response.AdministradorRespuesta;
import mx.com.upiicsa.na2.NA210.service.interfaces.IAdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("na2/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdministradorController {

    @Autowired
    private IAdministradorService sAdministrador;

    //Ya esta
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public AdministradorRespuesta listarAdministradores(
            @RequestParam(value = "pageNo",defaultValue = "0", required = false) int numeroPagina,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int sizePagina){
        return sAdministrador.obtenerAdministradores(numeroPagina,sizePagina);
    }

    //Ya está
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerAdminById(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(sAdministrador.obtenerAdministradorById(id),HttpStatus.OK);
    }

    //Ya esta
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AdministradorModel> guardarAdmin(@RequestBody AdministradorModel administradorDTO){
        return new ResponseEntity<>(sAdministrador.crearAdministrador(administradorDTO), HttpStatus.CREATED);
    }


    //Ya esta
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<?> actualizarAdmin( @RequestBody AdministradorModel administradorDTO){
        sAdministrador.actualizarAdministrador(administradorDTO);
        return new ResponseEntity<>("Actualizado",HttpStatus.NO_CONTENT);
    }

    //Ya está
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarAdministrador(@PathVariable(name = "id") long id){
        sAdministrador.eliminiarAdministrador(id);
        return new ResponseEntity<>("Administrador eliminado con exito",HttpStatus.NO_CONTENT);
    }

}