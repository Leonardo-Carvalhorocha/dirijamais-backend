package dirijamais.project.dirijamais.modulos.usuario.repositors;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dirijamais.project.dirijamais.modulos.usuario.models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID>{
    boolean existsByEmail(String email);

    boolean existsByEmailAndUuidNot(String email, UUID uuid);

    Optional<Usuario> findByEmail(String email);

    public Page<Usuario> findAll(Specification<Object> bySearchFilter, Pageable pageable);
    
}
