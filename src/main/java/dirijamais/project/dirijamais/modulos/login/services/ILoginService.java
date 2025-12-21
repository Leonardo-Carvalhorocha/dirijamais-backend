package dirijamais.project.dirijamais.modulos.login.services;

// import dirijamais.project.dirijamais.modulos.login.dtos.RegistroUsuarioLoginDTO;
import dirijamais.project.dirijamais.modulos.login.dtos.UsuarioLoginDTO;
import dirijamais.project.dirijamais.modulos.usuario.models.Usuario;

public interface ILoginService {

    public Usuario authenticate(UsuarioLoginDTO usuarioLoginDTO);
    
}
