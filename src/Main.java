import main.java.repository.ClientRepository;
import main.java.repository.ReservationRepository;
import main.java.repository.RoomRepository;
import main.java.service.*;
import main.java.ui.ReservationMenu;


public class Main {
    public static void main(String[] args) {

        RoomRepository roomRepository = new RoomRepository();
        RoomService roomService = new RoomService(roomRepository);
        ClientRepository clientRepository = new ClientRepository();
        ClientService clientService = new ClientService(clientRepository);
        ReservationRepository reservationRepository = new ReservationRepository(roomService, clientRepository,roomRepository);
        ReservationService reservationService = new ReservationService(reservationRepository ,roomRepository , clientRepository);
        StatisticsService statisticsService = new StatisticsService(reservationRepository , roomRepository, roomService);

        ReservationMenu reservationMenu = new ReservationMenu(reservationService , clientService , roomService , statisticsService);
        reservationMenu.reservationMenu();

    }
}