package dirijamais.project.dirijamais.aplicacao.models;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", updatable = false, nullable = false)
    @Getter
    @Setter
    private UUID uuid;

    @Getter
	@Setter
	@CreationTimestamp
	@Column(nullable = false, updatable = false, columnDefinition = "timestamp with time zone")
	private OffsetDateTime dataCriacao;

	@Getter
	@Setter
	@CreatedBy
	@Column(updatable = false)
	private String criadoPor;

	@Getter
	@Setter
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "timestamp with time zone")
	private OffsetDateTime dataUltimaModificacao;

    @Setter
	@Getter
	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean ativo = Boolean.FALSE;
}
