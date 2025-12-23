package dirijamais.project.dirijamais.modulos.transacao.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dirijamais.project.dirijamais.aplicacao.filtros.dto.FiltroDTO;
import dirijamais.project.dirijamais.modulos.transacao.models.TransacaoEntrada;

public interface ITransacaoEntradaService {
    TransacaoEntrada adicionar(TransacaoEntrada transacaoEntrada);

    TransacaoEntrada buscarPorUuid(UUID uuid);

    TransacaoEntrada atualizar(UUID usuarioUuid, TransacaoEntrada transacaoEntrada);

    Page<TransacaoEntrada> pesquisar(FiltroDTO filtro, Pageable pageable);

    void deletar(UUID uuid);
}
