package com.example.newsservicesecurity.controller;


import com.example.newsservicesecurity.api.v1.request.UserRq;
import com.example.newsservicesecurity.api.v1.response.UserRs;
import com.example.newsservicesecurity.service.UserService;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserRs>> getPageUsers(@AuthenticationPrincipal UserDetails userDetails,
                                                     @RequestParam(required = false) Integer offset,
                                                     @RequestParam(required = false) Integer perPage
    ) {
        return new ResponseEntity<>(userService.getAllUsers(offset, perPage), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<UserRs> getUserId(@AuthenticationPrincipal UserDetails userDetails,
                                            @PathVariable(required = false) Long id) {
        return new ResponseEntity<>(userService.getIdUser(id, userDetails), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<UserRs> editUser(@AuthenticationPrincipal UserDetails userDetails,
                                           @PathVariable(required = false) Long id,
                                           @RequestBody UserRq userRQ) {
        return new ResponseEntity<>(userService.putIdUser(id, userDetails, userRQ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserRs> addUser(@RequestBody(required = false) UserRq userRq) {
        return new ResponseEntity<>(userService.addUser(userRq), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<UserRs> deleteUser(@AuthenticationPrincipal UserDetails userDetails,
                                             @PathVariable @Min(0) Long id) {
        userService.deleteUser(id, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
