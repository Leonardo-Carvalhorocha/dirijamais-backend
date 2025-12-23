package dirijamais.project.dirijamais.modulos.transacao.models;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import dirijamais.project.dirijamais.aplicacao.enums.Origem;
import dirijamais.project.dirijamais.aplicacao.models.BaseEntity;
import dirijamais.project.dirijamais.modulos.transacao.enums.DiaSemana;
import dirijamais.project.dirijamais.modulos.usuario.models.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TransacaoEntrada extends BaseEntity {
     
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_uuid", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private OffsetDateTime data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiaSemana diaSemana;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Origem origem;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorEntrada;

    private Integer quantidadeDeViagens;

    private BigDecimal kmRodados;

    private Integer horasTrabalhados;

    private String observacao;

}
