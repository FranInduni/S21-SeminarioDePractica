package com.finduni.s21efip.entities.instancia;

import java.util.List;

/**
 * Instancia: Es una clase abstracta, contiene los atributos en común que tienen las instancias evaluativas y materias: ID de la instancia, nombre, si es inscribible en cada sub/período del año, número de cuatrimestre mínimo para
 * su inscripción y lista de instancias correlativas.
 * Esta clase abstracta es extendida por las clases Materia e InstanciaEvaluativa
 * 
 * @author Francisco Induni
 */
public abstract class Instancia {
    private Integer instanciaId;
    private String nombre;
    private Boolean inscripcionEnV;
    private Boolean inscripcionEn1A;
    private Boolean inscripcionEn1B;
    private Boolean inscripcionEn2A;
    private Boolean inscripcionEn2B;
    private Integer inscripcionCuatrimestreMinimo;
    private List<Instancia> correlativas;
    
    /*
    * Inicializador
    */
    public Instancia(Integer instanciaId, String nombre, Boolean inscripcionEnV, Boolean inscripcionEn1A, Boolean inscripcionEn1B, Boolean inscripcionEn2A, Boolean inscripcionEn2B,
            Integer inscripcionCuatrimestreMinimo, List<Instancia> correlativas) {
        this.instanciaId = instanciaId;
        this.nombre = nombre;
        this.inscripcionEnV = inscripcionEnV;
        this.inscripcionEn1A = inscripcionEn1A;
        this.inscripcionEn1B = inscripcionEn1B;
        this.inscripcionEn2A = inscripcionEn2A;
        this.inscripcionEn2B = inscripcionEn2B;
        this.inscripcionCuatrimestreMinimo = inscripcionCuatrimestreMinimo;
        this.correlativas = correlativas;
    }
    
    /*
    * Getters y Setters
    */
    public Integer getInstanciaId() {
        return instanciaId;
    }
    
    public void setInstanciaId(Integer instanciaId) {
        this.instanciaId = instanciaId;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getInscripcionEnV() {
        return inscripcionEnV;
    }

    public void setInscripcionEnV(Boolean inscripcionEnV) {
        this.inscripcionEnV = inscripcionEnV;
    }

    public Boolean getInscripcionEn1A() {
        return inscripcionEn1A;
    }

    public void setInscripcionEn1A(Boolean inscripcionEn1A) {
        this.inscripcionEn1A = inscripcionEn1A;
    }

    public Boolean getInscripcionEn1B() {
        return inscripcionEn1B;
    }

    public void setInscripcionEn1B(Boolean inscripcionEn1B) {
        this.inscripcionEn1B = inscripcionEn1B;
    }

    public Boolean getInscripcionEn2A() {
        return inscripcionEn2A;
    }

    public void setInscripcionEn2A(Boolean inscripcionEn2A) {
        this.inscripcionEn2A = inscripcionEn2A;
    }

    public Boolean getInscripcionEn2B() {
        return inscripcionEn2B;
    }

    public void setInscripcionEn2B(Boolean inscripcionEn2B) {
        this.inscripcionEn2B = inscripcionEn2B;
    }

    public Integer getInscripcionCuatrimestreMinimo() {
        return inscripcionCuatrimestreMinimo;
    }

    public void setInscripcionCuatrimestreMinimo(Integer inscripcionCuatrimestreMinimo) {
        this.inscripcionCuatrimestreMinimo = inscripcionCuatrimestreMinimo;
    }

    public List<Instancia> getCorrelativas() {
        return correlativas;
    }

    public void setCorrelativas(List<Instancia> correlativas) {
        this.correlativas = correlativas;
    }
    
}
