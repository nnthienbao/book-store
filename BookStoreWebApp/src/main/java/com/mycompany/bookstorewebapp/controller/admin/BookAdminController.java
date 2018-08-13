/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorewebapp.controller.admin;

import com.mycompany.bookstorethriftshare.Book;
import com.mycompany.bookstorewebapp.client.Client;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class BookAdminController {
    
    @Autowired
    private Client client;
    
    @GetMapping("/addbook")
    public String getAddBook(Model model) {
        model.addAttribute("newbook", new Book());
        return "admin/add-book";
    }
    
    @GetMapping("/updatebook")
    public String getUpdateBook(@RequestParam("id") String id, Model model) {
        try {
            Book bookUpdate = client.findById(id);
            model.addAttribute("bookUpdate", bookUpdate);
        } catch (TException ex) {
            Logger.getLogger(BookAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "admin/update-book";
    }
    
    @PostMapping("/addbook")
    public String postAddBook(@ModelAttribute("newbook") Book newbook, Model model) {
        try {
            client.add(newbook);            
        } catch (TException ex) {
            Logger.getLogger(BookAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/admin/addbook";
    }
    
    @PostMapping("/updatebook")
    public String postUpdateBook(@ModelAttribute("bookUpdate") Book bookUpdate, Model model) {
        System.out.println(bookUpdate);
        try {
            client.update(bookUpdate);
        } catch (TException ex) {
            Logger.getLogger(BookAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/admin/updatebook?id=" + bookUpdate.getId();
    }
    
    @PostMapping("/removebook")
    public String postRemoveBook(@RequestParam("idBook") String idBook) {
        try {
            client.remove(idBook);
        } catch (TException ex) {
            Logger.getLogger(BookAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/admin";
    }
}
