import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller{


    @FXML
    public Slider slider;


    public Slider getSlider() {
        return slider;
    }

    public void setSlider(Slider slider) {
        this.slider = slider;
    }

    @FXML
    public void buttonClicked(){
        // qui dentro andiamo a settare le variabili del virus
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        System.out.println((int)getSlider().getValue());
        System.out.println("Button clicked ! ");
    }

}
