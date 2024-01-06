package com.codigo.apigestionmarket.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@NamedQuery(name = "Usuarios.findByEmail", query = "SELECT u FROM Usuarios u WHERE u.email = :email")
@Entity
@Getter
@Setter
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int usuarioId;
    private String email;
    private String nombre;
    private String numeroContacto;
    private String password;
    private String role;
    private int status;





}
