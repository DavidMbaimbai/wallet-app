package com.davidm.accounts.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Customer extends BaseEntity {

    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    private Long customerId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "other_name")
    private String otherName;
    private String gender;
    private String email;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Column(name = "account_balance")
    private BigDecimal accountBalance;
    @Column(name = "account_number")
    private Long accountNumber;
    @OneToMany
    private List<AccountTransactions> transactions = new ArrayList<>();
}
