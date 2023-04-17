package mx.com.upiicsa.na2.NA210.service.interfaces;


import mx.com.upiicsa.na2.NA210.model.auth.UsuarioModel;
import mx.com.upiicsa.na2.NA210.model.auth.UsuarioRolModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface IUsuarioService {

    UsuarioModel saveUsuario(UsuarioModel usuarioModel, Set<UsuarioRolModel> usuariosRoles) throws Exception;

    UsuarioModel findUsuarioByUsername(String username);

    List<UsuarioModel> findAllUsuarios();

    void deleteUsuario(Long idUsuario);
}
