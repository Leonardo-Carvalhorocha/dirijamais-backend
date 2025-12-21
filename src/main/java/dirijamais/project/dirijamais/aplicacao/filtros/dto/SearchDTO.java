package dirijamais.project.dirijamais.aplicacao.filtros.dto;

import org.antlr.v4.runtime.misc.NotNull;

import com.fasterxml.jackson.annotation.JsonAlias;

import dirijamais.project.dirijamais.aplicacao.filtros.enums.OperatorEnum;
import dirijamais.project.dirijamais.aplicacao.filtros.enums.OperatorTypeEnum;
import dirijamais.project.dirijamais.aplicacao.filtros.enums.ValueTypeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Objeto contendo as informações de um campo de um filtro de pesquisa.<br>
 */
@Getter
@Setter
public class SearchDTO {

	/**
	 * Nome do campo do filtro de pesquisa. Ex: empresa.uuid ou empresa.organizacao.uuid ou unidadeMedida.uuid.
	 */
	@NotNull
	private String field;

	/**
	 * Valor do campo do filtro de pesquisa. Se operator for igual 'IN' esse campo deve receber uma lista de Strings, para os demais casos esse vampo recebe uma
	 * String.
	 */	
	private Object value;

	/**
	 * Tipo de dado do campo value
	 */
	@Setter
	@JsonAlias("value_type")
	private ValueTypeEnum valueType;
	
	

	/**
	 * Operacao a ser realizada.
	 */
	@NotNull
	private OperatorEnum operator;
	
	/**
	 * em caso operatorType OR será feita um OR entre todos os filtros do tipo OR e depois um AND com todos os filtros do tipo AND
	*/
	@Setter
	@JsonAlias("operator_type")
	private OperatorTypeEnum operatorType; 
	
	public SearchDTO() {
	}

	public SearchDTO(String field, Object value, OperatorEnum operator) {
		this.field = field;
		this.value = value;
		this.operator = operator;
	}

	public SearchDTO(String field, Object value, ValueTypeEnum valueType, OperatorEnum operator, OperatorTypeEnum operatorType) {
		this.field = field;
		this.value = value;
		this.valueType = valueType;
		this.operator = operator;
		this.operatorType = operatorType;
	}
	
	

}
