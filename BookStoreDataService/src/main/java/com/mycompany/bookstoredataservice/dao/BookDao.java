/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstoredataservice.dao;

import com.mycompany.bookstorethriftshare.Book;
import com.mycompany.bookstorethriftshare.BookNotFoundException;
import com.mycompany.bookstorethriftshare.BookService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kyotocabinet.DB;
import org.apache.thrift.TException;

/**
 *
 * @author cpu02453-local
 */
public class BookDao implements BookService.Iface {
    public boolean add(Book newBook) {
        DB bookDB = FactoryDb.getDBBook();
        String key = bookDB.count() + "";
        String value = key + "," + newBook.name + "," + newBook.author;
        bookDB.add(key, value);
        return true;
    }

    @Override
    public List<Book> getList() throws TException {
        List<Book> listBooks = new ArrayList();
        DB bookDB = FactoryDb.getDBBook();
        List<String> keys = bookDB.match_prefix("", 100);
        Map<String, String> records = bookDB.get_bulk(keys, false);        
        for (Map.Entry<String, String> entry : records.entrySet())
        {
            String[] arrProps = entry.getValue().split(",");
            Book book = new Book(entry.getKey(), arrProps[1], arrProps[2]);
            listBooks.add(book);
        }
        
        return listBooks;
    }

    @Override
    public Book findById(String id) throws BookNotFoundException {
        DB bookDB = FactoryDb.getDBBook();
        String value = bookDB.get(id);
        if(value == null) throw new BookNotFoundException("Khong tim thay sach");
        
        String arrProps[] = value.split(",");
        
        return new Book(arrProps[0], arrProps[1], arrProps[2]);
    }

    @Override
    public boolean update(Book updateBook) throws TException {
        DB bookDB = FactoryDb.getDBBook();
        String value = bookDB.get(updateBook.id);
        if(value == null) throw new BookNotFoundException("Khong tim thay sach");
        
        String newValue = updateBook.id + "," + updateBook.name + "," + updateBook.author;
        return bookDB.replace(updateBook.id, newValue);
    }

    @Override
    public boolean remove(String idBook) throws TException {
        DB bookDB = FactoryDb.getDBBook();
        String value = bookDB.get(idBook);
        if(value == null) throw new BookNotFoundException("Khong tim thay sach");
        
        return bookDB.remove(idBook);
    }       
}
