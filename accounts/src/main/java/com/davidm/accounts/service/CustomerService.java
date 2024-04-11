package com.davidm.accounts.service;


import com.davidm.accounts.dto.CustomerDto;

public interface CustomerService {

    /**
     * Method for creating an account on EasyBank
     * @param customerDto - CustomerDto Object
     */
    void createAccount(final CustomerDto customerDto);


    /**
     * Method to fetch the customer by the customer mobile number
     * @param mobileNumber - Customer mobile number
     * @return CustomerDetailsDto
     */
    CustomerDto fetchCustomerDetails(String mobileNumber);

    /**
     * @param customerDto - CustomerDto Object
     * @return - returns whether it has deleted or not
     */
    boolean updateCustomerDetails(CustomerDto customerDto);

    /**
     * @param mobileNumber - Customer mobile number
     * @return - returns whether it has deleted or not
     */
    boolean deleteCustomerDetails(String mobileNumber);
}

