package com.finduni.s21efip.entities.instancia;

import java.util.List;

/**
 * InstanciaEvaluativa: Representa las instancias evaluativas (No materias) de una carrera. Por ejemplo EFIP 1 y EFIP 2 en Licenciatura Informática en modalidad EDH
 * Extiende Instancia
 * 
 * @author Francisco Induni
 */
public final class InstanciaEvaluativa extends Instancia {
    
    /*
    * Inicializador
    */
    public InstanciaEvaluativa(Integer instanciaId, String nombre, Boolean inscripcionEnV, Boolean inscripcionEn1A, Boolean inscripcionEn1B, Boolean inscripcionEn2A, Boolean inscripcionEn2B, Integer inscripcionCuatrimestreMinimo, List<Instancia> correlativas) {
        super(instanciaId, nombre, inscripcionEnV, inscripcionEn1A, inscripcionEn1B, inscripcionEn2A, inscripcionEn2B, inscripcionCuatrimestreMinimo, correlativas);
    }
    
}
