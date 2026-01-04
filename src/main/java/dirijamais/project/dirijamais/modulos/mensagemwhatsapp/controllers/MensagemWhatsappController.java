package dirijamais.project.dirijamais.modulos.mensagemwhatsapp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dirijamais.project.dirijamais.modulos.mensagemwhatsapp.dtos.MensagemWhatsappRequestModel;
import dirijamais.project.dirijamais.modulos.mensagemwhatsapp.dtos.MensagemWhatsappResponseModel;
import dirijamais.project.dirijamais.modulos.mensagemwhatsapp.mappers.MensagemWhatsappMapper;
import dirijamais.project.dirijamais.modulos.mensagemwhatsapp.models.MensagemWhatsapp;
import dirijamais.project.dirijamais.modulos.mensagemwhatsapp.services.implementacao.MensagemWhatsappService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/whatsapp")
public class MensagemWhatsappController {

    @Autowired
    private MensagemWhatsappService service;

    @Autowired
    private MensagemWhatsappMapper mapper;

    @PostMapping()
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public MensagemWhatsappResponseModel agendarMensagem(@RequestBody MensagemWhatsappRequestModel mensagemWhatsappRequestModel) {
        MensagemWhatsapp mensagemWhatsapp = mapper.toDomain(mensagemWhatsappRequestModel);
        mensagemWhatsapp = service.criar(mensagemWhatsapp);
        MensagemWhatsappResponseModel responseModel = mapper.toResponseModel(mensagemWhatsapp);
        return responseModel;
    }
    

}
