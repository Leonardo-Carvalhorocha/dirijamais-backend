package dirijamais.project.dirijamais.modulos.transacao.dtos;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import dirijamais.project.dirijamais.modulos.transacao.enums.CategoriaGastos;
import dirijamais.project.dirijamais.modulos.transacao.enums.DiaSemana;
import dirijamais.project.dirijamais.modulos.transacao.enums.TipoGasto;
import dirijamais.project.dirijamais.modulos.transacao.enums.TipoPagamento;
import dirijamais.project.dirijamais.modulos.usuario.dtos.UsuarioUuid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoSaidaRequestModel {
    private UsuarioUuid usuario;

    private OffsetDateTime data;

    private DiaSemana diaSemana;

    private String descricaoGasto;

    private BigDecimal valor;

    private CategoriaGastos categoria;

    private TipoGasto tipoGasto;

    private TipoPagamento tipoPagamento;
}
