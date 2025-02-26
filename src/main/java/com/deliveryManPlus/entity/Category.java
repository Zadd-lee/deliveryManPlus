package com.deliveryManPlus.entity;

import com.deliveryManPlus.constant.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Category extends CreateAndUpdateDateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String name;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Category(String name) {
        this.name = name;
        this.status = Status.USE;
    }

    public void updateName(String name) {
        this.name = name;
    }
    public void delete() {
        this.status = Status.DELETED;
    }
}
