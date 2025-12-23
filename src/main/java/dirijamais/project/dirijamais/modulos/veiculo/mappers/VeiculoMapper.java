package dirijamais.project.dirijamais.modulos.veiculo.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dirijamais.project.dirijamais.modulos.veiculo.dtos.VeiculoRequestModel;
import dirijamais.project.dirijamais.modulos.veiculo.dtos.VeiculoResponseModel;
import dirijamais.project.dirijamais.modulos.veiculo.models.Veiculo;

@Component
public class VeiculoMapper {
    private static ModelMapper mapper;

    @Autowired
    public void setModelMppaer(ModelMapper mapper) {
        VeiculoMapper.mapper = mapper;
    }

    public Veiculo toDomain(VeiculoRequestModel veiculoRequestModel) {
        return mapper.map(veiculoRequestModel, Veiculo.class);
	}

	public void mapToDomain(VeiculoRequestModel veiculoRequestModel, Veiculo veiculo) {
		mapper.map(veiculoRequestModel, veiculo);
	}

	public VeiculoResponseModel toResponseModel(Veiculo veiculo) {
		return mapper.map(veiculo, VeiculoResponseModel.class);
	}

	public List<VeiculoResponseModel> toResponseModel(List<Veiculo> veiculos) {
		return veiculos
				.stream()
				.map(m -> toResponseModel(m))
				.collect(Collectors.toList());		
	}
}
