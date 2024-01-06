package com.codigo.apigestionmarket.service.impl;

import com.codigo.apigestionmarket.contantes.Constants;
import com.codigo.apigestionmarket.dao.UsuarioDAO;
import com.codigo.apigestionmarket.entity.Usuarios;
import com.codigo.apigestionmarket.security.CustomerDetailService;
import com.codigo.apigestionmarket.security.jwt.JwtUtil;
import com.codigo.apigestionmarket.service.UsuarioService;
import com.codigo.apigestionmarket.util.MarketUtils;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerDetailService customersDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<String> singUp(Map<String, String> requestMap) {
        log.info("Ingreso a Registrar Usuario");
        usuarioDAO.save(getUsuariosMap(requestMap));
        log.info("Termino de Registrar Usuario");
        return MarketUtils.getResponseEntity(Constants.MSG_USUARIO_CREADO, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Ingreso a Login");
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
            if(authentication.isAuthenticated()){
                if(customersDetailsService.getUsuarios().getStatus() == Constants.ACTIVO) {
                    return new ResponseEntity<>(jwtUtil.generateToken(customersDetailsService.getUsuarios().getEmail(),
                            customersDetailsService.getUsuarios().getRole()), HttpStatus.OK);
                }

            }else{
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public List<Usuarios> obtenerTodos() {
        return usuarioDAO.findAll();
    }

    private Usuarios getUsuariosMap(Map<String, String> requestMap){
        Usuarios usuarios = new Usuarios();
        usuarios.setNombre(requestMap.get("nombre"));
        usuarios.setNumeroContacto(requestMap.get("numeroContacto"));
        usuarios.setEmail(requestMap.get("email"));
        usuarios.setPassword(requestMap.get("password"));
        usuarios.setStatus(Constants.ACTIVO);
        usuarios.setRole(Constants.ROLE_USUARIO);

        return usuarios;
    }
}
