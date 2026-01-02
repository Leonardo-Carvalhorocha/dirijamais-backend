package dirijamais.project.dirijamais.modulos.relatorios.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;

import dirijamais.project.dirijamais.aplicacao.filtros.dto.FiltroDTO;
import dirijamais.project.dirijamais.modulos.relatorios.models.Relatorio;
import dirijamais.project.dirijamais.modulos.transacao.models.TransacaoEntrada;
import dirijamais.project.dirijamais.modulos.transacao.models.TransacaoSaida;

public interface IRelatorioService {

    Relatorio buscarRelatorio(FiltroDTO filtro, Pageable pageable);

    Relatorio gerarRelatorio(List<TransacaoEntrada> transacoesEntrada,  List<TransacaoSaida> transacoesSaidas);

    Integer calcularQuantidadeDeViagens(List<TransacaoEntrada> transacoesEntrada);

    Integer calcularHorasTrabalhada(List<TransacaoEntrada> transacoesEntrada);

    BigDecimal calcularKmRodados(List<TransacaoEntrada> transacoesEntrada);

    BigDecimal calcularFaturamentoTotal(List<TransacaoEntrada> transacoesEntrada);

    BigDecimal calcularDespesasTotais(List<TransacaoSaida> transacoesSaidas);

    BigDecimal calcularSaldoLiquido(BigDecimal faturamentoTotal, BigDecimal despesasTotais);

    BigDecimal calcularFaturamentoPorViagem(BigDecimal faturamentoTotal, Integer viagens);

    BigDecimal calcularFaturamentoMedioPorHora(BigDecimal faturamentoTotal, Integer horasTrabalhados);

    BigDecimal calcularFaturamentoMedioPorKM(BigDecimal faturamentoTotal, BigDecimal KmRodados);
    
    BigDecimal calcularCustosPorViagens(BigDecimal custosTotais, Integer totalViagens);

    BigDecimal calcularCustosPorHora(BigDecimal custosTotais, Integer horasTrabalhados);

    BigDecimal calcularCustosPorKM(BigDecimal custosTotais, BigDecimal kmRodados);

    BigDecimal calcularLucroPorViagem(BigDecimal saldoLiquido, Integer totalViagens);

    BigDecimal calcularLucroPorHorasTrabalhados(BigDecimal saldoLiquido, Integer horasTrabalhados);

    BigDecimal calcularLucroPorKmRodado(BigDecimal saldoLiquido, BigDecimal lucroPorKM);

}
