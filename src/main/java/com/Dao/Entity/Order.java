package com.Dao.Entity;

import lombok.Data;
import org.hibernate.annotations.Check;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "ding_dan")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer oid;

    @Column(nullable = false)
    @Check(constraints = "amount > 0")
    private Integer amount;

    @Column(nullable = false)
    @Check(constraints = "totalPrice > 0")
    private Float totalPrice;

    @Column(nullable = false)
    @Enumerated
    private OrderStatus orderStatus;

    @Column(nullable = false)
    @CreatedDate
    private Date createTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Goods goods;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Users buyer;


}
