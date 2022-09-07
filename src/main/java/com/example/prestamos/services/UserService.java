package com.example.prestamos.services;

import com.example.prestamos.entities.User;
import com.example.prestamos.repository.IUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Optional;

//Trabaja con un patron de inyeccion de dependencias, que quiere decir que el framework orientado a web
//automaticamente nos iniciliza ciertos objetos para que los utilicemos
@Service
public class UserService {

    //Creo toda la logica relacionada con los usuarios
    //vamos a crear una propiedad de nuestro servicio para acceder al repostiroio usuario de la interfaz que creamos, para hacer uso de la base de datos, debemos inicializarla

    private IUserRepository userRepository;

    public UserService(IUserRepository rep) {
        this.userRepository = rep;//lo asignamos a la propiedad que acabamos de inicializar
    }

    //Metodo que me permite obtener todos los usuarios de la base de datos
    public ArrayList<User> selectAll() { // un array list de tipo User, se conecta a la base de datos y trae lo que encuentra mapeado en una clase de Java
        return (ArrayList<User>) this.userRepository.findAll(); //conversion de list a arraylist, para poder regresarlo en un JSon 
    }

    //Metodo encargado de registrar o crear un usuario en la base de datos

    public Response createUser(User data) {
        Response response = new Response();//creo la instancia


        //No me permite crear usuarios donde el correo este mal y no ingrese password
        if (!isValidEmailAddress(data.getCorreoElectronico())){//Quiero que entre acá si el correo está errado
            response.setCode(200);
            response.setMessage("Error el usuario dado no es valido");
            return response;
        }

        //Validamos el password
        if (data.getPassword().equals(null) || data.getPassword().equals("")){
            response.setCode(200);
            response.setMessage("Error, su contraseña no es valida");
            return response;
        }

        //No me permite crear usuarios donde el correo que me estan enviando esta asociado a otro usuario

        ArrayList<User> existe = this.userRepository.validaCorreo(data.getCorreoElectronico());
        if (existe != null && existe.size() > 0){//si es mayor a 0 es qu ellego algun dato
            response.setCode(500);
            response.setMessage("Error, el correo electronico ya está en uso");
            return response;
        }

        this.userRepository.save(data);
        response.setCode(200);
        response.setMessage("Usuario registrado exitosamente");
        return response;
    }

    //Metodo para buscar un registro por ID

    public User selectById(int Id) {//retorno un objetio selectById tipo USer
        Optional<User> siExiste = this.userRepository.findById(Id);//Es un opcional, nosotros le pasamos un ID entonces pasan dos cosas, que exista o que no exista, opcional porque puede que si o que no. Buscar sobre geneericos en Java
        if (siExiste.isPresent()) {//si existe el usuario, si es true existe
            return siExiste.get();//El metodo Get me retorna un User porque me estamos apuntando a un repositorio User
        } else {// si no existe, será false
            return null; //si no existe esto será lo que retornara como que no existe
        }
    }

    //Metodo que me permita eliminar un usuario, pasan dos cosas que sea o que no sea existoso

    public Response deleteUserById(int Id) {//Objeto responde con un mensaje al usuario
        Response response = new Response();//creo la instancia
        try { //controlo los errores a traves de excepciones
            this.userRepository.deleteById(Id);//Lo eliminamos a traves de nuestro repositorio
            response.setCode(200);
            response.setMessage("Usuario eliminado exitosamente");
            return response;
        } catch (Exception ex) {
            response.setCode(500);
            response.setMessage("Error " + ex.getMessage());
            return response;
        }
    }

    //Metodo de actualizacion de un usuario dado

    public Response updateUser(User data) {//Update basico se realiza por la llave primaria, es decir el ID
        Response response = new Response();
        if (data.getId() == 0) {//si el ID no llega debe retornar un error
            response.setCode(500);
            response.setMessage("Error el ID del usuario no es valido");
            return response;
        }

        //Primero valido si el usuario que me piden actualizar, existe
        User exists = selectById(data.getId());//Uso el metodo selectbyid que me pide un id que lo selecciono de la data que me llego
        if (exists == null) {
            response.setCode(500);
            response.setMessage("Error, el usuario no existe en la base de datos");
            return response;
        }

        //si existe el usuario, ahora seteamos la data para modificar

        if (data.getCorreoElectronico().equals(null) || data.getCorreoElectronico().equals("")) {
            response.setCode(500);
            response.setMessage("Error, el correo electronico no es valido");
            return response;
        }

        // si el correo electronico no es correcto me devuelve el error
        if (!isValidEmailAddress(data.getCorreoElectronico())) {
            response.setCode(500);
            response.setMessage("Error, el correo electronico no tiene el formato adecuado");
            return response;
        }

        exists.setCorreoElectronico(data.getCorreoElectronico());//establezca un correo electronico del usuario que si exsite por uno que me llego del cliente
        exists.setEdad(data.getEdad());

        this.userRepository.save(exists);
        response.setCode(200);
        response.setMessage("Usuario modificado exitosamente");
        return response;

    }

    //Metodo que me va a permitir a traves del controlador validar que un usuario se pueda autenticar

    public Response loginUser(User data){
        //Vamos a validar dos campos en la base de datos, correo y contraseña
        Response response = new Response();


        //Validamos datos
        if (!isValidEmailAddress(data.getCorreoElectronico())){//Quiero que entre acá si el correo está errado
            response.setCode(200);
            response.setMessage("Error el usuario dado no es valido");
            return response;
        }

        //Validamos el password
        if (data.getPassword().equals(null) || data.getPassword().equals("")){
            response.setCode(200);
            response.setMessage("Error, su contraseña no es valida");
            return response;
        }

        //Validar los resultados
        ArrayList<User> existe = this.userRepository.validaCredenciales(data.getCorreoElectronico(), data.getPassword());
        if(existe != null && existe.size() > 0){
            response.setCode(200);
            response.setMessage("Usuario autenticado exitosamente");
            return response;
        }
        response.setCode(200);
        response.setMessage("Error, sus datos de acceso están errados ");
        return response;
    }


    //Metodo para validar si el correo electronico tiene una sintaxis correcta y es valido

    public boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
        }
}