package dirijamais.project.dirijamais.modulos.relatorios.models;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Relatorio {

    private Integer quantidadeDeViagens;

    private Integer horasTrabalhados;

    private BigDecimal faturamentoTotal;

    private BigDecimal despesasTotais;

    private BigDecimal saldoLiquido;
       
    private BigDecimal kmRodados;

    private BigDecimal faturamentoPorViagem;

    private BigDecimal faturamentoMedioPorHora;

    private BigDecimal faturamentoMedioPorKM;

    private BigDecimal custoPorViagem;

    private BigDecimal custoPorHora;

    private BigDecimal custoPorKM;

    private BigDecimal lucroPorViagem;

    private BigDecimal lucroPorHora;

    private BigDecimal lucroPorKM;

}
