package br.com.VomHive.VomHive.mensageria;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumidorRabbitMQ {

    @RabbitListener(queues = ConfiguracaoRabbitMQ.FILA)
    public void lerMensagem(String msg) {
        System.out.println("Mensagem recebida (via Rabbit): " + msg);
    }

}