
import main.java.repository.ClientRepository;
import main.java.repository.ReservationRepository;
import main.java.repository.RoomRepository;
import main.java.service.*;
import main.java.ui.DynamicPricingMenu;
import main.java.ui.ReservationMenu;
import main.java.validators.ReservationValidator;



public class Main {
    public static void main(String[] args) {

        RoomRepository roomRepository = new RoomRepository();
        RoomService roomService = new RoomService(roomRepository);
        ClientRepository clientRepository = new ClientRepository();
        ClientService clientService = new ClientService(clientRepository);
        DynamicPricingService dynamicPricingService = new DynamicPricingService();
        ReservationValidator reservationValidator = new ReservationValidator(roomService);
        ReservationRepository reservationRepository = new ReservationRepository(roomService, clientRepository, roomRepository, dynamicPricingService, reservationValidator);
        ReservationService reservationService = new ReservationService(reservationRepository, roomRepository, clientRepository);
        StatisticsService statisticsService = new StatisticsService(reservationRepository, roomRepository, roomService);
        DynamicPricingMenu dynamicPricingMenu = new DynamicPricingMenu(dynamicPricingService,reservationService, clientService,roomService,statisticsService);
        ReservationMenu reservationMenu = new ReservationMenu(reservationService, clientService, roomService, statisticsService, dynamicPricingMenu);
        reservationMenu.reservationMenu();

    }


}