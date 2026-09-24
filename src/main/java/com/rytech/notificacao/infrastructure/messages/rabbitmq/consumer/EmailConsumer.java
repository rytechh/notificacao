package com.rytech.notificacao.infrastructure.messages.rabbitmq.consumer;

import com.rytech.notificacao.business.EmailService;
import com.rytech.notificacao.business.dto.TarefasDTO;
import com.rytech.notificacao.infrastructure.messages.rabbitmq.config.RabbitMqConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = RabbitMqConfig.FILA_EMAIL)
    public void enviarEmail(TarefasDTO dto) {
        emailService.enviaEmail(dto);
    }
}
