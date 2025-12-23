package dirijamais.project.dirijamais.aplicacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dirijamais.project.dirijamais.aplicacao.dtos.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

      @ExceptionHandler(EmailJaCadastradoException.class) 
      public ResponseEntity<ErrorResponseDTO> handleEmailJaCadastrado(EmailJaCadastradoException ex) {
            return ResponseEntity
                  .status(HttpStatus.CONFLICT)
                  .body(new ErrorResponseDTO( HttpStatus.CONFLICT, ex.getMessage() ));
      }

      @ExceptionHandler(UsuarioNaoEncontradoException.class)
      public ResponseEntity<ErrorResponseDTO> handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException ex) {
            return ResponseEntity
                  .status(HttpStatus.NOT_FOUND)
                  .body(new ErrorResponseDTO(HttpStatus.NOT_FOUND, ex.getMessage()));
      }

      @ExceptionHandler(VeiculoNaoEncontradoException.class)
      public ResponseEntity<ErrorResponseDTO> handleVeiculoNaoEncontradoException(VeiculoNaoEncontradoException ex) {
            return ResponseEntity
                  .status(HttpStatus.NOT_FOUND)
                  .body(new ErrorResponseDTO(HttpStatus.NOT_FOUND, ex.getMessage()));
      }

      @ExceptionHandler(PerfilUsuarioNaoEncontradoException.class)
      public ResponseEntity<ErrorResponseDTO> handlePerfilUsuarioNaoEncontradoException(PerfilUsuarioNaoEncontradoException ex) {
            return ResponseEntity
                  .status(HttpStatus.NOT_FOUND)
                  .body(new ErrorResponseDTO(HttpStatus.NOT_FOUND, ex.getMessage()));
      }


      @ExceptionHandler(JaExistePerfilUsuarioMotorista.class)
      public ResponseEntity<ErrorResponseDTO> handleJaExistePerfilUsuarioMotorista(JaExistePerfilUsuarioMotorista ex) {
            return ResponseEntity
                  .status(HttpStatus.CONFLICT)
                  .body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST, ex.getMessage()));
      }

      @ExceptionHandler(TransacaoEntradaNaoEncontradaException.class)
      public ResponseEntity<ErrorResponseDTO> handleTransacaoEntradaNaoEncontradaException(TransacaoEntradaNaoEncontradaException ex) {
            return ResponseEntity
                  .status(HttpStatus.NOT_FOUND)
                  .body(new ErrorResponseDTO(HttpStatus.NOT_FOUND, ex.getMessage()));
      }

     @ExceptionHandler(UsuarioLoginException.class)
      public ResponseEntity<ErrorResponseDTO> handleUsuarioLoginException(UsuarioLoginException ex) {
      return ResponseEntity
                  .status(HttpStatus.UNAUTHORIZED)
                  .body(new ErrorResponseDTO(HttpStatus.UNAUTHORIZED, ex.getMessage()));
      }

    
}
