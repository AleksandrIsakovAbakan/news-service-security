package com.example.newsservicesecurity.controller;

import com.example.newsservicesecurity.api.v1.request.NewsCategoryRq;
import com.example.newsservicesecurity.api.v1.response.NewsCategoryRs;
import com.example.newsservicesecurity.service.NewsCategoryService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/newsCategory")
public class NewsCategoryController {

    private final NewsCategoryService newsCategoryService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<List<NewsCategoryRs>> getPageNewsCategory(@AuthenticationPrincipal UserDetails userDetails,
                                                                    @RequestParam(required = false) Integer offset,
                                                                    @RequestParam(required = false) Integer perPage) {
        return new ResponseEntity<>(newsCategoryService.getAllNewsCategory(userDetails, offset, perPage), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsCategoryRs> getNewsCategoryId(@PathVariable @Min(0) Long id,
                                                            @AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(newsCategoryService.getIdNewsCategory(id, userDetails), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsCategoryRs> editNewsCategory(@AuthenticationPrincipal UserDetails userDetails,
                                                           @PathVariable @Min(0) Long id,
                                                           @Validated @RequestBody NewsCategoryRq newsCategoryRq) {
        return new ResponseEntity<>(newsCategoryService.putIdNewsCategory(userDetails, id, newsCategoryRq), HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsCategoryRs> addNewsCategory(@AuthenticationPrincipal UserDetails userDetails,
                                                          @Validated @RequestBody NewsCategoryRq newsCategoryRq) {
        return new ResponseEntity<>(newsCategoryService.addNewsCategory(userDetails, newsCategoryRq), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsCategoryRs> deleteNewsCategory(@PathVariable @Min(0) Long id,
                                                             @AuthenticationPrincipal UserDetails userDetails) {
        newsCategoryService.deleteNewsCategory(id, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
