package java12.services;

import java12.entities.Address;
import java12.entities.Agency;

import java.util.List;
import java.util.Map;

public interface AddressService {
    Address getAddressById(Long id);
    void updateAddressById(Long id, Address newAddress);
    Address getAddressWithAgencyById(Long addressId);
    List<Agency> getAgenciesByCity(String city);
    Map<String, List<Agency>> groupByRegion();
}
