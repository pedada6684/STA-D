package com.klpc.stadalert.global.config;

import com.klpc.stadalert.domain.alert.controller.event.AdvertsStartEvent;
import com.klpc.stadalert.domain.alert.controller.event.ContentStartEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, ContentStartEvent> ContentStartEventKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, ContentStartEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory(ContentStartEvent.class));
		return factory;
	}

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, AdvertsStartEvent> AdvertsStartEventKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, AdvertsStartEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory(AdvertsStartEvent.class));
		return factory;
	}

	public <V> ConsumerFactory<String, V> consumerFactory(
		Class<V> valueType
	) {
		return new DefaultKafkaConsumerFactory<>(consumerConfigurations(),
			new ErrorHandlingDeserializer<>(new StringDeserializer()),
			new ErrorHandlingDeserializer<>(new JsonDeserializer<V>(valueType, false))
		);
	}

	@Bean
	public Map<String, Object> consumerConfigurations() {
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

		config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
		config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

		return config;
	}
}