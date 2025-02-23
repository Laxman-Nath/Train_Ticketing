package org.onlineticketing.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.onlineticketing.entities.Customer;
import org.onlineticketing.entities.Ticket;
import org.onlineticketing.entities.Train;
import org.onlineticketing.utils.IdGenerator;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CustomerServiceImpl {

    private static final String PATH = "D:\\Java FullStack\\Traein_Ticketsing\\src\\main\\java\\org\\onlineticketing\\repo\\customer.json";

    List<Customer> registeredCustomers = new ArrayList<>();
    ObjectMapper objectMapper = new ObjectMapper();
    File file = new File(PATH);
    TrainServiceImpl trainService = new TrainServiceImpl();
    Scanner sc = new Scanner(System.in);
    TicketServiceImpl ticketService = new TicketServiceImpl();

    public CustomerServiceImpl() {
        try {

            registeredCustomers = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Customer.class));
//            System.out.println("Registered customers:" + registeredCustomers.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean register(Customer customer) {

        Optional<Customer> customer1 = registeredCustomers.stream().filter((c) -> c.getId().equalsIgnoreCase(customer.getId())).findFirst();
        if (customer1.isPresent()) {
            System.out.println("****************** Registration Error:user with this id already exists:**************************");
            return false;
        }
        try {
            customer.setLoggedIn(false);
            customer.setHashedPassword(BCrypt.withDefaults().hashToString(12, customer.getPassword().toCharArray()));
            registeredCustomers.add(customer);
            objectMapper.writeValue(file, registeredCustomers);
            System.out.println("*************** You are registered sucessfully.You can login in now*******************");
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void addCustomer(Customer customer) throws IOException {
        registeredCustomers.add(customer);
        objectMapper.writeValue(file, registeredCustomers);
    }

    public boolean login(String userName, String password) {
        if (!isUserExistsByUserName(userName)) {
            System.out.println("************************** Error Logging in:Username is not valid **************************");
            return false;
        } else if (!isUserExistsByUserNameAndPassword(userName, password)) {
            System.out.println("************************** Error Logging in:Invalid password **************************");
            return false;
        }
        Optional<Customer> customer = registeredCustomers.stream().filter((c) -> c.getName().equalsIgnoreCase(userName)).findFirst();
        customer.ifPresent((c) -> {
            c.setLoggedIn(true);
            try {
                objectMapper.writeValue(file, registeredCustomers);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        System.out.println("************************** Login success:**************************");
        return true;
    }

    public boolean isUserExistsByUserName(String userName) {
        Optional<Customer> customer1 = registeredCustomers.stream().filter((c) -> c.getName().equalsIgnoreCase(userName)).findFirst();
        return customer1.isPresent();
    }

    public Customer getCustomerById(String userId) {

        Optional<Customer> customer = registeredCustomers.stream().filter((c) -> c.getId().equalsIgnoreCase(userId)).findFirst();
        return customer.orElse(null);
    }

    public boolean isUserExistsByUserNameAndPassword(String userName, String password) {
        Optional<Customer> customer1 = registeredCustomers.stream().filter((c) -> c.getName().equalsIgnoreCase(userName)).findFirst();
        if (customer1.isPresent()) {
            Customer c2 = customer1.get();
            return BCrypt.verifyer().verify(password.toCharArray(), c2.getHashedPassword()).verified;
        }
        return false;
    }

    public boolean bookTicket(String userId, String trainId) throws IOException {
        Optional<Customer> customer = registeredCustomers.stream().filter((c) -> c.getId().equalsIgnoreCase(userId)).findFirst();
        Train train = trainService.getTrainById(trainId);
        if (train == null) {
            System.out.println("**********************Invalid train id****************");
            return false;
        }

        if (customer.isEmpty()) {
            System.out.println("**********************Invalid user id ****************");
        }
// public Ticket(String id, Train train, Double perSeatPrice, Double totalPrice, Integer totalSeats, List<Integer> seats) {
        System.out.println("Enter the number of seats to book:");
        int totalSeats = sc.nextInt();
        int i = 2;
        List<Integer> seats = new ArrayList<>();
        while (i >0) {
            System.out.println("Enter train seat Number one by one:");
            Integer seatNo = sc.nextInt();
            if (trainService.isSeatAvailable(seatNo, train, i)) {
                if (trainService.bookSeat(seatNo, train)) {
                    System.out.println("******************This seat is booked successfully***********************");
                    i--;
                    seats.add(seatNo);

                } else {
                    System.out.println("******************Error booking seat.***********************");
                }
            } else {
                System.out.println("Enter 1 to exit from this service.Any other number to continue");
                int e = sc.nextInt();
                if (e == 1) {
                    return false;
                }
            }

        }


        Ticket ticket = new Ticket(IdGenerator.generateId(), train, 500.0, (double) (500 * totalSeats), totalSeats, seats);
//        ticketService.addTicket(ticket);
        if (ticketService.addTicket(ticket)) {
            List<Ticket> ticketList = customer.get().getBookedTickets();
            ticketList.add(ticket);
            customer.get().setBookedTickets(ticketList);
            objectMapper.writeValue(file,registeredCustomers);
            return true;
        }
        return false;
    }


    public List<Ticket> getAllBookedTicketsOfUser(String userId) {
        Optional<Customer> customer = registeredCustomers.stream().filter((c) -> c.getId().equalsIgnoreCase(userId)).findFirst();
//        System.out.println(registeredCustomers);
        if (customer.isEmpty()) {

            return null;
        }
        List<Ticket> ticketList = customer.get().getBookedTickets();
//        System.out.println(ticketList);
        return ticketList;


    }

    public boolean cancelTicket(String ticketId, String userId) throws IOException {
        Ticket ticket = ticketService.getTicketById(ticketId);
        Customer customer = getCustomerById(userId);

        if (ticket == null) {
            System.out.println("********************* Invalid id of ticket*******************");
            return false;
        }

        if (customer == null) {
            System.out.println("********************* Invalid id of user*******************");
            return false;
        }

        Optional<Ticket> ticket1 = customer.getBookedTickets().stream().filter((t) -> t.getId().equalsIgnoreCase(ticketId)).findFirst();
        if (ticket1.isEmpty()) {
            System.out.println("***************************** You have not booked ticket with this id ******************************");
            return false;
        }
        customer.getBookedTickets().remove(ticket1.get());
        objectMapper.writeValue(file,registeredCustomers);

        ticketService.cancelTicket(ticket1.get());
        trainService.changeTrainSeatStatus(ticket1.get());
        System.out.println("***************** Your ticket is cancelled successfully! **********************");
        return true;


    }


}
