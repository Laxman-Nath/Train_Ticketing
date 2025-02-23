package org.onlineticketing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.onlineticketing.entities.Customer;
import org.onlineticketing.entities.Ticket;
import org.onlineticketing.entities.Train;
import org.onlineticketing.services.CustomerServiceImpl;
import org.onlineticketing.services.TrainServiceImpl;
import org.onlineticketing.utils.IdGenerator;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class App {
    public static void main(String[] args) throws IOException {
        int choice;
        Scanner sc = new Scanner(System.in);
        CustomerServiceImpl customerService = new CustomerServiceImpl();
        TrainServiceImpl trainService = new TrainServiceImpl();
        do {
            System.out.println("**************************");
            System.out.println("1.Log in.....");
            System.out.println("2.Register.....");
            System.out.println("3.View all your booked tickets.......");
            System.out.println("4.Get All Available trains.......");
            System.out.println("5.Book Ticket");
            System.out.println("6.Check Train......");
            System.out.println("7.Cancel Ticket.......");
            System.out.println("8.Exit......");
            System.out.println("**************************");
            System.out.println("Enter your choice");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    sc.nextLine();
                    System.out.println("Enter username:");
                    String userName = sc.nextLine();
                    System.out.println("Enter password:");
                    String password = sc.next();
                    customerService.login(userName, password);
                    break;
                case 2:
                    System.out.println("Enter id:");
                    String id = sc.next();
                    sc.nextLine();
                    System.out.println("Enter username:");
                    userName = sc.nextLine();
                    System.out.println("Enter password:");
                    password = sc.next();
                    sc.nextLine();
                    System.out.println("Enter address:");
                    String address = sc.nextLine();
                    customerService.register(new Customer(id, userName, address, password, Collections.emptyList(), null));
//                {"id":"cde","name":"Laxman nath","address":"address","bookedTickets":null,"password":"1234","hashedPassword":"$2a$12$53cwNZGh6TK/Vkm/ZMcnt.Q4MOA3rDcN.rRry.nc/evd01NltPsFa","loggedIn":true}]
                    break;
                case 3:
                    System.out.println("Enter user id:");
                    id = sc.next();
                    List<Ticket> ticketList = customerService.getAllBookedTicketsOfUser(id);
                    if (ticketList == null) {
                        System.out.println("********************************** Invalid id of user ***********************************");
                    } else if (ticketList.isEmpty()) {
                        System.out.println("*******There are no tickets booked previously*********************");
                    } else {
                        ticketList.forEach((t) -> System.out.println(t.toString()));
                    }
                    break;
                case 4:

                    List<Train> trainList = trainService.getAllTrains();
                    if (trainList.isEmpty()) {
                        System.out.println("*******There are no trains available *********************");
                    } else {
                        trainList.forEach((t) -> System.out.println(t.toString()));
                    }
                    break;

                case 5:
                    sc.nextLine();
                    System.out.println("Enter train id :");
                    String trainId = sc.next();
                    sc.nextLine();
                    System.out.println("Enter user id :");
                    String userId = sc.next();
                    customerService.bookTicket(userId, trainId);
                    break;
                case 6:
                    sc.nextLine();
                    System.out.println("Enter train id to check:");
                    trainId = sc.next();
                    sc.nextLine();
                    System.out.println("Enter your entry point:");
                    String source = sc.nextLine();
//                    sc.nextLine();
                    System.out.println("Enter your exit point:");
                    String destination = sc.nextLine();
                    trainService.checkTrain(source, destination, trainId);
                    break;

                case 7:
                    sc.nextLine();
                    System.out.println("Enter ticket id :");
                    String ticketId = sc.next();
                    sc.nextLine();
                    System.out.println("Enter user id :");
                    userId = sc.next();
                    customerService.cancelTicket(ticketId, userId);
                    break;
                default:
                    if (choice != 8) {
                        System.out.println("Invalid choice");
                    }
            }

        } while (choice != 8);
        System.out.println("Thank you for using our system.Have a nice day");
        System.out.println(new CustomerServiceImpl().login("Laxman Nath", "1234"));

//        System.out.println(IdGenerator.generateId());

//        ObjectMapper objectMapper = new ObjectMapper();
//        List<Train> trains = objectMapper.readValue(new File("D:\\Java FullStack\\Traein_Ticketsing\\src\\main\\java\\org\\onlineticketing\\repo\\train.json"), objectMapper.getTypeFactory().constructCollectionType(List.class, Train.class));
//        System.out.println(trains.isEmpty());
//        trains.forEach(System.out::println);
    }
}
