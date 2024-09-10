package test.repository;

import main.java.entities.Client;
import main.java.repository.ClientRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.Assert.assertEquals;

public class ClientRepositoryTest {
    private ClientRepository clientRepository;
    private Client client;
    private Connection connection;
    private PreparedStatement preparedStatement;


    @Before
    public void setUp() throws Exception {
        client = new Client(1L,"John", "Smith", "212 6 37 93 33 66");
    }


    @Test
    public void findById() {

    }


    @Test
    public void save() {
      clientRepository.save(client);

    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }



    @Test
    public void findAll() {
    }
}