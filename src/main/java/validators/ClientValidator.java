package main.java.validators;

import main.java.entities.Client;
import main.java.exception.InvalidInputException;

public class ClientValidator {

    public static void validatorClient(Client client){
        if(client.getFirstName().isEmpty() || client.getLastName() == null){
            throw new InvalidInputException("Client first name cannot be empty or null");
        }

        if(client.getLastName().isEmpty() || client.getFirstName() == null){
            throw new InvalidInputException("Client last name cannot be empty or null");
        }

        if(client.getPhone().isEmpty() || client.getPhone() == null){
            throw new InvalidInputException("Client phone cannot be empty or null");
        }
    }
}
