package dirijamais.project.dirijamais.modulos.transacao.models;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import dirijamais.project.dirijamais.aplicacao.models.BaseEntity;
import dirijamais.project.dirijamais.modulos.transacao.enums.CategoriaGastos;
import dirijamais.project.dirijamais.modulos.transacao.enums.DiaSemana;
import dirijamais.project.dirijamais.modulos.transacao.enums.TipoGasto;
import dirijamais.project.dirijamais.modulos.transacao.enums.TipoPagamento;
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
public class TransacaoSaida extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_uuid", nullable = false)
    private Usuario usuario;

     @Column(nullable = false)
    private OffsetDateTime data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiaSemana diaSemana;

    private String descricaoGasto;

    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaGastos categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoGasto tipoGasto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPagamento tipoPagamento;
}
