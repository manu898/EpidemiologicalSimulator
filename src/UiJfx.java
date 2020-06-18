import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class UiJfx extends Application {

    public Stage window;

    // scena iniziale - Inserimento parametri

    private Label arenaHLabel = new Label("ArenaH");
    private TextField arenaH = new TextField();

    private Label arenaLLabel = new Label("ArenaL");
    private TextField arenaL = new TextField();

    private HBox arenaBox = new HBox(arenaHLabel,arenaH,arenaLLabel,arenaL);

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
    private final Tooltip tooltipRisorse = new Tooltip("Le risorse devono rispettare i seguenti vincoli");


    private Label infettivitaLabel = new Label("Infettività");
    private TextField infettivita = new TextField();
    private HBox infettivitaBox = new HBox(infettivitaLabel,infettivita);


    private Label sintomaticitaLabel = new Label("Sintomaticità");
    private TextField sintomaticita = new TextField();
    private HBox sintomaticitaBox = new HBox(sintomaticitaLabel,sintomaticita);


    private Label letalitaLabel = new Label("Letalità");
    private TextField letalita = new TextField();
    private HBox letalitaBox = new HBox(letalitaLabel,letalita);

    private RadioButton strg1 = new RadioButton("Strategia 1");
    private RadioButton strg2 = new RadioButton("Strategia 2");
    private RadioButton strg3 = new RadioButton("Strategia 3");
    private RadioButton strg4 = new RadioButton("Strategia 4");
    private RadioButton selected = null;

    ToggleGroup toggleGroup = new ToggleGroup();

    private HBox strategieBox = new HBox();

    private static Simulazione simulazioneVera = null;

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

    public Alert alert = new Alert(Alert.AlertType.ERROR);

    private Scene sceneIniziale = null;

    private VBox vBox = new VBox();

    private final FlowPane container = new FlowPane();

    private ScrollPane sc = new ScrollPane();




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


        btnInvia.setTooltip(tooltipRisorse);


        setFont(20.0, arenaHLabel,arenaLLabel,popolazioneLabel,
                velocitaLabel,risorseLabel,durataLabel,tamponeLabel,infettivitaLabel,sintomaticitaLabel,letalitaLabel);

        setWidth(arenaH,arenaL,popolazione,risorse,velocita,durata,tampone,infettivita,sintomaticita,letalita,btnInvia);

        strg1.setToggleGroup(toggleGroup);
        strg2.setToggleGroup(toggleGroup);
        strg3.setToggleGroup(toggleGroup);
        strg4.setToggleGroup(toggleGroup);

        strategieBox.getChildren().addAll(strg1,strg2,strg3,strg4);

        setPosAndMargin(arenaBox,popolaioneBox,risorseBox,velocitaBox,tamponeBox,durataBox,infettivitaBox,sintomaticitaBox,letalitaBox,strategieBox);



        /*vBox.getChildren().addAll(arenaLLabel,arenaL,arenaHLabel,arenaH,popolazioneLabel,popolazione,risorseLabel,risorse,velocitaLabel,velocita,
                tamponeLabel,tampone,durataLabel,durata,
                infettivitaLabel,infettivita,sintomaticitaLabel,sintomaticita,letalitaLabel,letalita,btnInvia);*/

        vBox.getChildren().addAll(arenaBox,popolaioneBox,risorseBox,velocitaBox,tamponeBox,durataBox,infettivitaBox,sintomaticitaBox,letalitaBox,strategieBox,btnInvia);

        vBox.setAlignment(Pos.CENTER);

        sceneIniziale = new Scene(vBox);


        btnInvia.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                boolean bool;

                bool = inviaDati();

                window.setScene(sceneMid);

                window.setFullScreen(true);

                boolean ret = true;

                if(bool){
                    while (ret) {
                        ret = simulazioneVera.run(1);
                    }
                    window.setScene(sceneFinale);
                    window.setFullScreen(true);
                }
            }
        });



        // scene intermedia - Interrompi

        vBoxMid = new VBox();

        vBoxMid.setAlignment(Pos.CENTER);

        sceneMid = new Scene(vBoxMid);

        vBoxMid.getChildren().addAll(fraseMid,btnInterrompi);

        btnInterrompi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                window.setScene(sceneChart);
                window.setFullScreen(true);
            }
        });




        // scena intermedia - Finale

        vBoxFinale = new VBox();

        vBoxFinale.setAlignment(Pos.CENTER);

        sceneFinale = new Scene(vBoxFinale);

        vBoxFinale.getChildren().addAll(fraseFinale,btnFinale);

        btnFinale.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                window.setScene(sceneChart);
                window.setFullScreen(true);
            }
        });


        // scena finale - Chart

        vBoxChart = new VBox(stackedAreaChart);

        vBoxChart.setAlignment(Pos.CENTER);

        sceneChart = new Scene(vBoxChart);

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

        stage.setFullScreen(true);
        stage.setScene(sceneIniziale);
        stage.setTitle("Epidemiological simulator");
        stage.show();


    }

    public  void setFont(double fontsize, Label... labels){
        for(Label label : labels)
            label.setFont(new Font(20.0));
    }

    public void setWidth(Region... element){
        for(Region node : element){
            node.setMaxWidth(100.0);
        }
    }

    public void setPosAndMargin(HBox... elements){
        for(HBox element : elements){
            element.setAlignment(Pos.CENTER);
            VBox.setMargin(element, new Insets(10.0));
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
            int popolazione_value = Integer.parseInt(getPopolazione().getText());
            double velocita_value = Double.parseDouble(getVelocita().getText());
            int risorse_value = Integer.parseInt(getRisorse().getText());
            int tampone_value = Integer.parseInt(getTampone().getText());
            int durata_value = Integer.parseInt(getDurata().getText());
            int infettivita_value = Integer.parseInt(getInfettivita().getText());
            int sintomaticita_value = Integer.parseInt(getSintomaticita().getText());
            int letalita_value = Integer.parseInt(getLetalita().getText());

            selected = (RadioButton) toggleGroup.getSelectedToggle();


            // vincoli sull'arena
            if(arenaH_value <= 0 || arenaL_value <= 0){
                alert.setContentText("La grandezza dell'arena non va bene");
                alert.show();
                return false;
            }

            //  vincoli sulla popolazione
            if(popolazione_value <= 0){
                alert.setContentText("Almeno un abitante deve esistere ");
                alert.show();
                return false;
            }

            // vincoli sulle risorse
            if(risorse_value < 0 | risorse_value > 10 * tampone_value * popolazione_value | risorse_value > popolazione_value * durata_value){
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

        Virus.setI(Integer.parseInt(getInfettivita().getText()));
        Virus.setL(Integer.parseInt(getLetalita().getText()));
        Virus.setS(Integer.parseInt(getSintomaticita().getText()));
        Virus.setD(Integer.parseInt(getDurata().getText()));

        Governo governo = new Governo(Integer.parseInt(getRisorse().getText()), Integer.parseInt(getTampone().getText()));

        simulazioneVera = new Simulazione(governo,
                new Arena(Integer.parseInt(getArenaH().getText()),Integer.parseInt(getArenaL().getText())),
                Integer.parseInt(getPopolazione().getText()),
                Double.parseDouble(getVelocita().getText()));

        System.out.println(selected);

        return true;

    }


    public static void main(String[] args) {
        launch(args); // come termino launch()
    }

}
