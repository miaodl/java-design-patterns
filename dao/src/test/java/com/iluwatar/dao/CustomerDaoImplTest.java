package com.iluwatar.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CustomerDaoImplTest {

  private CustomerDaoImpl impl;
  private List<Customer> customers;
  private static final Customer CUSTOMER = new Customer(1, "Freddy", "Kruger");

  @Before
  public void setUp() {
    customers = new ArrayList<Customer>();
    customers.add(CUSTOMER);
    impl = new CustomerDaoImpl(customers);
  }

  @Test
  public void deleteExistingCustomer() {
    assertEquals(1, impl.getAllCustomers().size());
    impl.deleteCustomer(CUSTOMER);
    assertTrue(impl.getAllCustomers().isEmpty());
  }

  @Test
  public void deleteNonExistingCustomer() {
    final Customer nonExistingCustomer = new Customer(2, "Robert", "Englund");
    impl.deleteCustomer(nonExistingCustomer);
    assertEquals(1, impl.getAllCustomers().size());
  }

  @Test
  public void updateExistingCustomer() {
    final String newFirstname = "Bernard";
    final String newLastname = "Montgomery";
    final Customer customer = new Customer(CUSTOMER.getId(), newFirstname, newLastname);
    impl.updateCustomer(customer);
    final Customer cust = impl.getCustomerById(CUSTOMER.getId());
    assertEquals(newFirstname, cust.getFirstName());
    assertEquals(newLastname, cust.getLastName());
  }

  @Test
  public void updateNonExistingCustomer() {
    final int nonExistingId = getNonExistingCustomerId();
    final String newFirstname = "Douglas";
    final String newLastname = "MacArthur";
    final Customer customer = new Customer(nonExistingId, newFirstname, newLastname);
    impl.updateCustomer(customer);
    assertNull(impl.getCustomerById(nonExistingId));
    final Customer existingCustomer = impl.getCustomerById(CUSTOMER.getId());
    assertEquals(CUSTOMER.getFirstName(), existingCustomer.getFirstName());
    assertEquals(CUSTOMER.getLastName(), existingCustomer.getLastName());
  }

  @Test
  public void addCustomer() {
    final Customer newCustomer = new Customer(3, "George", "Patton");
    impl.addCustomer(newCustomer);
    assertEquals(2, impl.getAllCustomers().size());
  }

  @Test
  public void addAlreadyAddedCustomer() {
    final Customer newCustomer = new Customer(3, "George", "Patton");
    impl.addCustomer(newCustomer);
    assertEquals(2, impl.getAllCustomers().size());
    impl.addCustomer(newCustomer);
    assertEquals(2, impl.getAllCustomers().size());
  }

  @Test
  public void getExistinCustomerById() {
    assertEquals(CUSTOMER, impl.getCustomerById(CUSTOMER.getId()));
  }

  @Test
  public void getNonExistinCustomerById() {
    final int nonExistingId = getNonExistingCustomerId();
    assertNull(impl.getCustomerById(nonExistingId));
  }

  /**
   * An arbitrary number which does not correspond to an active Customer id.
   * 
   * @return an int of a customer id which doesn't exist
   */
  private int getNonExistingCustomerId() {
    return 999;
  }
}
