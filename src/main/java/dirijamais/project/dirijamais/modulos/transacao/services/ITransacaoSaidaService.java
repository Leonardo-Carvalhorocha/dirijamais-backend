package dirijamais.project.dirijamais.modulos.transacao.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dirijamais.project.dirijamais.aplicacao.filtros.dto.FiltroDTO;
import dirijamais.project.dirijamais.modulos.transacao.models.TransacaoSaida;

public interface ITransacaoSaidaService {
    TransacaoSaida adicionar(TransacaoSaida transacaoSaida);

    TransacaoSaida buscarPorUuid(UUID uuid);

    TransacaoSaida atualizar(UUID usuarioUuid, TransacaoSaida transacaoSaida);

    Page<TransacaoSaida> pesquisar(FiltroDTO filtro, Pageable pageable);

    List<TransacaoSaida> buscarSaidasPorFiltro(FiltroDTO filtro, Pageable pageable);

    void deletar(UUID uuid);
}
