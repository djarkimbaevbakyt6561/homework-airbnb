package java12.dao.impls;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java12.config.DataBaseConnection;
import java12.dao.GeneralDao;
import java12.entities.Customer;
import java12.entities.RentInfo;

import java.time.LocalDate;
import java.util.Optional;

public class CustomerDaoImpl implements GeneralDao<Customer>   {
    EntityManagerFactory entityManagerFactory = DataBaseConnection.getEntityManagerFactory();

    @Override
    public void save(Customer customer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(customer);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);

        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Customer> getById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Customer customer = null;
        try {
            entityManager.getTransaction().begin();
            customer = entityManager.createQuery("select a from Customer a where a.id = :parId", Customer.class)
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
        return Optional.ofNullable(customer);
    }

    @Override
    public void updateById(Long id, Customer customer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Customer existingCustomer = entityManager.find(Customer.class, id);
            if (existingCustomer != null) {
                existingCustomer.setDateOfBirth(customer.getDateOfBirth());
                existingCustomer.setEmail(customer.getEmail());
                existingCustomer.setGender(customer.getGender());
                existingCustomer.setDateOfBirth(customer.getDateOfBirth());
                existingCustomer.setNationality(customer.getNationality());
                existingCustomer.setFamilyStatus(customer.getFamilyStatus());
                existingCustomer.setLastName(customer.getLastName());
                existingCustomer.setFirstName(customer.getFirstName());
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

            Customer customer = entityManager.find(Customer.class, id);

            if (customer != null) {
                RentInfo rentInfo = entityManager
                        .createQuery("SELECT r FROM RentInfo r WHERE r.customer.id = :customerId", RentInfo.class)
                        .setParameter("customerId", id)
                        .getSingleResult();

                if (rentInfo == null) {
                    entityManager.remove(customer);
                } else {
                    boolean anyRentActive = rentInfo.getCheckOut().isAfter(LocalDate.now());
                    if (!anyRentActive) {
                        entityManager.remove(customer);
                        entityManager.remove(rentInfo);
                    } else {
                        throw new RuntimeException("Нельзя удалить клиента так как арендовал дом!");
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


    private void handleException(EntityManager entityManager, Exception e) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
        System.err.println(e.getMessage());
    }

}
