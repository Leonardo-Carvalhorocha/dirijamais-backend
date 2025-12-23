package dirijamais.project.dirijamais.aplicacao.models;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class Parcelamento {

    private BigDecimal valorMensal;

    private Integer quantidadeParcelas;

    private OffsetDateTime inicioVigencia;

    private OffsetDateTime fimVigencia;

}
