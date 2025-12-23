package dirijamais.project.dirijamais.aplicacao.exception;

import jakarta.persistence.EntityNotFoundException;

public class VeiculoNaoEncontradoException extends EntityNotFoundException {
     public VeiculoNaoEncontradoException(String message) {
        super(message);
    }

    public VeiculoNaoEncontradoException() {
        super("Veículo não encontrado.");
    }
}
