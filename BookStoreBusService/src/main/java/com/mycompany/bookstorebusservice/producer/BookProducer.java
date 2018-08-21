
package com.mycompany.bookstorebusservice.producer;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class BookProducer {
	private Properties props = null;
	public BookProducer() {
		props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	}
	
	public void sendKeywordSearchNotFound(String keyword) {
		System.out.println("Request fetch data voi key word" + keyword);
		Producer<String, String> producer = new KafkaProducer<>(props);
		producer.send(new ProducerRecord<>("book-search-not-found", keyword));
		producer.close();
	}
	
	public static void main(String args[]) {
		BookProducer producer = new BookProducer();
		producer.sendKeywordSearchNotFound("thien bao");
	}
}
