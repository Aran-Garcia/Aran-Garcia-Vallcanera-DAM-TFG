// ARÁN GARCÍA VALLCANERA
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mongoDB.DatabaseManager;
import mongoDB.DialogMessage;
import simulation.Handler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;


/**
 * JavaFX App
 */

public class App extends Application {

    public static DatabaseManager dbManager;

    @Override
    public void start(Stage stage) throws IOException {
    	
    	//URL url = App.class.getResource("/fxml/navigation.fxml");
    	URL url = getClass().getResource("/fxml/navigation.fxml");
    	
        if (url == null) {
            throw new FileNotFoundException("No se encontró el archivo FXML");
        }
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(url);
    	//fxmlLoader.setClassLoader(getClass().getClassLoader());
    	Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
    	stage.setTitle("Numhammer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {    	
    	try {
    		dbManager = DatabaseManager.getInstance();    				
		} catch (Exception e) {
			DialogMessage.showAlert("Connection error", "Database connection fail. Database functions will be disabled.");
		}
    	
        launch(args);
    }
    
    @Override
    public void stop() {
    	try {
			super.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        Handler.shutdownDebouncers();
        dbManager.close();
        System.out.println("Aplicación detenida. Recursos liberados.");
    }

}