package dirijamais.project.dirijamais.modulos.usuario.repositors;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dirijamais.project.dirijamais.modulos.usuario.models.PerfilUsuarioMotorista;

@Repository
public interface PerfilUsuarioMotoristaRepository extends JpaRepository<PerfilUsuarioMotorista, UUID> {
    public Page<PerfilUsuarioMotorista> findAll(Specification<Object> bySearchFilter, Pageable pageable);
}
