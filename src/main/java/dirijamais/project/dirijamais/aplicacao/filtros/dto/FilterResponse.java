package dirijamais.project.dirijamais.aplicacao.filtros.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class FilterResponse {

    private Integer totalElements;

    private Integer totalPages;

    private Integer size;

    private Integer number;

    private Integer numberOfElements;

    private Boolean first;

    private Boolean last;

    private Boolean empty;

    private List<Object> content;
}
