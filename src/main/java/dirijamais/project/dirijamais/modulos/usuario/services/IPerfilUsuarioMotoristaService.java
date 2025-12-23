package dirijamais.project.dirijamais.modulos.usuario.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dirijamais.project.dirijamais.aplicacao.filtros.dto.FiltroDTO;
import dirijamais.project.dirijamais.modulos.usuario.models.PerfilUsuarioMotorista;

public interface IPerfilUsuarioMotoristaService {

    PerfilUsuarioMotorista adicionar(PerfilUsuarioMotorista perfilUsuarioMotorista);

    PerfilUsuarioMotorista buscarPorUuid(UUID uuid);

    PerfilUsuarioMotorista atualizar(UUID usuarioUuid, PerfilUsuarioMotorista perfilUsuarioMotorista);

    Page<PerfilUsuarioMotorista> pesquisar(FiltroDTO filtro, Pageable pageable);


}
