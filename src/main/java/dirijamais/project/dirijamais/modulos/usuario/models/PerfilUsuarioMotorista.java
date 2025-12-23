package dirijamais.project.dirijamais.modulos.usuario.models;

import java.math.BigDecimal;

import dirijamais.project.dirijamais.aplicacao.models.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PerfilUsuarioMotorista extends BaseEntity{

    private BigDecimal metaMensalLiquida;

    private Integer diasTrabalhadoPorSemana;

    private Integer horasTrabalhadoPorDia;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

}
