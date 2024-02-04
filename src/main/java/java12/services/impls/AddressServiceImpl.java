package java12.services.impls;

import java12.dao.impls.AddressDaoImpl;
import java12.entities.Address;
import java12.entities.Agency;
import java12.services.AddressService;

import java.util.List;
import java.util.Map;

public class AddressServiceImpl implements AddressService {
    AddressDaoImpl addressDao = new AddressDaoImpl();

    @Override
    public Address getAddressById(Long id) {
        return addressDao.getAddressById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public void updateAddressById(Long id, Address newAddress) {
        addressDao.updateAddressById(id, newAddress);
    }

    @Override
    public Address getAddressWithAgencyById(Long addressId) {
        return addressDao.getAddressWithAgencyById(addressId).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<Agency> getAgenciesByCity(String city) {
        return addressDao.getAgenciesByCity(city);
    }

    @Override
    public Map<String, List<Agency>> groupByRegion() {
        return addressDao.groupByRegion();
    }
}
