package dirijamais.project.dirijamais.modulos.mensagemwhatsapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WhatsappQueueMessageDTO {

    private String mensagem;

    private String contato;

}
