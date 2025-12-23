package dirijamais.project.dirijamais.modulos.transacao.dtos;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import dirijamais.project.dirijamais.aplicacao.enums.Origem;
import dirijamais.project.dirijamais.modulos.transacao.enums.DiaSemana;
import dirijamais.project.dirijamais.modulos.usuario.dtos.UsuarioUuid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoEntradaRequestModel {

    private UsuarioUuid usuario;

    private OffsetDateTime data;

    private DiaSemana diaSemana;

    private Origem origem;

    private BigDecimal valorEntrada;

    private Integer quantidadeDeViagens;

    private BigDecimal kmRodados;

    private Integer horasTrabalhados;

    private String observacao;
    
}
