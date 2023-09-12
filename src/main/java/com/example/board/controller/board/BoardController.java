package com.example.board.controller.board;

import com.example.board.dto.board.*;
import com.example.board.exception.NotExistFileException;
import com.example.board.service.board.BoardService;
import com.example.board.service.image.ImageService;
import javassist.tools.web.BadHttpRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
public class BoardController {

    private final BoardService boardService;
    @Autowired
    public BoardController(BoardService boardService){
        this.boardService=boardService;
    }
    /*
        @RequestPart를 통해 이미지 파일과 json포멧의 데이터를 같이 보낼 수 있다.
        이미지와 게시글등록요청 핸들러를 분리한 이유는 게시글을 등록할 때 이미지는 등록하지 않거나
        수정할 때 게시글의 내용과 이미지중 하나만 수정할 경우가 있기때문에 각 요청을 분리하여 처리하였다.
    */
    @PostMapping("/boards")
    public ResponseEntity<Object> addBoardContent(@RequestBody @Valid SaveBoardDTO saveBoardDTO,BindingResult bindingResult,ServletRequest request) throws IOException {

        if (bindingResult.hasErrors()) {
            // 에러 정보를 Map에 담아서 응답으로 반환
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        int boardNum = boardService.saveBoard(saveBoardDTO,request);
        return ResponseEntity.created(null).body(boardNum);
    }
    @GetMapping("/boards/{boardNum}")
    public ResponseEntity<Object> readBoard(@PathVariable int boardNum, ServletRequest request){

        ReadBoardDTO readBoardDTO = ReadBoardDTO.builder()
                .boardNum(boardNum)
                .build();
        BoardInfoDTO boardInfoDTO = boardService.readBoard(readBoardDTO,request);
        return ResponseEntity.ok().body(boardInfoDTO);
    }
    @GetMapping("/boards/pages/{pageNum}")
    public ResponseEntity<Object> readPreviewBoard(@PathVariable int pageNum){
        return ResponseEntity.ok().body(boardService.readPreviewBoard(pageNum));
    }
    @DeleteMapping("/boards/{boardNum}")
    public ResponseEntity<Object> deleteBoard(@PathVariable int boardNum){
        boardService.deleteBoard(boardNum);
        return ResponseEntity.ok().body("삭제가 완료되었습니다.");
    }
    @PatchMapping("/boards/{boardNum}")
    public ResponseEntity<Object> updateBoard(@PathVariable int boardNum ,@RequestBody @Valid UpdateBoardDTO updateBoardDTO,
                                              BindingResult bindingResult, ServletRequest request){
        if (bindingResult.hasErrors()) {
            // 에러 정보를 Map에 담아서 응답으로 반환
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        updateBoardDTO.setBoardNum(boardNum);
        boardService.updateBoard(updateBoardDTO,request);
        ReadBoardDTO readBoardDTO = ReadBoardDTO.builder()
                    .boardNum(boardNum)
                    .build();
        return ResponseEntity.ok().body(boardService.readBoard(readBoardDTO,request));
    }
}
