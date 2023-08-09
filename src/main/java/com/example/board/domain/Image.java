package com.example.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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


}
