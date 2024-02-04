package java12.dao.impls;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java12.config.DataBaseConnection;
import java12.dao.AddressDao;
import java12.entities.Address;
import java12.entities.Agency;

import java.util.*;

public class AddressDaoImpl implements AddressDao {
    EntityManagerFactory entityManagerFactory = DataBaseConnection.getEntityManagerFactory();

    @Override
    public Optional<Address> getAddressById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Address address = null;
        try {
            entityManager.getTransaction().begin();
            address = entityManager.createQuery("select a from Address a where a.id = :parId", Address.class)
                    .setParameter("parId", id)
                    .getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return Optional.ofNullable(address);

    }

    @Override
    public void updateAddressById(Long id, Address newAddress) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Address existingAddress = entityManager.find(Address.class, id);
            if (existingAddress != null) {
                existingAddress.setCity(newAddress.getCity());
                existingAddress.setRegion(newAddress.getRegion());
                existingAddress.setStreet(newAddress.getStreet());
                existingAddress.setAgency(newAddress.getAgency());
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Address> getAddressWithAgencyById(Long addressId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Address address = null;
        try {
            entityManager.getTransaction().begin();
            String jpql = "SELECT a FROM Address a WHERE a.agency.id = :addressId";
            address = entityManager.createQuery(jpql, Address.class)
                    .setParameter("addressId", addressId)
                    .getSingleResult();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
        return Optional.ofNullable(address);
    }

    @Override
    public List<Agency> getAgenciesByCity(String city) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Agency> agencies = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();

            String jpql = "SELECT a FROM Agency a WHERE a.address.city = :city";
            agencies = entityManager.createQuery(jpql, Agency.class)
                    .setParameter("city", city)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
        return agencies;
    }

    @Override
    public Map<String, List<Agency>> groupByRegion() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Map<String, List<Agency>> result = new HashMap<>();
        try {
            entityManager.getTransaction().begin();

            String jpql = "SELECT a FROM Agency a ORDER BY a.address.region";
            List<Agency> agencies = entityManager.createQuery(jpql, Agency.class)
                    .getResultList();

            for (Agency agency : agencies) {
                String region = agency.getAddress().getRegion();
                result.computeIfAbsent(region, k -> new ArrayList<>()).add(agency);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }

        return result;
    }
    private void handleException(EntityManager entityManager, Exception e) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
        System.err.println(e.getMessage());
    }


}
