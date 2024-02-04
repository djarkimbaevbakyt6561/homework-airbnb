package java12.dao.impls;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java12.config.DataBaseConnection;
import java12.dao.GeneralDao;
import java12.dao.RentInfoDao;
import java12.entities.RentInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RentInfoDaoImpl implements GeneralDao<RentInfo>, RentInfoDao {
    EntityManagerFactory entityManagerFactory = DataBaseConnection.getEntityManagerFactory();

    @Override
    public void save(RentInfo rentInfo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(rentInfo);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<RentInfo> getById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        RentInfo rentInfo = null;
        try {
            entityManager.getTransaction().begin();
            rentInfo = entityManager.createQuery("select a from RentInfo a where a.id = :parId", RentInfo.class)
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
        return Optional.ofNullable(rentInfo);
    }

    @Override
    public void updateById(Long id, RentInfo rentInfo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            RentInfo existingRentInfo = entityManager.find(RentInfo.class, id);
            if (existingRentInfo != null) {
                existingRentInfo.setAgency(rentInfo.getAgency());
                existingRentInfo.setOwner(rentInfo.getOwner());
                existingRentInfo.setHouse(rentInfo.getHouse());
                existingRentInfo.setCheckIn(rentInfo.getCheckIn());
                existingRentInfo.setCheckOut(rentInfo.getCheckOut());
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
            RentInfo rentInfo = entityManager.find(RentInfo.class, id);
            if (rentInfo != null) {
                entityManager.remove(rentInfo);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
    }


    @Override
    public List<RentInfo> getRentInfosByDate(LocalDate firstDate, LocalDate secondDate) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<RentInfo> rentInfos = new ArrayList<>();

        try {
            entityManager.getTransaction().begin();

            String jpql = "SELECT r FROM RentInfo r WHERE r.checkOut BETWEEN :firstDate AND :secondDate";
            rentInfos = entityManager.createQuery(jpql, RentInfo.class)
                    .setParameter("firstDate", firstDate)
                    .setParameter("secondDate", secondDate)
                    .getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }

        return rentInfos;
    }


    @Override
    public Integer getCountOfRentInfosByAgencyId(Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Integer count = null;

        try {
            entityManager.getTransaction().begin();
            count = entityManager
                    .createQuery("SELECT COUNT(r) FROM RentInfo r WHERE r.agency.id = :agencyId", Long.class)
                    .setParameter("agencyId", agencyId)
                    .getSingleResult()
                    .intValue();
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }

        return count;
    }

    private void handleException(EntityManager entityManager, Exception e) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
        System.err.println(e.getMessage());
    }
}
