package org.onlineticketing;

import org.onlineticketing.entities.Customer;
import org.onlineticketing.entities.Ticket;
import org.onlineticketing.services.CustomerServiceImpl;


public class App 
{
    public static void main( String[] args )
    {
//            public Customer(String id, String name, String address, List< Ticket > bookedTickets, String password, String hashedPassword)
//        while(true) {
//            System.out.println(new CustomerServiceImpl().register(new Customer("cde", "Laxman nath", "address", null, "1234", "5678")));
//        }
        System.out.println(new CustomerServiceImpl().login("Laxman Nath","1234"));
    }
}
