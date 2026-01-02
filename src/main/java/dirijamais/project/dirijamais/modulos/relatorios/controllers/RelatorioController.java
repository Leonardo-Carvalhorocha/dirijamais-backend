package dirijamais.project.dirijamais.modulos.relatorios.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dirijamais.project.dirijamais.aplicacao.filtros.dto.FiltroDTO;
import dirijamais.project.dirijamais.modulos.relatorios.models.Relatorio;
import dirijamais.project.dirijamais.modulos.relatorios.services.IRelatorioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/relatorio")
public class RelatorioController {

    @Autowired
    IRelatorioService service;

    @PostMapping()
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Relatorio buscarRelatorio(@RequestBody FiltroDTO filtro) {
        Relatorio relatorio = service.buscarRelatorio(filtro, PageRequest.of((filtro.getPage()), filtro.getPagesize()));
        return relatorio;
    }
    

}
