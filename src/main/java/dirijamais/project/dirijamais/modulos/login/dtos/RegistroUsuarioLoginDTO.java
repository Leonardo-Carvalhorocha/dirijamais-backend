package dirijamais.project.dirijamais.modulos.login.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroUsuarioLoginDTO {
    private String email;
    
    private String password;
    
    private String nome;
}
