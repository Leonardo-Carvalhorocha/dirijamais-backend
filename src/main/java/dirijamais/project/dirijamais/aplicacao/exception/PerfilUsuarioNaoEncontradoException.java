package dirijamais.project.dirijamais.aplicacao.exception;

import jakarta.persistence.EntityNotFoundException;

public class PerfilUsuarioNaoEncontradoException extends EntityNotFoundException {
    public PerfilUsuarioNaoEncontradoException(String message) {
        super(message);
    }

    public PerfilUsuarioNaoEncontradoException() {
        super("Perfil usuário não encontrado.");
    }
}
