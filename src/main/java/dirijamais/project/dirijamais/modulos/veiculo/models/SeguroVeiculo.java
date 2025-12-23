package dirijamais.project.dirijamais.modulos.veiculo.models;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeguroVeiculo {

    private BigDecimal valorTotalAnual;

    private Integer quantidadeParcelas;

    private OffsetDateTime inicioVigencia;

    private OffsetDateTime fimVigencia;

}
