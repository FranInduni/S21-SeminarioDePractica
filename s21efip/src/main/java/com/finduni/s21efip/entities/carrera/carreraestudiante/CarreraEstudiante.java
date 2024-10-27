package com.finduni.s21efip.entities.carrera.carreraestudiante;

import com.finduni.s21efip.entities.PeriodoParticular;
import com.finduni.s21efip.entities.instancia.instanciaestudiante.InstanciaEstudiante;
import java.util.ArrayList;

/**
 * CarreraEstudiante: Representa una inscripción de un estudiante en una carrera.
 * Contiene el ID de la instancia, una lista con las instanciasEstudiante (instancias de la carrera pero extendidas con datos propios del estudiante), el periodo de inicio del estudiante en la carrera
 * y un entero que indica los créditos extras de electivas que recibió el estudiante sin haber rendido materias electivas.
 * 
 * @author Francisco Induni
 */
public class CarreraEstudiante {
    private Integer carreraEstudianteId;
    private ArrayList<InstanciaEstudiante> instanciasEstudiante;
    private PeriodoParticular periodoParticularInicio;
    private Integer creditosExtra;
    
    /*
    * Inicializador
    */
    public CarreraEstudiante(Integer carreraEstudianteId, ArrayList<InstanciaEstudiante> instanciasEstudiante, PeriodoParticular periodoParticularInicio, Integer creditosExtra) {
        this.carreraEstudianteId = carreraEstudianteId;
        this.instanciasEstudiante = instanciasEstudiante;
        this.periodoParticularInicio = periodoParticularInicio;
        this.creditosExtra = creditosExtra;
    }
    
    /*
    * Getters y Setters
    */

    public Integer getCarreraEstudianteId() {
        return carreraEstudianteId;
    }

    public void setCarreraEstudianteId(Integer carreraEstudianteId) {
        this.carreraEstudianteId = carreraEstudianteId;
    }

    public ArrayList<InstanciaEstudiante> getInstanciasEstudiante() {
        return instanciasEstudiante;
    }

    public void setInstanciasEstudiante(ArrayList<InstanciaEstudiante> instanciasEstudiante) {
        this.instanciasEstudiante = instanciasEstudiante;
    }

    public PeriodoParticular getPeriodoParticularInicio() {
        return periodoParticularInicio;
    }

    public void setPeriodoParticularInicio(PeriodoParticular periodoParticularInicio) {
        this.periodoParticularInicio = periodoParticularInicio;
    }

    public Integer getCreditosExtra() {
        return creditosExtra;
    }

    public void setCreditosExtra(Integer creditosExtra) {
        this.creditosExtra = creditosExtra;
    }
}
