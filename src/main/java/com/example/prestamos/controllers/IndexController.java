package com.example.prestamos.controllers;

import com.example.prestamos.entities.User;
import com.example.prestamos.services.Response;
import com.example.prestamos.services.UserService;
import org.jboss.jandex.Index;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("index")
public class IndexController {

    //Se coloca una propiedad del Tipo UserService para poder trabajar con la logica de negocio de la aplicacion
    private UserService userService;

    //Por medio de la inyección de dependencias, se inicializa el servicio

    public IndexController(UserService service) {
        this.userService = service;
    }

    //Un controlador basicamente es un clase
    //Vamos a interactuar el controllador con Postman
    //Como no va retornar vistas vamos a utilizar el REST para que retorne datos JSON

    //Ruta para que pueda retornar una lista de usuarios cuando el usuario haga la peticion
    @RequestMapping("getusuarios")
    public ArrayList<User> getUsuarios() {
        return this.userService.selectAll();
    }

    //Metodo que me permita consultar el metodo nuevo de consulta. En web se conoce como ruta relativa, ruta con parametros

    @RequestMapping("getusuario/{id}")
    //los controladores los accedemos por medio de rutas, el ID sera un parametro(un numero por ej)
    public User getUsuario(@PathVariable int id) {
        return this.userService.selectById(id);
    }

    //Metodo como recurso que me servira para recibir la peticion de crear el usuario
    @PostMapping("create")
    public Response createUser(@RequestBody User request) {
        return this.userService.createUser(request);
    }

    //Metodo para eliminar un dato
    @DeleteMapping("delete/{id}")
    public Response deleteUsuario(@PathVariable int id) {
        return this.userService.deleteUserById(id);
    }

    //Metodo para actualizar un dato
    @PutMapping("update")
    public Response updateUser(@RequestBody User request){
       return this.userService.updateUser(request);
    }

    //Metodo para loguearme
    @PostMapping("auth")
    public Response authentication(@RequestBody User request){
        return this.userService.loginUser(request);
    }
}