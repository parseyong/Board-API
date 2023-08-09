package com.example.board.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "board")
public class Member extends  BaseEntity{

    @Id
    String email;
    @Column
    String name;
    @Column
    String password;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Board> board;

}
