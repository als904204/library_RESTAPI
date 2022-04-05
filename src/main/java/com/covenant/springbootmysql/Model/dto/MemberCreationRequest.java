package com.covenant.springbootmysql.Model.dto;

import lombok.Data;

@Data
public class MemberCreationRequest {
    private String firstName;
    private String lastName;
}
