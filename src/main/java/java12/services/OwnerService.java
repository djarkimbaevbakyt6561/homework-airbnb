package java12.services;

import java12.entities.House;
import java12.entities.Owner;

import java.util.List;
import java.util.Map;

public interface OwnerService {
    void assignOwnerToAgency(Long agencyId, Long ownerId);
    void createOwnerWithHouse(Owner owner, House house);
    List<Owner> getOwnersByAgencyId(Long agencyId);
    Map<List<String>, List<Integer>> getOwnersNameAndAge();
}
