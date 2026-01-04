package dirijamais.project.dirijamais.modulos.mensagemwhatsapp.services;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import dirijamais.project.dirijamais.modulos.mensagemwhatsapp.models.MensagemWhatsapp;

public interface IMensagemWhatsappService {

    MensagemWhatsapp criar(MensagemWhatsapp mensagemWhatsapp);

    List<MensagemWhatsapp> buscarMensagensParaEnvio(LocalTime horario);

    void processarEnvio(MensagemWhatsapp mensagemWhatsapp);

    String gerarMensagemPorUsuario(UUID uuidUsuario);

}
