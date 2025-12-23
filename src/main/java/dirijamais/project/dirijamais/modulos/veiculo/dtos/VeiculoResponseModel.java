package dirijamais.project.dirijamais.modulos.veiculo.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import dirijamais.project.dirijamais.modulos.usuario.dtos.UsuarioUuid;
import dirijamais.project.dirijamais.modulos.veiculo.enums.SituacaoVeiculo;
import dirijamais.project.dirijamais.modulos.veiculo.enums.TipoCombustivel;
import dirijamais.project.dirijamais.modulos.veiculo.enums.TipoVeiculo;
import dirijamais.project.dirijamais.modulos.veiculo.models.Combustivel;
import dirijamais.project.dirijamais.modulos.veiculo.models.FinanciamentoVeiculo;
import dirijamais.project.dirijamais.modulos.veiculo.models.FranquiaAluguel;
import dirijamais.project.dirijamais.modulos.veiculo.models.SeguroVeiculo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoResponseModel {

    private UUID uuid;

    private UsuarioUuid usuario;

    private TipoVeiculo tipoVeiculo;

    private SituacaoVeiculo situacaoVeiculo;

    private TipoCombustivel tipoCombustivel;

    private BigDecimal consumoMedioKmPorLitro;

    private SeguroVeiculo seguroVeiculo;

    private FinanciamentoVeiculo financiamentoVeiculo;

    private FranquiaAluguel franquiaAluguel;

    private Combustivel combustivel;
    
    private boolean ativo;

}
