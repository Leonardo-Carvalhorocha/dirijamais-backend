package dirijamais.project.dirijamais.modulos.usuario.services.implementacao;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dirijamais.project.dirijamais.aplicacao.exception.JaExistePerfilUsuarioMotorista;
import dirijamais.project.dirijamais.aplicacao.exception.PerfilUsuarioNaoEncontradoException;
import dirijamais.project.dirijamais.aplicacao.filtros.DynamicSpecifications;
import dirijamais.project.dirijamais.aplicacao.filtros.dto.FiltroDTO;
import dirijamais.project.dirijamais.modulos.usuario.models.PerfilUsuarioMotorista;
import dirijamais.project.dirijamais.modulos.usuario.models.Usuario;
import dirijamais.project.dirijamais.modulos.usuario.repositors.PerfilUsuarioMotoristaRepository;
import dirijamais.project.dirijamais.modulos.usuario.services.IPerfilUsuarioMotoristaService;
import jakarta.transaction.Transactional;

@Service
public class PerfilUsuarioMotoristaService implements IPerfilUsuarioMotoristaService {

    @Autowired
    private PerfilUsuarioMotoristaRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public PerfilUsuarioMotorista adicionar(PerfilUsuarioMotorista perfilUsuarioMotorista) {
        Usuario usuario = usuarioService.buscarPorUuid(perfilUsuarioMotorista.getUsuario().getUuid());

        if(usuario.getPerfilMotorista() != null) {
            throw new JaExistePerfilUsuarioMotorista("Já existe um perfil para este usuário.");
        }

        perfilUsuarioMotorista.setUsuario(usuario);
        PerfilUsuarioMotorista perfil = repository.save(perfilUsuarioMotorista);
        return perfil;
    }

    @Override
    public PerfilUsuarioMotorista buscarPorUuid(UUID uuid) {
        PerfilUsuarioMotorista perfilUsuarioMotorista = repository.findById(uuid).orElseThrow(() -> {
            throw new PerfilUsuarioNaoEncontradoException();
        });
        return perfilUsuarioMotorista;
    }

    @Override
    @Transactional
    public PerfilUsuarioMotorista atualizar(UUID usuarioUuid, PerfilUsuarioMotorista perfilUsuarioMotorista) {

        Usuario usuario = usuarioService.buscarPorUuid(usuarioUuid);

        PerfilUsuarioMotorista perfil = usuario.getPerfilMotorista();

        if (perfil == null) {
            perfil = new PerfilUsuarioMotorista();
        }

        perfil.setMetaMensalLiquida(perfilUsuarioMotorista.getMetaMensalLiquida());
        perfil.setDiasTrabalhadoPorSemana(perfilUsuarioMotorista.getDiasTrabalhadoPorSemana());
        perfil.setHorasTrabalhadoPorDia(perfilUsuarioMotorista.getHorasTrabalhadoPorDia());

        usuario.setPerfilMotorista(perfil);

        usuarioService.atualizar(usuario);

        return perfil;
    }

    @Override
    public Page<PerfilUsuarioMotorista> pesquisar(FiltroDTO filtro, Pageable pageable) {
        Page<PerfilUsuarioMotorista> perfilUsuariosMotoristas = repository.findAll(DynamicSpecifications.bySearchFilter(filtro.getFilters(), filtro.getOrders()), pageable);
		List<PerfilUsuarioMotorista> responses = perfilUsuariosMotoristas
				.getContent()
				.stream()
				.collect(Collectors.toList());
		Page<PerfilUsuarioMotorista> response = new PageImpl(responses, perfilUsuariosMotoristas.getPageable(), perfilUsuariosMotoristas.getTotalElements());
		return response;
    }

}
