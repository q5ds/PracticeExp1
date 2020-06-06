package com.Dao.Entity;

import lombok.Data;
import javax.persistence.*;
import java.util.List;


@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    @Column(unique = true, nullable = false, length = 8)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Goods> goodsSet;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Order> orders;

}
