package java12.dao;

import java12.entities.Address;
import java12.entities.Agency;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AddressDao {
    Optional<Address> getAddressById(Long id);
    void updateAddressById(Long id, Address newAddress);
    Optional<Address> getAddressWithAgencyById(Long addressId);
    List<Agency> getAgenciesByCity(String city);
    Map<String, List<Agency>> groupByRegion();
}
