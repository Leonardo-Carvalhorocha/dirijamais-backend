package dirijamais.project.dirijamais.modulos.mensagemwhatsapp.services.implementacao;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import dirijamais.project.dirijamais.modulos.mensagemwhatsapp.models.MensagemWhatsapp;


@Service
public class WhatsappScheduleService {
  
    @Autowired
    private MensagemWhatsappService service;

    @Scheduled(fixedRate = 60000) // a cada 1 minuto
    public void executar() {
        LocalTime agora = LocalTime.now()
                .truncatedTo(ChronoUnit.MINUTES);

        System.out.println("Scheduler executado em: " + agora);

        List<MensagemWhatsapp> mensagens = service.buscarMensagensParaEnvio(agora);
        System.out.println("Mensagens encontradas: " + mensagens.size());
        mensagens.forEach(service::processarEnvio);
    }

}
