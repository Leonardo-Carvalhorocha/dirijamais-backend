package dirijamais.project.dirijamais.modulos.mensagemwhatsapp.repositors;

import java.time.LocalTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dirijamais.project.dirijamais.modulos.mensagemwhatsapp.models.MensagemWhatsapp;

@Repository
public interface MensagemWhatsappRepository extends JpaRepository<MensagemWhatsapp, UUID> {
    @Query("""
        SELECT m
        FROM MensagemWhatsapp m
        WHERE m.horarioEnvio = :horario
            AND m.ultimoEnvio IS NULL
        """
    )
    List<MensagemWhatsapp> buscarParaEnvio(@Param("horario") LocalTime horario);

}
