/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorebusservice;

import com.mycompany.bookstorethriftshare.Book;
import com.mycompany.bookstorethriftshare.BookService;
import com.mycompany.bookstorethriftshare.User;
import com.mycompany.bookstorethriftshare.UserService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Test {
    public static void main(String args[]) {
        TTransport transport;
        try {            
            transport = new TSocket("localhost", 3001);
            transport.open();
            TProtocol protocol = new  TBinaryProtocol(transport);
            TMultiplexedProtocol mulProtocol = new TMultiplexedProtocol(protocol, "bookService");
            BookService.Client bookService = new BookService.Client(mulProtocol);
            
            System.out.println(bookService.searchByKeyword("aaaaaaaaaaaaaaaaaaaa"));
            
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
