package com.example.board.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@ToString(exclude = "board")
public class Member extends  BaseEntity{

    @Id
    String email;
    @Column
    String name;
    @Column
    String password;
    @Column
    String roleName;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Board> board;

    @Builder
    public Member(String email,String name,String password,String roleName,List<Board> board){
        this.email=email;
        this.name=name;
        this.password=password;
        this.roleName=roleName;
        this.board=board;
    }

    public void changeName(String name){
        this.name=name;
    }
    public void changePassword(String password){
        this.password=password;
    }

}
