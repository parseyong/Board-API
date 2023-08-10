package com.example.board.dto.file;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileInfoDTO {

    private String originName;

    private String savedPath;
}
