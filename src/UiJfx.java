import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UiJfx extends Application {

    private Scene scene = null;

    private VBox vBox = new VBox();

    private Scene sceneChart = null;

    private VBox vBoxChart = null;

    private static Simulazione simulazioneVera = null;

    public Stage window;


    private boolean ret = true;

    // scene

    private Label arenaHLabel = new Label("ArenaH");
    private TextField arenaH = new TextField();

    private Label arenaLLabel = new Label("ArenaL");
    private TextField arenaL = new TextField();

    private Button btnInvia = new Button("Inizia simulazione");

    private Label popolazioneLabel = new Label("Popolazione");
    private TextField popolazione = new TextField();

    private Label velocitaLabel = new Label("Velocita");
    private TextField velocita = new TextField();

    private Label durataLabel = new Label("Durata");
    private TextField durata = new TextField();

    private Label tamponeLabel = new Label("Tampone");
    private TextField tampone = new TextField();

    private Label risorseLabel = new Label("Risorse");
    private TextField risorse = new TextField();

    private Label infettivitaLabel = new Label("Infettività");
    private TextField infettivita = new TextField();

    private Label sintomaticitaLabel = new Label("Sintomaticità");
    private TextField sintomaticita = new TextField();

    private Label letalitaLabel = new Label("Letalità");
    private TextField letalita = new TextField();


    public TextField getArenaH() {
        return arenaH;
    }

    public TextField getArenaL() {
        return arenaL;
    }

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

    public static void setSimulazione(Simulazione simulazione) {
        simulazioneVera = simulazione;
    }

    public Simulazione getSimulazione() {
        return simulazioneVera;
    }

    public Alert alert = new Alert(Alert.AlertType.ERROR);



    // sceneChart

    NumberAxis xAxis = new NumberAxis();

    NumberAxis yAxis = new NumberAxis();

    LineChart stackedAreaChart = new LineChart(xAxis, yAxis);

    XYChart.Series morti = new XYChart.Series();

    XYChart.Series asintomatici = new XYChart.Series();

    XYChart.Series sintomatici = new XYChart.Series();

    XYChart.Series guariti = new XYChart.Series();


    @Override
    public void start(Stage stage) throws Exception {
        window = stage;

        // fare un setStyleSheet riferito direttamente ai TF

        arenaHLabel.setFont(new Font(20));

        setWidthAndMargin(arenaH,arenaL,popolazione,risorse,velocita,durata,tampone,infettivita,sintomaticita,letalita,btnInvia);


        vBox.getChildren().addAll(arenaHLabel,arenaH,arenaLLabel,arenaL,popolazioneLabel,popolazione,risorseLabel,risorse,velocitaLabel,velocita,durataLabel,durata,tamponeLabel,tampone,
                infettivitaLabel,infettivita,sintomaticitaLabel,sintomaticita,letalitaLabel,letalita,btnInvia);

        btnInvia.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                inviaDati();
                while (ret){
                    ret = simulazioneVera.run(1);
                }
                window.setScene(sceneChart);
                window.setFullScreen(true);

            }
        });



        vBox.setAlignment(Pos.CENTER);
        scene = new Scene(vBox);

        // sceneChart

        xAxis.setLabel("Time");
        yAxis.setLabel("Total");

        morti.setName("MORTI");
        asintomatici.setName("ASINTOMATICI");
        sintomatici.setName("SINOTMATICI");
        guariti.setName("GUARITI");


        ArrayList<Coppia> guaritiTotali = new ArrayList<>();
        guaritiTotali.add(new Coppia(0, 300));
        guaritiTotali.add(new Coppia(1, 100));
        guaritiTotali.add(new Coppia(2, 600));

        ArrayList<Coppia> mortiTotali = new ArrayList<>();
        mortiTotali.add(new Coppia(0, 900));
        mortiTotali.add(new Coppia(1, 800));
        mortiTotali.add(new Coppia(2, 200));

        ArrayList<Coppia> asintomaticiTotali = new ArrayList<>();
        asintomaticiTotali.add(new Coppia(0, 1000));
        asintomaticiTotali.add(new Coppia(1,1200));
        asintomaticiTotali.add(new Coppia(2,1500));

        ArrayList<Coppia> sintomaticiTotali = new ArrayList<>();
        sintomaticiTotali.add(new Coppia(0, 700));
        sintomaticiTotali.add(new Coppia(1, 400));
        sintomaticiTotali.add(new Coppia(2, 300));


        addSeries(guariti, guaritiTotali);
        addSeries(morti, mortiTotali);
        addSeries(asintomatici, asintomaticiTotali);
        addSeries(sintomatici, sintomaticiTotali);


        stackedAreaChart.getData().addAll(morti,asintomatici,sintomatici,guariti);


        vBoxChart = new VBox(stackedAreaChart);

        vBoxChart.setAlignment(Pos.CENTER);
        sceneChart = new Scene(vBoxChart);


        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.setTitle("Prova bella");
        stage.show();


    }


    public void setWidthAndMargin(Region... element){
        for(Region node : element){
            node.setMaxWidth(100.0);
            VBox.setMargin(node, new Insets(20));
        }
    }

    public void addSeries(XYChart.Series series, ArrayList<Coppia> coppie){
        for(Coppia coppia : coppie){
            series.getData().add(new XYChart.Data(coppia.getY(),coppia.getX()));
        }
    }

    public void inviaDati() throws NumberFormatException{
        alert.setTitle("ERRORE");
        try{
            int arenaH_value = Integer.parseInt(getArenaH().getText());
            int arenaL_value = Integer.parseInt(getArenaL().getText());
            int popolazione_value = Integer.parseInt(getPopolazione().getText());
            double velocita_value = Double.parseDouble(getVelocita().getText());
            int risorse_value = Integer.parseInt(getRisorse().getText());
            int tampone_value = Integer.parseInt(getTampone().getText());
            int durata_value = Integer.parseInt(getDurata().getText());
            int infettivita_value = Integer.parseInt(getInfettivita().getText());
            int sintomaticita_value = Integer.parseInt(getSintomaticita().getText());
            int letalita_value = Integer.parseInt(getLetalita().getText());

            // vincoli sull'arena
            if(arenaH_value <= 0 || arenaL_value <= 0){
                alert.setContentText("La grandezza dell'arena non va bene");
                alert.show();
            }

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

        Virus.setI(Integer.parseInt(getInfettivita().getText()));
        Virus.setL(Integer.parseInt(getLetalita().getText()));
        Virus.setS(Integer.parseInt(getSintomaticita().getText()));
        Virus.setD(Integer.parseInt(getDurata().getText()));

        Governo governo = new Governo(Integer.parseInt(getRisorse().getText()), Integer.parseInt(getTampone().getText()));

        simulazioneVera = new Simulazione(governo,
                new Arena(Integer.parseInt(getArenaH().getText()),Integer.parseInt(getArenaL().getText())),
                Integer.parseInt(getPopolazione().getText()),
                Double.parseDouble(getVelocita().getText()));

    }


    public static void main(String[] args) {
        launch(args); // come termino launch()
    }

}
