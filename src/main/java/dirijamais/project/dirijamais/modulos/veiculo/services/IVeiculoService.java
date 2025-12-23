package dirijamais.project.dirijamais.modulos.veiculo.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dirijamais.project.dirijamais.aplicacao.filtros.dto.FiltroDTO;
import dirijamais.project.dirijamais.modulos.veiculo.models.Veiculo;

public interface IVeiculoService {

    Veiculo adicionar(Veiculo veiculo);

    Veiculo buscarPorUuid(UUID uuid);

    Veiculo atualizar(Veiculo veiculo);

    Page<Veiculo> pesquisar(FiltroDTO filtro, Pageable pageable);

}
