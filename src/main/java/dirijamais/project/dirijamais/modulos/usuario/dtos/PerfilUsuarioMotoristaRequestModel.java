package dirijamais.project.dirijamais.modulos.usuario.dtos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerfilUsuarioMotoristaRequestModel {
    private BigDecimal metaMensalLiquida;

    private Integer diasTrabalhadoPorSemana;

    private Integer horasTrabalhadoPorDia;

    private UsuarioUuid usuario;
}
