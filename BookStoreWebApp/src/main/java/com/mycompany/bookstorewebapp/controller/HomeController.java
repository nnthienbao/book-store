/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorewebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.mycompany.bookstorethriftshare.BookService;
import com.mycompany.bookstorethriftshare.ResultQueryBook;
import com.mycompany.bookstorewebapp.client.BookClient;
import com.mycompany.bookstorewebapp.client.ClientFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private static final int LIMIT = 12;
    @Autowired
    private ClientFactory clientFactory;
    
    @GetMapping({"/", "/index"})
    public String listBook(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name= "kind", defaultValue = "") String kind,
			Model model) {
        try {
			ResultQueryBook resultAllBook = null;
			if(kind.isEmpty()) {
				resultAllBook =  clientFactory.getBookClient().getList(page, LIMIT);
			} else {
				resultAllBook = clientFactory.getBookClient().getBookByKind(kind, page, LIMIT);
			}
			
            model.addAttribute("listBooks", resultAllBook.getListBooks());
			model.addAttribute("totalPage", (resultAllBook.total + LIMIT - 1) / LIMIT);
			model.addAttribute("currentPage", page);
			
			model.addAttribute("listKinds", clientFactory.getBookClient().getListKinds());
        } catch (TException ex) {
            ex.printStackTrace();
        }
        return "index";
    }
}
