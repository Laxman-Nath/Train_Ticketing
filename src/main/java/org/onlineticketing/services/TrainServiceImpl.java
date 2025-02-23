package org.onlineticketing.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.onlineticketing.entities.Customer;
import org.onlineticketing.entities.Ticket;
import org.onlineticketing.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TrainServiceImpl {

    private static final String PATH="D:\\Java FullStack\\Traein_Ticketsing\\src\\main\\java\\org\\onlineticketing\\repo\\train.json";

    List<Train> registeredTrains=new ArrayList<>();
    ObjectMapper objectMapper=new ObjectMapper();
    File file=new File(PATH);

    public TrainServiceImpl(){
        try{
          registeredTrains= objectMapper.readValue(file,objectMapper.getTypeFactory().constructCollectionType(List.class,Train.class));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean addTrain(Train train){
        try {
            registeredTrains.add(train);
            System.out.println("Registered trains:"+registeredTrains);
            objectMapper.writeValue(file, registeredTrains);
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Train getTrainById(String id){
        Optional<Train> train=registeredTrains.stream().filter((t)->t.getId().equalsIgnoreCase(id)).findFirst();
        return train.orElse(null);

    }

    public List<Train> getAllTrains(){
//        registeredTrains.forEach((t) -> System.out.println(t.toString()));
        return  registeredTrains;
    }

    public boolean isSeatAvailable(Integer seatNo,Train train,Integer totalSeats){
        Train train1=getTrainById(train.getId());
        if(totalSeats>train.getSeats().size()){
            System.out.println("*************Invalid train id:*******************");
            return false;
        }
        if(isTrainFull(train)){
            System.out.println("******************* Train is packed already**********************");
            return false;
        }

        if(getAvailableSeatsOfTrain(train)<totalSeats){
            System.out.println("****************** This much seat is not available in this train ***************");
        }

        for(Integer seat:train1.getSeats().keySet()){
           if(Objects.equals(seat, seatNo) && train1.getSeats().get(seat).equals(true)) {
               System.out.println("**************** This seat is alreay booked.Try Booking another seat*******************");
             return false;
           }
        }
          return true;
    }

    public boolean bookSeat(Integer seatNo,Train train) throws IOException {
        train.getSeats().put(seatNo,true);
        objectMapper.writeValue(file, registeredTrains);

        return true;
    }

    public boolean checkTrain(String source,String destination,String trainId){
        Train train=getTrainById(trainId);
        System.out.println(registeredTrains);
        if(train==null){
            System.out.println("******************* Invalid train id*****************************");
            return  false;
        }
        if(new HashSet<>(train.getRoutes()).containsAll(List.of(source,destination))){
            if(train.getRoutes().indexOf(source)<train.getRoutes().indexOf(destination)){
                System.out.println("Train is perfect for your journey......");
                return true;
            }
            System.out.println("******************* Invalid train routes according to your journey*****************************");
        }
        return false;
    }


public boolean isTrainFull(Train train){

        for(Integer i:train.getSeats().keySet()){
            if(train.getSeats().get(i).equals(false)){
                return false;
            }
        }
        return true;
}

public int getAvailableSeatsOfTrain(Train train){
       int availableSeats=0;
    for(Integer i:train.getSeats().keySet()){
        if(train.getSeats().get(i).equals(false)){
            availableSeats++;
        }
    }
    return availableSeats;
}

public boolean changeTrainSeatStatus(Ticket ticket) throws IOException {
    List<Integer> seats = ticket.getSeats();
    Train train=ticket.getTrain();

    for(Integer i:train.getSeats().keySet()){
        if(seats.contains(i)){
            if(train.getSeats().get(i).equals(true)){
                train.getSeats().put(i,false);
            }
        }
    }
objectMapper.writeValue(file,registeredTrains);
    return true;
}

}
