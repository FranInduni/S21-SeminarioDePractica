package com.finduni.s21efip;

import com.finduni.s21efip.databaseinterface.DatabaseInterface;
import com.finduni.s21efip.exceptions.DBConnError;
import com.finduni.s21efip.exceptions.InvalidOrNullParam;
import com.finduni.s21efip.handlers.HandlingCarrera;
import com.finduni.s21efip.handlers.HandlingInstancia;
import com.finduni.s21efip.handlers.HandlingPerfil;
import com.finduni.s21efip.providers.ProvidingCarrera;
import com.finduni.s21efip.providers.ProvidingInstancia;
import com.finduni.s21efip.providers.ProvidingPerfil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App: Controlador principal del programa, el punto de entrada
 * 
 * @author Francisco Induni
 */
public class App extends Application {
    
    private static Stage stage;
    private static Scene scene;
    // Se instanciará el DatabaseInterface y los Handlers y Providers una sola vez para todo el programa
    // Siempre que otra parte del programa (fuera de esta clase App) requiera utilizar alguna de estas instancias las obtendrá de acá en lugar de inicializarlas nuevamente
    private static DatabaseInterface databaseInterface;
    private static HandlingInstancia handlingInstancia;
    private static HandlingCarrera handlingCarrera;
    private static HandlingPerfil handlingPerfil;
    private static ProvidingInstancia providingInstancia;
    private static ProvidingCarrera providingCarrera;
    private static ProvidingPerfil providingPerfil;
    
    public static void main(String[] args) {
        launch();
    }

    /*
    * Punto de entrada del programa.
    */
    @Override
    public void start(Stage originalStage) {
        stage = originalStage;
        stage.setOnCloseRequest(event -> { // Cuando se cierre la ventana principal del programa se cerrará la conexión a la DB
            try {
                if (databaseInterface != null) {
                    databaseInterface.closeConn();
                }
            } catch (Exception e) {}
        });
        
        try {
            // Se inicializan por única vez el DatabaseInterface y los handlers y providers del programa
            databaseInterface = new DatabaseInterface();
            handlingInstancia = new HandlingInstancia(databaseInterface);
            handlingCarrera = new HandlingCarrera(databaseInterface, handlingInstancia);
            handlingPerfil = new HandlingPerfil(databaseInterface);
            providingInstancia = new ProvidingInstancia(handlingInstancia);
            providingCarrera = new ProvidingCarrera(handlingCarrera);
            providingPerfil = new ProvidingPerfil(handlingPerfil);
        } catch (DBConnError | InvalidOrNullParam e) {
            fatalError();
            return;
        }
        
        stage.setTitle("Sistema de Gestión Universitaria");
        goHome();
    }
    
    /*
    *******************
    * Métodos públicos
    *******************
    */
    
    /*
    * Muestra en la ventana principal la vista Home
    */
    public static void goHome() {
        try {
            changeScene("views/HomeView");
        } catch (IOException e) {
            System.out.println("Error al cambiar de escena al Home: " + e);
            fatalError();
        }
    }
    
    /*
    * Muestra en la ventana principal la vista AdministrarCarreras
    */
    public static void goToAdministrarCarreras() {
        try {
            changeScene("views/AdministrarCarrerasView");
        } catch (IOException e) {
            System.out.println("Error al cambiar de escena a Administrar Carreras: " + e);
            fatalError();
        }
    }
    
    /*
    * Muestra en la ventana principal la vista CrearCarrera
    */
    public static void goToCrearCarrera() {
        try {
            changeScene("views/CrearCarreraView");
        } catch (IOException e) {
            System.out.println("Error al cambiar de escena a Crear Carrera: " + e);
            fatalError();
        }
    }
    
    /*
    * Muestra en la ventana principal un mensaje de error fatal. Esta vista solo muestra el mensaje y la única opción disponible es cerrar el programa
    */
    public static void fatalError() {
        try {
            changeScene("views/FatalErrorView");
        } catch (IOException e) {
            System.out.println("Error al cambiar de escena a fatal Error: " + e);
        }
    }
    
    /*
    * Devuelve la instancia de DatabaseInterface
    */
    public static DatabaseInterface getDatabaseInterface() {
        return databaseInterface;
    }
    
    /*
    * Devuelve la instancia de HandlingInstancia
    */
    public static HandlingInstancia getHandlingInstancia() {
        return handlingInstancia;
    }
    
    /*
    * Devuelve la instancia de HandlingCarrera
    */
    public static HandlingCarrera getHandlingCarrera() {
        return handlingCarrera;
    }
    
    /*
    * Devuelve la instancia de HandlingPerfil
    */
    public static HandlingPerfil getHandlingPerfil() {
        return handlingPerfil;
    }
    
    /*
    * Devuelve la instancia de ProvidingInstancia
    */
    public static ProvidingInstancia getProvidingInstancia() {
        return providingInstancia;
    }
    
    /*
    * Devuelve la instancia de ProvidingCarrera
    */
    public static ProvidingCarrera getProvidingCarrera() {
        return providingCarrera;
    }
    
    /*
    * Devuelve la instancia de ProvidingPerfil
    */
    public static ProvidingPerfil getProvidingPerfil() {
        return providingPerfil;
    }
    
    /*
    *******************
    * Métodos privados
    *******************
    */
    
    /*
    * Cambia la escena del stage principal del programa dado el nombre de la vista
    */
    private static void changeScene(String fxml) throws IOException {
        scene = new Scene(loadFXML(fxml), 1280, 720);
        stage.setScene(scene);
        stage.show();
    }
    
    /*
    * Carga la vista dado su nombre
    */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

}