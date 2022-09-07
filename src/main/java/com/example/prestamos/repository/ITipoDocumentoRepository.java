package com.example.prestamos.repository;

import com.example.prestamos.entities.TipoDocumento;
import com.example.prestamos.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

//interactuar con la tabla tipo documento en la supabase, entonces llego aca

@Repository
public interface ITipoDocumentoRepository extends JpaRepository<TipoDocumento,Integer> {
    //crear metodo que me permita buscar un documento por nombre
    //me va a permitir escribir syntaxis SQL y Java para hacer consultas
    @Query("SELECT t FROM TipoDocumento t WHERE t.nombre = ?1")//Inyeccion de datos
    ArrayList<TipoDocumento> findByNombre(String nombre);//Lo que quiero que me retorne
    //
    //select * from tipodocumento ....
    // + where nombre = "" quiero que me ejecute hibernate mediante de eun ijnyeccion de SQL a traves java
    //?1 sera reeem´lazo porlo que yo pase en la vairable nombre
}
