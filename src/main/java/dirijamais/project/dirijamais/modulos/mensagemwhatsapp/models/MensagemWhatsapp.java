package dirijamais.project.dirijamais.modulos.mensagemwhatsapp.models;

import java.time.LocalDateTime;
import java.time.LocalTime;

import dirijamais.project.dirijamais.aplicacao.models.BaseEntity;
import dirijamais.project.dirijamais.modulos.usuario.models.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "whatsapp_schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MensagemWhatsapp extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    private String numero;

    @Column(length = 500)
    private String mensagem;

    private LocalTime horarioEnvio;

    private LocalDateTime ultimoEnvio;
    
}
