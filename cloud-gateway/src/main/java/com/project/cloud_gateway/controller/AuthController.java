package com.project.cloud_gateway.controller;

import com.project.cloud_gateway.model.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public ResponseEntity<AuthResponse> login(@AuthenticationPrincipal OAuth2User user, Model model,
                                              @RegisteredOAuth2AuthorizedClient("keycloak") OAuth2AuthorizedClient client) {

        AuthResponse authResponse = AuthResponse.builder()
                .userId(user.getAttribute("email"))
                .accessToken(client.getAccessToken().getTokenValue())
                .refreshToken(client.getRefreshToken().getTokenValue())
                .expiresAt(client.getAccessToken().getExpiresAt().getEpochSecond())
                .authorities(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .build();

        return ResponseEntity.ok(authResponse);
    }
}
