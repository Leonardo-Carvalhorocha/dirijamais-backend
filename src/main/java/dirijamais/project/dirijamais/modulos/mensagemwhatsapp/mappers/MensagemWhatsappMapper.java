package dirijamais.project.dirijamais.modulos.mensagemwhatsapp.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dirijamais.project.dirijamais.modulos.mensagemwhatsapp.dtos.MensagemWhatsappRequestModel;
import dirijamais.project.dirijamais.modulos.mensagemwhatsapp.dtos.MensagemWhatsappResponseModel;
import dirijamais.project.dirijamais.modulos.mensagemwhatsapp.models.MensagemWhatsapp;

@Component
public class MensagemWhatsappMapper {
      private static ModelMapper mapper;

    @Autowired
    public void setModelMppaer(ModelMapper mapper) {
        MensagemWhatsappMapper.mapper = mapper;
    }

    public MensagemWhatsapp toDomain(MensagemWhatsappRequestModel requestModel) {
        return mapper.map(requestModel, MensagemWhatsapp.class);
    }

    public void mapToDomain( MensagemWhatsappRequestModel requestModel, MensagemWhatsapp whatsappSchedule) {
        mapper.map(requestModel, whatsappSchedule);
    }

    public MensagemWhatsappResponseModel toResponseModel(MensagemWhatsapp whatsappSchedule) {
        return mapper.map(whatsappSchedule, MensagemWhatsappResponseModel.class);
    }

    public List<MensagemWhatsappResponseModel> toResponseModel(List<MensagemWhatsapp> mensagens) {
        return mensagens.stream()
                .map(this::toResponseModel)
                .collect(Collectors.toList());
    }
}
