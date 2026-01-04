package dirijamais.project.dirijamais.modulos.mensagemwhatsapp.dtos;

import java.time.LocalTime;

import dirijamais.project.dirijamais.modulos.usuario.dtos.UsuarioUuid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensagemWhatsappResponseModel {

     private UsuarioUuid usuario;

    private String numero;

    private LocalTime horarioEnvio;

}
