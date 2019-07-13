/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.trackthor.service;

import cl.trackthor.model.Usuario;
import cl.trackthor.repository.UsuarioRepository;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 *
 * @author matia
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        Map<String, Object> attributes = user.getAttributes();
        
        /*
        String email = (String) attributes.get("email");
        Boolean emailVerified = (Boolean) attributes.get("email_verified");
        
        if ( email != null && emailVerified.equals(Boolean.TRUE) ) {
            // Buscar usuario por correo
            Usuario usuario = this.usuarioRepository.findByEmail(email);
            
            // Si el usuario existe
            if ( usuario != null ) {
                
                // Actualizar información personal
                usuario.setNombres( (String) attributes.get("given_name") );
                usuario.setApellidos( (String) attributes.get("family_name") );
                usuario.setFoto( (String) attributes.get("picture") );

                this.usuarioRepository.save(usuario);

                // Retornar usuario
                return usuario;
            }
        }

        throw new OAuth2AuthenticationException(new OAuth2Error("Usuario inválido"));
        */
        return user;
    }

}
