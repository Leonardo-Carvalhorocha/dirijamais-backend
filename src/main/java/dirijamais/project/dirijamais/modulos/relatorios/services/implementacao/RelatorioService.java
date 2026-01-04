package dirijamais.project.dirijamais.modulos.relatorios.services.implementacao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dirijamais.project.dirijamais.aplicacao.filtros.dto.FiltroDTO;
import dirijamais.project.dirijamais.modulos.relatorios.models.Relatorio;
import dirijamais.project.dirijamais.modulos.relatorios.services.IRelatorioService;
import dirijamais.project.dirijamais.modulos.transacao.models.TransacaoEntrada;
import dirijamais.project.dirijamais.modulos.transacao.models.TransacaoSaida;
import dirijamais.project.dirijamais.modulos.transacao.services.implementacao.TransacaoEntradaService;
import dirijamais.project.dirijamais.modulos.transacao.services.implementacao.TransacaoSaidaService;

@Service
public class RelatorioService implements IRelatorioService {

    @Autowired
    TransacaoEntradaService transacaoEntradaService;

    @Autowired
    TransacaoSaidaService transacaoSaidaService;

    @Override
    public Relatorio buscarRelatorio(FiltroDTO filtro, Pageable pageable) {
        List<TransacaoEntrada> transacoesEntrada = transacaoEntradaService.buscarEntradasPorFiltro(filtro, pageable);
        List<TransacaoSaida> transacoesSaidas = transacaoSaidaService.buscarSaidasPorFiltro(filtro, pageable);
        Relatorio relatorio = gerarRelatorio(transacoesEntrada, transacoesSaidas);

        return relatorio;
    }

    @Override
    public Relatorio gerarRelatorio(List<TransacaoEntrada> transacoesEntrada, List<TransacaoSaida> transacoesSaidas) {
        Relatorio relatorio = new Relatorio();

        relatorio.setQuantidadeDeViagens(calcularQuantidadeDeViagens(transacoesEntrada));
        relatorio.setHorasTrabalhados(calcularHorasTrabalhada(transacoesEntrada));
        relatorio.setFaturamentoTotal(calcularFaturamentoTotal(transacoesEntrada));
        relatorio.setDespesasTotais(calcularDespesasTotais(transacoesSaidas));
        relatorio.setKmRodados(calcularKmRodados(transacoesEntrada));
        relatorio.setSaldoLiquido(calcularSaldoLiquido(relatorio.getFaturamentoTotal(), relatorio.getDespesasTotais()));
        relatorio.setFaturamentoPorViagem(calcularFaturamentoPorViagem(relatorio.getFaturamentoTotal(), relatorio.getQuantidadeDeViagens()));
        relatorio.setFaturamentoMedioPorHora(calcularFaturamentoMedioPorHora(relatorio.getFaturamentoTotal(), relatorio.getHorasTrabalhados()));
        relatorio.setFaturamentoMedioPorKM(calcularFaturamentoMedioPorKM(relatorio.getFaturamentoTotal(), relatorio.getKmRodados()));
        relatorio.setCustoPorViagem(calcularCustosPorViagens(relatorio.getDespesasTotais(), relatorio.getQuantidadeDeViagens()));
        relatorio.setCustoPorHora(calcularCustosPorHora(relatorio.getDespesasTotais(), relatorio.getHorasTrabalhados()));
        relatorio.setCustoPorKM(calcularCustosPorKM(relatorio.getDespesasTotais(), relatorio.getKmRodados()));
        relatorio.setLucroPorViagem(calcularLucroPorViagem(relatorio.getSaldoLiquido(), relatorio.getQuantidadeDeViagens()));
        relatorio.setLucroPorHora(calcularLucroPorHorasTrabalhados(relatorio.getSaldoLiquido(), relatorio.getHorasTrabalhados()));
        relatorio.setLucroPorKM(calcularLucroPorKmRodado(relatorio.getSaldoLiquido(), relatorio.getKmRodados()));
        

        return relatorio;
    }

/*
    $$$$$$\   $$$$$$\  $$\       $$$$$$\  $$\   $$\ $$\       $$$$$$\   $$$$$$\  
    $$  __$$\ $$  __$$\ $$ |     $$  __$$\ $$ |  $$ |$$ |     $$  __$$\ $$  __$$\ 
    $$ /  \__|$$ /  $$ |$$ |     $$ /  \__|$$ |  $$ |$$ |     $$ /  $$ |$$ /  \__|
    $$ |      $$$$$$$$ |$$ |     $$ |      $$ |  $$ |$$ |     $$ |  $$ |\$$$$$$\  
    $$ |      $$  __$$ |$$ |     $$ |      $$ |  $$ |$$ |     $$ |  $$ | \____$$\ 
    $$ |  $$\ $$ |  $$ |$$ |     $$ |  $$\ $$ |  $$ |$$ |     $$ |  $$ |$$\   $$ |
    \$$$$$$  |$$ |  $$ |$$$$$$$$\\$$$$$$  |\$$$$$$  |$$$$$$$$\ $$$$$$  |\$$$$$$  |
    \______/ \__|  \__|\________|\______/  \______/ \________|\______/  \______/                                                                               
*/

    @Override
    public Integer calcularQuantidadeDeViagens(List<TransacaoEntrada> transacoesEntrada) {
        Integer quantidade = transacoesEntrada.stream()
            .map(TransacaoEntrada::getQuantidadeDeViagens)
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .sum();
        
        return quantidade;
    }

    @Override
    public Integer calcularHorasTrabalhada(List<TransacaoEntrada> transacoesEntrada) {
        Integer horas = transacoesEntrada.stream()
            .map(TransacaoEntrada::getHorasTrabalhados)
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .sum();
        
        return horas;
    }

    @Override
    public BigDecimal calcularKmRodados(List<TransacaoEntrada> transacoesEntrada) {
        BigDecimal kmRodados = transacoesEntrada.stream()
            .map(transacaoEntrada -> transacaoEntrada.getKmRodados() != null ? transacaoEntrada.getKmRodados() : BigDecimal.ZERO)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return kmRodados;
    }

    @Override
    public BigDecimal calcularFaturamentoTotal(List<TransacaoEntrada> transacoesEntrada) {
        BigDecimal faturamento = transacoesEntrada.stream()
            .map(t -> t.getValorEntrada() != null ? t.getValorEntrada() : BigDecimal.ZERO)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return faturamento;
    }

    @Override
    public BigDecimal calcularDespesasTotais(List<TransacaoSaida> transacoesSaidas) {
        BigDecimal despesas = transacoesSaidas.stream()
            .map(t -> t.getValor() != null ? t.getValor() : BigDecimal.ZERO)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return despesas;
    }

    @Override
    public BigDecimal calcularSaldoLiquido(BigDecimal faturamentoTotal, BigDecimal despesasTotais) {
        BigDecimal saldoLiquido = faturamentoTotal.subtract(despesasTotais);
        return saldoLiquido;
    }

    @Override
    public BigDecimal calcularFaturamentoPorViagem(BigDecimal faturamentoTotal, Integer viagens) {

        if (viagens == null || viagens == 0 || faturamentoTotal == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal faturamentoPorViagem = faturamentoTotal.divide(
            BigDecimal.valueOf(viagens), 
            2, 
            RoundingMode.HALF_UP
        );

        return faturamentoPorViagem;
    }

    @Override
    public BigDecimal calcularFaturamentoMedioPorHora(BigDecimal faturamentoTotal, Integer horasTrabalhados) {

        if (faturamentoTotal == null || horasTrabalhados == null || horasTrabalhados == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal faturamentoMedioPorHora = faturamentoTotal.divide(
            BigDecimal.valueOf(horasTrabalhados),
            2,
            RoundingMode.HALF_UP
        );

        return faturamentoMedioPorHora;
    }

    @Override
    public BigDecimal calcularFaturamentoMedioPorKM(BigDecimal faturamentoTotal, BigDecimal kmRodados) {

        if (faturamentoTotal == null 
            || kmRodados == null 
            || kmRodados.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal faturamentoMedioPorKM = faturamentoTotal.divide(
            kmRodados,
            2,
            RoundingMode.HALF_UP
        );

        return faturamentoMedioPorKM;
    }

    @Override
    public BigDecimal calcularCustosPorViagens(BigDecimal custosTotais, Integer totalViagens) {
        
        if (custosTotais == null || totalViagens == null || totalViagens == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal custosTotaisPorViagem =  custosTotais.divide(
            BigDecimal.valueOf(totalViagens),
            2,
            RoundingMode.HALF_UP
        );

        return custosTotaisPorViagem;
   }

    @Override
    public BigDecimal calcularCustosPorHora(BigDecimal custosTotais, Integer horasTrabalhados) {

        if (custosTotais == null || horasTrabalhados == null || horasTrabalhados == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal custosPorHora = custosTotais.divide(
            BigDecimal.valueOf(horasTrabalhados),
            2,
            RoundingMode.HALF_UP
        );

        return custosPorHora;
    }

    @Override
    public BigDecimal calcularCustosPorKM(BigDecimal custosTotais, BigDecimal kmRodados) {

        if (custosTotais == null 
            || kmRodados == null 
            || kmRodados.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal custosPorKM = custosTotais.divide(
            kmRodados,
            2,
            RoundingMode.HALF_UP
        );

        return custosPorKM;
    }

    @Override
    public BigDecimal calcularLucroPorViagem(BigDecimal saldoLiquido, Integer totalViagens) {

        if (saldoLiquido == null || totalViagens == null || totalViagens == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal lucroPorViagem = saldoLiquido.divide(
            BigDecimal.valueOf(totalViagens),
            2,
            RoundingMode.HALF_UP
        );

        return lucroPorViagem;
    }

    @Override
    public BigDecimal calcularLucroPorHorasTrabalhados(
            BigDecimal saldoLiquido,
            Integer horasTrabalhados) {

        if (saldoLiquido == null || horasTrabalhados == null || horasTrabalhados == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal lucroPorHoraTrabalhado = saldoLiquido.divide(
            BigDecimal.valueOf(horasTrabalhados),
            2,
            RoundingMode.HALF_UP
        );

        return lucroPorHoraTrabalhado;
    }

   @Override
    public BigDecimal calcularLucroPorKmRodado(
            BigDecimal saldoLiquido,
            BigDecimal kmRodados) {

        if (saldoLiquido == null || kmRodados == null || kmRodados.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal lucroPorKmRodado = saldoLiquido.divide(
            kmRodados,
            2,
            RoundingMode.HALF_UP
        );

        return lucroPorKmRodado;
    }

}
