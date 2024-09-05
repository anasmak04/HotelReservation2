package main.java.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Client {
    private Long clientId;
    private String firstName;
    private String lastName;
    private String phone;


}
