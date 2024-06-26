package com.davidm.accounts.entity;
import com.davidm.accounts.constants.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class AccountTransactions extends BaseEntity {
    @Column(name = "customer_id")
    private Long customerId;
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    private Long transactionId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    @Column(name = "account_number")
    private Long accountNumber;
    @ManyToOne
    private Customer customer;
}
