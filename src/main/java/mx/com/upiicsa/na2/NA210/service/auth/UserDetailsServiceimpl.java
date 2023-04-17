package mx.com.upiicsa.na2.NA210.service.auth;


import mx.com.upiicsa.na2.NA210.model.auth.UsuarioModel;
import mx.com.upiicsa.na2.NA210.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceimpl implements UserDetailsService {

    @Autowired
    private IUsuarioRepository iUsuario;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioModel usuario = iUsuario.findByUsername(username);
        if(usuario == null){
            throw new UsernameNotFoundException("USUARIO NO ENCONTRADO");
        }
        return usuario;
    }
}
