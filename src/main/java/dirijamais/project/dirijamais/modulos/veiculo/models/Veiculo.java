package dirijamais.project.dirijamais.modulos.veiculo.models;

import java.math.BigDecimal;

import dirijamais.project.dirijamais.aplicacao.models.BaseEntity;
import dirijamais.project.dirijamais.modulos.usuario.models.Usuario;
import dirijamais.project.dirijamais.modulos.veiculo.enums.SituacaoVeiculo;
import dirijamais.project.dirijamais.modulos.veiculo.enums.TipoCombustivel;
import dirijamais.project.dirijamais.modulos.veiculo.enums.TipoVeiculo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
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

    @Transient
    private SeguroVeiculo seguroVeiculo;
}
