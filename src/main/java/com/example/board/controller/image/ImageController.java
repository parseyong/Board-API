package com.example.board.controller.image;

import com.example.board.service.image.ImageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Log4j2
public class ImageController {

    private final ImageService imageService;
    @Autowired
    public ImageController(ImageService imageService){
        this.imageService=imageService;
    }

    @PostMapping("/boards/images")
    public ResponseEntity<Object> addBoardImage(@RequestParam("file") MultipartFile file,
                                                @RequestParam("boardNum") int boardNum) throws IOException {
        //이 요청은 저장할 파일이 있을때만 요청되야한다.
        if(file.isEmpty())
            ResponseEntity.badRequest().body("저장할 파일이 없습니다.");
        imageService.saveImage(file,boardNum);
        return ResponseEntity.created(null).body("이미지 등록완료");
    }
    @DeleteMapping("/boards/images/{savedImagePath}")
    public ResponseEntity<Object> deleteBoardImage(@PathVariable String savedImagePath){
        imageService.deleteImage(savedImagePath);
        return ResponseEntity.ok().body("이미지 삭제완료");
    }
}
