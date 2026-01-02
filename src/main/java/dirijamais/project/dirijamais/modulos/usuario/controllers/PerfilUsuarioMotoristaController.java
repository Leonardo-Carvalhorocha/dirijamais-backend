package dirijamais.project.dirijamais.modulos.usuario.controllers;

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
import dirijamais.project.dirijamais.modulos.usuario.dtos.PerfilUsuarioMotoristaRequestModel;
import dirijamais.project.dirijamais.modulos.usuario.dtos.PerfilUsuarioMotoristaResponseModel;
import dirijamais.project.dirijamais.modulos.usuario.mappers.PerfilUsuarioMotoristaMapper;
import dirijamais.project.dirijamais.modulos.usuario.models.PerfilUsuarioMotorista;
import dirijamais.project.dirijamais.modulos.usuario.services.IPerfilUsuarioMotoristaService;
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
@RequestMapping("/api/v1/perfil-motorista")
public class PerfilUsuarioMotoristaController {

    @Autowired
    private IPerfilUsuarioMotoristaService service;

    @Autowired
    private PerfilUsuarioMotoristaMapper mapper;

    @Operation(summary = "Criar perfil para motorista", description = "Criando um perfil para adicionar em um motorista")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Motorista criado"),
    })
    @PostMapping()
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public PerfilUsuarioMotoristaResponseModel adicionar(@RequestBody PerfilUsuarioMotoristaRequestModel request) {
        PerfilUsuarioMotorista perfilUsuarioMotorista = mapper.toDomain(request);
        perfilUsuarioMotorista = service.adicionar(perfilUsuarioMotorista);
        PerfilUsuarioMotoristaResponseModel perfilUsuarioMotoristaResponseModel = mapper.toResponseModel(perfilUsuarioMotorista);
        return perfilUsuarioMotoristaResponseModel;
    }
    
    @Operation(summary = "Buscar um perfil de motorista")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil encotrado"),
    })
    @GetMapping("/{uuid}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public PerfilUsuarioMotoristaResponseModel buscarPorUuid(@PathVariable UUID uuid) {
        PerfilUsuarioMotorista perfilUsuarioMotorista = service.buscarPorUuid(uuid);
        PerfilUsuarioMotoristaResponseModel perfilUsuarioMotoristaResponseModel = mapper.toResponseModel(perfilUsuarioMotorista);
        return perfilUsuarioMotoristaResponseModel;
    }
    
    @PutMapping("/{uuid}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public PerfilUsuarioMotoristaResponseModel atualizar(@PathVariable UUID uuid, @RequestBody PerfilUsuarioMotoristaRequestModel request) {
        PerfilUsuarioMotorista perfilUsuarioMotorista = mapper.toDomain(request);
        perfilUsuarioMotorista = service.atualizar(perfilUsuarioMotorista.getUsuario().getUuid(), perfilUsuarioMotorista);
        PerfilUsuarioMotoristaResponseModel responseModel = mapper.toResponseModel(perfilUsuarioMotorista);
        return responseModel;
    }

    @PostMapping("/filter")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<PerfilUsuarioMotoristaResponseModel> pesquisar(@RequestBody FiltroDTO filtro) {
        Page<PerfilUsuarioMotorista> perfilUsuariosMotoristas = service.pesquisar(filtro, PageRequest.of((filtro.getPage()), filtro.getPagesize()));
		List<PerfilUsuarioMotoristaResponseModel> lista = mapper.toResponseModel(perfilUsuariosMotoristas.getContent());
		return new PageImpl<>(lista, perfilUsuariosMotoristas.getPageable(), perfilUsuariosMotoristas.getTotalElements());
    }
    

}
