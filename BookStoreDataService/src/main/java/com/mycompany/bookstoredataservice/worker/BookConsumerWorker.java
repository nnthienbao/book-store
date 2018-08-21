package com.mycompany.bookstoredataservice.worker;

import com.mycompany.bookstoredataservice.dao.BookDao;
import com.mycompany.bookstoredataservice.dao.FactoryDb;
import com.mycompany.bookstorethriftshare.Book;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookConsumerWorker implements Runnable {
	
	public BookConsumerWorker() {
		
	}
	
	@Override
	public void run() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", "bookstore");
		props.put("enable.auto.commit", "false");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Arrays.asList("book-search-not-found"));
		
		BookDao bookDao = new BookDao();
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
			for (ConsumerRecord<String, String> record : records) {
				String keyword = record.value();
				System.out.println("Fetch data voi keyword" + keyword);
				List<Book> listBooks = fetchBookFromInternet(keyword);
				for(Book book : listBooks) {
					bookDao.add(book, "");
				}
			}
		}
	}
	
	public List<Book> fetchBookFromInternet(String keyword) {
		try {
			return GoogleApiProductBook.productByKeyword(keyword);
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} catch (GeneralSecurityException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static void main(String args[]) {
		Thread t = new Thread(new BookConsumerWorker());
		t.start();
	}

}
