package com.example.board.dto.paging;

import com.example.board.dto.board.BoardInfoDTO;
import com.example.board.dto.board.PreviewBoardDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
// 페이징에 사용되는 DTO, 기본적으로 한 페이지에 10개씩 보여준다.
public class PagingInfoDTO {

    private int pagingNum; // 현재 페이지 번호
    private int firstPage; // 페이징 바에서 보이는 첫번째 페이지
    private int lastPage; // 페이징 바에서 보이는 마지막 페이지
    // 이전,다음페이지의 유무에따라 페이지이동 버튼의 활성여부를 결정
    private boolean hasPreviousPage;// 아전페이지의 유무
    private boolean hasNextPage; // 다음 페이지의 유무
    private List<PreviewBoardDTO> previewList; // 게시판의 preview 정보를 가지는 dto리스트
}
