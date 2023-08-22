package com.example.board.controller.reply;

import com.example.board.dto.board.UpdateBoardDTO;
import com.example.board.dto.reply.SaveReplyDTO;
import com.example.board.dto.reply.UpdateReplyDTO;
import com.example.board.service.reply.ReplyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
public class ReplyController {

    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService){
        this.replyService=replyService;
    }

    @PostMapping("/boards/{boardNum}/replys")
    public ResponseEntity<Object> addReply(@PathVariable int boardNum, @RequestBody @Valid SaveReplyDTO saveReplyDTO,
                                           BindingResult bindingResult, ServletRequest request){
        if (bindingResult.hasErrors()) {
            // 에러 정보를 Map에 담아서 응답으로 반환
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        saveReplyDTO.setBoardNum(boardNum);
        replyService.saveReply(saveReplyDTO,request);
        return ResponseEntity.created(null).body("댓글 등록완료");
    }
    @DeleteMapping("/boards/replys/{replyNum}")
    public ResponseEntity<Object> deleteReply(@PathVariable int replyNum){
        replyService.deleteReply(replyNum);
        return ResponseEntity.ok().body("댓글 삭제완료");
    }
    @PutMapping("/boards/replys/{replyNum}")
    public ResponseEntity<Object> updateReply(@PathVariable int replyNum, @RequestBody @Valid UpdateReplyDTO updateReplyDTO,
                                              BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            // 에러 정보를 Map에 담아서 응답으로 반환
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        updateReplyDTO.setReplyNum(replyNum);
        replyService.updateReply(updateReplyDTO);
        return ResponseEntity.ok().body("댓글 수정완료");
    }


}
