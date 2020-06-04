import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller{


    @FXML
    public Slider slider1;

    @FXML
    public Slider slider2;

    @FXML
    public Slider slider3;





    public Slider getSlider() {
        return slider1;
    }

    public void setSlider(Slider slider) {
        this.slider1 = slider;
    }

    @FXML
    public void buttonClicked(){
        // qui dentro andiamo a settare le variabili del virus
        slider1.setShowTickMarks(true);
        slider1.setShowTickLabels(true);
        slider2.setShowTickMarks(true);
        slider2.setShowTickLabels(true);
        slider3.setShowTickMarks(true);
        slider3.setShowTickLabels(true);
        System.out.println((int)getSlider().getValue());
        System.out.println("Button clicked ! ");
    }

}
