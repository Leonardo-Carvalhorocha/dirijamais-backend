package dirijamais.project.dirijamais.modulos.transacao.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dirijamais.project.dirijamais.modulos.transacao.dtos.TransacaoSaidaRequestModel;
import dirijamais.project.dirijamais.modulos.transacao.dtos.TransacaoSaidaResponseModel;
import dirijamais.project.dirijamais.modulos.transacao.models.TransacaoSaida;

@Component
public class TransacaoSaidaMapper {
  private static ModelMapper mapper;

    @Autowired
    public void setModelMapper(ModelMapper mapper) {
        TransacaoSaidaMapper.mapper = mapper;
    }

    public TransacaoSaida toDomain(TransacaoSaidaRequestModel transacaoSaidaRequestModel) {
        return mapper.map(transacaoSaidaRequestModel, TransacaoSaida.class);
    }

    public void mapToDomain(TransacaoSaidaRequestModel transacaoSaidaRequestModel, TransacaoSaida transacaoSaida) {
        mapper.map(transacaoSaidaRequestModel, transacaoSaida);
    }

    public TransacaoSaidaResponseModel toResponseModel(TransacaoSaida transacaoSaida) {
        return mapper.map(transacaoSaida, TransacaoSaidaResponseModel.class);
    }

    public List<TransacaoSaidaResponseModel> toResponseModel(List<TransacaoSaida> transacaoSaidas) {
        return transacaoSaidas
                .stream()
                .map(this::toResponseModel)
                .collect(Collectors.toList());
    }
}
