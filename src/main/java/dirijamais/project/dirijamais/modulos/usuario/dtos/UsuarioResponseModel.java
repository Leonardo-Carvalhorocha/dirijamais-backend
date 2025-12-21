package dirijamais.project.dirijamais.modulos.usuario.dtos;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseModel {

    private UUID uuid;

    private String nome;

    private String email;

    private String cpf;

    private String telefone;

    private OffsetDateTime dataNascimento;
}
