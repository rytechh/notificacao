package com.rytech.notificacao.business;

import com.rytech.notificacao.business.dto.StatusTarefaDTO;
import com.rytech.notificacao.business.dto.TarefasDTO;
import com.rytech.notificacao.business.enums.StatusNotificacaoEnum;
import com.rytech.notificacao.infrastructure.exceptions.EmailException;
import com.rytech.notificacao.infrastructure.messages.kafka.producer.KafkaProducer;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final KafkaProducer producer;


    @Value("${envio.email.remetente}")
    public String remetente;

    @Value("${envio.email.nomeRemetente}")
    public String nomeRemetente;

    public void enviaEmail(TarefasDTO dto) {
        try {
            MimeMessage mensagem = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                    mensagem, true, StandardCharsets.UTF_8.name());
            mimeMessageHelper.setFrom(new InternetAddress(remetente, nomeRemetente));

            InternetAddress[] destinatarios = InternetAddress.parse(dto.getEmailUsuario());

            mimeMessageHelper.setTo(destinatarios);
            mimeMessageHelper.setSubject("Notificação de Tarefa");

            Context context = new Context();
            context.setVariable("nomeTarefa", dto.getNomeTarefa());
            context.setVariable("dataEvento", dto.getDataEvento());
            context.setVariable("descricao", dto.getDescricao());
            String template = templateEngine.process("notificacao", context);
            mimeMessageHelper.setText(template, true);
            javaMailSender.send(mensagem);

            producer.enviarStatusTarefa(buildStatus(dto.getId(), StatusNotificacaoEnum.NOTIFICADO));


        } catch (AddressException e) {
            producer.enviarStatusTarefa(buildStatus(dto.getId(), StatusNotificacaoEnum.FALHA));
            throw new EmailException("Erro ao enviar o email - email inválido " + e.getCause());

        } catch (MailAuthenticationException e) {
            producer.enviarStatusTarefa(buildStatus(dto.getId(), StatusNotificacaoEnum.PENDENTE));
            throw new EmailException("Erro ao enviar o email - autenticação smtp incorreta " + e.getCause());

        } catch (MailSendException e) {
            producer.enviarStatusTarefa(buildStatus(dto.getId(), StatusNotificacaoEnum.FALHA));
            throw new EmailException("Erro ao enviar o email - destinatário rejeitado " + e.getCause());

        } catch (Exception e) {
            producer.enviarStatusTarefa(buildStatus(dto.getId(), StatusNotificacaoEnum.PENDENTE));
            throw new EmailException("Erro ao enviar o email - erro no servidor do notificação " + e.getCause());
        }
    }


    private StatusTarefaDTO buildStatus(String id, StatusNotificacaoEnum status) {
        return StatusTarefaDTO.builder()
                .id(id)
                .statusNotificacaoEnum(status)
                .build();
    }


}
