package mx.com.upiicsa.na2.NA210.controller;

import lombok.extern.slf4j.Slf4j;
import mx.com.upiicsa.na2.NA210.configuration.JwtUtils;
import mx.com.upiicsa.na2.NA210.exception.UsuarioNoFoundException;
import mx.com.upiicsa.na2.NA210.model.auth.*;
import mx.com.upiicsa.na2.NA210.repository.IRolRepository;
import mx.com.upiicsa.na2.NA210.service.auth.UserDetailsServiceimpl;
import mx.com.upiicsa.na2.NA210.service.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/na2/auth")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager authM;

    @Autowired
    private UserDetailsServiceimpl uDetailS;

    @Autowired
    private JwtUtils jUtils;

    @Autowired
    private BCryptPasswordEncoder bcPassword;

    @Autowired
    private IRolRepository iRol;

    @Autowired
    private IUsuarioService sUsuario;


    @GetMapping("/actual")
    public UsuarioModel obtenerUsuarioActual(Principal principal){
        return (UsuarioModel) uDetailS.loadUserByUsername(principal.getName());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/usuarios")
    public ResponseEntity<?> obtenerUsuarios(){
        return new ResponseEntity<>(sUsuario.findAllUsuarios(),HttpStatus.OK);
    }



    @GetMapping("/usuario/{username}")
    public ResponseEntity<?> obtenerUsuarioByUserName(@PathVariable(value = "username") String username){
        UsuarioModel usuario = sUsuario.findUsuarioByUsername(username);
        return new ResponseEntity<>(usuario,HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest)throws Exception{
        log.info("USUARIO: {}",jwtRequest);
        try{
            authenticar(jwtRequest.getUsername(),jwtRequest.getPassword());
        }
        catch (UsuarioNoFoundException e) {
            e.printStackTrace();
            throw new Exception("USUARIO NO ENCONTRADO");
        }
        UserDetails userDetails = this.uDetailS.loadUserByUsername(jwtRequest.getUsername());
        String  token = this.jUtils.generateToken(userDetails);
        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/usuario")
    public ResponseEntity<?> guardarUsuarioRecibo(@RequestBody UsuarioModel usuario, @RequestParam String rol) throws Exception{
        usuario.setPassword(bcPassword.encode(usuario.getPassword()));
        Set<UsuarioRolModel> usuarioRoles = new HashSet<>();

        RolModel roles = iRol.findByNombreRol("ROLE_"+rol);
        if(roles !=null){
            roles.setNombreRol("ROLE_"+rol);

            UsuarioRolModel usuarioRolModel = new UsuarioRolModel();
            usuarioRolModel.setUsuario(usuario);
            usuarioRolModel.setRol(roles);

            usuarioRoles.add(usuarioRolModel);
            return new ResponseEntity<>(sUsuario.saveUsuario(usuario,usuarioRoles),HttpStatus.CREATED);
        }
        else{
            RolModel rolNuevo = new RolModel();
            rolNuevo.setNombreRol("ROLE_"+rol);
            iRol.save(rolNuevo);


            UsuarioRolModel usuarioRolModel = new UsuarioRolModel();
            usuarioRolModel.setUsuario(usuario);
            usuarioRolModel.setRol(rolNuevo);

            usuarioRoles.add(usuarioRolModel);
            return new ResponseEntity<>(sUsuario.saveUsuario(usuario,usuarioRoles),HttpStatus.CREATED);
        }

    }

    //Ya está
    private void authenticar(String username ,String password)throws Exception{
        try{
            authM.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }
        catch (DisabledException exception){
            throw new Exception("USUARIO DESHABILITADO"+exception.getMessage());
        }
        catch (BadCredentialsException exception){
            throw new Exception("CREDENCIALES invalidad"+exception.getMessage());
        }
    }
}
