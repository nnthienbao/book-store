/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorewebapp.controller;

import com.mycompany.bookstorethriftshare.Book;
import com.mycompany.bookstorewebapp.client.BookClient;
import com.mycompany.bookstorewebapp.client.ClientFactory;
import com.mycompany.bookstorewebapp.model.BookSearch;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BookController {
    
    @Autowired
    private ClientFactory clientFactory;
    
    @RequestMapping("/book")
    public String getBook(@RequestParam(value = "id") String id, Model model) {
        try {
            Book book = clientFactory.getBookClient().findById(id); 
            model.addAttribute("book", book);
			model.addAttribute("listKinds", clientFactory.getBookClient().getListKinds());
        } catch (TException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "book";
    }
	
	@RequestMapping(value = "/ajax-search-book", method = RequestMethod.GET)
	public @ResponseBody List<BookSearch> ajaxSearchBookByKeyword(@RequestParam("keyword") String keyword) throws TException {
		List<Book> resultSearch = clientFactory.getBookClient().searchByKeyword(keyword);
		List<BookSearch> resultReturn = new ArrayList<>();
		for(Book book : resultSearch) {
			BookSearch bookSearch = new BookSearch(book.getId(), book.getName());
			resultReturn.add(bookSearch);
		}
		
		return resultReturn;
	}
}
