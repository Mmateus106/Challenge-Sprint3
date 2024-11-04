package br.com.VomHive.VomHive.mensageria;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracaoRabbitMQ {

    public static final String FILA = "VomHiveFila";
    public static final String ROTEADOR = "VomHiveRoteador";
    public static final String CHAVE_ROTA = "VomHiveRota";


    @Bean
    public org.springframework.amqp.core.Queue fila() {
        return new org.springframework.amqp.core.Queue(FILA, true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(ROTEADOR);
    }

    @Bean
    public Binding binding(Queue fila, DirectExchange roteador) {
        return BindingBuilder.bind(fila).to(roteador).with(CHAVE_ROTA);
    }
}
