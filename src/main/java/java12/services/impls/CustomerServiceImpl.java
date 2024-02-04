package java12.services.impls;

import java12.dao.impls.CustomerDaoImpl;
import java12.entities.Customer;
import java12.services.GeneralService;

public class CustomerServiceImpl implements GeneralService<Customer> {
    CustomerDaoImpl customerDao = new CustomerDaoImpl();

    @Override
    public void save(Customer customer) {
        customerDao.save(customer);
    }

    @Override
    public Customer getById(Long id) {
        return customerDao.getById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public void updateById(Long id, Customer customer) {
        customerDao.updateById(id, customer);
    }

    @Override
    public void deleteById(Long id) {
        customerDao.deleteById(id);
    }
}
