package dirijamais.project.dirijamais.modulos.login.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dirijamais.project.dirijamais.aplicacao.services.JwtService;
import dirijamais.project.dirijamais.modulos.login.dtos.UsuarioLoginDTO;
import dirijamais.project.dirijamais.modulos.login.dtos.response.LoginResponse;
import dirijamais.project.dirijamais.modulos.login.services.ILoginService;
import dirijamais.project.dirijamais.modulos.usuario.dtos.UsuarioResponseModel;
import dirijamais.project.dirijamais.modulos.usuario.mappers.UsuarioMapper;
import dirijamais.project.dirijamais.modulos.usuario.models.Usuario;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    @Autowired
    private ILoginService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody UsuarioLoginDTO usuarioLoginDTO) {
        
        Usuario usuarioAutenticado = service.authenticate(usuarioLoginDTO);
        String jwtToken = jwtService.generateToken(usuarioAutenticado);

        UsuarioResponseModel usuarioResponseModel = usuarioMapper.toResponseModel(usuarioAutenticado);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setUsuario(usuarioResponseModel);

        return ResponseEntity.ok(loginResponse);
    }

}
