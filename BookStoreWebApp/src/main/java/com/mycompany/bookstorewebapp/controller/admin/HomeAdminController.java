/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorewebapp.controller.admin;

import com.mycompany.bookstorewebapp.client.Client;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class HomeAdminController {
    
    @Autowired
    private Client client;
    
    @GetMapping({"", "/", "/index"})
    public String getIndex(Model model) {
        try {
            model.addAttribute("listBooks", client.getList());            
        } catch (TException ex) {
            ex.printStackTrace();
        }
        return "admin/index";
    }
}
