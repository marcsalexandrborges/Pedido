package net.atos.api.pedido.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class CancelaPedidoQueueConfig {
	
	@Configuration
	public class CancelaOrdemQueueConfig {
		
		
		@Bean
		public Queue cancelaPedidoQueue() {
			return QueueBuilder
					.durable("cancela-pedido-servico")
					.deadLetterExchange("orcamento")
					.deadLetterRoutingKey("orc.canceled.pedido-dead-letter")
					.build();
			
		}
		
		@Bean 
		public Exchange cancelaPedidoExchange() {
			return ExchangeBuilder.topicExchange("orcamento").durable(true).build();		
		}
		
		@Bean
		public Binding cancelaPedidoBinding() {
			return BindingBuilder
					.bind(this.cancelaPedidoQueue())
					.to(this.cancelaPedidoExchange())
					.with("orc.canceled.pedido")
					.noargs();
		}
		
		
		@Bean
		public Queue cancelaPedidoQueueDeadLetter() {
			return QueueBuilder
					.durable("cancela-pedido-servico-deadletter")				
					.build();
			
		}
		
		@Bean
		public Binding cancelaPedidoDeadLetterBinding() {
			return BindingBuilder
					.bind(this.cancelaPedidoQueueDeadLetter())
					.to(this.cancelaPedidoExchange())
					.with("orc.canceled.pedido-dead-letter")
					.noargs();
		}

	}


}
