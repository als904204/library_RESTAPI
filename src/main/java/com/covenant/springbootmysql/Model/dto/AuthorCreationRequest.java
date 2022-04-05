package com.covenant.springbootmysql.Model.dto;


import lombok.Data;

@Data
public class AuthorCreationRequest {
    private String firstName;
    private String lastName;
}
