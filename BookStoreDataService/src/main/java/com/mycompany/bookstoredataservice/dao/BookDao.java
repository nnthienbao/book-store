/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstoredataservice.dao;

import com.mycompany.bookstoredataservice.utils.Utils;
import com.mycompany.bookstorethriftshare.Book;
import com.mycompany.bookstorethriftshare.BookNotFoundException;
import com.mycompany.bookstorethriftshare.BookService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kyotocabinet.Cursor;
import kyotocabinet.DB;
import org.apache.thrift.TException;

/**
 *
 * @author cpu02453-local
 */
public class BookDao implements BookService.Iface {
    
    private String generateBookId() {
        DB bookDB = FactoryDb.getDBBookStore();
        return bookDB.count() + "";
    }
    
    public boolean add(Book newBook) {
        DB bookDB = FactoryDb.getDBBookStore();
        String bookId = generateBookId();
        newBook.setId(bookId);
        
        byte[] bytes = Utils.toByte(newBook);
        bookDB.add(bookId.getBytes(), bytes);
        
        return true;
    }

    @Override
    public List<Book> getList() throws TException {
        List<Book> listBooks = new ArrayList();
        DB bookDB = FactoryDb.getDBBookStore();
        List<String> keys = bookDB.match_prefix("", 1000);
        for(String key : keys) {
            byte[] bytesBook = bookDB.get(key.getBytes());
            listBooks.add((Book) Utils.toObject(bytesBook));
        }
        return listBooks;
    }

    @Override
    public Book findById(String id) throws BookNotFoundException {
        DB bookDB = FactoryDb.getDBBookStore();
        String value = bookDB.get(id);
        if(value == null) throw new BookNotFoundException("Khong tim thay sach");
        
        byte[] bytes = bookDB.get(id.getBytes());
        
        return (Book)Utils.toObject(bytes);
    }

    @Override
    public boolean update(Book updateBook) throws TException {
        DB bookDB = FactoryDb.getDBBookStore();
        byte[] value = bookDB.get(updateBook.id.getBytes());
        if(value == null) throw new BookNotFoundException("Khong tim thay sach");
        
        return bookDB.replace(updateBook.id.getBytes(), Utils.toByte(updateBook));
    }

    @Override
    public boolean remove(String idBook) throws TException {
        DB bookDB = FactoryDb.getDBBookStore();
        String value = bookDB.get(idBook);
        if(value == null) throw new BookNotFoundException("Khong tim thay sach");
        
        return bookDB.remove(idBook);
    }       
}
