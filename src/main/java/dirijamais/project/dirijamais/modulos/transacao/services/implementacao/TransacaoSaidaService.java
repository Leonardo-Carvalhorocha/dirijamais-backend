package dirijamais.project.dirijamais.modulos.transacao.services.implementacao;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dirijamais.project.dirijamais.aplicacao.exception.TransacaoSaidaNaoEncontradaException;
import dirijamais.project.dirijamais.aplicacao.filtros.DynamicSpecifications;
import dirijamais.project.dirijamais.aplicacao.filtros.dto.FiltroDTO;
import dirijamais.project.dirijamais.modulos.transacao.models.TransacaoSaida;
import dirijamais.project.dirijamais.modulos.transacao.repositors.TransacaoSaidaRepository;
import dirijamais.project.dirijamais.modulos.transacao.services.ITransacaoSaidaService;
import dirijamais.project.dirijamais.modulos.usuario.services.implementacao.UsuarioService;

@Service
public class TransacaoSaidaService implements ITransacaoSaidaService {

    @Autowired
    private TransacaoSaidaRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public TransacaoSaida adicionar(TransacaoSaida transacaoSaida) {
        usuarioService.buscarPorUuid(transacaoSaida.getUsuario().getUuid());
        return repository.save(transacaoSaida);
    }

    @Override
    public TransacaoSaida buscarPorUuid(UUID uuid) {
        return repository.findById(uuid)
                .orElseThrow(TransacaoSaidaNaoEncontradaException::new);
    }

    @Override
    public TransacaoSaida atualizar(UUID usuarioUuid, TransacaoSaida transacaoSaida) {
        usuarioService.buscarPorUuid(transacaoSaida.getUsuario().getUuid());

        TransacaoSaida transacao = buscarPorUuid(transacaoSaida.getUuid());

        if (transacaoSaida.getData() != null) {
            transacao.setData(transacaoSaida.getData());
            transacao.setDiaSemana(transacaoSaida.getDiaSemana());
        }

        if (transacaoSaida.getDescricaoGasto() != null) {
            transacao.setDescricaoGasto(transacaoSaida.getDescricaoGasto());
        }

        if (transacaoSaida.getValor() != null) {
            transacao.setValor(transacaoSaida.getValor());
        }

        if (transacaoSaida.getCategoria() != null) {
            transacao.setCategoria(transacaoSaida.getCategoria());
        }

        if (transacaoSaida.getTipoGasto() != null) {
            transacao.setTipoGasto(transacaoSaida.getTipoGasto());
        }

        if (transacaoSaida.getTipoPagamento() != null) {
            transacao.setTipoPagamento(transacaoSaida.getTipoPagamento());
        }

        return transacao;
    }

    @Override
    public Page<TransacaoSaida> pesquisar(FiltroDTO filtro, Pageable pageable) {
        Page<TransacaoSaida> page = repository.findAll(
                DynamicSpecifications.bySearchFilter(
                        filtro.getFilters(),
                        filtro.getOrders()
                ),
                pageable
        );

        return new PageImpl<>(
                page.getContent(),
                page.getPageable(),
                page.getTotalElements()
        );
    }

    @Override
    public void deletar(UUID uuid) {
        TransacaoSaida transacaoSaida = buscarPorUuid(uuid);
        repository.delete(transacaoSaida);
    }
}

