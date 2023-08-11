package com.example.board.Repository;

import com.example.board.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ReplyRepository extends JpaRepository<Reply,Integer>,
        QuerydslPredicateExecutor<Reply> {

}
