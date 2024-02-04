package java12.services.impls;

import java12.dao.impls.RentInfoDaoImpl;
import java12.entities.RentInfo;
import java12.services.GeneralService;
import java12.services.RentInfoService;

import java.time.LocalDate;
import java.util.List;

public class RentInfoServiceImpl implements RentInfoService, GeneralService<RentInfo> {
    RentInfoDaoImpl rentInfoDao = new RentInfoDaoImpl();
    @Override
    public void save(RentInfo rentInfo) {
        rentInfoDao.save(rentInfo);
    }

    @Override
    public RentInfo getById(Long id) {
        return rentInfoDao.getById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public void updateById(Long id, RentInfo rentInfo) {
        rentInfoDao.updateById(id, rentInfo);
    }

    @Override
    public void deleteById(Long id) {
        rentInfoDao.deleteById(id);
    }

    @Override
    public List<RentInfo> getRentInfosByDate(LocalDate firstDate, LocalDate secondDate) {
        return rentInfoDao.getRentInfosByDate(firstDate, secondDate);
    }

    @Override
    public Integer getCountOfRentInfosByAgencyId(Long agencyId) {
        return rentInfoDao.getCountOfRentInfosByAgencyId(agencyId);
    }
}
