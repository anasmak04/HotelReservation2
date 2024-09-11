import main.java.config.DatabaseConnection;
import main.java.entities.Client;
import main.java.entities.DynamicPricing;
import main.java.entities.Room;
import main.java.repository.ClientRepository;
import main.java.repository.ReservationRepository;
import main.java.repository.RoomRepository;
import main.java.service.*;
import main.java.ui.ReservationMenu;
import main.java.validators.ReservationValidator;

import java.time.LocalDate;
import java.util.List;


public class Main {
    public static void main(String[] args) {

//        RoomRepository roomRepository = new RoomRepository();
//        RoomService roomService = new RoomService(roomRepository);
//        ClientRepository clientRepository = new ClientRepository();
//        ClientService clientService = new ClientService(clientRepository);
//        DynamicPricingService dynamicPricingService = new DynamicPricingService();
//        ReservationValidator reservationValidator = new ReservationValidator(roomService);
//        ReservationRepository reservationRepository = new ReservationRepository(roomService, clientRepository,roomRepository,dynamicPricingService, reservationValidator);
//        ReservationService reservationService = new ReservationService(reservationRepository ,roomRepository , clientRepository);
//        StatisticsService statisticsService = new StatisticsService(reservationRepository , roomRepository, roomService);
//        ReservationMenu reservationMenu = new ReservationMenu(reservationService , clientService , roomService , statisticsService);
//          reservationMenu.reservationMenu();




        DynamicPricing dynamicPricing = new DynamicPricing();
        DynamicPricingService calculator = new DynamicPricingService();

        LocalDate startDate = LocalDate.of(2024, 6, 1); // June 1, 2024
        LocalDate endDate = LocalDate.of(2024, 10, 1); // October 1, 2024

        double multiplier = calculator.getSeasonMultiplierForPeriod(startDate, endDate);


        System.out.println("Total Season Multiplier: " + multiplier);





    }



}