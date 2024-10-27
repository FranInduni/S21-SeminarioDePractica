package com.finduni.s21efip.entities;

/**
 * Periodo Particular: Contiene el sub/periodo del año lectivo y un año. Usado para indicar en que periodo particular se inscribió el alumno a una carrera o instancia
 * 
 * @author Francisco Induni
 */
public class PeriodoParticular {
    private Integer year;
    private Periodo periodo;
    
    public PeriodoParticular(Integer year, Periodo periodo) {
        this.year = year;
        this.periodo = periodo;
    }
    
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }
}
