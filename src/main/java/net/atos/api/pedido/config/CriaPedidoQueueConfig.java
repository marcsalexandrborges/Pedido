package net.atos.api.pedido.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CriaPedidoQueueConfig {
	

	@Bean
	public Queue criaPedidoQueue() {
		return QueueBuilder
				.durable("cria-pedido-servico")
				.deadLetterExchange("orcamento")
				.deadLetterRoutingKey("orc.created.pedido-dead-letter")
				.build();
		
	}
	
	@Bean 
	public Exchange criaPedidoExchange() {
		return ExchangeBuilder.topicExchange("orcamento").durable(true).build();		
	}
	
	@Bean
	public Binding criaPedidoBinding() {
		return BindingBuilder
				.bind(this.criaPedidoQueue())
				.to(this.criaPedidoExchange())
				.with("orc.created.pedido")
				.noargs();
	}
	
	
	@Bean
	public Queue criaPedidoQueueDeadLetter() {
		return QueueBuilder
				.durable("cria-pedido-servico-deadletter")				
				.build();
		
	}
	
	@Bean
	public Binding criaPedidoDeadLetterBinding() {
		return BindingBuilder
				.bind(this.criaPedidoQueueDeadLetter())
				.to(this.criaPedidoExchange())
				.with("orc.created.pedido-dead-letter")
				.noargs();
	}

}


