package mx.com.upiicsa.na2.NA210;

import mx.com.upiicsa.na2.NA210.exception.UsuarioFoundException;
import mx.com.upiicsa.na2.NA210.model.auth.RolModel;
import mx.com.upiicsa.na2.NA210.model.auth.UsuarioModel;
import mx.com.upiicsa.na2.NA210.model.auth.UsuarioRolModel;
import mx.com.upiicsa.na2.NA210.service.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private IUsuarioService sUsuario;

	@Autowired
	private BCryptPasswordEncoder bcPassword;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			if(sUsuario.findUsuarioByUsername("adminTI")!=null){
				UsuarioModel usuarioModel= sUsuario.findUsuarioByUsername("adminTI");
				System.out.println("INICIO DE APLICACION CON USUARIO: "+usuarioModel.getNombre());
			}
			else{
				UsuarioModel usuario = new UsuarioModel();
				usuario.setNombre("AdministradorTI");
				usuario.setUsername("adminTI");
				usuario.setPassword(bcPassword.encode("12345"));
				usuario.setArea("TI");
				RolModel rol = new RolModel();
				rol.setIdRol(1L);
				rol.setNombreRol("ROLE_ADMIN");
				Set<UsuarioRolModel> usuariosRoles = new HashSet<>();
				UsuarioRolModel usuarioRol = new UsuarioRolModel();
				usuarioRol.setRol(rol);
				usuarioRol.setUsuario(usuario);
				usuariosRoles.add(usuarioRol);
				UsuarioModel usuarioGuardado = sUsuario.saveUsuario(usuario, usuariosRoles);
				if(usuarioGuardado != null){
					System.out.println("Nombre del Usuario: "+usuarioGuardado.getNombre());
				}
				else{
					System.out.println("No se ha guardado el usuario");
				}
			}

		}
		catch (UsuarioFoundException exception){
			System.out.println("USUARIO YA CREADO");
		}
		catch (Exception e){
			System.out.println(e);
		}
	}

}
