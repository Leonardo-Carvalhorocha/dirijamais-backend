package dirijamais.project.dirijamais.aplicacao.filtros;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import dirijamais.project.dirijamais.aplicacao.exception.DomainException;
import dirijamais.project.dirijamais.aplicacao.filtros.dto.OrderDTO;
import dirijamais.project.dirijamais.aplicacao.filtros.dto.SearchDTO;
import dirijamais.project.dirijamais.aplicacao.filtros.enums.OperatorTypeEnum;
import dirijamais.project.dirijamais.aplicacao.filtros.enums.OrderEnum;
import dirijamais.project.dirijamais.aplicacao.filtros.enums.ValueTypeEnum;
import dirijamais.project.dirijamais.aplicacao.utils.Utilities;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Recebe uma lista de campos para filtrar e uma lista de campos para ordenar. Para cada campo busca a entidade raíz (root). No caso deste for de uma entidade
 * relacionada, deve ser passada com um '.'. Exemplo: 'empresa.uuid', 'classificacao.tipo', etc. E para cada campo de ordenação, faz de acordo com o valor
 * priority. Quanto menor o valor, maior prioridade.
 *
 *
 * Caso de uso filter Produto, consulta executada no banco de dados pelos operadores das classes OperatorEnum e OperatorTypeEnum: Ao selecionar AND e OR, a
 * consulta gerada realiza:
 *
 * Agrupamento de todos os filtros ORs. Cria um AND entre todos os ANDs
 *
 * Exemplo 1 
 * (... campos da tabela Produto) from produto p1_0 where lower(cast(p1_0.empresa_uuid as text))=? and lower(cast(p1_0.organizacao_uuid as text))=?
 * and ( lower(cast(p1_0.tipo_gerador_clarion as text)) in (?,?) or p1_0.tipo_gerador_clarion is null ) order by p1_0.data_criacao offset ? rows fetch first ?
 * rows only
 *
 * Exemplo 2 
 * (... campos da tabela Produto) from produto p1_0 where lower(cast(p1_0.empresa_uuid as text))=? and lower(cast(p1_0.organizacao_uuid as text))=?
 * and lower(cast(p1_0.data_criacao as text)) between ? and ? and lower(cast(p1_0.codigo as text)) in (?,?,?) and ( lower(cast(p1_0.codigo as text))=? or
 * lower(cast(p1_0.codigo as text))=? or p1_0.peso_liquido is null or p1_0.preco_unitario < ? ) order by p1_0.data_criacao offset ? rows fetch first ? rows only
 */
public class DynamicSpecifications {

	public static <T> Specification<T> bySearchFilter(final List<SearchDTO> filters) {
		return bySearchFilter(filters, null);
	}

	public static <T> Specification<T> bySearchFilter(final List<SearchDTO> filters, final List<OrderDTO> orders) {
		return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {

			Predicate predicatesOr = null;
			Predicate predicatesAnd = null;

			filters.stream()
					.forEach(filtro -> {
						if (filtro.getOperatorType() == null) {
							filtro.setOperatorType(OperatorTypeEnum.AND);
						}
						if (filtro.getValueType() == null) {
							filtro.setValueType(ValueTypeEnum.STRING);
						}
					});

			List<SearchDTO> fieldsOr = filters.stream()
					.filter(filtro -> filtro.getOperatorType().equals(OperatorTypeEnum.OR))
					.toList();

			List<SearchDTO> fieldsAnd = filters.stream()
					.filter(filtro -> filtro.getOperatorType().equals(OperatorTypeEnum.AND))
					.toList();

			if (!fieldsAnd.isEmpty()) {
				List<Predicate> predicates = getPredicates(fieldsAnd, root, builder);
				predicatesAnd = builder.and(predicates.toArray(Predicate[]::new));
			}
			if (!fieldsOr.isEmpty()) {
				List<Predicate> predicates = getPredicates(fieldsOr, root, builder);
				predicatesOr = builder.or(predicates.toArray(Predicate[]::new));
			}

			if (!Utilities.isNullOrEmpty(orders)) {
				List<Order> orderList = new ArrayList<>();
				orders.sort(OrderDTO.ASCENDING_COMPARATOR_BY_PRIORITY);
				for (OrderDTO order : orders) {
					String[] names = StringUtils.split(order.getField(), ".");
					if (names == null) {
						names = new String[] { order.getField() };
					}
					Path expression = root.get(names[0]);
					for (int i = 1; i < names.length; i++) {
						expression = expression.get(names[i]);
					}
					if (OrderEnum.DESC.equals(order.getOrder())) {
						orderList.add(builder.desc(expression));
					} else {
						orderList.add(builder.asc(expression));
					}
				}

				query.orderBy(orderList);
			}

			if (predicatesAnd == null && predicatesOr == null) {
				return null;
			}

			if (predicatesAnd != null && predicatesOr != null) {
				return builder.and(predicatesAnd, predicatesOr);
			}

			if (predicatesAnd == null && predicatesOr != null) {
				return predicatesOr;
			}

			return predicatesAnd;
		};
	}

	private static List<Predicate> getPredicates(List<SearchDTO> filters, Root root, CriteriaBuilder builder) {

		List<Predicate> predicates = new ArrayList<>();

		if (!Utilities.isNullOrEmpty(filters)) {

			for (SearchDTO filter : filters) {
				if (filter.getValueType() == null) {
					filter.setValueType(ValueTypeEnum.STRING);
				}
				String[] names = StringUtils.split(filter.getField(), ".");
				if (names == null) {
					names = new String[] { filter.getField() };
				}
				Path expression = root.get(names[0]);

				for (int i = 1; i < names.length; i++) {
					expression = expression.get(names[i]);
				}

				switch (filter.getOperator()) {
					case EQUALS ->
						predicates.add(getEqualsPredicate(builder, expression, filter));
					case GREATHER_THAN ->
						predicates.add(getGreaterThanPredicate(builder, expression, filter));
					case LESS_THAN ->
						predicates.add(getLessThanPredicate(builder, expression, filter));
					case GREATHER_THAN_OR_EQUAL ->
						predicates.add(getGreaterThanOrEqualToPredicate(builder, expression, filter));
					case LESS_THAN_OR_EQUAL ->
						predicates.add(getLessThanOrEqualToPredicate(builder, expression, filter));
					case NOT_EQUAL ->
						predicates.add(getNotEqualPredicate(builder, expression, filter));
					case LIKE ->
						predicates.add(getLikePredicate(builder, expression, filter));
					case NOT_LIKE ->
						predicates.add(getNotLikePredicate(builder, expression, filter));
					case STAR_WITH ->
						predicates.add(getStartWithPredicate(builder, expression, filter));
					case END_WITH ->
						predicates.add(getEndWithPredicate(builder, expression, filter));
					case IS_NULL ->
						predicates.add(getNullPredicate(builder, expression, filter));
					case NOT_NULL ->
						predicates.add(getNotNullPredicate(builder, expression, filter));
					case IS_TRUE ->
						predicates.add(getIsTruePredicate(builder, expression, filter));
					case IS_FALSE ->
						predicates.add(getIsFalsePredicate(builder, expression, filter));
					case IN ->
						predicates.add(getInPredicate(builder, expression, filter));
					case BETWEEN ->
						predicates.add(getBetweenPredicate(builder, expression, filter));
				}
			}
		}
		return predicates;
	}

	private static BigDecimal getNumber(Object obj) throws DomainException {
		try {
			return new BigDecimal(obj.toString());
		} catch (Exception e) {
			throw new DomainException(String.format("O valor %s informado no filtro não corresponde a um número.", obj));
		}
	}

	private static String getString(Object obj) throws DomainException {
		if (obj != null) {
			return obj.toString().toLowerCase();
		}
		throw new DomainException("O valor informado no filtro não pode ser nulo.");

	}

	private static OffsetDateTime getDateTime(Object obj) {
		try {
			return OffsetDateTime.parse(obj.toString());
		} catch (Exception e) {
			throw new DomainException(
				String.format("O valor %s informado não corresponde a uma data/hora válida.", obj)
			);
		}
	}


	private static Predicate getEqualsPredicate(CriteriaBuilder builder, Path path, SearchDTO filter) {
		if (filter.getValue() == null) {
			return getNullPredicate(builder, path, filter);
		}
		switch (filter.getValueType()) {
			case STRING -> {
				var value = getString(filter.getValue());
				Predicate predicate = builder.equal(builder.lower(builder.toString(path)), value);
				return predicate;
			}
			case NUMBER -> {
				var value = getNumber(filter.getValue());
				Predicate predicate = builder.equal(path, value);
				return predicate;
			}
			case DATETIME -> {
				OffsetDateTime value = getDateTime(filter.getValue());
				return builder.equal(path, value);
			}

		}

		return null;

	}

	private static Predicate getGreaterThanPredicate(CriteriaBuilder builder, Path path, SearchDTO filter) {
		switch (filter.getValueType()) {
			case STRING -> {
				var value = getString(filter.getValue());
				Predicate predicate = builder.greaterThan(builder.lower(builder.toString(path)), (Comparable) value);
				return predicate;
			}
			case NUMBER -> {
				var value = getNumber(filter.getValue());
				Predicate predicate = builder.greaterThan(path, (Comparable) value);
				return predicate;
			}
			case DATETIME -> {
				OffsetDateTime value = getDateTime(filter.getValue());
				return builder.greaterThan(path, value);
			}


		}
		return null;
	}

	private static Predicate getLessThanPredicate(CriteriaBuilder builder, Path path, SearchDTO filter) {
		switch (filter.getValueType()) {
			case STRING -> {
				var value = getString(filter.getValue());
				Predicate predicate = builder.lessThan(builder.lower(builder.toString(path)), (Comparable) value);
				return predicate;
			}
			case NUMBER -> {
				var value = getNumber(filter.getValue());
				Predicate predicate = builder.lessThan(path, (Comparable) value);
				return predicate;
			}
			case DATETIME -> {
				OffsetDateTime value = getDateTime(filter.getValue());
				return builder.lessThan(path, value);
			}


		}
		return null;
	}

	private static Predicate getGreaterThanOrEqualToPredicate(CriteriaBuilder builder, Path path, SearchDTO filter) {
		switch (filter.getValueType()) {
			case STRING -> {
				var value = getString(filter.getValue());
				Predicate predicate = builder.greaterThanOrEqualTo(builder.lower(builder.toString(path)), (Comparable) value);
				return predicate;
			}
			case NUMBER -> {
				var value = getNumber(filter.getValue());
				Predicate predicate = builder.greaterThanOrEqualTo(path, (Comparable) value);
				return predicate;
			}
			case DATETIME -> {
				OffsetDateTime value = getDateTime(filter.getValue());
				return builder.greaterThanOrEqualTo(path, value);
			}


		}
		return null;
	}

	private static Predicate getLessThanOrEqualToPredicate(CriteriaBuilder builder, Path path, SearchDTO filter) {
		switch (filter.getValueType()) {
			case STRING -> {
				var value = getString(filter.getValue());
				Predicate predicate = builder.lessThanOrEqualTo(builder.lower(builder.toString(path)), (Comparable) value);
				return predicate;
			}
			case NUMBER -> {
				var value = getNumber(filter.getValue());
				Predicate predicate = builder.lessThanOrEqualTo(path, (Comparable) value);
				return predicate;
			}
			case DATETIME -> {
				OffsetDateTime value = getDateTime(filter.getValue());
				return builder.lessThanOrEqualTo(path, value);
			}


		}
		return null;
	}

	private static Predicate getNotEqualPredicate(CriteriaBuilder builder, Path path, SearchDTO filter) {
		if (filter.getValue() == null) {
			return getNotNullPredicate(builder, path, filter);
		}
		switch (filter.getValueType()) {
			case STRING -> {
				var value = getString(filter.getValue());
				Predicate predicate = builder.notEqual(builder.lower(builder.toString(path)), value);
				return predicate;
			}
			case NUMBER -> {
				var value = getNumber(filter.getValue());
				Predicate predicate = builder.notEqual(path, value);
				return predicate;
			}
		}

		return null;

	}

	private static Predicate getLikePredicate(CriteriaBuilder builder, Path path, SearchDTO filter) {
		switch (filter.getValueType()) {
			case STRING -> {
				var value = "%" + getString(filter.getValue()) + "%";
				Predicate predicate = builder.like(builder.lower(builder.toString(path)), value);
				return predicate;
			}

		}
		return null;
	}

	private static Predicate getNotLikePredicate(CriteriaBuilder builder, Path path, SearchDTO filter) {
		switch (filter.getValueType()) {
			case STRING -> {
				var value = "%" + getString(filter.getValue()) + "%";
				Predicate predicate = builder.notLike(builder.lower(builder.toString(path)), value);
				return predicate;
			}

		}
		return null;
	}

	private static Predicate getStartWithPredicate(CriteriaBuilder builder, Path path, SearchDTO filter) {
		switch (filter.getValueType()) {
			case STRING -> {
				var value = getString(filter.getValue()) + "%";
				Predicate predicate = builder.like(builder.lower(builder.toString(path)), value);
				return predicate;
			}

		}
		return null;
	}

	private static Predicate getEndWithPredicate(CriteriaBuilder builder, Path path, SearchDTO filter) {
		switch (filter.getValueType()) {
			case STRING -> {
				var value = "%" + getString(filter.getValue());
				Predicate predicate = builder.like(builder.lower(builder.toString(path)), value);
				return predicate;
			}

		}
		return null;
	}

	private static Predicate getNullPredicate(CriteriaBuilder builder, Path path, SearchDTO filter) {
		Predicate predicate = builder.isNull(path);
		return predicate;

	}

	private static Predicate getNotNullPredicate(CriteriaBuilder builder, Path path, SearchDTO filter) {
		Predicate predicate = builder.isNotNull(path);
		return predicate;

	}

	private static Predicate getIsTruePredicate(CriteriaBuilder builder, Path path, SearchDTO filter) {
		Predicate predicate = builder.isTrue(path);
		return predicate;
	}

	private static Predicate getIsFalsePredicate(CriteriaBuilder builder, Path path, SearchDTO filter) {
		Predicate predicate = builder.isFalse(path);
		return predicate;
	}

	private static Predicate getInPredicate(CriteriaBuilder builder, Path path, SearchDTO filter) {

		if (filter.getValue() instanceof List values) {
			switch (filter.getValueType()) {
				case STRING -> {
					List<String> strValues = values.stream()
							.filter(value -> value != null)
							.map(value -> value.toString().toLowerCase())
							.toList();

					boolean nullValue = values.stream()
							.anyMatch(value -> value == null);

					CriteriaBuilder.In<String> inClause = builder.in(builder.lower(builder.toString(path)));
					strValues.forEach(value -> inClause.value(value));
					if (nullValue) {
						Predicate nullPredicate = getNullPredicate(builder, path, filter);
						return builder.or(inClause, nullPredicate);
					}
					return inClause;
				}
				case NUMBER -> {
					List<BigDecimal> intValues = values.stream()
							.filter(value -> value != null)
							.map(value -> getNumber(value))
							.toList();

					boolean nullValue = values.stream()
							.anyMatch(value -> value == null);

					CriteriaBuilder.In<BigDecimal> inClause = builder.in(path);
					intValues.forEach(value -> inClause.value(value));

					if (nullValue) {
						Predicate nullPredicate = getNullPredicate(builder, path, filter);
						return builder.or(inClause, nullPredicate);
					}

					return inClause;
				}
			}
		}

		return null;
	}

	private static Predicate getBetweenPredicate(CriteriaBuilder builder, Path path, SearchDTO filter) {

		if (filter.getValue() instanceof List values) {
			if (values.size() != 2) {
				throw new DomainException("O filtro between deve possuir dois valores.");
			}
			switch (filter.getValueType()) {
				case STRING -> {
					String primeiro = getString(values.get(0));
					String segundo = getString(values.get(1));
					return builder.between(builder.lower(builder.toString(path)), primeiro, segundo);
				}
				case NUMBER -> {
					BigDecimal primeiroNumero = getNumber(values.get(0));
					BigDecimal segundoNumero = getNumber(values.get(1));
					return builder.between(path, primeiroNumero, segundoNumero);
				}
			}
		}

		return null;
	}
}
