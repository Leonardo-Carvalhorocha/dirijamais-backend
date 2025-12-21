package dirijamais.project.dirijamais.modulos.login.services.implementacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import dirijamais.project.dirijamais.modulos.login.dtos.UsuarioLoginDTO;
import dirijamais.project.dirijamais.modulos.login.services.ILoginService;
import dirijamais.project.dirijamais.modulos.usuario.models.Usuario;
import dirijamais.project.dirijamais.modulos.usuario.repositors.UsuarioRepository;

@Service
public class LoginService implements ILoginService {

    @Autowired
    private UsuarioRepository repository;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Usuario authenticate(UsuarioLoginDTO usuarioLoginDTO) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    usuarioLoginDTO.getEmail(),
                    usuarioLoginDTO.getPassword()
            )
        );

        return repository.findByEmail(usuarioLoginDTO.getEmail())
                .orElseThrow();
    }

}
