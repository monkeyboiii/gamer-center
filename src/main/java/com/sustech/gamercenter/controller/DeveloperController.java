package com.sustech.gamercenter.controller;

import com.sustech.gamercenter.security.AuthToken;
import com.sustech.gamercenter.util.model.JsonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/developer/")
public class DeveloperController {

    @AuthToken(role = "d")
    @GetMapping
    public JsonResponse getPlayerInfo(@RequestParam("oauth") String oathToken) {

        return new JsonResponse(0, "success");
    }
}
