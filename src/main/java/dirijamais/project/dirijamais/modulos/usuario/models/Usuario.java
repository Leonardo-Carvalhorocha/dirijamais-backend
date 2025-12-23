package dirijamais.project.dirijamais.modulos.usuario.models;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import dirijamais.project.dirijamais.aplicacao.models.BaseEntity;
import dirijamais.project.dirijamais.modulos.usuario.enums.Role;
import dirijamais.project.dirijamais.modulos.veiculo.models.Veiculo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
    }
)
public class Usuario extends BaseEntity implements UserDetails  {

    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private String cpf;

    private String telefone;

    private OffsetDateTime dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.ROLE_USER;

    @OneToMany(mappedBy = "usuario")
    private List<Veiculo> veiculos = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name().toString());
        return List.of(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

}
