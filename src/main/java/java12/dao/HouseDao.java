package java12.dao;

import java12.entities.House;

import java.time.LocalDate;
import java.util.List;

public interface HouseDao {
    List<House> getHousesByRegion(String region);
    List<House> getHousesByAgency(Long agencyId);
    List<House> getHousesByOwner(Long ownerId);
    List<House> getHouseByDate(LocalDate firstDate, LocalDate secondDate);
}
