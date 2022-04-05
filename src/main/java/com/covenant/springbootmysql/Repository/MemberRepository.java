package com.covenant.springbootmysql.Repository;

import com.covenant.springbootmysql.Model.Author;
import com.covenant.springbootmysql.Model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
