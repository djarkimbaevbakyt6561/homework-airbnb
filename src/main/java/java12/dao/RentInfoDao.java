package java12.dao;

import java12.entities.RentInfo;

import java.time.LocalDate;
import java.util.List;

public interface RentInfoDao {
    List<RentInfo> getRentInfosByDate(LocalDate firstDate, LocalDate secondDate);
    Integer getCountOfRentInfosByAgencyId(Long agencyId);
}
