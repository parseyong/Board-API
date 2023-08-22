package com.example.board.dto.image;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImageDTO {
    private String imagePath;
    private String savedimageName;
}
