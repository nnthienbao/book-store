/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorebusservice;

import com.mycompany.bookstorethriftshare.Book;
import com.mycompany.bookstorethriftshare.BookService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 *
 * @author cpu02453-local
 */
public class Test {
    public static void main(String args[]) {
        TTransport transport;
        try {            
            transport = new TSocket("localhost", 3001);
            transport.open();
            
            TProtocol protocol = new  TBinaryProtocol(transport);
            BookService.Client client = new BookService.Client(protocol);
            
            System.out.println(client.findById("1"));
            
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}