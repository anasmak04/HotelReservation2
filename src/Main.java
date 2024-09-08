import main.java.connection.DatabaseConnection;
import main.java.repository.ClientRepository;
import main.java.repository.ReservationRepository;
import main.java.repository.RoomRepository;
import main.java.service.ClientService;
import main.java.service.ReservationService;
import main.java.service.RoomService;
import main.java.ui.ClientMenu;
import main.java.ui.ReservationMenu;
import main.java.ui.RoomMenu;

public class Main {
    public static void main(String[] args) {
        ClientRepository clientRepository = new ClientRepository();
        ClientService clientService = new ClientService(clientRepository);
        ClientMenu clientMenu = new ClientMenu(clientService);
//        clientMenu.clientMenu();
        RoomRepository roomRepository = new RoomRepository();
        RoomService roomService = new RoomService(roomRepository);
        RoomMenu roomMenu = new RoomMenu(roomService);
//        roomMenu.roomMenu();
        ReservationRepository reservationRepository = new ReservationRepository(roomService);
        ReservationService reservationService = new ReservationService(reservationRepository,roomRepository,clientRepository);
        ReservationMenu reservationMenu = new ReservationMenu(reservationService);
        reservationMenu.reservationMenu();

    }
}