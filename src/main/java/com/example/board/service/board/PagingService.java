package com.example.board.service.board;

import com.example.board.Repository.BoardRepository;
import com.example.board.dto.paging.PagingRequestDTO;
import com.example.board.exception.NotExistPageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagingService {
    private final BoardRepository boardRepository;

    @Autowired
    public PagingService(BoardRepository boardRepository){
        this.boardRepository=boardRepository;
    }
    public PagingRequestDTO getPagingRequestDTO(int currentPageNum){
        int totalPagecnt = findTotalPageCnt();

        if(currentPageNum ==0 || currentPageNum>totalPagecnt)
            throw new NotExistPageException("존재하지 않는 페이지입니다.");

        int firstPage = findFirstPage(currentPageNum);

        return PagingRequestDTO.builder()
                .firstPage(firstPage)
                .lastPage(findLastPage(totalPagecnt,firstPage))
                .currentPageNum(currentPageNum)
                .isNextPage(isExistNextPage(totalPagecnt,currentPageNum))
                .isPreviousPage(isExistLastPage(currentPageNum))
                .totalPageCnt(totalPagecnt)
                .build();
    }
    public int findTotalPageCnt() {
        int boardCnt=boardRepository.findAll().size();

        if(boardCnt<=10)
            return 1;
        else{
            if(boardCnt%10==0){
                return boardCnt/10;
            }
            else
                return (boardCnt/10)+1;
        }
    }
    public int findFirstPage(int currentPageNum) {
        if(currentPageNum <=10)
            return 1;
        else if(currentPageNum%10==0){
            return currentPageNum-9;
        }
        else{
            int firstPage=(currentPageNum/10)*10;
            return firstPage+1;
        }

    }
    public int findLastPage(int totalPageCnt,int firstPage) {
        if(firstPage+9>totalPageCnt){
            return totalPageCnt;
        }
        else{
            int lastPage=firstPage+9;
            return lastPage;
        }
    }
    public boolean isExistNextPage(int totalPageCnt,int currentPageNum){
        if(totalPageCnt > currentPageNum)
            return true;
        else
            return false;
    }
    public boolean isExistLastPage(int currentPageNum){
        if(currentPageNum > 1)
            return true;
        else
            return false;
    }
}
