package org.example.moviesearchservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.example.moviesearchservice.service.ViewService;

@RestController
public class MovieViews {
    public final ViewService viewService;

    public MovieViews(ViewService viewService) {
        this.viewService = viewService;
    }

    @GetMapping("/views-on")
    public int views() {
        return viewService.viewsOn();
    }
}
