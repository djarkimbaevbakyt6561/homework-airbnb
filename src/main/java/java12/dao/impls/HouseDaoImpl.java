package java12.dao.impls;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java12.config.DataBaseConnection;
import java12.dao.GeneralDao;
import java12.dao.HouseDao;
import java12.entities.House;
import java12.entities.RentInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HouseDaoImpl implements GeneralDao<House>, HouseDao {
    EntityManagerFactory entityManagerFactory = DataBaseConnection.getEntityManagerFactory();

    @Override
    public void save(House house) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(house);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<House> getById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        House house = null;
        try {
            entityManager.getTransaction().begin();
            house = entityManager.createQuery("select a from House a where a.id = :parId", House.class)
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
        return Optional.ofNullable(house);
    }

    @Override
    public void updateById(Long id, House house) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            House existingHouse = entityManager.find(House.class, id);
            if (existingHouse != null) {
                existingHouse.setHouseType(house.getHouseType());
                existingHouse.setAddress(house.getAddress());
                existingHouse.setPrice(house.getPrice());
                existingHouse.setOwner(house.getOwner());
                existingHouse.setRoom(house.getRoom());
                existingHouse.setFurniture(house.isFurniture());
                existingHouse.setDescription(house.getDescription());
                existingHouse.setRating(house.getRating());
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
            House house = entityManager.find(House.class, id);
            if (house != null) {
                RentInfo rentInfo = entityManager
                        .createQuery("SELECT r FROM RentInfo r WHERE r.house.id = :houseId", RentInfo.class)
                        .setParameter("houseId", id)
                        .getSingleResult();

                if (rentInfo == null) {
                    entityManager.remove(house);
                } else {
                    boolean anyRentActive = rentInfo.getCheckOut().isAfter(LocalDate.now());
                    if (!anyRentActive) {
                        entityManager.remove(house);
                        entityManager.remove(rentInfo);
                    } else {
                        throw new RuntimeException("Нельзя удалить дом так как она арендована!");
                    }
                }
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<House> getHousesByRegion(String region) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();

        try {
            entityManager.getTransaction().begin();

            String jpql = "SELECT h FROM House h WHERE h.address.region = :region";
            houses = entityManager.createQuery(jpql, House.class)
                    .setParameter("region", region)
                    .getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }

        return houses;
    }

    @Override
    public List<House> getHousesByAgency(Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();

        try {
            entityManager.getTransaction().begin();

            String jpql = "SELECT r.house FROM RentInfo r WHERE r.agency.id = :agencyId";
            houses = entityManager.createQuery(jpql, House.class)
                    .setParameter("agencyId", agencyId)
                    .getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }

        return houses;
    }


    @Override
    public List<House> getHousesByOwner(Long ownerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();

        try {
            entityManager.getTransaction().begin();

            String jpql = "SELECT h FROM House h WHERE h.owner.id = :ownerId";
            houses = entityManager.createQuery(jpql, House.class)
                    .setParameter("ownerId", ownerId)
                    .getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }

        return houses;
    }

    @Override
    public List<House> getHouseByDate(LocalDate firstDate, LocalDate secondDate) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();

        try {
            entityManager.getTransaction().begin();

            String jpql = "SELECT r.house FROM RentInfo r WHERE r.checkIn BETWEEN :firstDate AND :secondDate";
            houses = entityManager.createQuery(jpql, House.class)
                    .setParameter("firstDate", firstDate)
                    .setParameter("secondDate", secondDate)
                    .getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }

        return houses;
    }

    private void handleException(EntityManager entityManager, Exception e) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
        System.err.println(e.getMessage());
    }

}
