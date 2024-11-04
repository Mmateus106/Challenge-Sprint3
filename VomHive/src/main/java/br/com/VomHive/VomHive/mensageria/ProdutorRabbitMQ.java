package br.com.VomHive.VomHive.mensageria;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutorRabbitMQ {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarMensagem(String msg) {
        rabbitTemplate.convertAndSend(ConfiguracaoRabbitMQ.ROTEADOR,
                ConfiguracaoRabbitMQ.CHAVE_ROTA, msg);
        System.out.println("Mensagem enviada (via Rabbit): " + msg);
    }
}
