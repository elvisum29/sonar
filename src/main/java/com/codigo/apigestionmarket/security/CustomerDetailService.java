package com.codigo.apigestionmarket.security;

import com.codigo.apigestionmarket.dao.UsuarioDAO;
import com.codigo.apigestionmarket.entity.Usuarios;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
@Getter
public class CustomerDetailService implements UserDetailsService {

    @Autowired
    private UsuarioDAO usuarioDAO;


    private Usuarios usuarios;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Ingreso a loadUserByUsername: ",username);
        usuarios = usuarioDAO.findByEmail(username);
        if (!Objects.isNull(usuarios)){
            return new User(usuarios.getEmail(), usuarios.getPassword(), new ArrayList<>());
        }else{
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }



}
