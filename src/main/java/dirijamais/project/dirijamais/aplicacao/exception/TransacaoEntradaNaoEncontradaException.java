package dirijamais.project.dirijamais.aplicacao.exception;

import jakarta.persistence.EntityNotFoundException;

public class TransacaoEntradaNaoEncontradaException extends EntityNotFoundException {

    public TransacaoEntradaNaoEncontradaException(String message) {
        super(message);
    }

    public TransacaoEntradaNaoEncontradaException() {
        super("Transação de entrada não encontrado.");
    }

}
