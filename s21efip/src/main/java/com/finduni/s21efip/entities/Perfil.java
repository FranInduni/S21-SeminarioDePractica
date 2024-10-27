package com.finduni.s21efip.entities;

import com.finduni.s21efip.entities.carrera.carreraestudiante.CarreraEstudiante;
import java.util.ArrayList;

/**
 * Perfil: Representa un perfil dentro del programa. Contiene el ID de perfil, el nombre y una lista con las carreras en las que el estudiante/usuario está anotado
 * 
 * @author Francisco Induni
 */
public class Perfil {
    private Integer perfilId;
    private String nombre;
    private ArrayList<CarreraEstudiante> carrerasInscriptas;
    
    /*
    * Inicializador
    */
    public Perfil(Integer perfilId, String nombre, ArrayList<CarreraEstudiante> carrerasInscriptas) {
        this.perfilId = perfilId;
        this.nombre = nombre;
        this.carrerasInscriptas = carrerasInscriptas;
    }
    
    /*
    * Getters y Setters
    */
    public Integer getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Integer perfilId) {
        this.perfilId = perfilId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<CarreraEstudiante> getCarrerasInscriptas() {
        return carrerasInscriptas;
    }

    public void setCarrerasInscriptas(ArrayList<CarreraEstudiante> carrerasInscriptas) {
        this.carrerasInscriptas = carrerasInscriptas;
    }
}
