import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    public Stage window;

    BackgroundImage bi = new BackgroundImage(new Image("coronavirus.jpg"),null,null,null,new BackgroundSize(1000, 800, false, false, false, false));

    ParametriSimulazione ps = new ParametriSimulazione();

    // scena iniziale - Inserimento parametri

    private Label arenaHLabel = new Label("ArenaH");
    private TextField arenaH = new TextField();

    private Label arenaLLabel = new Label("ArenaL");
    private TextField arenaL = new TextField();

    private Label spostamentoLabel = new Label("Spostamento max");
    private TextField spostamento = new TextField();

    private HBox arenaBox = new HBox(arenaHLabel,arenaH,arenaLLabel,arenaL,spostamentoLabel,spostamento);

    private Button btnInvia = new Button("Inizia simulazione");


    private Label popolazioneLabel = new Label("Popolazione");
    private TextField popolazione = new TextField();
    private HBox popolaioneBox = new HBox(popolazioneLabel,popolazione);


    private Label velocitaLabel = new Label("Velocita");
    private TextField velocita = new TextField();
    private HBox velocitaBox = new HBox(velocitaLabel,velocita);


    private Label durataLabel = new Label("Durata");
    private TextField durata = new TextField();
    private HBox durataBox = new HBox(durataLabel,durata);


    private Label tamponeLabel = new Label("Tampone");
    private TextField tampone = new TextField();
    private HBox tamponeBox = new HBox(tamponeLabel,tampone);


    private Label risorseLabel = new Label("Risorse");
    private TextField risorse = new TextField();
    private HBox risorseBox = new HBox(risorseLabel,risorse);
    private final Tooltip tooltipRisorse = new Tooltip("R < 10 * costoTampone * popolazione , R < popolazione * durataMalattia");


    private Label infettivitaLabel = new Label("Infettività");
    private TextField infettivita = new TextField();
    private HBox infettivitaBox = new HBox(infettivitaLabel,infettivita);


    private Label sintomaticitaLabel = new Label("Sintomaticità");
    private TextField sintomaticita = new TextField();
    private HBox sintomaticitaBox = new HBox(sintomaticitaLabel,sintomaticita);


    private Label letalitaLabel = new Label("Letalità");
    private TextField letalita = new TextField();
    private HBox letalitaBox = new HBox(letalitaLabel,letalita);


    ToggleGroup toggleGroup = new ToggleGroup();
    private RadioButton strg1 = new RadioButton("Strategia 1");
    private final Tooltip tooltip1 = new Tooltip("Descrizione strategia 1");
    private RadioButton strg2 = new RadioButton("Strategia 2");
    private final Tooltip tooltip2 = new Tooltip("Descrizione strategia 2");
    private RadioButton strg3 = new RadioButton("Strategia 3");
    private final Tooltip tooltip3 = new Tooltip("Descrizione strategia 3");
    private RadioButton strg4 = new RadioButton("Strategia 4");
    private final Tooltip tooltip4 = new Tooltip("Descrizione strategia 4");
    private RadioButton selectedRadioButton = null;

    private HBox strategieBox = new HBox();

    private static Simulazione simulazione = null;

    private static Strategia strategia = null;

    public TextField getArenaH() {
        return arenaH;
    }

    public TextField getArenaL() {
        return arenaL;
    }

    public TextField getSpostamento() {
        return spostamento;
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

    public RadioButton getSelectedRadioButton() {
        return selectedRadioButton;
    }

    public void setStrategia(RadioButton r){

        switch (r.getId()){
            case "strategia1":
                strategia = new Strategia1();
                break;
            case "strategia2":
                strategia = new Strategia2();
                break;
            case "strategia3":
                strategia = new Strategia3();
                break;
            case "strategia4":
                strategia = new Strategia4();
                break;
        }
    }


    public static Strategia getStrategia() {
        return strategia;
    }


    public Alert alert = new Alert(Alert.AlertType.ERROR);

    private Scene sceneIniziale = null;

    private VBox vBox = new VBox();



    // scena intermedia - interrompi

    private Label fraseMid = new Label(" Sto simulando ...");

    private Button btnInterrompi = new Button("Interrompi simulazione");

    private Scene sceneMid = null;

    private VBox vBoxMid = null;





    // scena intermedia - finale

    private Label fraseFinale = new Label("Simulazione finita ! Vedi le statistiche ");

    private Button btnFinale = new Button("Vedi statistiche");

    private Scene sceneFinale = null;

    private VBox vBoxFinale = null;




    // scena finale - sceneChart

    NumberAxis xAxis = new NumberAxis();

    NumberAxis yAxis = new NumberAxis();

    LineChart stackedAreaChart = new LineChart(xAxis, yAxis);

    XYChart.Series morti = new XYChart.Series();

    XYChart.Series asintomatici = new XYChart.Series();

    XYChart.Series sintomatici = new XYChart.Series();

    XYChart.Series guariti = new XYChart.Series();

    private Scene sceneChart = null;

    private VBox vBoxChart = null;



    @Override
    public void start(Stage stage) throws Exception {

        window = stage;

        // scena iniziale - Inserimento parametri


        String stylesInviaBtn = "-fx-background-color : dodgerblue;" +
                "-fx-font-size: 20;" +
                "-fx-max-width: 200;" +
                "-fx-text-fill : white;";
        String stylesInterrompiBtn = "-fx-background-color : red;" +
                "-fx-font-size: 20;" +
                "-fx-max-width: 200;" +
                "-fx-text-fill: white";
        String stylesFinaleBtn = "-fx-background-color: limegreen;" +
                "-fx-font-size: 20;" +
                "-fx-max-width: 200;" +
                "-fx-text-fill: white;";


        btnInvia.setStyle(stylesInviaBtn);
        btnInterrompi.setStyle(stylesInterrompiBtn);
        btnFinale.setStyle(stylesFinaleBtn);

        setFontAndPadding(20.0, arenaHLabel,arenaLLabel,spostamentoLabel,popolazioneLabel,
                velocitaLabel,risorseLabel,durataLabel,tamponeLabel,infettivitaLabel,sintomaticitaLabel,letalitaLabel,strg1,strg2,strg3,strg4);
        setWidth(arenaH,arenaL,spostamento,popolazione,risorse,velocita,durata,tampone,infettivita,sintomaticita,letalita);

        risorse.setTooltip(tooltipRisorse);
        strg1.setId("strategia1");
        strg1.setTooltip(tooltip1);
        strg1.setToggleGroup(toggleGroup);
        strg2.setId("strategia2");
        strg2.setTooltip(tooltip2);
        strg2.setToggleGroup(toggleGroup);
        strg3.setId("strategia3");
        strg3.setTooltip(tooltip3);
        strg3.setToggleGroup(toggleGroup);
        strg4.setId("strategia4");
        strg4.setTooltip(tooltip4);
        strg4.setToggleGroup(toggleGroup);

        strategieBox.getChildren().addAll(strg1,strg2,strg3,strg4);

        setPosAndMargin(arenaBox,popolaioneBox,risorseBox,velocitaBox,tamponeBox,durataBox,infettivitaBox,sintomaticitaBox,letalitaBox,strategieBox);

        vBox.getChildren().addAll(arenaBox,popolaioneBox,risorseBox,velocitaBox,tamponeBox,durataBox,infettivitaBox,sintomaticitaBox,letalitaBox,strategieBox,btnInvia);

        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(new Background(bi));

        sceneIniziale = new Scene(vBox,1000,800);


        btnInvia.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                boolean bool;

                bool = inviaDati();

                boolean ret = true;

                if(bool){
                    window.setScene(sceneMid);
                    while (ret) {
                        ret = simulazione.run(1);
                    }
                    window.setScene(sceneFinale);
                }
            }
        });



        // scene intermedia - Interrompi

        vBoxMid = new VBox();

        vBoxMid.setAlignment(Pos.CENTER);

        sceneMid = new Scene(vBoxMid,1000,800);

        vBoxMid.getChildren().addAll(fraseMid,btnInterrompi);

        btnInterrompi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                window.setScene(sceneChart);
            }
        });


        // scena intermedia - Finale

        vBoxFinale = new VBox();

        vBoxFinale.setAlignment(Pos.CENTER);

        sceneFinale = new Scene(vBoxFinale,1000,800);

        vBoxFinale.getChildren().addAll(fraseFinale,btnFinale);

        btnFinale.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                window.setScene(sceneChart);
            }
        });


        // scena finale - Chart

        vBoxChart = new VBox(stackedAreaChart);

        vBoxChart.setAlignment(Pos.CENTER);

        sceneChart = new Scene(vBoxChart,1000,800);

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




        // Stage - inizialmente visualizziamo la scena iniziale di inserimento parametriì

        stage.setScene(sceneIniziale);
        stage.setTitle("Epidemiological simulator");
        stage.show();


    }

    public  void setFontAndPadding(double fontsize, Labeled... labels){
        for(Labeled labeled : labels) {
            labeled.setFont(new Font(20.0));
            labeled.setPadding(new Insets(20.0));
            labeled.setTextFill(Color.WHITE);
        }
    }

    public void setWidth(TextField... element){
        for(TextField node : element){
            node.setMaxWidth(100.0);
            node.setFont(new Font(16));
            node.setAlignment(Pos.CENTER);
        }
    }

    public void setPosAndMargin(HBox... elements){
        for(HBox element : elements){
            element.setAlignment(Pos.CENTER);
        }
    }


    public void addSeries(XYChart.Series series, ArrayList<Coppia> coppie){
        for(Coppia coppia : coppie){
            series.getData().add(new XYChart.Data(coppia.getY(),coppia.getX()));
        }
    }

    public boolean inviaDati() throws NumberFormatException{

        alert.setTitle("ERRORE");

        try{

            int arenaH_value = Integer.parseInt(getArenaH().getText());
            int arenaL_value = Integer.parseInt(getArenaL().getText());
            int spostamento_value = Integer.parseInt(getSpostamento().getText());
            int popolazione_value = Integer.parseInt(getPopolazione().getText());
            double velocita_value = Double.parseDouble(getVelocita().getText());
            int risorse_value = Integer.parseInt(getRisorse().getText());
            int tampone_value = Integer.parseInt(getTampone().getText());
            int durata_value = Integer.parseInt(getDurata().getText());
            int infettivita_value = Integer.parseInt(getInfettivita().getText());
            int sintomaticita_value = Integer.parseInt(getSintomaticita().getText());
            int letalita_value = Integer.parseInt(getLetalita().getText());

            selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();


            // vincoli sull'arena
            if(arenaH_value <= 0 || arenaL_value <= 0){
                alert.setContentText("La grandezza dell'arena non va bene");
                alert.show();
                return false;
            }

            if(spostamento_value <= 0){
                alert.setContentText("Lo spostamento massimo deve essere maggiore di 0");
                alert.show();
            }

            //  vincoli sulla popolazione
            if(popolazione_value <= 0){
                alert.setContentText("Almeno un abitante deve esistere ");
                alert.show();
                return false;
            }

            // vincoli sulle risorse
            if(risorse_value < 0 | risorse_value >= 10 * tampone_value * popolazione_value | risorse_value >= popolazione_value * durata_value){
                alert.setContentText("Le risorse non rispettano i vincoli, cambia il numero di risorse");
                alert.show();
                return false;
            }

            // vincoli sulla velocità
            if(velocita_value <= 0){
                alert.setContentText("La velocità deve essere maggiore di 0");
                alert.show();
                return false;
            }

            // vincoli sul costo del tampone
            if(tampone_value < 0){
                alert.setContentText("Il tampone non può costare meno di 0 risorse");
                alert.show();
                return false;
            }

            if(durata_value <= 0){
                alert.setContentText("Il virus deve durare almeno 1 giorno");
                alert.show();
                return false;
            }

            if(infettivita_value <= 0 | infettivita_value > 100){
                alert.setContentText("L'infettività non va bene");
                alert.show();
                return false;
            }

            if(sintomaticita_value <= 0 | sintomaticita_value > 100 ){
                alert.setContentText("La sintomaticità non va bene");
                alert.show();
                return false;
            }

            if(letalita_value <= 0 | letalita_value > 100){
                alert.setContentText("La letalità non va bene");
                alert.show();
                return false;
            }

        }catch (NumberFormatException e){
            alert.setContentText("Devi inserire valori numerici !!!");
            alert.show();
            return false;
        }

        if(selectedRadioButton == null){
            alert.setContentText("Devi selezionare una strategia");
            alert.show();
        }

        ps.setArenaH(Integer.parseInt(getArenaH().getText()));
        ps.setArenaL(Integer.parseInt(getArenaL().getText()));
        ps.setSpostamentoMax(Integer.parseInt(getSpostamento().getText()));
        ps.setPopolazione(Integer.parseInt(getPopolazione().getText()));
        ps.setRisorse(Integer.parseInt(getRisorse().getText()));
        ps.setVelocita(Double.parseDouble(getVelocita().getText()));
        ps.setCosto_tampone(Integer.parseInt(getTampone().getText()));
        ps.setDurata(Integer.parseInt(getDurata().getText()));
        ps.setInfettivita(Integer.parseInt(getInfettivita().getText()));
        ps.setSintomaticita(Integer.parseInt(getSintomaticita().getText()));
        ps.setLetalita(Integer.parseInt(getLetalita().getText()));
        setStrategia(selectedRadioButton);
        ps.setStrategia(strategia);

        simulazione = new Simulazione(ps);

        return true;

    }


    public static void main(String[] args) {
        launch(args); // come termino launch()
    }

}