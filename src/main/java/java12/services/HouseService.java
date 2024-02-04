package java12.services;

import java12.entities.House;

import java.time.LocalDate;
import java.util.List;

public interface HouseService {
    List<House> getHousesByRegion(String region);
    List<House> getHousesByAgency(Long agencyId);
    List<House> getHousesByOwner(Long ownerId);
    List<House> getHouseByDate(LocalDate firstDate, LocalDate secondDate);
}
