/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstoredataservice.dao;

import kyotocabinet.DB;

/**
 *
 * @author cpu02453-local
 */
public class FactoryDb {
    private FactoryDb(){}
    
    private static DB _dbBook = null;
    
    public static DB getDBBook() {
        if(_dbBook == null) {
            _dbBook = new DB();
            if (!_dbBook.open("book.kch", DB.OWRITER | DB.OCREATE)){
                System.out.println("Khong mo duoc database");
            }
        }
        return _dbBook;
    }
}
