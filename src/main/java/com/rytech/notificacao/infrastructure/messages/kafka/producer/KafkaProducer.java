package com.rytech.notificacao.infrastructure.messages.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rytech.notificacao.business.dto.StatusTarefaDTO;
import com.rytech.notificacao.infrastructure.exceptions.MensagemException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    public void enviarStatusTarefa(StatusTarefaDTO dto) {
        try {
            String json = objectMapper.writeValueAsString(dto);
            kafkaTemplate.send("status-notificacao", json);
        } catch (JsonProcessingException e) {
            throw new MensagemException("Erro ao serializar objeto");
        }
    }
}
