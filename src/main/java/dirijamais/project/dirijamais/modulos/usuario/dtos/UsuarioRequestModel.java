package dirijamais.project.dirijamais.modulos.usuario.dtos;

import java.time.OffsetDateTime;

import dirijamais.project.dirijamais.modulos.usuario.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestModel {
    private String nome;

    private String email;

    private String password;

    private String cpf;

    private String telefone;

    private OffsetDateTime dataNascimento;

    private Role role;
}
