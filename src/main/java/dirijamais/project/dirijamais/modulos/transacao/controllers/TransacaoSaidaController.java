package dirijamais.project.dirijamais.modulos.transacao.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dirijamais.project.dirijamais.aplicacao.filtros.dto.FiltroDTO;
import dirijamais.project.dirijamais.modulos.transacao.dtos.TransacaoSaidaRequestModel;
import dirijamais.project.dirijamais.modulos.transacao.dtos.TransacaoSaidaResponseModel;
import dirijamais.project.dirijamais.modulos.transacao.mappers.TransacaoSaidaMapper;
import dirijamais.project.dirijamais.modulos.transacao.models.TransacaoSaida;
import dirijamais.project.dirijamais.modulos.transacao.services.implementacao.TransacaoSaidaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/transacao-saida")
public class TransacaoSaidaController {

    @Autowired
    private TransacaoSaidaService service;

    @Autowired
    private TransacaoSaidaMapper mapper;

    @Operation(summary = "Criar transação de saída")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Transação de saída criada"),
    })
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public TransacaoSaidaResponseModel adicionar(@RequestBody TransacaoSaidaRequestModel request) {
        TransacaoSaida transacaoSaida = mapper.toDomain(request);
        transacaoSaida = service.adicionar(transacaoSaida);
        return mapper.toResponseModel(transacaoSaida);
    }

    @GetMapping("/{uuid}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public TransacaoSaidaResponseModel buscarPorUuid(@PathVariable UUID uuid) {
        TransacaoSaida transacaoSaida = service.buscarPorUuid(uuid);
        return mapper.toResponseModel(transacaoSaida);
    }

    @PutMapping("/{uuid}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public TransacaoSaidaResponseModel atualizar(
            @PathVariable UUID uuid,
            @RequestBody TransacaoSaidaRequestModel request) {

        TransacaoSaida transacaoSaida = mapper.toDomain(request);
        transacaoSaida.setUuid(uuid);

        transacaoSaida = service.atualizar(
                transacaoSaida.getUsuario().getUuid(),
                transacaoSaida
        );

        return mapper.toResponseModel(transacaoSaida);
    }

    @PostMapping("/filter")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Page<TransacaoSaidaResponseModel> pesquisar(@RequestBody FiltroDTO filtro) {
        Page<TransacaoSaida> page = service.pesquisar(filtro, PageRequest.of(filtro.getPage(), filtro.getPagesize()));
        List<TransacaoSaidaResponseModel> lista = mapper.toResponseModel(page.getContent());
        return new PageImpl<>(lista, page.getPageable(), page.getTotalElements());
    }

    @DeleteMapping("/{uuid}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void deletar(@PathVariable UUID uuid) {
        service.deletar(uuid);
    }
}

