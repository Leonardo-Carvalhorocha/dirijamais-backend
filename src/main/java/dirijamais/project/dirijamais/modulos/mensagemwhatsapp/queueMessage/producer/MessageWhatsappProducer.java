package dirijamais.project.dirijamais.modulos.mensagemwhatsapp.queueMessage.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dirijamais.project.dirijamais.aplicacao.configuracoes.RabbitMQConfig;
import dirijamais.project.dirijamais.modulos.mensagemwhatsapp.dtos.WhatsappQueueMessageDTO;

@Component
public class MessageWhatsappProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void enviar(WhatsappQueueMessageDTO whatsappQueueMessageDTO) {
        System.out.println(whatsappQueueMessageDTO.getMensagem().toString());
         try {
            amqpTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_SEND,
                whatsappQueueMessageDTO
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar mensagem para fila", e);
        }
    }

}
