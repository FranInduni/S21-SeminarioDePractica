package com.finduni.s21efip.entities.instancia;

import java.util.List;

/**
 * Materia: Representa las materias de las carreras universitarias.
 * Extiene instancia y le agrega: Tipología de la materia, su cuatrimestre en carrera, indicador de si gasta cupo de inscripción al cursarla, y la cantidad de créditos que otorga (si corresponde)
 * 
 * @author Francisco Induni
 */
public final class Materia extends Instancia {
    private Tipologia tipologia;
    private Integer cuatrimestreEnCarrera;
    private Boolean gastaCupos;
    private Integer creditos;
    
    /*
    * Inicializador
    */
    public Materia(Integer instanciaId, String nombre, Boolean inscripcionEnV, Boolean inscripcionEn1A, Boolean inscripcionEn1B, Boolean inscripcionEn2A, Boolean inscripcionEn2B, Integer inscripcionCuatrimestreMinimo, List<Instancia> correlativas, Tipologia tipologia, Integer cuatrimestreEnCarrera, Boolean gastaCupos, Integer creditos) {
        super(instanciaId, nombre, inscripcionEnV, inscripcionEn1A, inscripcionEn1B, inscripcionEn2A, inscripcionEn2B, inscripcionCuatrimestreMinimo, correlativas);
        this.tipologia = tipologia;
        this.cuatrimestreEnCarrera = cuatrimestreEnCarrera;
        this.gastaCupos = gastaCupos;
        this.creditos = creditos;
    }
    
    /*
    * Getters y Setters
    */
    public Tipologia getTipologia() {
        return tipologia;
    }

    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }

    public Integer getCuatrimestreEnCarrera() {
        return cuatrimestreEnCarrera;
    }

    public void setCuatrimestreEnCarrera(Integer cuatrimestreEnCarrera) {
        this.cuatrimestreEnCarrera = cuatrimestreEnCarrera;
    }

    public Boolean getGastaCupos() {
        return gastaCupos;
    }

    public void setGastaCupos(Boolean gastaCupos) {
        this.gastaCupos = gastaCupos;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }
}
