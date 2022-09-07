package com.example.prestamos.entities;

//Quiero que esta clase se convierta en una tabla en la BD y en la BD guardo informacion de los usuarios.
//Anotacion a nivel de clase

import javax.persistence.*;

@Entity //Esta anotacion me permite que esta clase se mapee con una tabla de la base de datos. Hibernate toma esta clase y crea la tabla en la base
@Table(name = "users")//Como quiero que se llame la tabla
public class User {

    //Esta clase no tendra acciones u objetos, Solo lo vamos a mapear con sus atributos
    //A las propiedades tambien le debemos definir anotaciones

    @Id //Llave primaria o identificador unico para cada usuario
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Para decirle que es un valor automatico, dejo la gestion al motor de base de datos
    private int id; //Seran privados para acceder a ellos con getters y setters desde Java


    @Column(name = "nombres")
    private String nombres;


    @Column(name = "apellidos")
    private String apellidos;


    @Column(name = "edad")
    private int edad;


    @Column(name = "numeroDocumento")
    private String numeroDocumento;

    @Column(name = "correoElectronico")
    private String correoElectronico;

    //relacionar una tabla con otra por un campo en comun, se establece con una llave foranea - relaciones en bases de datos
    @ManyToOne
    @JoinColumn(name = "tipodocumentoid") //relacion entre tablas, nombre de la tabla usuario que va a hacer join con la llave primaria de la table documento
    private TipoDocumento tipoDocumento; //Cada usuario va a tener un tipo de documento //tipo objeto

    //Creo otra columna llamada perfil
    @Column(name = "perfil")
    private EnumPerfil perfil;//lo defino como un campo del tipo EnumPerfil

    @Column(name = "password", length = 200)//nullable = false
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public EnumPerfil getPerfil() {
        return perfil;
    }

    public void setPerfil(EnumPerfil perfil) {
        this.perfil = perfil;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

