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
import com.mycompany.bookstorewebapp.client.BookClient;
import com.mycompany.bookstorewebapp.client.ClientFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class HomeController {
    
    @Autowired
    private ClientFactory clientFactory;
    
    @GetMapping({"/", "/index"})
    public String listBook(Model model) {
        try {
            model.addAttribute("listBooks", clientFactory.getBookClient().getList());            
        } catch (TException ex) {
            ex.printStackTrace();
        }
        return "index";
    }
}
