package com.example.board.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "reply")
public class Reply extends  BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int replyNum;
    @Column
    String content;
    @Column
    String replyler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_boardNum")
    private Board board;
}
