package dirijamais.project.dirijamais.modulos.transacao.controllers;

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
import dirijamais.project.dirijamais.modulos.transacao.dtos.TransacaoEntradaRequestModel;
import dirijamais.project.dirijamais.modulos.transacao.dtos.TransacaoEntradaResponseModel;
import dirijamais.project.dirijamais.modulos.transacao.mappers.TransacaoEntradaMapper;
import dirijamais.project.dirijamais.modulos.transacao.models.TransacaoEntrada;
import dirijamais.project.dirijamais.modulos.transacao.services.implementacao.TransacaoEntradaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/transacao-entrada")
public class TransacaoEntradaController {

    @Autowired
    private TransacaoEntradaService service;

    @Autowired
    private TransacaoEntradaMapper mapper;

    @Operation(summary = "Criar transação de entrada")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Transação de entrada criado"),
    })
    @PostMapping()
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public TransacaoEntradaResponseModel adicionar(@RequestBody TransacaoEntradaRequestModel request) {
        TransacaoEntrada transacaoEntrada = mapper.toDomain(request);
        transacaoEntrada = service.adicionar(transacaoEntrada);
        TransacaoEntradaResponseModel responseModel = mapper.toResponseModel(transacaoEntrada);
        return responseModel;
    }

    @GetMapping("/{uuid}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public TransacaoEntradaResponseModel buscarPorUuid(@PathVariable UUID uuid) {
        TransacaoEntrada transacaoEntrada = service.buscarPorUuid(uuid);
        TransacaoEntradaResponseModel transacaoEntradaResponseModel = mapper.toResponseModel(transacaoEntrada);
        return transacaoEntradaResponseModel;
    }

    @PutMapping("/{uuid}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public TransacaoEntradaResponseModel atualizar(@PathVariable UUID uuid, @RequestBody TransacaoEntradaRequestModel request) {
        TransacaoEntrada transacaoEntrada = mapper.toDomain(request);
        transacaoEntrada.setUuid(uuid);
        transacaoEntrada = service.atualizar(transacaoEntrada.getUsuario().getUuid(), transacaoEntrada);
        TransacaoEntradaResponseModel responseModel = mapper.toResponseModel(transacaoEntrada);
        return responseModel;
    }
    
    @PostMapping("/filter")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Page<TransacaoEntradaResponseModel> pesquisar(@RequestBody FiltroDTO filtro) {
		Page<TransacaoEntrada> transacaoEntrada = service.pesquisar(filtro, PageRequest.of((filtro.getPage()), filtro.getPagesize()));
		List<TransacaoEntradaResponseModel> lista = mapper.toResponseModel(transacaoEntrada.getContent());
		return new PageImpl<>(lista, transacaoEntrada.getPageable(), transacaoEntrada.getTotalElements());
	}

    @DeleteMapping("/{uuid}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void deletar(@PathVariable UUID uuid) {
        service.deletar(uuid);
    }

}
