package com.finduni.s21efip.providers;

import com.finduni.s21efip.entities.Perfil;
import com.finduni.s21efip.exceptions.DBConnError;
import com.finduni.s21efip.exceptions.InvalidOrNullParam;
import com.finduni.s21efip.handlers.HandlingPerfil;
import java.util.ArrayList;

/**
 * ProvidingPerfil: Intermediador entre los exposers y el handler de Perfil. Verifica que se cumplan las reglas de negocio establecidas al crear/modificar una entidad perfil.
 * 
 * @author Francisco Induni
 */
public class ProvidingPerfil {
    private HandlingPerfil handlingPerfil;
    
    /*
    * Inicializador
    */
    public ProvidingPerfil(HandlingPerfil handlingPerfil) throws InvalidOrNullParam {
        if (handlingPerfil != null) {
            this.handlingPerfil = handlingPerfil;
        } else {
            throw new InvalidOrNullParam("handlingPerfil no puede ser nulo");
        }
    }
    
    /*
    * Crea un perfil y lo persiste en DB. Devuelve el perfil ya con el ID obtenido de la DB
    */
    public Perfil crearPerfil(String nombre) throws InvalidOrNullParam {
        Perfil perfil = new Perfil(null, nombre, new ArrayList<>());
        
        try {
            this.handlingPerfil.guardarPerfil(perfil);
        } catch (DBConnError e) {
            System.out.println("Error al guardar perfil: " + e);
            throw new InvalidOrNullParam();
        }
        return perfil;
    }
    
    /*
    * Borra un perfil recibido de la DB
    */
    public void borrarPerfil(Perfil perfil) throws InvalidOrNullParam {
        try {
            this.handlingPerfil.borrarPerfil(perfil);
        } catch (DBConnError e) {
            System.out.println("Error al borrar perfil: " + e);
            throw new InvalidOrNullParam();
        }
    }
    
    /*
    * Obtiene y devuelve la lista de perfiles persistidos desde la DB
    */
    public ArrayList<Perfil> obtenerPerfiles() throws Exception {
        try {
            return this.handlingPerfil.obtenerPerfiles();
        } catch (DBConnError e) {
            System.out.println("Error al obtener perfiles: " + e);
            throw new InvalidOrNullParam();
        }
    }
}
