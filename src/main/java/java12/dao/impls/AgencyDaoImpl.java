package java12.dao.impls;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java12.config.DataBaseConnection;
import java12.dao.GeneralDao;
import java12.entities.Agency;
import java12.entities.RentInfo;

import java.util.Optional;

public class AgencyDaoImpl implements GeneralDao<Agency> {
    EntityManagerFactory entityManagerFactory = DataBaseConnection.getEntityManagerFactory();

    @Override
    public void save(Agency agency) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(agency);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Agency> getById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Agency agency = null;
        try {
            entityManager.getTransaction().begin();
            agency = entityManager.createQuery("select a from Agency a where a.id = :parId", Agency.class)
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
        return Optional.ofNullable(agency);
    }

    @Override
    public void updateById(Long id, Agency agency) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Agency existingAgency = entityManager.find(Agency.class, id);
            if (existingAgency != null) {
                existingAgency.setAddress(agency.getAddress());
                existingAgency.setName(agency.getName());
                existingAgency.setPhoneNumber(agency.getPhoneNumber());
                existingAgency.setOwners(agency.getOwners());
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
            Agency agency = entityManager.find(Agency.class, id);
            if (agency != null) {
                RentInfo rentInfo = entityManager.createQuery("SELECT r from RentInfo r where r.agency.id = :parId", RentInfo.class).setParameter("parId", id).getSingleResult();
                entityManager.remove(rentInfo);
                entityManager.remove(agency);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }

    }
    private void handleException(EntityManager entityManager, Exception e) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
        System.err.println(e.getMessage());
    }

}
