package br.com.drogariamenk.msdmcolaborador.infra.mensageria;

import br.com.drogariamenk.msdmcolaborador.dto.DadosTransferenciaColaboradorDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class NovaRequisicaoColaboradorPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queueCanalColaborador;

    public void novaRequisicaoDeColaborador(DadosTransferenciaColaboradorDTO dados) throws JsonProcessingException {
        var json = convertToJson(dados);
        rabbitTemplate.convertAndSend(queueCanalColaborador.getName(), json);
    }

    private String convertToJson(DadosTransferenciaColaboradorDTO dados) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(dados);
        return json;
    }

}
