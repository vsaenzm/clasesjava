package com.example.prestamos.controllers;

import com.example.prestamos.entities.TipoDocumento;
import com.example.prestamos.services.Response;
import com.example.prestamos.services.TipoDocumentoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("tipodocumento")
public class TipoDocumentoController {

    //tenemos que inicializar las depedencias
    private TipoDocumentoService service;

    //constructor para este controlador
    public TipoDocumentoController(TipoDocumentoService ser){
        this.service = ser;
    }

    //controlador para obtener todos los documentos
    @RequestMapping("get")
    public ArrayList<TipoDocumento> get(){
        return this.service.selectAll();
    }

    @PostMapping("create")
    public Response create(@RequestBody TipoDocumento data){
        return service.createDocumento(data);
    }





}
