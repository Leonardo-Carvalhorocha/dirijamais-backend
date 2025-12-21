package dirijamais.project.dirijamais.aplicacao.configuracoes;


import org.modelmapper.AbstractCondition;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
	ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
				.setSkipNullEnabled(true)
				.setPropertyCondition(isStringNotNullAndNotEmpty);
		return modelMapper;
	}

    Condition<?, ?> isStringNotNullAndNotEmpty = new AbstractCondition<Object, Object>() {
	    @Override
		public boolean applies(MappingContext<Object, Object> context) {
			if (context.getSource() instanceof String str) {
				return str != null && !str.isEmpty();
			} else {
				return context.getSource() != null;
			}
	    }
	};
}
