package dirijamais.project.dirijamais.modulos.usuario.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerfilUsuarioMotoristaResponseModel {

    private UUID uuid;

    private BigDecimal metaMensalLiquida;

    private Integer diasTrabalhadoPorSemana;

    private Integer horasTrabalhadoPorDia;

    private UsuarioUuid usuario;
}
