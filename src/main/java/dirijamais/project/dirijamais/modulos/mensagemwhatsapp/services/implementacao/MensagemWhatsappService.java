package dirijamais.project.dirijamais.modulos.mensagemwhatsapp.services.implementacao;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import dirijamais.project.dirijamais.aplicacao.filtros.dto.FiltroDTO;
import dirijamais.project.dirijamais.aplicacao.filtros.dto.SearchDTO;
import dirijamais.project.dirijamais.aplicacao.filtros.enums.OperatorEnum;
import dirijamais.project.dirijamais.aplicacao.filtros.enums.OperatorTypeEnum;
import dirijamais.project.dirijamais.aplicacao.filtros.enums.ValueTypeEnum;
import dirijamais.project.dirijamais.modulos.mensagemwhatsapp.dtos.WhatsappQueueMessageDTO;
import dirijamais.project.dirijamais.modulos.mensagemwhatsapp.models.MensagemWhatsapp;
import dirijamais.project.dirijamais.modulos.mensagemwhatsapp.queueMessage.producer.MessageWhatsappProducer;
import dirijamais.project.dirijamais.modulos.mensagemwhatsapp.repositors.MensagemWhatsappRepository;
import dirijamais.project.dirijamais.modulos.mensagemwhatsapp.services.IMensagemWhatsappService;
import dirijamais.project.dirijamais.modulos.relatorios.models.Relatorio;
import dirijamais.project.dirijamais.modulos.relatorios.services.implementacao.RelatorioService;
import dirijamais.project.dirijamais.modulos.usuario.models.Usuario;
import dirijamais.project.dirijamais.modulos.usuario.services.implementacao.UsuarioService;

@Service
public class MensagemWhatsappService implements IMensagemWhatsappService {

    @Autowired
    private MensagemWhatsappRepository repository;

    @Autowired
    private RelatorioService relatorioService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MessageWhatsappProducer producer;

    @Override
    public MensagemWhatsapp criar(MensagemWhatsapp mensagemWhatsapp) {
        mensagemWhatsapp = repository.save(mensagemWhatsapp);
        return mensagemWhatsapp;
    }

    @Override
    public List<MensagemWhatsapp> buscarMensagensParaEnvio(LocalTime horario) {
        return repository.buscarParaEnvio(horario);
    }

    @Override
    public void processarEnvio(MensagemWhatsapp mensagemWhatsapp) {

        mensagemWhatsapp.setMensagem(gerarMensagemPorUsuario(mensagemWhatsapp.getUsuario().getUuid()));

         producer.enviar(new WhatsappQueueMessageDTO(
            mensagemWhatsapp.getMensagem(),
            mensagemWhatsapp.getNumero()
        ));

        mensagemWhatsapp.setUltimoEnvio(LocalDateTime.now());
        mensagemWhatsapp.setMensagem(null);
        repository.save(mensagemWhatsapp);
    }

    @Override
    public String gerarMensagemPorUsuario(UUID uuidUsuario) {

        ZoneId zoneBR = ZoneId.of("America/Sao_Paulo");

        ZonedDateTime inicioOntem =
                LocalDate.now(zoneBR)
                        .minusDays(1)
                        .atStartOfDay(zoneBR);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        List<SearchDTO> filtros = List.of(
            new SearchDTO(
                "data",
                inicioOntem.format(formatter),
                ValueTypeEnum.DATETIME,
                OperatorEnum.GREATHER_THAN_OR_EQUAL,
                OperatorTypeEnum.AND
            ),
            new SearchDTO(
                "usuario.uuid",
                uuidUsuario.toString(),
                ValueTypeEnum.STRING,
                OperatorEnum.EQUALS,
                OperatorTypeEnum.AND
            )
        );

        FiltroDTO filtroDTO = new FiltroDTO();
        filtroDTO.setPage(0);
        filtroDTO.setPagesize(1);
        filtroDTO.setFilters(filtros);

        Relatorio relatorio = relatorioService.buscarRelatorio(
            filtroDTO,
            PageRequest.of(0, 1)
        );

        if (relatorio == null) {
            return montarMensagemSemDados(uuidUsuario, inicioOntem.toLocalDate());
        }

        return montarMensagem(relatorio, uuidUsuario, inicioOntem.toLocalDate());
    }


    private String montarMensagem(Relatorio relatorio, UUID uuidUsuario, LocalDate ontem) {
        Usuario usuario = usuarioService.buscarPorUuid(uuidUsuario);

        Locale localeBR = new Locale("pt", "BR");
        NumberFormat moeda = NumberFormat.getCurrencyInstance(localeBR);
        DecimalFormat decimal = new DecimalFormat("#,##0.00");

        String nomeUsuario = usuario.getNome();
        String dataFormatada = ontem.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        StringBuilder mensagem = new StringBuilder();

        mensagem.append("Olá, ").append(nomeUsuario).append("! 👋\n\n");

        mensagem.append("Segue o relatório do seu dia 📅 ")
                .append(dataFormatada)
                .append(":\n\n");

        mensagem.append("🚗 Quilometragem percorrida:\n")
                .append("• Total: ")
                .append(decimal.format(relatorio.getKmRodados()))
                .append(" km\n\n");

        mensagem.append("🧾 Produção do dia:\n")
                .append("• Quantidade de viagens: ")
                .append(relatorio.getQuantidadeDeViagens())
                .append("\n")
                .append("• Horas trabalhadas: ")
                .append(relatorio.getHorasTrabalhados())
                .append(" horas\n\n");

        mensagem.append("💰 Financeiro:\n")
                .append("• Faturamento total: ")
                .append(moeda.format(relatorio.getFaturamentoTotal()))
                .append("\n")
                .append("• Despesas totais: ")
                .append(moeda.format(relatorio.getDespesasTotais()))
                .append("\n")
                .append("• Saldo líquido do dia: ")
                .append(moeda.format(relatorio.getSaldoLiquido()))
                .append("\n\n");

        mensagem.append("📊 Médias de desempenho:\n")
                .append("• Faturamento por viagem: ")
                .append(moeda.format(relatorio.getFaturamentoPorViagem()))
                .append("\n")
                .append("• Faturamento médio por hora: ")
                .append(moeda.format(relatorio.getFaturamentoMedioPorHora()))
                .append("\n")
                .append("• Faturamento médio por KM: ")
                .append(moeda.format(relatorio.getFaturamentoMedioPorKM()))
                .append("\n\n");

        mensagem.append("📉 Custos médios:\n")
                .append("• Custo por viagem: ")
                .append(moeda.format(relatorio.getCustoPorViagem()))
                .append("\n")
                .append("• Custo por hora: ")
                .append(moeda.format(relatorio.getCustoPorHora()))
                .append("\n")
                .append("• Custo por KM: ")
                .append(moeda.format(relatorio.getCustoPorKM()))
                .append("\n\n");

        mensagem.append("📈 Lucro médio:\n")
                .append("• Lucro por viagem: ")
                .append(moeda.format(relatorio.getLucroPorViagem()))
                .append("\n")
                .append("• Lucro por hora: ")
                .append(moeda.format(relatorio.getLucroPorHora()))
                .append("\n")
                .append("• Lucro por KM: ")
                .append(moeda.format(relatorio.getLucroPorKM()))
                .append("\n\n");

        mensagem.append("✅ Continue acompanhando seus resultados diariamente para melhorar ainda mais seus ganhos.\n\n")
                .append("Qualquer dúvida, estou à disposição.");

        return mensagem.toString();
    }

    private String montarMensagemSemDados(UUID uuidUsuario, LocalDate data) {

        Usuario usuario = usuarioService.buscarPorUuid(uuidUsuario);

        String dataFormatada = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return """
            Olá, %s! 👋

            Não identificamos atividades registradas no dia 📅 %s.

            Caso isso não esteja correto, verifique seus registros.
            Qualquer dúvida, estou à disposição.
            """.formatted(usuario.getNome(), dataFormatada);
    }


}
