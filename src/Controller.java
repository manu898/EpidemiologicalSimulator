import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

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


    public Controller(){
        slider1.setShowTickMarks(true);
        slider1.setShowTickLabels(true);
        slider2.setShowTickMarks(true);
        slider2.setShowTickLabels(true);
        slider3.setShowTickMarks(true);
        slider3.setShowTickLabels(true);
    }



    public Slider getSlider() {
        return slider1;
    }

    public void setSlider(Slider slider) {
        this.slider1 = slider;
    }


    @FXML
    public void inviaDati(){
        // qui dentro andiamo a settare le variabili del virus
        System.out.println("Sto inviando i parametri della simulazione ... ");
        int infettivita = (int)getSlider().getValue();
        System.out.println(infettivita);
    }

}
