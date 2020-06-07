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
    public TextField popolazione;

    @FXML
    public TextField velocita;

    @FXML
    public TextField durata;

    @FXML
    public TextField tampone;

    @FXML
    public TextField risorse;

    @FXML
    public Slider infettivita;

    @FXML
    public Slider sintomaticita;

    @FXML
    public Slider letalita;

    @FXML
    public Button btn;


    public Alert alert = new Alert(AlertType.ERROR);


    public TextField getPopolazione() {
        return popolazione;
    }

    public TextField getDurata() {
        return durata;
    }

    public Slider getInfettivita() {
        return infettivita;
    }

    public Slider getSintomaticita() {
        return sintomaticita;
    }

    public Slider getLetalita() {
        return letalita;
    }

    public TextField getRisorse() {
        return risorse;
    }

    public TextField getTampone() {
        return tampone;
    }

    public TextField getVelocita() {
        return velocita;
    }


    @FXML
    public void inviaDati() throws NumberFormatException{
        alert.setTitle("ERRORE");
        try{
            double popolazione_value = Double.parseDouble(getPopolazione().getText());
            double velocita_value = Double.parseDouble(getVelocita().getText());
            double risorse_value = Double.parseDouble(getRisorse().getText());
            int tampone_value = Integer.parseInt(getTampone().getText());
            int durata_value = Integer.parseInt(getDurata().getText());

            //  vincoli sulla popolazione
            if(popolazione_value <= 0){
                alert.setContentText("Almeno un abitante deve esistere ");
                alert.show();
            }

            // vincoli sulle risorse
            if(risorse_value < 0 | risorse_value > 10 * tampone_value * popolazione_value | risorse_value > popolazione_value * durata_value){
                alert.setContentText("Le risorse non rispettano i vincoli, cambia il numero di risorse");
                alert.show();
            }

            // vincoli sulla velocità
            if(velocita_value <= 0){
                alert.setContentText("La velocità deve essere maggiore di 0");
                alert.show();
            }

            // vincoli sul costo del tampone
            if(tampone_value < 0){
                alert.setContentText("Il tampone non può costare meno di 0 risorse");
            }


        }catch (NumberFormatException e){
            alert.setContentText("Devi inserire valori numerici !!!");
            alert.show();
        }

        Virus.setI((int) getInfettivita().getValue());
        Virus.setS((int) getSintomaticita().getValue());
        Virus.setL((int) getLetalita().getValue());
        Virus.setD(Integer.parseInt(getDurata().getText()));

        // assegnare popolazione, risorse, costo tampone, velocità


    }

}
