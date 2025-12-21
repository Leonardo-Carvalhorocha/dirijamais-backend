package dirijamais.project.dirijamais.aplicacao.filtros.dto;

import java.util.Comparator;

import org.antlr.v4.runtime.misc.NotNull;

import dirijamais.project.dirijamais.aplicacao.filtros.enums.OrderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Comparable<OrderDTO>{

    @NotNull
    private String field;

    @NotNull
    private OrderEnum order;

    @NotNull
    private Integer priority;

    @Override
    public int compareTo(OrderDTO orderDTO) {
        return (this.priority).compareTo(orderDTO.priority);
    }

    public static final Comparator<OrderDTO> ASCENDING_COMPARATOR_BY_PRIORITY = new Comparator<OrderDTO>() {
        @Override
        public int compare(OrderDTO orderDTO, OrderDTO t1) {
            return orderDTO.priority - t1.priority;
        }
    };
}
