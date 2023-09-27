package com.example.board.domain;

import lombok.*;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@ToString(exclude = "member")
public class Board extends  BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int boardNum;
    @Column
    String title;
    @Column
    String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_email")
    private Member member;

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Reply> reply;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Image> image;

    @Builder
    public Board(int boardNum,String title,String content,Member member,List<Reply> reply,List<Image> image){
        this.boardNum=boardNum;
        this.title=title;
        this.content=content;
        this.member=member;
        this.reply=reply;
        this.image=image;
    }
    public void changeTitle(String title){
        this.title=title;
    }
    public void changeContent(String content){
        this.content=content;
    }

}
