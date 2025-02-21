package org.onlineticketing.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.onlineticketing.entities.Customer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl {

    private static final String PATH="D:\\Java FullStack\\Traein_Ticketsing\\src\\main\\java\\org\\onlineticketing\\repo\\localrepo.json";

    List<Customer> registeredCustomers=new ArrayList<>();
    ObjectMapper objectMapper=new ObjectMapper();
    File file=new File(PATH);

    public  CustomerServiceImpl(){
        try {

            registeredCustomers = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Customer.class));
            System.out.println("Registered customers:"+registeredCustomers.toString());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean register(Customer customer){

       Optional <Customer> customer1=registeredCustomers.stream().filter((c)->c.getId().equalsIgnoreCase(customer.getId())).findFirst();
       if(customer1.isPresent()){
           System.out.println("****************** Registration Error:user with this id already exists:**************************");
           return false;
       }
        try{
            customer.setLoggedIn(false);
            customer.setHashedPassword(BCrypt.withDefaults().hashToString(12,customer.getPassword().toCharArray()));
         registeredCustomers.add(customer);
         objectMapper.writeValue(file,registeredCustomers);
           return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean login(String userName,String password){
        if(!isUserExistsByUserName(userName)){
            System.out.println("************************** Error Logging in:Username is not valid **************************");
            return false;
        }
        else if(!isUserExistsByUserNameAndPassword(userName,password)){
            System.out.println("************************** Error Logging in:Invalid password **************************");
            return false;
        }
      Optional<Customer> customer=  registeredCustomers.stream().filter((c)->c.getName().equalsIgnoreCase(userName)).findFirst();
        customer.ifPresent((c)->{
            c.setLoggedIn(true);
            try{
                objectMapper.writeValue(file,registeredCustomers);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        });

        System.out.println("************************** Login success:**************************");
        return true;
    }

    public boolean isUserExistsByUserName(String userName){
        Optional <Customer> customer1=registeredCustomers.stream().filter((c)->c.getName().equalsIgnoreCase(userName)).findFirst();
        if(customer1.isPresent()){
            return true;
        }
        return  false;
    }

    public boolean isUserExistsByUserNameAndPassword(String userName,String password){
        Optional <Customer> customer1=registeredCustomers.stream().filter((c)->c.getName().equalsIgnoreCase(userName)).findFirst();
        if(customer1.isPresent()){
            Customer c2=customer1.get();
            if(BCrypt.verifyer().verify(password.toCharArray(),c2.getHashedPassword()).verified){
                return true;
            }
            return false;
        }
        return  false;
    }



}
