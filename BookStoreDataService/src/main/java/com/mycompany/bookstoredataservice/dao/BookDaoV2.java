/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstoredataservice.dao;

import com.mycompany.bookstorethriftshare.Book;
import com.mycompany.bookstorethriftshare.BookService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import kyotocabinet.DB;
import org.apache.thrift.TException;

public class BookDaoV2 implements BookService.Iface {

    private String generateBookId() {
        Date now = new Date();
        return "book:" + now.getTime() + "";
    }
    
    @Override
    public List<Book> getList() throws TException {
        List<Book> listBooks = new ArrayList();
        DB bookDB = FactoryDb.getDBBookStore();
        String regex = "book:[[:digit:]]+.name";
        List<String> keyBookNames = bookDB.match_regex(regex, -1);
        System.out.println(keyBookNames);
        for(String keyBookName : keyBookNames) {
            String bookId = keyBookName.split(".name")[0];
            System.out.println(bookId);
            String keyBookKind = bookId + ".kind";
            String keyBookAuthor = bookId + ".author";
            String keyBookPrice = bookId + ".price";
            
            String bookName = bookDB.get(keyBookName);
            String bookKind = bookDB.get(keyBookKind);
            String bookAuthor = bookDB.get(keyBookAuthor);
            int bookPrice = Integer.parseInt(bookDB.get(keyBookPrice));
            
            Book book = new Book(bookId, bookName, bookKind, bookAuthor, bookPrice);
            
            listBooks.add(book);
        }
        return listBooks;
    }

    @Override
    public Book findById(String bookId) throws TException {
        DB bookDB = FactoryDb.getDBBookStore();
        String keyBookName = bookId + ".name";
        String keyBookKind = bookId + ".kind";
        String keyBookAuthor = bookId + ".author";
        String keyBookPrice = bookId + ".price";
        
        String bookName = bookDB.get(keyBookName);
        String bookKind = bookDB.get(keyBookKind);
        String bookAuthor = bookDB.get(keyBookAuthor);
        int bookPrice = Integer.parseInt(bookDB.get(keyBookPrice));
        
        return new Book(bookId, bookName, bookKind, bookAuthor, bookPrice);
    }

    @Override
    public boolean add(Book newBook, String token) throws TException {
        DB bookDB = FactoryDb.getDBBookStore();
        String bookId = generateBookId();
        String keyBookName = bookId + ".name";
        String keyBookKind = bookId + ".kind";
        String keyBookAuthor = bookId + ".author";
        String keyBookPrice = bookId + ".price";
        
        bookDB.add(keyBookName, newBook.getName());
        bookDB.add(keyBookKind, newBook.getKind());
        bookDB.add(keyBookAuthor, newBook.getAuthor());
        bookDB.add(keyBookPrice, newBook.getPrice() + "");
        
        return true;
    }

    @Override
    public boolean update(Book updateBook, String token) throws TException {
        DB bookDB = FactoryDb.getDBBookStore();
        String bookId = updateBook.getId();
        String keyBookName = bookId + ".name";
        String keyBookKind = bookId + ".kind";
        String keyBookAuthor = bookId + ".author";
        String keyBookPrice = bookId + ".price";
        
        bookDB.replace(keyBookName, updateBook.getName());
        bookDB.replace(keyBookKind, updateBook.getKind());
        bookDB.replace(keyBookAuthor, updateBook.getAuthor());
        bookDB.replace(keyBookPrice, updateBook.getPrice() + "");
        
        return true;
    }

    @Override
    public boolean remove(String bookId, String token) throws TException {
        DB bookDB = FactoryDb.getDBBookStore();
        String keyBookName = bookId + ".name";
        String keyBookKind = bookId + ".kind";
        String keyBookAuthor = bookId + ".author";
        String keyBookPrice = bookId + ".price";
        
        bookDB.remove(keyBookName);
        bookDB.remove(keyBookKind);
        bookDB.remove(keyBookAuthor);
        bookDB.remove(keyBookPrice);
        
        return true;
    }
    
}
