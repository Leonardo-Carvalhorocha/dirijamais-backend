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
import dirijamais.project.dirijamais.aplicacao.models.Parcelamento;
import dirijamais.project.dirijamais.modulos.usuario.models.Usuario;
import dirijamais.project.dirijamais.modulos.usuario.services.implementacao.UsuarioService;
import dirijamais.project.dirijamais.modulos.veiculo.models.FranquiaAluguel;
import dirijamais.project.dirijamais.modulos.veiculo.models.Veiculo;
import dirijamais.project.dirijamais.modulos.veiculo.repositors.VeiculoRepository;
import dirijamais.project.dirijamais.modulos.veiculo.services.IVeiculoService;

/**
 * Classe responsável por gerenciar veículos.
 *
 * @author Leonardo Carvalho
 */
@Service
public class VeiculoService implements IVeiculoService {


    @Autowired
    private VeiculoRepository repository;

    @Autowired
    private UsuarioService usuarioService;

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

        ajustarObjetosPorSituacao(veiculo);

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

                                                                                      
// $$\      $$\ $$$$$$$$\ $$$$$$$$\  $$$$$$\  $$$$$$$\   $$$$$$\   $$$$$$\         $$$$$$\  $$$$$$$$\ $$$$$$$\   $$$$$$\  $$$$$$\  $$$$$$\  
// $$$\    $$$ |$$  _____|\__$$  __|$$  __$$\ $$  __$$\ $$  __$$\ $$  __$$\       $$  __$$\ $$  _____|$$  __$$\ $$  __$$\ \_$$  _|$$  __$$\ 
// $$$$\  $$$$ |$$ |         $$ |   $$ /  $$ |$$ |  $$ |$$ /  $$ |$$ /  \__|      $$ /  \__|$$ |      $$ |  $$ |$$ /  $$ |  $$ |  $$ /  \__|
// $$\$$\$$ $$ |$$$$$\       $$ |   $$ |  $$ |$$ |  $$ |$$ |  $$ |\$$$$$$\        $$ |$$$$\ $$$$$\    $$$$$$$  |$$$$$$$$ |  $$ |  \$$$$$$\  
// $$ \$$$  $$ |$$  __|      $$ |   $$ |  $$ |$$ |  $$ |$$ |  $$ | \____$$\       $$ |\_$$ |$$  __|   $$  __$$< $$  __$$ |  $$ |   \____$$\ 
// $$ |\$  /$$ |$$ |         $$ |   $$ |  $$ |$$ |  $$ |$$ |  $$ |$$\   $$ |      $$ |  $$ |$$ |      $$ |  $$ |$$ |  $$ |  $$ |  $$\   $$ |
// $$ | \_/ $$ |$$$$$$$$\    $$ |    $$$$$$  |$$$$$$$  | $$$$$$  |\$$$$$$  |      \$$$$$$  |$$$$$$$$\ $$ |  $$ |$$ |  $$ |$$$$$$\ \$$$$$$  |
// \__|     \__|\________|   \__|    \______/ \_______/  \______/  \______/        \______/ \________|\__|  \__|\__|  \__|\______| \______/ 
                                                                                                                                                                                                        

    private void ajustarObjetosPorSituacao(Veiculo veiculo) {
        if (isEmpty(veiculo.getSeguroVeiculo())) {
            veiculo.setSeguroVeiculo(null);
        }

        switch (veiculo.getSituacaoVeiculo()) {

            case ALUGADO -> {
                veiculo.setFinanciamentoVeiculo(null);

                if (isEmpty(veiculo.getFranquiaAluguel())) {
                    veiculo.setFranquiaAluguel(null);
                }
            }

            case FINANCIADO -> {
                veiculo.setFranquiaAluguel(null);

                if (isEmpty(veiculo.getFinanciamentoVeiculo())) {
                    veiculo.setFinanciamentoVeiculo(null);
                }
            }

            case PROPRIO -> {
                veiculo.setFranquiaAluguel(null);
                veiculo.setFinanciamentoVeiculo(null);
            }
        }
    }

    private boolean isEmpty(FranquiaAluguel franquia) {
        if (franquia == null) return true;

        return franquia.getValorSemanal() == null && franquia.getKmPermitidosSemanal() == null;
    }

    private boolean isEmpty(Parcelamento parcelamento) {
        if (parcelamento == null) return true;

        return parcelamento.getValorMensal() == null
            && parcelamento.getQuantidadeParcelas() == null
            && parcelamento.getInicioVigencia() == null
            && parcelamento.getFimVigencia() == null;
    }

}
