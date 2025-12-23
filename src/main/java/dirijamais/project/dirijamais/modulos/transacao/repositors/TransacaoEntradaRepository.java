package dirijamais.project.dirijamais.modulos.transacao.repositors;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dirijamais.project.dirijamais.modulos.transacao.models.TransacaoEntrada;

@Repository
public interface TransacaoEntradaRepository extends JpaRepository<TransacaoEntrada, UUID> {
    public Page<TransacaoEntrada> findAll(Specification<Object> bySearchFilter, Pageable pageable);
}
