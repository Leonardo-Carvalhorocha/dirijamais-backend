package dirijamais.project.dirijamais.modulos.usuario.services.implementacao;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dirijamais.project.dirijamais.aplicacao.exception.EmailJaCadastradoException;
import dirijamais.project.dirijamais.aplicacao.exception.UsuarioNaoEncontradoException;
import dirijamais.project.dirijamais.aplicacao.filtros.DynamicSpecifications;
import dirijamais.project.dirijamais.aplicacao.filtros.dto.FiltroDTO;
import dirijamais.project.dirijamais.modulos.usuario.models.Usuario;
import dirijamais.project.dirijamais.modulos.usuario.repositors.UsuarioRepository;
import dirijamais.project.dirijamais.modulos.usuario.services.IUsuarioService;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario adicionar(Usuario usuario) {

        if(repository.existsByEmail(usuario.getEmail())) {
            throw new EmailJaCadastradoException("Email já está em uso.");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return repository.save(usuario);
    }

    @Override
    public Usuario buscarPorUuid(UUID uuid) {
        return repository.findById(uuid).orElseThrow(() -> {
            throw new UsuarioNaoEncontradoException();
        });
    }

    @Override
    public Usuario atualizar(Usuario usuario) {    

        if (repository.existsByEmailAndUuidNot(usuario.getEmail(), usuario.getUuid())) {
            throw new EmailJaCadastradoException("Email já está em uso.");
        }

        Usuario usuarioEncontrado = buscarPorUuid(usuario.getUuid());
        usuarioEncontrado.setCpf(usuario.getCpf());
        usuarioEncontrado.setDataNascimento(usuario.getDataNascimento());
        usuarioEncontrado.setEmail(usuario.getEmail());
        usuarioEncontrado.setTelefone(usuario.getTelefone());
        usuarioEncontrado.setAtivo(usuario.getAtivo());
        usuarioEncontrado.setNome(usuario.getNome());

        usuarioEncontrado = repository.save(usuarioEncontrado);

        return usuarioEncontrado;
    }

    @Override
    public Page<Usuario> pesquisar(FiltroDTO filtro, Pageable pageable) {
        Page<Usuario> usuarios = repository.findAll(DynamicSpecifications.bySearchFilter(filtro.getFilters(), filtro.getOrders()), pageable);
		List<Usuario> responses = usuarios
				.getContent()
				.stream()
				.collect(Collectors.toList());
		Page<Usuario> response = new PageImpl(responses, usuarios.getPageable(), usuarios.getTotalElements());
		return response;
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        Usuario usuario = repository.findByEmail(email).orElseThrow(() -> {
            throw new UsuarioNaoEncontradoException();
        });
        return usuario;
    }

}
