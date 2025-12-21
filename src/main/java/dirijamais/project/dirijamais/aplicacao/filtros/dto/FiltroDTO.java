package dirijamais.project.dirijamais.aplicacao.filtros.dto;

import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class FiltroDTO {

	@NotNull
	private Integer page;

	@NotNull
	private Integer pagesize;

	@NotNull
	private List<SearchDTO> filters;

	private List<OrderDTO> orders;

}
