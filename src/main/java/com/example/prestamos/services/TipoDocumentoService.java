package com.example.prestamos.services;

import com.example.prestamos.entities.TipoDocumento;
import com.example.prestamos.repository.ITipoDocumentoRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Service
public class TipoDocumentoService {

    //inicializo el repositorio
    private ITipoDocumentoRepository repository;

    //constructor par a hacer la inyeccion de la dependencia y lo asignado en el constructor
    //el servidor cuando suba esto inicializa las dependencias por nosotros

    public TipoDocumentoService(ITipoDocumentoRepository rep){
        this.repository = rep;
    }

    //get y create de tipo documento

    public ArrayList<TipoDocumento> selectAll(){
        return (ArrayList<TipoDocumento>) this.repository.findAll();
    }

    //Metodo que permite registrar un documento
    public Response createDocumento(TipoDocumento data){

        //Antes de crearlo debo validar si el documento ya existe
        ArrayList<TipoDocumento> documentos = this.repository.findByNombre(data.getNombre());//Me lo retorna en un ArrayList

        //Si llega con datos es decir que ya existe y me va a responder con el siguiente error
        if (documentos != null && documentos.size() > 0){//Docuemntos diferente de nulo y documentos.size es = aa 0 es decir tiene datos
            //Debo cortar el programa
            Response response = new Response();//creo la instancia
            response.setCode(500);
            response.setMessage("Este tipo de documento ya existe");
            return response;
        }

        //Si no existe, habilito la creacion
        this.repository.save(data);
        Response response = new Response();//creo la instancia
        response.setCode(200);
        response.setMessage("Usuario registrado exitosamente");
        return response;
    }


}
