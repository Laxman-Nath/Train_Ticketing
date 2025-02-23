package org.onlineticketing.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.onlineticketing.entities.Ticket;
import org.onlineticketing.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketServiceImpl {
    private static final String PATH="D:\\Java FullStack\\Traein_Ticketsing\\src\\main\\java\\org\\onlineticketing\\repo\\ticket.json";

    List<Ticket> registeredTickets=new ArrayList<>();
    ObjectMapper objectMapper=new ObjectMapper();
    File file=new File(PATH);

    public TicketServiceImpl(){
        try{
            registeredTickets= objectMapper.readValue(file,objectMapper.getTypeFactory().constructCollectionType(List.class,Ticket.class));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean addTicket(Ticket ticket){
        try {
            registeredTickets.add(ticket);
            objectMapper.writeValue(file, registeredTickets);
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Ticket getTicketById(String id){
//        System.out.println(registeredTickets);
        Optional<Ticket> ticket=registeredTickets.stream().filter((t)->t.getId().equalsIgnoreCase(id)).findFirst();
        return ticket.orElse(null);

    }

    public List<Ticket> getRegisteredTickets(){
        return  registeredTickets;
    }

    public boolean cancelTicket(Ticket ticket) throws IOException {
        registeredTickets.remove(ticket);
        objectMapper.writeValue(file,registeredTickets);
        return true;
    }



}
