package com.finduni.s21efip.views;

import com.finduni.s21efip.App;
import com.finduni.s21efip.entities.Perfil;
import com.finduni.s21efip.exposers.ExposingHomePerfilInfo;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

/**
 * HomePerfilInfoView: Vista usada para mostrar cada perfil en la vista Home
 * 
 * @author Francisco Induni
 */
public class HomePerfilInfoView extends ListCell<Perfil> {
    private Parent root;
    private ExposingHomePerfilInfo controller;
    
    /*
    * Inicializador
    */
    public HomePerfilInfoView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/finduni/s21efip/views/HomePerfilInfoView.fxml"));
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            System.out.println("Error en HomePerfilInfo: " + e);
            App.fatalError();
        }
    }
    
    @Override
    protected void updateItem(Perfil perfil, boolean empty) {
        super.updateItem(perfil, empty);
        if (empty || perfil == null) {
            setGraphic(null);
        } else {
            controller.initAttributes(perfil);
            setGraphic(root);
        }
    }
    
}
