/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.trackthor.security;

import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.Filter;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;

/**
 *
 * @author matia
 */
@Configuration
public class OAuth2FilterConfig {
    
    @Bean(name = "oauth2AuthenticationProcessingFilter")
    public Filter oauth2AuthenticationProcessingFilter() {
        // Lista de filtros
        ArrayList<AuthenticationManager> authenticationManagers = new ArrayList<>();
        authenticationManagers.add(googleManager());
        authenticationManagers.add(facebookManager());
        
        MultiOAuth2AuthenticationProcessingFilter filter = new MultiOAuth2AuthenticationProcessingFilter();
        filter.setAuthenticationManagers(authenticationManagers);
        
        return filter;
    }
    
    public OAuth2AuthenticationManager googleManager() {
        
        // OAuth2ProtectedResourceDetails
        AuthorizationCodeResourceDetails resourceDetails = new AuthorizationCodeResourceDetails();
        resourceDetails.setClientId("GOOGLE_CLIENT_ID");
        resourceDetails.setClientSecret("GOOGLE_CLIENT_SECRET");
        resourceDetails.setAccessTokenUri("https://www.googleapis.com/oauth2/v4/token");
        resourceDetails.setAuthenticationScheme(AuthenticationScheme.query);
        resourceDetails.setUserAuthorizationUri("https://accounts.google.com/o/oauth2/v2/auth");
        resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);
        resourceDetails.setScope(Arrays.asList("email", "profile"));
        resourceDetails.setUseCurrentUri(false);
        
        // OAuth2RestTemplate
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);
        
        // ResourceServerTokenServices
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(
                "https://www.googleapis.com/oauth2/v3/userinfo",
                resourceDetails.getClientId()
        );
        tokenServices.setRestTemplate(restTemplate);
        
        // AuthenticationManager
        OAuth2AuthenticationManager manager = new OAuth2AuthenticationManager();
        manager.setTokenServices(tokenServices);
        
        return manager;
    }
    
    public OAuth2AuthenticationManager facebookManager() {
        
        // OAuth2ProtectedResourceDetails
        AuthorizationCodeResourceDetails resourceDetails = new AuthorizationCodeResourceDetails();
        resourceDetails.setClientId("FACEBOOK_CLIENT_ID");
        resourceDetails.setClientSecret("FACEBOOK_CLIENT_SECRET");
        resourceDetails.setAccessTokenUri("https://graph.facebook.com/v2.8/oauth/access_token");
        resourceDetails.setAuthenticationScheme(AuthenticationScheme.query);
        resourceDetails.setUserAuthorizationUri("https://www.facebook.com/v2.8/dialog/oauth");
        resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);
        resourceDetails.setScope(Arrays.asList("public_profile", "email"));
        resourceDetails.setUseCurrentUri(false);
        resourceDetails.setTokenName("oauth_token");
        
        // OAuth2RestTemplate
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);
        
        // ResourceServerTokenServices
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(
                "https://graph.facebook.com/me?fields=id,name,email",
                resourceDetails.getClientId()
        );
        tokenServices.setRestTemplate(restTemplate);
        
        // AuthenticationManager
        OAuth2AuthenticationManager manager = new OAuth2AuthenticationManager();
        manager.setTokenServices(tokenServices);
        
        return manager;
    }
    
}
