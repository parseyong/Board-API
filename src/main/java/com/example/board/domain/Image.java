package com.example.board.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Image {

    @Id
    @Column
    private String savedName;
    @Column
    private String originalName;
    @Column
    private String savedPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_boardNum")
    private Board board;

    @Builder
    public Image(String savedName,String originalName,String savedPath,Board board){
        this.savedName=savedName;
        this.originalName=originalName;
        this.savedPath=savedPath;
        this.board=board;
    }
}
