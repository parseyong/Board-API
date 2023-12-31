package com.example.board.dto.paging;

import com.example.board.dto.board.PreviewBoardDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PagingRequestDTO {

    private List<PreviewBoardDTO> previewBoardDTOList;
    private int currentPageNum;
    private int firstPage;
    private int lastPage;
    private int totalPageCnt;
    private boolean isNextPage;
    private boolean isPreviousPage;
}
