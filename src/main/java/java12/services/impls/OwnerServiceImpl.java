package java12.services.impls;

import java12.dao.impls.OwnerDaoImpl;
import java12.entities.House;
import java12.entities.Owner;
import java12.services.GeneralService;
import java12.services.OwnerService;

import java.util.List;
import java.util.Map;

public class OwnerServiceImpl implements GeneralService<Owner>, OwnerService {
    OwnerDaoImpl ownerDao = new OwnerDaoImpl();

    @Override
    public void save(Owner owner) {
        ownerDao.save(owner);
    }

    @Override
    public Owner getById(Long id) {
        return ownerDao.getById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public void updateById(Long id, Owner owner) {
        ownerDao.updateById(id, owner);
    }

    @Override
    public void deleteById(Long id) {
        ownerDao.deleteById(id);
    }

    @Override
    public void assignOwnerToAgency(Long agencyId, Long ownerId) {
        ownerDao.assignOwnerToAgency(agencyId, ownerId);
    }

    @Override
    public void createOwnerWithHouse(Owner owner, House house) {
        ownerDao.createOwnerWithHouse(owner, house);
    }

    @Override
    public List<Owner> getOwnersByAgencyId(Long agencyId) {
        return ownerDao.getOwnersByAgencyId(agencyId);
    }

    @Override
    public Map<List<String>, List<Integer>> getOwnersNameAndAge() {
        return ownerDao.getOwnersNameAndAge();
    }
}
