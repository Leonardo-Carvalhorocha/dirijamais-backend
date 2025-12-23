package dirijamais.project.dirijamais.modulos.usuario.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dirijamais.project.dirijamais.modulos.usuario.dtos.PerfilUsuarioMotoristaRequestModel;
import dirijamais.project.dirijamais.modulos.usuario.dtos.PerfilUsuarioMotoristaResponseModel;
import dirijamais.project.dirijamais.modulos.usuario.models.PerfilUsuarioMotorista;

@Component
public class PerfilUsuarioMotoristaMapper {
    private static ModelMapper mapper;

    @Autowired
    public void setModelMppaer(ModelMapper mapper) {
        PerfilUsuarioMotoristaMapper.mapper = mapper;
    }

    public PerfilUsuarioMotorista toDomain(PerfilUsuarioMotoristaRequestModel perfilUsuarioMotoristaRequestModel) {
        return mapper.map(perfilUsuarioMotoristaRequestModel, PerfilUsuarioMotorista.class);
	}

	public void mapToDomain(PerfilUsuarioMotoristaRequestModel PerfilUsuarioMotoristaRequestModel, PerfilUsuarioMotorista perfilUsuarioMotorista) {
		mapper.map(PerfilUsuarioMotoristaRequestModel, perfilUsuarioMotorista);
	}

	public PerfilUsuarioMotoristaResponseModel toResponseModel(PerfilUsuarioMotorista perfilUsuarioMotorista) {
		return mapper.map(perfilUsuarioMotorista, PerfilUsuarioMotoristaResponseModel.class);
	}

	public List<PerfilUsuarioMotoristaResponseModel> toResponseModel(List<PerfilUsuarioMotorista> perfilUsuarioMotoristas) {
		return perfilUsuarioMotoristas
				.stream()
				.map(m -> toResponseModel(m))
				.collect(Collectors.toList());		
	}
}
