package com.finduni.s21efip.providers;

import com.finduni.s21efip.entities.carrera.Carrera;
import com.finduni.s21efip.entities.carrera.Modalidad;
import com.finduni.s21efip.entities.instancia.Instancia;
import com.finduni.s21efip.exceptions.DBConnError;
import com.finduni.s21efip.exceptions.InvalidOrNullParam;
import com.finduni.s21efip.handlers.HandlingCarrera;
import java.util.ArrayList;

/**
 * ProvidingCarrera: Intermediador entre los exposers y el handler de Carrera. Verifica que se cumplan las reglas de negocio establecidas al crear/modificar una entidad carrera.
 * 
 * @author Francisco Induni
 */
public class ProvidingCarrera {
    private HandlingCarrera handlingCarrera;
    
    /*
    * Inicializador
    */
    public ProvidingCarrera(HandlingCarrera handlingCarrera) throws InvalidOrNullParam {
        if (handlingCarrera != null) {
            this.handlingCarrera = handlingCarrera;
        } else {
            throw new InvalidOrNullParam("handlingCarrera no puede ser nulo");
        }
    }
    
    /*
    * Crea y persiste una carrera dado sus atributos. Devuelve la carrera ya con el ID obtenido de la DB.
    */
    public Carrera crearYGuardarCarrera(String nombre, Modalidad modalidad, Integer creditosElectivas, Integer cuposVerano, Integer cuposNormal, Integer cuposSoloEnB, Integer cuposIngresantes, ArrayList<Instancia> instancias) throws InvalidOrNullParam, Exception {
        if (modalidad == Modalidad.PH || modalidad == Modalidad.ED || modalidad == Modalidad.EDH && cuposSoloEnB == null) {
            throw new InvalidOrNullParam("cuposSoloEnB no puede ser nulo para la modalidad seleccionada");
        }
        if (modalidad == Modalidad.MP) {
            cuposSoloEnB = null;
        }
        
        for (Instancia instancia : instancias) {
            if (modalidad == Modalidad.PH) {
                instancia.setInscripcionEn1B(null);
                instancia.setInscripcionEn2B(null);
            }
        }
        
        // DEV TODO: Revisar que no haya ciclos de correlativas en las instancias
        
        Carrera carrera = new Carrera(null, nombre, modalidad, creditosElectivas, cuposVerano, cuposNormal, cuposSoloEnB, cuposIngresantes, instancias);
        this.handlingCarrera.guardarCarrera(carrera);
        return carrera;
    }
    
    /*
    * Borra una carrera recibida de la DB
    */
    public void borrarCarrera(Carrera carrera) throws InvalidOrNullParam {
        if (carrera.getCarreraId() != null) {
            try {
                this.handlingCarrera.borrarCarrera(carrera);
            } catch (DBConnError e) {
                System.out.println("Error al borrar carrera: " + e);
                throw new InvalidOrNullParam();
            }
        } else {
            throw new InvalidOrNullParam("La carrera no puede tener ID nulo");
        }
        
    }
    
    /*
    * Obtiene y devuelve la lista de carreras persistidas en la DB
    */
    public ArrayList<Carrera> obtenerTodasLasCarreras() throws Exception {
        try {
            return this.handlingCarrera.obtenerCarreras();
        } catch (DBConnError e) {
            System.out.println("Error al obtener todas las carreras: " + e);
            throw new Exception(e);
        }
    }
}
