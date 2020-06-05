import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller{


    @FXML
    public Slider slider1 = new Slider();

    @FXML
    public Slider slider2 = new Slider();

    @FXML
    public Slider slider3 = new Slider();


    @FXML
    public Button btn;



    public Slider getSlider() {
        return slider1;
    }

    public void setSlider(Slider slider) {
        this.slider1 = slider;
    }



    @FXML
    public void inviaDati(){
        // inserire un controllo sui parametri appena inseriti
        // qui dentro andiamo a settare le variabili del virus
        System.out.println("Sto inviando i parametri della simulazione ... ");
        int infettivita = (int)getSlider().getValue();
        System.out.println(infettivita);
    }

}
