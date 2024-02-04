package java12;

import java12.entities.*;
import java12.enums.FamilyStatus;
import java12.enums.Gender;
import java12.enums.HouseType;
import java12.services.impls.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        AddressServiceImpl addressService = new AddressServiceImpl();
        AgencyServiceImpl agencyService = new AgencyServiceImpl();
        CustomerServiceImpl customerService = new CustomerServiceImpl();
        HouseServiceImpl houseService = new HouseServiceImpl();
        RentInfoServiceImpl rentInfoService = new RentInfoServiceImpl();
        OwnerServiceImpl ownerService = new OwnerServiceImpl();

//        Owner owner = new Owner("Sergey", "Antropov", "king@gmail.com", LocalDate.of(2005, 12, 30), Gender.MALE);
//        ownerService.save(owner);
//        House house = new House(HouseType.HOUSE, BigDecimal.valueOf(3000), 4.5, "Very good House",3, true);
//        house.setOwner(ownerService.getById(5L));
//
//        Address address = new Address("Bishkek", "Chui", "Grazhdanskaya");
//        house.setAddress(address);
//
//        houseService.save(house);
//        Agency agency = new Agency("bahadyr");
//        agency.setPhoneNumber("+996709234354");
//        agency.setAddress(addressService.getAddressById(3L));
//        agencyService.save(agency);
//        Customer customer = new Customer("Sergey", "Antropov", "king@gmail.com", LocalDate.of(2005, 12, 30), Gender.MALE,"Kyrgyz", FamilyStatus.MARRIED);
//        customerService.save(customer);
//        RentInfo rentInfo = new RentInfo(LocalDate.of(2024, 2, 2), LocalDate.of(2024, 2, 4));
//        rentInfo.setHouse(houseService.getById(16L));
//        rentInfo.setAgency(agencyService.getById(4L));
//        rentInfo.setCustomer(customerService.getById(1L));
//        rentInfoService.save(rentInfo);
//          agencyService.deleteById(1L);
//        addressService.updateAddressById(3L, new Address("Hello", "France", "What"));
//        System.out.println(addressService.getAddressWithAgencyById(4L));
//        System.out.println(addressService.getAgenciesByCity("Hello"));
//        System.out.println(ownerService.getOwnersNameAndAge());
//        ownerService.assignOwnerToAgency(4L, 5L);
//        System.out.println(ownerService.getOwnersByAgencyId(4L));
//        System.out.println(houseService.getHouseByDate(LocalDate.of(2024, 2, 2), LocalDate.of(2024, 2, 4)));
//        System.out.println(rentInfoService.getRentInfosByDate(LocalDate.of(2024, 2, 2), LocalDate.of(2024, 2, 4)));
//          rentInfoService.getCountOfRentInfosByAgencyId(4L);
    }

}
