/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorewebapp.controller;

import com.mycompany.bookstorethriftshare.Book;
import com.mycompany.bookstorewebapp.client.Client;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {
    
    @Autowired
    private Client client;
    
    @RequestMapping("/book")
    public String getBook(@RequestParam(value = "id") String id, Model model) {
        try {
            Book book = client.findById(id); 
            model.addAttribute("book", book);
        } catch (TException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "book";
    }
}
