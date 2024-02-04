package java12.services.impls;

import java12.dao.impls.HouseDaoImpl;
import java12.entities.House;
import java12.services.GeneralService;
import java12.services.HouseService;

import java.time.LocalDate;
import java.util.List;

public class HouseServiceImpl implements GeneralService<House>, HouseService {
    HouseDaoImpl houseDao = new HouseDaoImpl();

    @Override
    public void save(House house) {
        houseDao.save(house);
    }

    @Override
    public House getById(Long id) {
        return houseDao.getById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public void updateById(Long id, House house) {
        houseDao.updateById(id, house);
    }

    @Override
    public void deleteById(Long id) {
        houseDao.deleteById(id);
    }

    @Override
    public List<House> getHousesByRegion(String region) {
        return houseDao.getHousesByRegion(region);
    }

    @Override
    public List<House> getHousesByAgency(Long agencyId) {
        return houseDao.getHousesByAgency(agencyId);
    }

    @Override
    public List<House> getHousesByOwner(Long ownerId) {
        return houseDao.getHousesByOwner(ownerId);
    }

    @Override
    public List<House> getHouseByDate(LocalDate firstDate, LocalDate secondDate) {
        return houseDao.getHouseByDate(firstDate, secondDate);
    }
}
