package com.finduni.s21efip.entities.instancia.instanciaestudiante;

import com.finduni.s21efip.entities.PeriodoParticular;
import com.finduni.s21efip.entities.instancia.Instancia;
import java.util.List;

/**
 * InstanciaEstudiante: Es una clase abstracta. Contiene los atributos en común que tienen InstanciaEvaluativaEstudiante y MateriaEstudiante: La instancia de la carrera, las correlativas de la carrera (pero de tipo
 * InstanciaEstudiante en lugar de Instancia), un indicador de si el estudiante ya la aprobó y el periodo particular en la que se inscribió (o se planea inscribir)
 * 
 * @author Francisco Induni
 */
public abstract class InstanciaEstudiante {
    private Instancia instancia;
    private List<InstanciaEstudiante> correlativasInstanciasEstudiante;
    private Boolean aprobada;
    private PeriodoParticular inscripcion;
    
    /*
    * Inicializador
    */
    public InstanciaEstudiante(Instancia instancia, List<InstanciaEstudiante> correlativasInstanciasEstudiante, Boolean aprobada, PeriodoParticular inscripcion) {
        this.instancia = instancia;
        this.correlativasInstanciasEstudiante = correlativasInstanciasEstudiante;
        this.aprobada = aprobada;
        this.inscripcion = inscripcion;
    }

    /*
    * Getters y Setters
    */
    public Instancia getInstancia() {
        return instancia;
    }

    public void setInstancia(Instancia instancia) {
        this.instancia = instancia;
    }

    public List<InstanciaEstudiante> getCorrelativasInstanciasEstudiante() {
        return correlativasInstanciasEstudiante;
    }

    public void setCorrelativasInstanciasEstudiante(List<InstanciaEstudiante> correlativasInstanciasEstudiante) {
        this.correlativasInstanciasEstudiante = correlativasInstanciasEstudiante;
    }

    public Boolean getAprobada() {
        return aprobada;
    }

    public void setAprobada(Boolean aprobada) {
        this.aprobada = aprobada;
    }

    public PeriodoParticular getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(PeriodoParticular inscripcion) {
        this.inscripcion = inscripcion;
    }
}
