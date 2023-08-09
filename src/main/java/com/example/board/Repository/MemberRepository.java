package com.example.board.Repository;

import com.example.board.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MemberRepository extends JpaRepository<Member,String>,
        QuerydslPredicateExecutor<Member> {

}
