package com.example.prestamos.repository;

import com.example.prestamos.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

//Creamos esta interfaz que va a extenderse desde JPA y le indicábamos a que entidad ibamos a hacer relación.
@Repository //Esta anotación permite identificar que esta interfaz va a trabajar con un repositorio con acceso a base de datos
public interface IUserRepository extends JpaRepository<User, Integer>{ //Usted trabaja con la tabla usuario ¿como sabe que es una tabla? en el momento que especificamos User, la configuramos como tabla.

    @Query("SELECT u FROM User u WHERE u.correoElectronico = ?1 and u.password = ?2")
    //Metodo que no va a tener cuerpo sino que JPA lo va a interpretar y correr en la base de datos
    ArrayList<User> validaCredenciales(String usuario, String password);//Si encuentra datos es porque las credenciales existen y estan ok.


    @Query("SELECT u FROM User u WHERE u.correoElectronico = ?1")
    //Metodo que me permite validar y me retorna un array list de usuarios si encontro susuarios con el correo que le estoy dando
    ArrayList<User> validaCorreo(String correoElectronico);
}


