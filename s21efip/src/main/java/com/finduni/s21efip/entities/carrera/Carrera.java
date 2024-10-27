package com.finduni.s21efip.entities.carrera;

import com.finduni.s21efip.entities.instancia.Instancia;
import java.util.ArrayList;

/**
 * Carrera: Representa una carrera universitaria.
 * Contiene el ID de la instancia, su nombre, modalidad, créditos requeridos de materias electivas, máximo de materias que se puede cursar en: Verano, periodo normal, periodo B (si no se cursó en A), primer periodo siendo ingresante.
 * También contiene una lista de instancias (materias e instancias evaluativas como EFIP) que pertenecen a la carrera. 
 * 
 * @author Francisco Induni
 */
public class Carrera {
    private Integer carreraId;
    private String nombre;
    private Modalidad modalidad;
    private Integer creditosElectivas;
    private Integer cuposVerano;
    private Integer cuposNormal;
    private Integer cuposSoloEnB;
    private Integer cuposIngresantes;
    private ArrayList<Instancia> instancias;
    
    /*
    * Inicializador
    */
    public Carrera(Integer carreraId, String nombre, Modalidad modalidad, Integer creditosElectivas, Integer cuposVerano, Integer cuposNormal, Integer cuposSoloEnB, Integer cuposIngresantes, ArrayList<Instancia> instancias) {
        this.carreraId = carreraId;
        this.nombre = nombre;
        this.modalidad = modalidad;
        this.creditosElectivas = creditosElectivas;
        this.cuposVerano = cuposVerano;
        this.cuposNormal = cuposNormal;
        this.cuposSoloEnB = cuposSoloEnB;
        this.cuposIngresantes = cuposIngresantes;
        this.instancias = instancias;
    }
    
    /*
    * Getters y Setters
    */
    public Integer getCarreraId() {
        return carreraId;
    }

    public void setCarreraId(Integer carreraId) {
        this.carreraId = carreraId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public Integer getCreditosElectivas() {
        return creditosElectivas;
    }

    public void setCreditosElectivas(Integer creditosElectivas) {
        this.creditosElectivas = creditosElectivas;
    }

    public Integer getCuposVerano() {
        return cuposVerano;
    }

    public void setCuposVerano(Integer cuposVerano) {
        this.cuposVerano = cuposVerano;
    }

    public Integer getCuposNormal() {
        return cuposNormal;
    }

    public void setCuposNormal(Integer cuposNormal) {
        this.cuposNormal = cuposNormal;
    }

    public Integer getCuposSoloEnB() {
        return cuposSoloEnB;
    }

    public void setCuposSoloEnB(Integer cuposSoloEnB) {
        this.cuposSoloEnB = cuposSoloEnB;
    }

    public Integer getCuposIngresantes() {
        return cuposIngresantes;
    }

    public void setCuposIngresantes(Integer cuposIngresantes) {
        this.cuposIngresantes = cuposIngresantes;
    }

    public ArrayList<Instancia> getInstancias() {
        return instancias;
    }

    public void setInstancias(ArrayList<Instancia> instancias) {
        this.instancias = instancias;
    }
}
