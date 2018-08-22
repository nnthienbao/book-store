package com.mycompany.bookstoredataservice.worker;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.model.Volumes;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.mycompany.bookstorethriftshare.Book;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class GoogleApiProductBook {

	private static final String APPLICATION_NAME = "bookstore";
	private static final String API_KEY = "AIzaSyDq-J5DjAhKpOQfglJe3FwYW4YRqwhSAAo";
	
	public static java.util.List<Book> productByKeyword(String keyword) throws IOException, GeneralSecurityException {
		final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null)
				.setApplicationName(APPLICATION_NAME)
				.setGoogleClientRequestInitializer(new BooksRequestInitializer(API_KEY))
				.build();

		List volumesList = (List) books.volumes().list(keyword);
		// Execute the query.
		Volumes volumes = volumesList.execute();
		if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
			System.out.println("No matches found.");
			return null;
		}

		java.util.List<Book> results = new ArrayList<>();
		
		System.out.println(volumes.size());
		// Output results.
		for (Volume volume : volumes.getItems()) {
			Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
			Volume.SaleInfo saleInfo = volume.getSaleInfo();
			
//			System.out.println(volumeInfo.getTitle());
//			System.out.println(volumeInfo.getAuthors());
//			System.out.println(volumeInfo.getCategories());
			
			String title = volumeInfo.getTitle();
			String authors = getStrAuthor(volumeInfo.getAuthors());
			String categories = getStrCategories(volumeInfo.getCategories());
			int price = getPrice(saleInfo);
			if(price == -1) continue;
			if(volumeInfo.getImageLinks() == null) continue;
			String imageSrc = volumeInfo.getImageLinks().getThumbnail();
			
			results.add(new Book("", title, categories, authors, price, imageSrc, "web"));
		}
		return results;
	}
	
	private static String getStrAuthor(java.util.List<String> listStringAuthors) {
		if(listStringAuthors == null) return "";
		String strAuthors = "";
		for(int i = 0; i < listStringAuthors.size(); i++) {
			strAuthors += listStringAuthors.get(i);
			if(i < listStringAuthors.size() - 1) strAuthors += ", ";
		}
		return strAuthors;
	}
	
	private static String getStrCategories(java.util.List<String> listStringCategories) {
		if(listStringCategories == null) return "";
		String strCategories = "";
		for(int i = 0; i < listStringCategories.size(); i++) {
			strCategories += listStringCategories.get(i);
			if(i < listStringCategories.size() - 1) strCategories += ", ";
		}
		return strCategories;
	}
	
	private static int getPrice(Volume.SaleInfo saleInfo) {
		return (int) (saleInfo.getListPrice() != null ? saleInfo.getListPrice().getAmount() : -1);
	}
	
	public static void main(String args[]) throws IOException, GeneralSecurityException {
		GoogleApiProductBook.productByKeyword("aaaaaaaa");
	}
}