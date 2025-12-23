package dirijamais.project.dirijamais.modulos.veiculo.models;

import java.math.BigDecimal;

import dirijamais.project.dirijamais.aplicacao.models.BaseEntity;
import dirijamais.project.dirijamais.modulos.usuario.models.Usuario;
import dirijamais.project.dirijamais.modulos.veiculo.enums.SituacaoVeiculo;
import dirijamais.project.dirijamais.modulos.veiculo.enums.TipoCombustivel;
import dirijamais.project.dirijamais.modulos.veiculo.enums.TipoVeiculo;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Veiculo extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_uuid", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVeiculo tipoVeiculo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoVeiculo situacaoVeiculo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCombustivel tipoCombustivel;

    private BigDecimal consumoMedioKmPorLitro;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "valorMensal", column = @Column(name = "seguro_valor_mensal")),
        @AttributeOverride(name = "quantidadeParcelas", column = @Column(name = "seguro_qtd_parcelas")),
        @AttributeOverride(name = "inicioVigencia", column = @Column(name = "seguro_inicio_vigencia")),
        @AttributeOverride(name = "fimVigencia", column = @Column(name = "seguro_fim_vigencia"))
    })
    private SeguroVeiculo seguroVeiculo;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "valorMensal", column = @Column(name = "fin_valor_mensal")),
        @AttributeOverride(name = "quantidadeParcelas", column = @Column(name = "fin_qtd_parcelas")),
        @AttributeOverride(name = "inicioVigencia", column = @Column(name = "fin_inicio_vigencia")),
        @AttributeOverride(name = "fimVigencia", column = @Column(name = "fin_fim_vigencia"))
    })
    private FinanciamentoVeiculo financiamentoVeiculo;

    @Embedded
    private FranquiaAluguel franquiaAluguel;

    @Embedded
    private Combustivel combustivel;

}
