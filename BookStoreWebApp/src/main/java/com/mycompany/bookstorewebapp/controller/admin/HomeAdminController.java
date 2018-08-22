/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorewebapp.controller.admin;

import com.mycompany.bookstorethriftshare.ResultQueryBook;
import com.mycompany.bookstorewebapp.client.BookClient;
import com.mycompany.bookstorewebapp.client.ClientFactory;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class HomeAdminController {
    private static final int LIMIT = 8;
	
    @Autowired
    private ClientFactory clientFactory;
    
    @GetMapping({"", "/", "/index"})
    public String getIndex(
			@RequestParam(name = "page", defaultValue = "0") int page,
			Model model) {
        try {
			ResultQueryBook result =  clientFactory.getBookClient().getList(page, LIMIT);
            model.addAttribute("listBooks", result.getListBooks());
			model.addAttribute("totalPage", (result.total + LIMIT - 1) / LIMIT);
			model.addAttribute("currentPage", page);          
        } catch (TException ex) {
            ex.printStackTrace();
        }
        return "admin/index";
    }
}
