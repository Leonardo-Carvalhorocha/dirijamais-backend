package dirijamais.project.dirijamais.modulos.login.dtos.response;

import dirijamais.project.dirijamais.modulos.usuario.dtos.UsuarioResponseModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private String token;

    private long expiresIn;

    private UsuarioResponseModel usuario; 

}
