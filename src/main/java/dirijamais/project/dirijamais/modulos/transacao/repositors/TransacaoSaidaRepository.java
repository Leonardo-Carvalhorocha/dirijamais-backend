package dirijamais.project.dirijamais.modulos.transacao.repositors;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import dirijamais.project.dirijamais.modulos.transacao.models.TransacaoSaida;

public interface TransacaoSaidaRepository extends JpaRepository<TransacaoSaida, UUID> {
    public Page<TransacaoSaida> findAll(Specification<Object> bySearchFilter, Pageable pageable);
}
