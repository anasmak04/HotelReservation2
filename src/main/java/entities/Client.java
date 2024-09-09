package main.java.entities;


import java.util.ArrayList;
import java.util.List;

public class Client {

    private Long clientId;
    private String firstName;
    private String lastName;
    private String phone;
    private List<Reservation> reservations = new ArrayList<>();

    public Client() {
    }

    public Client(Long clientId) {
        this(clientId, null);
    }

    public Client(Long clientId, String firstName) {
        this(clientId, firstName, null);
    }

    public Client(Long clientId, String firstName, String lastName) {
        this(clientId, firstName, lastName, null);
    }

    public Client(Long clientId, String firstName, String lastName, String phone) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }
}
