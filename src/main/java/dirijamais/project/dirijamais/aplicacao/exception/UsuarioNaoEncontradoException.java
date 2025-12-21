package dirijamais.project.dirijamais.aplicacao.exception;

import jakarta.persistence.EntityNotFoundException;

public class UsuarioNaoEncontradoException extends EntityNotFoundException {
    
    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }

    public UsuarioNaoEncontradoException() {
        super("Usuário não encontrado.");
    }

}
