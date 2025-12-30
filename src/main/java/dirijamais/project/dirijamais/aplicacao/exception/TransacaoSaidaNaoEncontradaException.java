package dirijamais.project.dirijamais.aplicacao.exception;

import jakarta.persistence.EntityNotFoundException;

public class TransacaoSaidaNaoEncontradaException extends EntityNotFoundException {

    public TransacaoSaidaNaoEncontradaException(String message) {
        super(message);
    }

    public TransacaoSaidaNaoEncontradaException() {
        super("Transação de saída não encontrado.");
    }

}