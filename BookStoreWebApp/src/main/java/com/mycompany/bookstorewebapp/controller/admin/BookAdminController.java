/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorewebapp.controller.admin;

import com.mycompany.bookstorethriftshare.Book;
import com.mycompany.bookstorewebapp.authenticate.UserInfo;
import com.mycompany.bookstorewebapp.client.ClientFactory;
import com.mycompany.bookstorewebapp.utils.Utils;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class BookAdminController {
    
    @Autowired
    private ClientFactory clientFactory;
    
    @GetMapping("/addbook")
    public String getAddBook(Model model) {
        model.addAttribute("newbook", new Book());
        return "admin/add-book";
    }
    
    @GetMapping("/updatebook")
    public String getUpdateBook(@RequestParam("id") String id, Model model) {
        try {
            Book bookUpdate = clientFactory.getBookClient().findById(id);
            model.addAttribute("bookUpdate", bookUpdate);
        } catch (TException ex) {
            Logger.getLogger(BookAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "admin/update-book";
    }
    
    @PostMapping("/addbook")
    public String postAddBook(@ModelAttribute("newbook") Book newbook, @RequestParam("anhBia") MultipartFile anhBia, Model model) {
        UserInfo userInfo = (UserInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userInfo.getToken());
        try {
            newbook.setImage(Utils.toBase64(anhBia.getBytes()));
            String extFileName = FilenameUtils.getExtension(anhBia.getOriginalFilename());
            newbook.setExtImage(extFileName);
            clientFactory.getBookClient().add(newbook, userInfo.getToken());       
        } catch (Exception ex) {
            Logger.getLogger(BookAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/admin/addbook";
    }
    
    @PostMapping("/updatebook")
    public String postUpdateBook(@ModelAttribute("bookUpdate") Book bookUpdate, @RequestParam("anhBia") MultipartFile anhBia, Model model) {
        UserInfo userInfo = (UserInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            bookUpdate.setImage(Utils.toBase64(anhBia.getBytes()));
            String extFileName = FilenameUtils.getExtension(anhBia.getOriginalFilename());
            bookUpdate.setExtImage(extFileName);
            clientFactory.getBookClient().update(bookUpdate, userInfo.getToken());
        } catch (Exception ex) {
            Logger.getLogger(BookAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/admin/updatebook?id=" + bookUpdate.getId();
    }
    
    @PostMapping("/removebook")
    public String postRemoveBook(@RequestParam("idBook") String idBook) {
        UserInfo userInfo = (UserInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            clientFactory.getBookClient().remove(idBook, userInfo.getToken());
        } catch (TException ex) {
            Logger.getLogger(BookAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/admin";
    }
}
