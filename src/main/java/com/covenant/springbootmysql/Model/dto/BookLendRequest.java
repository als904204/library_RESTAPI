package com.covenant.springbootmysql.Model.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookLendRequest {
    public List<Long> bookIds;
    public Long memberId;



}
