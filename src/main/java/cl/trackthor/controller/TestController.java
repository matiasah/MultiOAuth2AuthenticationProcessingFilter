/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.trackthor.controller;

import java.security.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author matia
 */
@RestController
public class TestController {

    @RequestMapping("/user")
    public Principal getLogin(Principal principal) {
        return principal;
    }

    @GetMapping("/")
    public String index(Authentication authentication) {
        OAuth2User user = getCurrentUser(authentication);

        StringBuffer authorities = new StringBuffer();
        user.getAuthorities().forEach((a) -> authorities.append(a.toString()).append(","));
        return "Hello " + user.getAttributes().get("name") + ". Your email is " + user.getAttributes().get("email")
                + " and your profile picture is <img src='" + user.getAttributes().get("picture") + "' /> <br />"
                + "You have the following attributes: " + authorities.toString() + "<br />"
                + "<a href='/logout'>logout</a>";
    }

    public OAuth2User getCurrentUser(Authentication auth) {
        return ((OAuth2AuthenticationToken) auth).getPrincipal();
    }

}
