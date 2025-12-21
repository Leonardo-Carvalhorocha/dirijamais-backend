package dirijamais.project.dirijamais.modulos.usuario.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dirijamais.project.dirijamais.aplicacao.filtros.dto.FiltroDTO;
import dirijamais.project.dirijamais.modulos.usuario.dtos.UsuarioRequestModel;
import dirijamais.project.dirijamais.modulos.usuario.dtos.UsuarioResponseModel;
import dirijamais.project.dirijamais.modulos.usuario.mappers.UsuarioMapper;
import dirijamais.project.dirijamais.modulos.usuario.models.Usuario;
import dirijamais.project.dirijamais.modulos.usuario.services.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    @Autowired
    private IUsuarioService service;

    @Autowired
    private UsuarioMapper mapper;

    @Operation(summary = "Criar usuário", description = "Adiciona um novo usuário no sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Uusário criado"),
    })
    @PostMapping()
    public UsuarioResponseModel adicionar(@RequestBody UsuarioRequestModel request) {
        Usuario usuario = mapper.toDomain(request);
        usuario = service.adicionar(usuario);
        UsuarioResponseModel usuarioResponseModel = mapper.toResponseModel(usuario);
        return usuarioResponseModel;
    }

    @Operation(summary = "Buscar usuário por UUID", description = "Retorna um usuário pelo UUID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{uuid}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public UsuarioResponseModel buscarPorUuid(@PathVariable UUID uuid) {
        Usuario usuario = service.buscarPorUuid(uuid);
        UsuarioResponseModel usuarioResponseModel = mapper.toResponseModel(usuario);
        return usuarioResponseModel;
    }

    
    @Operation(summary = "Atualizar usuário", description = "Atualiza um usuário existente no sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
    })
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{uuid}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public UsuarioResponseModel atualizar(@PathVariable UUID uuid, @RequestBody UsuarioRequestModel requestModel) {
        Usuario usuario = mapper.toDomain(requestModel);
        usuario.setUuid(uuid);
        Usuario usuarioAtualizado = service.atualizar(usuario);
        UsuarioResponseModel usuarioResponseModel = mapper.toResponseModel(usuarioAtualizado); 
        return usuarioResponseModel;
    }

    @Operation(
        summary = "Pesquisar usuários",
        description = "Retorna uma lista paginada de usuários com base nos filtros informados. Somente usuário com ROLE_ADMIN pode fazer a pesquisa"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pesquisa realizada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/filter")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UsuarioResponseModel> pesquisar(@RequestBody FiltroDTO filtro) {
		Page<Usuario> usuarios = service.pesquisar(filtro, PageRequest.of((filtro.getPage()), filtro.getPagesize()));
		List<UsuarioResponseModel> lista = mapper.toResponseModel(usuarios.getContent());
		return new PageImpl<>(lista, usuarios.getPageable(), usuarios.getTotalElements());
	}
    

}
