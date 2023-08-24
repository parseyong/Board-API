package com.example.board.service.image;

import com.example.board.Repository.BoardRepository;
import com.example.board.Repository.ImageRepository;
import com.example.board.domain.Board;
import com.example.board.domain.Image;
import com.example.board.dto.image.ImageDTO;
import com.example.board.exception.InvalidSaveFileException;
import com.example.board.exception.NotExistBoardException;
import com.example.board.exception.NotExistFileException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class ImageService {

    private final ImageRepository imageRepository;
    private final BoardRepository boardRepository;
    //properties에 저장된 파일 저장경로를 지정
    @Value("${file.dir}")
    private String fileDir;

    @Autowired
    public ImageService(ImageRepository imageRepository, BoardRepository boardRepository) {
        this.imageRepository=imageRepository;
        this.boardRepository=boardRepository;
    }
    public void saveImage(MultipartFile file, int boardNum) throws IOException {

        Optional<Board> result = boardRepository.findById(boardNum);

        Board board = result.get();
        // 원래 파일 이름 추출
        String originName = file.getOriginalFilename();
        if(originName.length() <= 0 || originName ==null)
            throw new InvalidSaveFileException("파일을 저장할 수 없습니다.");

        log.info(originName);
        // 파일 이름으로 쓸 uuid 생성, 동일한 파일명이 들어왔을 때 파일명의 중복을 피하기위해 UUID를 사용.
        String uuid = UUID.randomUUID().toString();

        // 확장자 추출(ex : .png)
        String extension = originName.substring(originName.lastIndexOf("."));

        // uuid와 확장자 결합
        String savedName = uuid + extension;

        // 파일을 불러올 때 사용할 파일 경로
        String savedPath = fileDir + savedName;

        // 파일 엔티티 생성
        Image fileInDatabase = Image.builder()
                .savedName(savedName).originalName(originName).savedPath(savedPath).board(board).build();

        // 실제로 로컬에 uuid를 파일명으로 저장. (MultipartFile타입의 객체).transferTo(new File(파일의 경로))
        file.transferTo(new File(savedPath));

        // 데이터베이스에 파일 정보 저장 , 기존파일명,저장경로,uuid명 을 필드로 가지는 엔티티를 db에 저장
        Image savedFile = imageRepository.save(fileInDatabase);
    }
    public List<ImageDTO> readImage(Board board){
        List<Image> imageList = board.getImage();
        List<ImageDTO> imageDTOList = new ArrayList<>();

        for(int i=0;i<imageList.size();i++){
            Image image = imageList.get(i);
            ImageDTO fileDTO = ImageDTO.builder()
                    .savedimageName(image.getSavedName())
                    .imagePath(image.getSavedPath())
                    .build();
            imageDTOList.add(fileDTO);
        }
        return imageDTOList;
    }
    public void deleteImage(String savedImageName){
        if(!imageRepository.existsById(savedImageName))
            throw new NotExistFileException("파일이 존재하지 않습니다.");
        imageRepository.deleteById(savedImageName);
    }
}
