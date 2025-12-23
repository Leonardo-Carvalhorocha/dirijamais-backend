package dirijamais.project.dirijamais.modulos.transacao.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dirijamais.project.dirijamais.modulos.transacao.dtos.TransacaoEntradaRequestModel;
import dirijamais.project.dirijamais.modulos.transacao.dtos.TransacaoEntradaResponseModel;
import dirijamais.project.dirijamais.modulos.transacao.models.TransacaoEntrada;

@Component
public class TransacaoEntradaMapper {
    private static ModelMapper mapper;

    @Autowired
    public void setModelMppaer(ModelMapper mapper) {
        TransacaoEntradaMapper.mapper = mapper;
    }

    public TransacaoEntrada toDomain(TransacaoEntradaRequestModel transacaoEntradaRequestModel) {
        return mapper.map(transacaoEntradaRequestModel, TransacaoEntrada.class);
	}

	public void mapToDomain(TransacaoEntradaRequestModel transacaoEntradaRequestModel, TransacaoEntrada transacaoEntrada) {
		mapper.map(transacaoEntradaRequestModel, transacaoEntrada);
	}

	public TransacaoEntradaResponseModel toResponseModel(TransacaoEntrada transacaoEntrada) {
		return mapper.map(transacaoEntrada, TransacaoEntradaResponseModel.class);
	}

	public List<TransacaoEntradaResponseModel> toResponseModel(List<TransacaoEntrada> transacaoEntradas) {
		return transacaoEntradas
				.stream()
				.map(m -> toResponseModel(m))
				.collect(Collectors.toList());		
	}
}
