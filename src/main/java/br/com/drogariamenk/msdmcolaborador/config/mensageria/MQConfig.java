package br.com.drogariamenk.msdmcolaborador.config.mensageria;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {


    @Value("${mq.queues.canal-colaborador}")
    private String canalColaborador;

    @Bean
    public Queue queueNovoColaboradorCadastrado() {
        return new Queue(canalColaborador, true);
    }

}
