package com.finduni.s21efip.views;

import com.finduni.s21efip.App;
import com.finduni.s21efip.entities.carrera.Carrera;
import com.finduni.s21efip.exposers.ExposingAdministrarCarrerasInfoCarrera;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

/**
 * AdministrarCarrerasInfoCarreraView: Vista usada para mostrar cada carrera en la vista Administrar Carreras
 * 
 * @author Francisco Induni
 */
public class AdministrarCarrerasInfoCarreraView extends ListCell<Carrera> {
    private Parent root;
    private ExposingAdministrarCarrerasInfoCarrera controller;
    
    /*
    * Inicializador
    */
    public AdministrarCarrerasInfoCarreraView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/finduni/s21efip/views/AdministrarCarrerasInfoCarreraView.fxml"));
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            System.out.println("Error en AdministrarCarrerasInfoCarreraView: " + e);
            App.fatalError();
        }
    }
    
    @Override
    protected void updateItem(Carrera carrera, boolean empty) {
        super.updateItem(carrera, empty);
        if (empty || carrera == null) {
            setGraphic(null);
        } else {
            controller.initAttributes(carrera);
            setGraphic(root);
        }
    }
    
}
