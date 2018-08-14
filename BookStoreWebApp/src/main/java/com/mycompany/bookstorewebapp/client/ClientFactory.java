/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorewebapp.client;

import com.mycompany.bookstorethriftshare.BookService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class ClientFactory {
    private TTransport transport;
    private TProtocol clientProtocol;
    
    public ClientFactory() {
        try {            
            transport = new TSocket("localhost", 3001);
            transport.open();
            
            clientProtocol = new  TBinaryProtocol(transport);                     

        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
    
    public BookClient getBookClient() {
        return new BookClient(clientProtocol);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        transport.close();
    }
    
    
}
