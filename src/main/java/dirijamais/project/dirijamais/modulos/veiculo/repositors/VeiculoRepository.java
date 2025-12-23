package dirijamais.project.dirijamais.modulos.veiculo.repositors;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dirijamais.project.dirijamais.modulos.veiculo.models.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, UUID> {

    Optional<Veiculo> findByUsuarioUuidAndAtivoTrue(UUID usuarioUuid);

    public Page<Veiculo> findAll(Specification<Object> bySearchFilter, Pageable pageable);

}
