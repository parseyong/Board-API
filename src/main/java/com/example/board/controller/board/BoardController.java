package com.example.board.controller.board;

import com.example.board.dto.board.BoardInfoDTO;
import com.example.board.dto.board.ReadBoardDTO;
import com.example.board.dto.board.SaveBoardDTO;
import com.example.board.service.board.BoardService;
import com.example.board.service.image.ImageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
public class BoardController {

    private final BoardService boardService;
    private final ImageService imageService;
    @Autowired
    public BoardController(BoardService boardService, ImageService imageService){
        this.boardService=boardService;
        this.imageService=imageService;
    }
    /*
        더욱 rest한 api를 만들기 위해 이미지업로드와 게시글 등록로직을 분리하여 처리하였다.
        이렇게되면 @RequestPart를 사용하지 않아 saveBoardDTO의 데이터를 http body를 통해 전달할 수 있다.
        클라이언트에서 먼저 게시글등록 요청을 보내고 응답으로 게시글 pk값을 보낸다.
        받은 pk값과 파일을통해 이미지를 업로드한다.
     */
    @PostMapping("/boards")
    public ResponseEntity<Object> addBoardContent(@RequestBody @Valid SaveBoardDTO saveBoardDTO, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            // 에러 정보를 Map에 담아서 응답으로 반환
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        int boardNum = boardService.addBoard(saveBoardDTO);
        return ResponseEntity.created(null).body(boardNum);
    }
    @PostMapping("/boards/images")
    public ResponseEntity<Object> addBoardImage(@RequestParam("file") MultipartFile file,
                                                @RequestParam("boardNum") int boardNum) throws IOException {
        imageService.saveImage(file,boardNum);
        return ResponseEntity.created(null).body("게시글 등록 완료");
    }
    @GetMapping("/boards")
    public ResponseEntity<BoardInfoDTO> readBoard(@RequestBody ReadBoardDTO readBoardDTO){
        BoardInfoDTO boardInfoDTO = boardService.readBoard(readBoardDTO);
        return ResponseEntity.ok().body(boardInfoDTO);
    }
}
