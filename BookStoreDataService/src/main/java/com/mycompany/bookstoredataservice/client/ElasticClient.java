/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstoredataservice.client;

import com.mycompany.bookstoredataservice.dao.BookDao;
import com.mycompany.bookstorethriftshare.Book;
import com.mycompany.bookstorethriftshare.BookNotFoundException;
import com.mycompany.bookstorethriftshare.ResultQueryBook;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.Fuzziness;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ElasticClient {

	private TransportClient elasticClient = null;
	private BookDao bookDao = null;
	
	public ElasticClient(BookDao bookDao) throws UnknownHostException {
		connectToCluster();
		this.bookDao = bookDao;
	}
	
	public List<Book> searchByKeyword(String keyword) {
		List<Book> result = new ArrayList<>();
		SearchResponse response = elasticClient.prepareSearch("bookstore")
        .setTypes("book")
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setQuery(QueryBuilders.queryStringQuery(keyword))
        .get();
		
		SearchHit[] hits = response.getHits().getHits();
		
		for(SearchHit hit : hits) {
			try {
				String bookId = hit.getId();
				Book book = bookDao.findById(bookId);
				result.add(book);
			} catch (BookNotFoundException ex) {
				ex.printStackTrace();
			}
		}
		
		return result;
	}
	
	public ResultQueryBook getList(int page, int limit) {		
		ResultQueryBook result = new ResultQueryBook();
		SearchResponse response = elasticClient.prepareSearch("bookstore")
        .setTypes("book")
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setQuery(QueryBuilders.matchAllQuery()).setFrom(page * limit).setSize(limit)
        .get();
		
		SearchHit[] hits = response.getHits().getHits();
		for(SearchHit hit : hits) {
			try {
				String bookId = hit.getId();
				Book book = bookDao.findById(bookId);
				result.addToListBooks(book);
			} catch (BookNotFoundException ex) {				
				ex.printStackTrace();
			}
		}
		
		result.setTotal(response.getHits().getTotalHits());
		
		return result;
	}
	
	public boolean addBook(Book book) {
		IndexResponse response = null;
		try {
			response = elasticClient.prepareIndex("bookstore", "book", book.getId())
					.setSource(jsonBuilder()
							.startObject()
							.field("name", book.getName())
							.field("kind", book.getKind())
							.field("author", book.getAuthor())
							.endObject()
					)
					.get();
		} catch (IOException ex) {
			return false;
		}
		return response.status() == RestStatus.OK;
	}
	
	public boolean updateBook(Book book) {
		UpdateResponse response = null;
		try {
			response = elasticClient.prepareUpdate("bookstore", "book", book.getId())
					.setDoc(jsonBuilder()
							.startObject()
							.field("name", book.getName())
							.field("kind", book.getKind())
							.field("author", book.getAuthor())
							.endObject()
					).get();
		} catch (IOException ex) {
			return false;
		}
		return response.status() == RestStatus.OK;
	}
	
	public boolean removeBook(String idBook) {
		DeleteResponse response = elasticClient.prepareDelete("bookstore", "book", idBook).get();
		return response.status() == RestStatus.OK;
	}

	private void connectToCluster() throws UnknownHostException {
		elasticClient = new PreBuiltTransportClient(Settings.EMPTY)
        .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
	}

	@Override
	protected void finalize() throws Throwable {
		if(elasticClient != null) {
			elasticClient.close();
		}
	}
	
}
