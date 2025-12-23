package dirijamais.project.dirijamais.modulos.veiculo.services.implementacao;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dirijamais.project.dirijamais.aplicacao.exception.VeiculoNaoEncontradoException;
import dirijamais.project.dirijamais.aplicacao.filtros.DynamicSpecifications;
import dirijamais.project.dirijamais.aplicacao.filtros.dto.FiltroDTO;
import dirijamais.project.dirijamais.modulos.usuario.models.Usuario;
import dirijamais.project.dirijamais.modulos.usuario.services.implementacao.UsuarioService;
import dirijamais.project.dirijamais.modulos.veiculo.models.Veiculo;
import dirijamais.project.dirijamais.modulos.veiculo.repositors.VeiculoRepository;
import dirijamais.project.dirijamais.modulos.veiculo.services.IVeiculoService;

@Service
public class VeiculoService implements IVeiculoService {


    @Autowired
    private VeiculoRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    // @Transactional
    @Override
    public Veiculo adicionar(Veiculo veiculo) {
        Usuario usuario = usuarioService.buscarPorUuid(veiculo.getUsuario().getUuid());
        veiculo.setUsuario(usuario);

        if (Boolean.TRUE.equals(veiculo.getAtivo())) {
            repository.findByUsuarioUuidAndAtivoTrue(veiculo.getUsuario().getUuid())
            .ifPresent(v -> {
                v.setAtivo(false);
                repository.save(v);
            });
        }

        veiculo = repository.save(veiculo);

        return veiculo;
    }

    @Override
    public Veiculo buscarPorUuid(UUID uuid) {
        Veiculo veiculo = repository.findById(uuid).orElseThrow(() -> {
            throw new VeiculoNaoEncontradoException();
        });
        return veiculo;
    }

    @Override
    public Veiculo atualizar(Veiculo veiculo) {
         Veiculo veiculoPersistido = buscarPorUuid(veiculo.getUuid());

        if (veiculo.getUsuario() != null && veiculo.getUsuario().getUuid() != null) {
            Usuario usuario = usuarioService.buscarPorUuid(
                veiculo.getUsuario().getUuid()
            );
            veiculoPersistido.setUsuario(usuario);
        }

        veiculoPersistido.setTipoVeiculo(veiculo.getTipoVeiculo());
        veiculoPersistido.setSituacaoVeiculo(veiculo.getSituacaoVeiculo());
        veiculoPersistido.setTipoCombustivel(veiculo.getTipoCombustivel());
        veiculoPersistido.setConsumoMedioKmPorLitro(veiculo.getConsumoMedioKmPorLitro());
        veiculoPersistido.setSeguroVeiculo(veiculo.getSeguroVeiculo());

        if (Boolean.TRUE.equals(veiculo.getAtivo()) && !Boolean.TRUE.equals(veiculoPersistido.getAtivo())) {
            repository.findByUsuarioUuidAndAtivoTrue(
                    veiculoPersistido.getUsuario().getUuid()
                )
                .ifPresent(v -> {
                    v.setAtivo(false);
                    repository.save(v);
                });

            veiculoPersistido.setAtivo(true);
        }

        return repository.save(veiculoPersistido);
    }

    @Override
    public Page<Veiculo> pesquisar(FiltroDTO filtro, Pageable pageable) {
        Page<Veiculo> veiculos = repository.findAll(DynamicSpecifications.bySearchFilter(filtro.getFilters(), filtro.getOrders()), pageable);
		List<Veiculo> responses = veiculos
				.getContent()
				.stream()
				.collect(Collectors.toList());
		Page<Veiculo> response = new PageImpl(responses, veiculos.getPageable(), veiculos.getTotalElements());
		return response;
    }

}
