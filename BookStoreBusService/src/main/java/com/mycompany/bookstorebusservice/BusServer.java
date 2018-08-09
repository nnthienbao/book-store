/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorebusservice;

import com.mycompany.bookstorethriftshare.BookService;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author cpu02453-local
 */
public class BusServer {
    public static void main(String args[]) throws TTransportException {
        BusBookHandler daoBookHandler = new BusBookHandler();
        BookService.Processor processor = new BookService.Processor(daoBookHandler);
        
        TServerTransport serverTransport = new TServerSocket(3001);
        TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
        
        System.out.println("Starting the BUS server...");
        server.serve();
    }
}
