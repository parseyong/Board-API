package com.example.board.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ToString(exclude = "board")
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

    @Builder
    public Reply(String content,String replyler,Board board){
        this.content=content;
        this.replyler=replyler;
        this.board=board;
    }
    public void changeContent(String content){
        this.content=content;
    }
}
