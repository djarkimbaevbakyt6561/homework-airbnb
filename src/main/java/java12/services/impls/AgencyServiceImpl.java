package java12.services.impls;

import java12.dao.impls.AgencyDaoImpl;
import java12.entities.Agency;
import java12.services.GeneralService;

public class AgencyServiceImpl implements GeneralService<Agency> {
    AgencyDaoImpl agencyDao = new AgencyDaoImpl();

    @Override
    public void save(Agency agency) {
        agencyDao.save(agency);
    }

    @Override
    public Agency getById(Long id) {
        return agencyDao.getById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public void updateById(Long id, Agency agency) {
        agencyDao.updateById(id, agency);
    }

    @Override
    public void deleteById(Long id) {
        agencyDao.deleteById(id);
    }
}
