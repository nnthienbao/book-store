/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorewebapp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class BookAdminController {
    @GetMapping("/addbook")
    public String getAddBook() {
        return "admin/add-book";
    }
    
    @GetMapping("/updatebook")
    public String getUpdateBook() {
        return "admin/update-book";
    }
}
