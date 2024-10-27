package com.finduni.s21efip.providers;

import com.finduni.s21efip.entities.instancia.Instancia;
import com.finduni.s21efip.entities.instancia.InstanciaEvaluativa;
import com.finduni.s21efip.entities.instancia.Materia;
import com.finduni.s21efip.entities.instancia.Tipologia;
import com.finduni.s21efip.exceptions.InvalidOrNullParam;
import com.finduni.s21efip.handlers.HandlingInstancia;
import java.util.List;

/**
 * ProvidingInstancia: Intermediador entre los exposers y el handler de Instancia. Verifica que se cumplan las reglas de negocio establecidas al crear/modificar una entidad instancia.
 * 
 * @author Francisco Induni
 */
public class ProvidingInstancia {
    
    private HandlingInstancia handlingInstancia;
    
    /*
    * Inicializador
    */
    public ProvidingInstancia(HandlingInstancia handlingInstancia) throws InvalidOrNullParam {
        if (handlingInstancia != null) {
            this.handlingInstancia = handlingInstancia;
        } else {
            throw new InvalidOrNullParam("handlingInstancia no puede ser nulo");
        }
    }
    
    /*
    * Crea y devuelve una materia dado sus atributos. Aún no es persistida en DB. Eso ocurre cuando se guarda la carrera que contiene la materia
    */
    public Materia crearMateria(String nombre, Boolean inscripcionEnV, Boolean inscripcionEn1A, Boolean inscripcionEn1B, Boolean inscripcionEn2A, Boolean inscripcionEn2B, Integer inscripcionCuatrimestreMinimo,
            List<Instancia> correlativas, Tipologia tipologia, Integer cuatrimestreEnCarrera, Boolean gastaCupos, Integer creditos) throws InvalidOrNullParam {
        if (tipologia == Tipologia.ELECTIVA && creditos == null) {
            throw new InvalidOrNullParam("Créditos no puede ser nulo si la tipología es ELECTIVA");
        }
        if (tipologia != Tipologia.ELECTIVA) {
            creditos = null;
        }
        if (tipologia == Tipologia.MIP || tipologia == Tipologia.PROCESO) {
            inscripcionEnV = false;
            inscripcionEn1B = false;
            inscripcionEn2B = false;
        }
        return new Materia(null, nombre, inscripcionEnV, inscripcionEn1A, inscripcionEn1B, inscripcionEn2A, inscripcionEn2B, inscripcionCuatrimestreMinimo, correlativas, tipologia, cuatrimestreEnCarrera, gastaCupos, creditos);
    }
    
    /*
    * Actualiza una materia dado sus atributos. Aún no es persistida en DB. Eso ocurre cuando se guarda la carrera que contiene la materia
    */
    public void actualizarMateria(Materia materia, String nombre, Boolean inscripcionEnV, Boolean inscripcionEn1A, Boolean inscripcionEn1B, Boolean inscripcionEn2A, Boolean inscripcionEn2B, Integer inscripcionCuatrimestreMinimo,
            List<Instancia> correlativas, Tipologia tipologia, Integer cuatrimestreEnCarrera, Boolean gastaCupos, Integer creditos) throws InvalidOrNullParam {
        if (tipologia == Tipologia.ELECTIVA && creditos == null) {
            throw new InvalidOrNullParam("Créditos no puede ser nulo si la tipología es ELECTIVA");
        }
        if (tipologia != Tipologia.ELECTIVA) {
            creditos = null;
        }
        if (tipologia == Tipologia.MIP || tipologia == Tipologia.PROCESO) {
            inscripcionEnV = false;
            inscripcionEn1B = false;
            inscripcionEn2B = false;
        }
        materia.setNombre(nombre);
        materia.setInscripcionEnV(inscripcionEnV);
        materia.setInscripcionEn1A(inscripcionEn1A);
        materia.setInscripcionEn1B(inscripcionEn1B);
        materia.setInscripcionEn2A(inscripcionEn2A);
        materia.setInscripcionEn2B(inscripcionEn2B);
        materia.setCorrelativas(correlativas);
        materia.setTipologia(tipologia);
        materia.setCuatrimestreEnCarrera(cuatrimestreEnCarrera);
        materia.setCuatrimestreEnCarrera(cuatrimestreEnCarrera);
        materia.setGastaCupos(gastaCupos);
        materia.setCreditos(creditos);
    }
    
    /*
    * Crea y devuelve una instancia evaluativa dado sus atributos. Aún no es persistida en DB. Eso ocurre cuando se guarda la carrera que contiene la instancia evaluativa
    */
    public InstanciaEvaluativa crearInstanciaEvaluativa(String nombre, Boolean inscripcionEnV, Boolean inscripcionEn1A, Boolean inscripcionEn1B, Boolean inscripcionEn2A, Boolean inscripcionEn2B, Integer inscripcionCuatrimestreMinimo,
            List<Instancia> correlativas) {
        return new InstanciaEvaluativa(null, nombre, inscripcionEnV, inscripcionEn1A, inscripcionEn1B, inscripcionEn2A, inscripcionEn2B, inscripcionCuatrimestreMinimo, correlativas);
    }
    
    /*
    * Actualiza una instancia evaluativa dado sus atributos. Aún no es persistida en DB. Eso ocurre cuando se guarda la carrera que contiene la instancia evaluativa
    */
    public void actualizarInstanciaEvaluativa(InstanciaEvaluativa instanciaEvaluativa, String nombre, Boolean inscripcionEnV, Boolean inscripcionEn1A, Boolean inscripcionEn1B, Boolean inscripcionEn2A, Boolean inscripcionEn2B,
            Integer inscripcionCuatrimestreMinimo, List<Instancia> correlativas) {
        instanciaEvaluativa.setNombre(nombre);
        instanciaEvaluativa.setInscripcionEnV(inscripcionEnV);
        instanciaEvaluativa.setInscripcionEn1A(inscripcionEn1A);
        instanciaEvaluativa.setInscripcionEn1B(inscripcionEn1B);
        instanciaEvaluativa.setInscripcionEn2A(inscripcionEn2A);
        instanciaEvaluativa.setInscripcionEn2B(inscripcionEn2B);
        instanciaEvaluativa.setInscripcionCuatrimestreMinimo(inscripcionCuatrimestreMinimo);
        instanciaEvaluativa.setCorrelativas(correlativas);
    }
}
