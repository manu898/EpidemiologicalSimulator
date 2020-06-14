import com.sun.prism.Image;
import com.sun.tools.attach.VirtualMachine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.lang.Character.UnicodeBlock;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller{

    @FXML
    private TextField popolazione;

    @FXML
    private TextField velocita;

    @FXML
    private TextField durata;

    @FXML
    private TextField tampone;

    @FXML
    private TextField risorse;

    @FXML
    private TextField infettivita;

    @FXML
    private TextField sintomaticita;

    @FXML
    private TextField letalita;

    @FXML
    private Button btn;


    public Alert alert = new Alert(AlertType.ERROR);


    public TextField getPopolazione() {
        return popolazione;
    }

    public TextField getDurata() {
        return durata;
    }

    public TextField getInfettivita() {
        return infettivita;
    }

    public TextField getSintomaticita() {
        return sintomaticita;
    }

    public TextField getLetalita() {
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
            int popolazione_value = Integer.parseInt(getPopolazione().getText());
            double velocita_value = Double.parseDouble(getVelocita().getText());
            int risorse_value = Integer.parseInt(getRisorse().getText());
            int tampone_value = Integer.parseInt(getTampone().getText());
            int durata_value = Integer.parseInt(getDurata().getText());
            int infettivita_value = Integer.parseInt(getInfettivita().getText());
            int sintomaticita_value = Integer.parseInt(getSintomaticita().getText());
            int letalita_value = Integer.parseInt(getLetalita().getText());

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
                alert.show();
            }

            if(durata_value <= 0){
                alert.setContentText("Il virus deve durare almeno 1 giorno");
                alert.show();
            }

            if(infettivita_value <= 0 | infettivita_value > 100){
                alert.setContentText("L'infettività non va bene");
                alert.show();
            }

            if(sintomaticita_value <= 0 | sintomaticita_value > 100 ){
                alert.setContentText("La sintomaticità non va bene");
            }

            if(letalita_value <= 0 | letalita_value > 100){
                alert.setContentText("La letalità non va bene");
            }

        }catch (NumberFormatException e){
            alert.setContentText("Devi inserire valori numerici !!!");
            alert.show();
        }

        /*
        Universo.setPopolazione(Integer.parseInt(getPopolazione().getText()));
        Universo.setVelocita(Double.parseDouble(getVelocita().getText()));
        Governo.setCosto_tampone(Integer.parseInt(getTampone().getText()));
        Governo.setRisorse(Integer.parseInt(getRisorse().getText()));
        Virus.setI(Integer.parseInt(getInfettivita().getText()));
        Virus.setL(Integer.parseInt(getLetalita().getText()));
        Virus.setS(Integer.parseInt(getSintomaticita().getText()));
        Virus.setD(Integer.parseInt(getDurata().getText()));

        System.out.println(Virus.getI());
        System.out.println(Universo.getVelocita());

         */
    }

}
