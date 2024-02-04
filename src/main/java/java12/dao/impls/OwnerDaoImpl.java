package java12.dao.impls;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java12.config.DataBaseConnection;
import java12.dao.GeneralDao;
import java12.dao.OwnerDao;
import java12.entities.Agency;
import java12.entities.House;
import java12.entities.Owner;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class OwnerDaoImpl implements OwnerDao, GeneralDao<Owner> {
    EntityManagerFactory entityManagerFactory = DataBaseConnection.getEntityManagerFactory();

    @Override
    public void save(Owner owner) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(owner);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Owner> getById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Owner owner = null;
        try {
            entityManager.getTransaction().begin();
            owner = entityManager.createQuery("select a from Owner a where a.id = :parId", Owner.class)
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
        return Optional.ofNullable(owner);
    }

    @Override
    public void updateById(Long id, Owner owner) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Owner existingOwner = entityManager.find(Owner.class, id);
            if (existingOwner != null) {
                existingOwner.setAgencies(owner.getAgencies());
                existingOwner.setHouses(owner.getHouses());
                existingOwner.setGender(owner.getGender());
                existingOwner.setEmail(owner.getEmail());
                existingOwner.setFirstName(owner.getFirstName());
                existingOwner.setLastName(owner.getLastName());
                existingOwner.setRentInfos(owner.getRentInfos());
                existingOwner.setDateOfBirth(owner.getDateOfBirth());
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Owner owner = entityManager.find(Owner.class, id);
            if (owner != null) {
                entityManager.remove(owner);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void assignOwnerToAgency(Long agencyId, Long ownerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Agency agency = entityManager.find(Agency.class, agencyId);
            Owner owner = entityManager.find(Owner.class, ownerId);

            if (agency == null || owner == null) {
                throw new RuntimeException("Агентство или Владелец не найдены по ID");
            }

            if (agency.getOwners().contains(owner)) {
                throw new RuntimeException("Владелец уже находится в агентсве!");
            }

            agency.getOwners().add(owner);
            owner.getAgencies().add(agency);

            entityManager.merge(agency);
            entityManager.merge(owner);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
    }


    @Override
    public void createOwnerWithHouse(Owner owner, House house) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            owner.getHouses().add(house);
            house.setOwner(owner);

            entityManager.persist(owner);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Owner> getOwnersByAgencyId(Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Owner> owners = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            owners = entityManager
                    .createQuery("SELECT o FROM Owner o JOIN o.agencies a WHERE a.id = :agencyId", Owner.class)
                    .setParameter("agencyId", agencyId)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
        return owners;
    }


    @Override
    public Map<List<String>, List<Integer>> getOwnersNameAndAge() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Map<List<String>, List<Integer>> owners = new HashMap<>();
        try {
            entityManager.getTransaction().begin();
            List<Object[]> result = entityManager
                    .createQuery("SELECT o.firstName, o.dateOfBirth FROM Owner o",Object[].class)
                    .getResultList();
            List<String> firstNames = new ArrayList<>();
            List<Integer> ages = new ArrayList<>();
            for (Object[] row : result) {
                String firstName = (String) row[0];
                LocalDate dateOfBirth = (LocalDate) row[1];

                int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
                firstNames.add(firstName);
                ages.add(age);
            }
            owners.put(firstNames, ages);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }

        return owners;
    }


    private void handleException(EntityManager entityManager, Exception e) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
        System.err.println(e.getMessage());
    }
}
