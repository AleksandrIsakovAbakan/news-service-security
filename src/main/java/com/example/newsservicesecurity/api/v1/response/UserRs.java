package com.example.newsservicesecurity.api.v1.response;

import com.example.newsservicesecurity.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRs {

    private Long id;

    private String username;

    private String password;

    private List<RoleType> roles;
}
