package dirijamais.project.dirijamais.modulos.veiculo.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dirijamais.project.dirijamais.aplicacao.filtros.dto.FiltroDTO;
import dirijamais.project.dirijamais.modulos.veiculo.dtos.VeiculoRequestModel;
import dirijamais.project.dirijamais.modulos.veiculo.dtos.VeiculoResponseModel;
import dirijamais.project.dirijamais.modulos.veiculo.mappers.VeiculoMapper;
import dirijamais.project.dirijamais.modulos.veiculo.models.Veiculo;
import dirijamais.project.dirijamais.modulos.veiculo.services.IVeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/veiculos")
public class VeiculoController {

    @Autowired
    private IVeiculoService service;

    @Autowired
    private VeiculoMapper mapper;

    @Operation(summary = "Criar veículo", description = "Adiciona um novo veículo no sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Veículo criado"),
    })
    @PostMapping()
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public VeiculoResponseModel adicionar(@RequestBody VeiculoRequestModel request) {
        Veiculo veiculo = mapper.toDomain(request);
        veiculo = service.adicionar(veiculo);
        VeiculoResponseModel veiculoResponseModel = mapper.toResponseModel(veiculo);
        return veiculoResponseModel;
    }

    @Operation(summary = "Buscar veículo", description = "Busca o veículo no sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Veículo encontrado"),
    })
    @GetMapping("/{uuid}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public VeiculoResponseModel buscarPorUuid(@PathVariable UUID uuid) {
        Veiculo veiculo = service.buscarPorUuid(uuid);
        VeiculoResponseModel veiculoResponseModel = mapper.toResponseModel(veiculo);
        return veiculoResponseModel;
    }

    @Operation(summary = "Atualiza um veículo", description = "Atualiza um veículo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Veículo atualizado"),
    })
    @PutMapping("/{uuid}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public VeiculoResponseModel atualizar(@PathVariable UUID uuid, @RequestBody VeiculoRequestModel request) {
        Veiculo veiculo = service.buscarPorUuid(uuid);
        veiculo = service.atualizar(veiculo);
        VeiculoResponseModel veiculoResponseModel = mapper.toResponseModel(veiculo);
        return veiculoResponseModel;
    }
    
    @Operation(summary = "Buscar lista de veículos", description = "Faz uma busca pelo banco passando o fiterDTO")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Veículos encontrados"),
    })
    @PostMapping("/filter")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<VeiculoResponseModel> pesquisar(@RequestBody FiltroDTO filtro) {
        Page<Veiculo> veiculos = service.pesquisar(filtro, PageRequest.of((filtro.getPage()), filtro.getPagesize()));
		List<VeiculoResponseModel> lista = mapper.toResponseModel(veiculos.getContent());
		return new PageImpl<>(lista, veiculos.getPageable(), veiculos.getTotalElements());
    }
    
}
