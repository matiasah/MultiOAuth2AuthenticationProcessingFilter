/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.trackthor.repository;

import cl.trackthor.model.Usuario;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author matia
 */
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    
    public Usuario findByEmail(String correo);
    
}
