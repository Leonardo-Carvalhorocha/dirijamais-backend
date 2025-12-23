package dirijamais.project.dirijamais.modulos.login.services.implementacoes;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dirijamais.project.dirijamais.aplicacao.exception.UsuarioLoginException;
import dirijamais.project.dirijamais.modulos.login.dtos.UsuarioLoginDTO;
import dirijamais.project.dirijamais.modulos.login.services.ILoginService;
import dirijamais.project.dirijamais.modulos.usuario.models.Usuario;
import dirijamais.project.dirijamais.modulos.usuario.services.implementacao.UsuarioService;

@Service
public class LoginService implements ILoginService {

    @Autowired
    private UsuarioService service;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Usuario authenticate(UsuarioLoginDTO usuarioLoginDTO) {
          try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    usuarioLoginDTO.getEmail(), 
                    usuarioLoginDTO.getPassword()
                )
            );
        } catch (BadCredentialsException e) {
            throw new UsuarioLoginException("Usuário ou senha estão incorretos");
        }

        return service.buscarPorEmail(usuarioLoginDTO.getEmail());
    }

}
