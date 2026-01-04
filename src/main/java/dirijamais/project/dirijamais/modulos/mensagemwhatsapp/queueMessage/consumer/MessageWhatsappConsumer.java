// package dirijamais.project.dirijamais.modulos.mensagemwhatsapp.queueMessage.consumer;

// import org.springframework.amqp.rabbit.annotation.RabbitListener;
// import org.springframework.stereotype.Component;

// import dirijamais.project.dirijamais.aplicacao.configuracoes.RabbitMQConfig;
// import dirijamais.project.dirijamais.modulos.mensagemwhatsapp.dtos.WhatsappQueueMessageDTO;

// @Component
// public class MessageWhatsappConsumer {

//      @RabbitListener(queues = RabbitMQConfig.QUEUE_SEND)
//     public void consumir(WhatsappQueueMessageDTO dto) {
//         System.out.println("Mensagem recebida:");
//         System.out.println(dto);
//     }
// }
