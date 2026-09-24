package com.rytech.notificacao.business.dto;

import com.rytech.notificacao.business.enums.StatusNotificacaoEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusTarefaDTO {
    private String id;
    private StatusNotificacaoEnum statusNotificacaoEnum;
}
