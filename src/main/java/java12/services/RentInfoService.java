package java12.services;

import java12.entities.RentInfo;

import java.time.LocalDate;
import java.util.List;

public interface RentInfoService {
    List<RentInfo> getRentInfosByDate(LocalDate firstDate, LocalDate secondDate);
    Integer getCountOfRentInfosByAgencyId(Long agencyId);
}
