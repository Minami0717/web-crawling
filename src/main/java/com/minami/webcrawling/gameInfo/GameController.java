package com.minami.webcrawling.gameInfo;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class GameController {
    private final GameService service;

    @GetMapping("/test")
    public void downloadMock(HttpServletResponse res) throws IOException {
        service.download(res);
    }
}
