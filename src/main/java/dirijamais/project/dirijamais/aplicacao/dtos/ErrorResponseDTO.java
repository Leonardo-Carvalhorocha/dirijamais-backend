package dirijamais.project.dirijamais.aplicacao.dtos;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDTO {
    private HttpStatus status;
    private String message;

    public ErrorResponseDTO(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
