/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstoredataservice.dao;

import com.mycompany.bookstoredataservice.client.ElasticClient;
import com.mycompany.bookstoredataservice.utils.Utils;
import com.mycompany.bookstorethriftshare.Book;
import com.mycompany.bookstorethriftshare.BookNotFoundException;
import com.mycompany.bookstorethriftshare.BookService;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kyotocabinet.DB;
import org.apache.thrift.TException;

/**
 *
 * @author cpu02453-local
 */
public class BookDao implements BookService.Iface {

	private ElasticClient elasticClient = null;
	
	public BookDao() {
		try {
			elasticClient = new ElasticClient(this);
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		}
	}
	
	private String generateBookId() {
		Date now = new Date();
		return "book:" + now.getTime() + "";
	}

	@Override
	public boolean add(Book newBook, String token) {
		DB bookDB = FactoryDb.getDBBookStore();
		String bookId = generateBookId();
		newBook.setId(bookId);
		bookDB.add(bookId.getBytes(), Utils.toByte(newBook));
		elasticClient.addBook(newBook);
		return true;
	}

	@Override
	public List<Book> getList() throws TException {
		List<Book> listBooks = new ArrayList();
		DB bookDB = FactoryDb.getDBBookStore();
		List<String> keys = bookDB.match_prefix("book:", -1);
		for (String key : keys) {
			byte[] bytesBook = bookDB.get(key.getBytes());
			listBooks.add((Book) Utils.toObject(bytesBook));
		}
		return listBooks;
	}

	@Override
	public Book findById(String id) throws BookNotFoundException {
		DB bookDB = FactoryDb.getDBBookStore();
		String value = bookDB.get(id);
		if (value == null) {
			throw new BookNotFoundException("Khong tim thay sach");
		}

		byte[] bytes = bookDB.get(id.getBytes());

		return (Book) Utils.toObject(bytes);
	}

	@Override
	public boolean update(Book updateBook, String token) throws TException {
		DB bookDB = FactoryDb.getDBBookStore();
		byte[] bytes = bookDB.get(updateBook.id.getBytes());
		
		if (bytes == null) {
			throw new BookNotFoundException("Khong tim thay sach");
		}
		
		if(updateBook.getImage().isEmpty() || updateBook.getImage() == null) {
			Book oldBook = (Book) Utils.toObject(bytes);
			updateBook.setImage(oldBook.getImage());
			updateBook.setExtImage(oldBook.getExtImage());
		}		
		elasticClient.updateBook(updateBook);
		return bookDB.replace(updateBook.id.getBytes(), Utils.toByte(updateBook));
	}

	@Override
	public boolean remove(String idBook, String token) throws TException {
		DB bookDB = FactoryDb.getDBBookStore();
		String value = bookDB.get(idBook);
		if (value == null) {
			throw new BookNotFoundException("Khong tim thay sach");
		}
		elasticClient.removeBook(idBook);
		return bookDB.remove(idBook);
	}

	@Override
	public List<Book> searchByKeyword(String keyword) throws TException {
		return elasticClient.searchByKeyword(keyword);
	}
}
