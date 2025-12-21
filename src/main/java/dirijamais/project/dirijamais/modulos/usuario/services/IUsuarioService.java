package dirijamais.project.dirijamais.modulos.usuario.services;

import java.util.UUID;

import dirijamais.project.dirijamais.aplicacao.filtros.dto.FiltroDTO;
import dirijamais.project.dirijamais.modulos.usuario.models.Usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsuarioService {

    Usuario adicionar(Usuario usuario);

    Usuario buscarPorUuid(UUID uuid);

    Usuario atualizar(Usuario usuario);

    Page<Usuario> pesquisar(FiltroDTO filtro, Pageable pageable);

}
