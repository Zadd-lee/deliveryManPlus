package com.deliveryManPlus.menu.entity;

import com.deliveryManPlus.common.entity.CreateAndUpdateDateEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class MenuOption extends CreateAndUpdateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private Integer selectionLimit;
    private Boolean requirement;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @OneToMany(mappedBy = "menuOption")
    private List<MenuOptionDetail> menuOptionDetailList;

    @Builder
    public MenuOption(String title, String content, Integer selectionLimit, Boolean requirement, Menu menu, List<MenuOptionDetail> menuOptionDetailList) {
        this.title = title;
        this.content = content;
        this.selectionLimit = selectionLimit;
        this.requirement = requirement;
        this.menu = menu;
        this.menuOptionDetailList = menuOptionDetailList;
    }



    public void updateMenuOptionDetailList(List<MenuOptionDetail> menuOptionDetailList) {
        this.menuOptionDetailList = menuOptionDetailList;
    }

    public void updateMenu(Menu menu) {
        this.menu = menu;
    }
}
