package dirijamais.project.dirijamais.modulos.transacao.services.implementacao;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dirijamais.project.dirijamais.aplicacao.exception.TransacaoEntradaNaoEncontradaException;
import dirijamais.project.dirijamais.aplicacao.filtros.DynamicSpecifications;
import dirijamais.project.dirijamais.aplicacao.filtros.dto.FiltroDTO;
import dirijamais.project.dirijamais.modulos.transacao.models.TransacaoEntrada;
import dirijamais.project.dirijamais.modulos.transacao.repositors.TransacaoEntradaRepository;
import dirijamais.project.dirijamais.modulos.transacao.services.ITransacaoEntradaService;
import dirijamais.project.dirijamais.modulos.usuario.services.implementacao.UsuarioService;

@Service
public class TransacaoEntradaService implements ITransacaoEntradaService {

    @Autowired
    private TransacaoEntradaRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public TransacaoEntrada adicionar(TransacaoEntrada transacaoEntrada) {
        usuarioService.buscarPorUuid(transacaoEntrada.getUsuario().getUuid());
        transacaoEntrada = repository.save(transacaoEntrada);
        return transacaoEntrada;
    }

    @Override
    public TransacaoEntrada buscarPorUuid(UUID uuid) {
        TransacaoEntrada transacaoEntrada = repository.findById(uuid).orElseThrow(() -> {
            throw new TransacaoEntradaNaoEncontradaException();
        });

        return transacaoEntrada;
    }

    @Override
    public TransacaoEntrada atualizar(UUID usuarioUuid, TransacaoEntrada transacaoEntrada) {
        usuarioService.buscarPorUuid(transacaoEntrada.getUsuario().getUuid());
        TransacaoEntrada transacao = buscarPorUuid(transacaoEntrada.getUuid());
        
        if (transacaoEntrada.getAtivo() != null) {
            transacao.setAtivo(transacaoEntrada.getAtivo());
        }

        if (transacaoEntrada.getData() != null) {
            transacao.setData(transacaoEntrada.getData());
            transacao.setDiaSemana(transacaoEntrada.getDiaSemana());
        }

        if (transacaoEntrada.getHorasTrabalhados() != null) {
            transacao.setHorasTrabalhados(transacaoEntrada.getHorasTrabalhados());
        }

        if (transacaoEntrada.getKmRodados() != null) {
            transacao.setKmRodados(transacaoEntrada.getKmRodados());
        }

        if (transacaoEntrada.getObservacao() != null) {
            transacao.setObservacao(transacaoEntrada.getObservacao());
        }

        if (transacaoEntrada.getOrigem() != null) {
            transacao.setOrigem(transacaoEntrada.getOrigem());
        }

        if (transacaoEntrada.getQuantidadeDeViagens() != null) {
            transacao.setQuantidadeDeViagens(transacaoEntrada.getQuantidadeDeViagens());
        }

        if (transacaoEntrada.getValorEntrada() != null) {
            transacao.setValorEntrada(transacaoEntrada.getValorEntrada());
        }

        return transacao;
    }

    @Override
    public Page<TransacaoEntrada> pesquisar(FiltroDTO filtro, Pageable pageable) {
        Page<TransacaoEntrada> transacaoEntrada = repository.findAll(DynamicSpecifications.bySearchFilter(filtro.getFilters(), filtro.getOrders()), pageable);
		List<TransacaoEntrada> responses = transacaoEntrada
				.getContent()
				.stream()
				.collect(Collectors.toList());
		Page<TransacaoEntrada> response = new PageImpl(responses, transacaoEntrada.getPageable(), transacaoEntrada.getTotalElements());
		return response;
    }

    @Override
    public void deletar(UUID uuid) {
        TransacaoEntrada transacaoEntrada = buscarPorUuid(uuid);
        repository.delete(transacaoEntrada);
    }

    @Override
    public List<TransacaoEntrada> buscarEntradasPorFiltro(FiltroDTO filtro, Pageable pageable) {
        Page<TransacaoEntrada> transacaoEntrada = repository.findAll(DynamicSpecifications.bySearchFilter(filtro.getFilters(), filtro.getOrders()), pageable);
		List<TransacaoEntrada> responses = transacaoEntrada
				.getContent()
				.stream()
				.collect(Collectors.toList());

        return responses;
    }


}
