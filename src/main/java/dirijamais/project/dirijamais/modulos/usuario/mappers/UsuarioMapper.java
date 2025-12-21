package dirijamais.project.dirijamais.modulos.usuario.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dirijamais.project.dirijamais.modulos.usuario.dtos.UsuarioRequestModel;
import dirijamais.project.dirijamais.modulos.usuario.dtos.UsuarioResponseModel;
import dirijamais.project.dirijamais.modulos.usuario.models.Usuario;

@Component
public class UsuarioMapper {
    private static ModelMapper mapper;

    @Autowired
    public void setModelMppaer(ModelMapper mapper) {
        UsuarioMapper.mapper = mapper;
    }

    public Usuario toDomain(UsuarioRequestModel UsuarioRequestModel) {
        return mapper.map(UsuarioRequestModel, Usuario.class);
	}

	public void mapToDomain(UsuarioRequestModel UsuarioRequestModel, Usuario usuario) {
		mapper.map(UsuarioRequestModel, usuario);
	}

	public UsuarioResponseModel toResponseModel(Usuario usuario) {
		return mapper.map(usuario, UsuarioResponseModel.class);
	}

	public List<UsuarioResponseModel> toResponseModel(List<Usuario> usuarios) {
		return usuarios
				.stream()
				.map(m -> toResponseModel(m))
				.collect(Collectors.toList());		
	}
}
