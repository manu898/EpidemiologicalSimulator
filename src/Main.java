import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.*;

import java.util.ArrayList;

public class Main extends Application {

    // parametri dello stage

    public Stage window;

    BackgroundImage bi = new BackgroundImage(new Image("coronavirus.jpg"),null,null,null,new BackgroundSize(1000, 800, false, false, false, false));

    public Alert alert = new Alert(Alert.AlertType.ERROR);

    // parametri per  gestione/creazione della simulazione

    ParametriSimulazione ps = new ParametriSimulazione();

    private static Simulazione simulazione = null;

    private static Strategia strategia = null;

    private DatiStatistici statistiche = new DatiStatistici();

    //public? private?
    boolean return_from_simulazione = true;

    //public? private?
    String filecsv = "filemagnifico.csv";


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
    private HBox popolazioneBox = new HBox(popolazioneLabel,popolazione);


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
    private final Tooltip tooltip1 = new Tooltip("Non viene fatto alcun tampone e non viene fermato nessuno");
    private RadioButton strg2 = new RadioButton("Strategia 2");
    private final Tooltip tooltip2 = new Tooltip("Si fa il tampone alla metà della popolazione (scelti randomicamente) e i positivi vengono fermati");
    private RadioButton strg3 = new RadioButton("Strategia 3");
    private final Tooltip tooltip3 = new Tooltip("Si fa il tampone a tutte le persone incontrate dai sintomatici\ngiorno per giorno e se positivi si fermano");
    private RadioButton strg4 = new RadioButton("Strategia 4");
    private final Tooltip tooltip4 = new Tooltip("Si fa il tampone a tutte le persone incontrate durante la simulazione\ndagli individui risultanti sintomatici giorno per giorno e\nse positivi si fermano");
    private RadioButton selectedRadioButton = null;
    private HBox strategieBox = new HBox();

    // scena iniziale - parametri

    private VBox vBox = new VBox();
    private Scene sceneIniziale = new Scene(vBox,1000,800);

    // getter

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


    // setter

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



    // scena intermedia - interrompi

    private Label fraseMid = new Label(" Sto simulando ...");

    private Button btnInterrompi = new Button("Interrompi simulazione");

    private VBox vBoxMid = new VBox();

    private Scene sceneMid = new Scene(vBoxMid,1000 ,800 );

    private boolean interrompi = false;


    // scena intermedia 2 - hai premuto interrompi

    private VBox vBoxMid2 = new VBox();

    private Scene sceneMid2 = new Scene(vBoxMid2,1000 ,800 ) ;

    private Label fraseMid2 = new Label(" Sto interrompendo ... aspetta 5 ore dai !");


    // scena finale

    private VBox vBoxFinale = new VBox();

    private Scene sceneFinale = new Scene(vBoxFinale,1000 ,800 );

    private Label fraseFinale = new Label("Simulazione terminata");

    private Button btnFinale = new Button("Vedi statistiche");


    // scena grafico - parametri generali

    private VBox vBoxChart = new VBox();

    private Scene sceneChart = new Scene(vBoxChart,1000 ,800 ) ;

    private Button btnNewSimulation = new Button("Nuova simulazione");


    // scena grafico - grafico Governo

    NumberAxis xAxisGoverno = new NumberAxis();

    NumberAxis yAxisGoverno = new NumberAxis();

    LineChart lineChartGoverno = new LineChart(xAxisGoverno, yAxisGoverno);

    XYChart.Series mortiGoverno = new XYChart.Series();

    XYChart.Series asintomaticiGoverno = new XYChart.Series();

    XYChart.Series sintomaticiGoverno = new XYChart.Series();

    XYChart.Series guaritiGoverno = new XYChart.Series();

    XYChart.Series verdiGoverno = new XYChart.Series();


    // scena grafico - grafico Simulazione

    NumberAxis xAxisSimulazione = new NumberAxis();

    NumberAxis yAxisSimulazione = new NumberAxis();

    LineChart lineChartSimulazione = new LineChart(xAxisSimulazione, yAxisSimulazione);

    XYChart.Series mortiSimulazione = new XYChart.Series();

    XYChart.Series asintomaticiSimulazione = new XYChart.Series();

    XYChart.Series sintomaticiSimulazione = new XYChart.Series();

    XYChart.Series guaritiSimulazione = new XYChart.Series();

    XYChart.Series verdiSimulazione = new XYChart.Series();


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
            if(risorse_value < 0 || risorse_value >= 10 * tampone_value * popolazione_value || risorse_value >= popolazione_value * durata_value){
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

            if(infettivita_value <= 0 || infettivita_value > 100){
                alert.setContentText("L'infettività non va bene");
                alert.show();
                return false;
            }

            if(sintomaticita_value <= 0 || sintomaticita_value > 100 ){
                alert.setContentText("La sintomaticità non va bene");
                alert.show();
                return false;
            }

            if(letalita_value <= 0 || letalita_value > 100){
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



    @Override
    public void start(Stage stage) throws Exception {

        window = stage;

        // CSS

        sceneIniziale.getStylesheets().add("stylesheets/style.css");
        sceneMid.getStylesheets().add("stylesheets/style.css");
        sceneMid2.getStylesheets().add("stylesheets/style.css");
        sceneFinale.getStylesheets().add("stylesheets/style.css");
        sceneChart.getStylesheets().add("stylesheets/style.css");


        btnInvia.setId("btnInvia");
        btnInvia.getStyleClass().add("btn");
        btnInterrompi.setId("btnInterrompi");
        btnInterrompi.getStyleClass().add("btn");
        btnFinale.setId("btnFinale");
        btnFinale.getStyleClass().add("btn");
        btnNewSimulation.setId("btnNewSimulation");
        btnNewSimulation.getStyleClass().add("btn");
        fraseMid.getStyleClass().add("frase");
        fraseMid2.getStyleClass().add("frase");
        fraseFinale.getStyleClass().add("frase");


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

        setPosAndMargin(arenaBox,popolazioneBox,risorseBox,velocitaBox,tamponeBox,durataBox,infettivitaBox,sintomaticitaBox,letalitaBox,strategieBox);

        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(new Background(bi));


        // scena iniziale


        vBox.getChildren().addAll(arenaBox,popolazioneBox,risorseBox,velocitaBox,tamponeBox,durataBox,infettivitaBox,sintomaticitaBox,letalitaBox,strategieBox,btnInvia);


        // scene intermedia1 e intermedia2 - Interrompi


        vBoxMid.setAlignment(Pos.CENTER);
        vBoxMid2.setAlignment(Pos.CENTER);

        vBoxMid.getChildren().addAll(fraseMid,btnInterrompi);
        vBoxMid2.getChildren().addAll(fraseMid2);


        btnInterrompi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                interrompi = true;
                window.setScene(sceneMid2);
            }
        });


        // scena finale

        vBoxFinale.setAlignment(Pos.CENTER);

        vBoxFinale.getChildren().addAll(fraseFinale,btnFinale);

        btnNewSimulation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                window.close();
                Platform.runLater(() -> {
                    try {
                        new Main().start(new Stage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });


        // Chart Governo

        vBoxChart.getChildren().addAll(lineChartGoverno,lineChartSimulazione,btnNewSimulation);

        vBoxChart.setAlignment(Pos.CENTER);

        lineChartGoverno.setTitle("Governo");

        xAxisGoverno.setLabel("Time");
        yAxisGoverno.setLabel("Total");

        xAxisGoverno.setAutoRanging(false);
        xAxisGoverno.setLowerBound(1);
        xAxisGoverno.setTickUnit(1);
        xAxisGoverno.setMinorTickVisible(true);

        yAxisGoverno.setAutoRanging(false);
        yAxisGoverno.setLowerBound(0);
        yAxisGoverno.setMinorTickVisible(true);

        mortiGoverno.setName("MORTI");
        asintomaticiGoverno.setName("ASINTOMATICI");
        sintomaticiGoverno.setName("SINTOMATICI");
        guaritiGoverno.setName("GUARITI");
        verdiGoverno.setName("NON MALATI");


        ArrayList<Coppia> guaritiGovernoSeries = new ArrayList<>();

        ArrayList<Coppia> mortiGovernoSeries = new ArrayList<>();

        ArrayList<Coppia> asintomaticiGovernoSeries = new ArrayList<>();

        ArrayList<Coppia> sintomaticiGovernoSeries = new ArrayList<>();

        ArrayList<Coppia> verdiGovernoSeries = new ArrayList<>();


        lineChartGoverno.getData().addAll(mortiGoverno,asintomaticiGoverno,sintomaticiGoverno,guaritiGoverno,verdiGoverno);


        // Chart Simulazione


        lineChartSimulazione.setTitle("Simulazione");

        xAxisSimulazione.setLabel("Time");
        yAxisSimulazione.setLabel("Total");

        xAxisSimulazione.setAutoRanging(false);
        xAxisSimulazione.setLowerBound(1);
        xAxisSimulazione.setTickUnit(1);
        xAxisSimulazione.setMinorTickVisible(false);

        yAxisSimulazione.setAutoRanging(false);
        yAxisSimulazione.setLowerBound(0);
        yAxisSimulazione.setMinorTickVisible(false);

        mortiSimulazione.setName("MORTI");
        asintomaticiSimulazione.setName("ASINTOMATICI");
        sintomaticiSimulazione.setName("SINTOMATICI");
        guaritiSimulazione.setName("GUARITI");
        verdiSimulazione.setName("NON MALATI");

        ArrayList<Coppia> guaritiSimulazioneSeries = new ArrayList<>();

        ArrayList<Coppia> mortiSimulazioneSeries = new ArrayList<>();

        ArrayList<Coppia> asintomaticiSimulazioneSeries = new ArrayList<>();

        ArrayList<Coppia> sintomaticiSimulazioneSeries = new ArrayList<>();

        ArrayList<Coppia> verdiSimulazioneSeries = new ArrayList<>();

        lineChartSimulazione.getData().addAll(mortiSimulazione,asintomaticiSimulazione,sintomaticiSimulazione,guaritiSimulazione,verdiSimulazione);

        btnInvia.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                boolean bool;

                bool = inviaDati();

                Thread simula = new Thread() {

                    public void run() {
                        while (return_from_simulazione && !interrompi) {
                            System.out.println("GIORNO " + simulazione.getGiorno().getValore());
                            return_from_simulazione = simulazione.run(1);

                            statistiche = simulazione.getDati();

                            if (return_from_simulazione){
                                int giorno_passato = simulazione.getGiorno().getValore() - 1;
                                //System.out.println("Giorno " + (giorno_passato));
                                System.out.println("DATI FINE GIORNATA");
                                System.out.println("Risorse rimaste: " + statistiche.risorseRimaste.get(giorno_passato - 1));
                                System.out.println("Morti: " + statistiche.morti.get(giorno_passato - 1));
                                System.out.println("Sintomatici: " + statistiche.sintomatici.get(giorno_passato - 1));
                                System.out.println("AsintomaticiGov: " + statistiche.asintomaticiGoverno.get(giorno_passato - 1));
                                System.out.println("Asintomatici simulazione: " + statistiche.asintomaticiSimulazione.get(giorno_passato - 1));
                                System.out.println("GuaritiGov: " + statistiche.guaritiGoverno.get(giorno_passato - 1));
                                System.out.println("Guariti simulazione: " + statistiche.guaritiSimulazione.get(giorno_passato - 1));
                                System.out.println("VerdiGov: " + statistiche.verdiGoverno.get(giorno_passato - 1));
                                System.out.println("Verdi simulazione: " + simulazione.getVerdi());
                                System.out.println("Verdi sani simulazione: " + simulazione.getVerdi_sani());
                                System.out.println(statistiche.risultato.get(giorno_passato - 1));
                                System.out.println();
                                System.out.println();
                            }

                        }

                        int giorno_passato = simulazione.getGiorno().getValore();
                        System.out.println("DATI FINE GIORNATA");
                        System.out.println("Risorse rimaste: " + statistiche.risorseRimaste.get(giorno_passato-1));
                        System.out.println("Morti: " + statistiche.morti.get(giorno_passato-1));
                        System.out.println("Sintomatici: " + statistiche.sintomatici.get(giorno_passato-1));
                        System.out.println("AsintomaticiGov: " + statistiche.asintomaticiGoverno.get(giorno_passato-1));
                        System.out.println("Asintomatici simulazione: " + statistiche.asintomaticiSimulazione.get(giorno_passato - 1));
                        System.out.println("GuaritiGov: " +  statistiche.guaritiGoverno.get(giorno_passato-1));
                        System.out.println("Guariti simulazione: " + statistiche.guaritiSimulazione.get(giorno_passato - 1));
                        System.out.println("VerdiGov: " + statistiche.verdiGoverno.get(giorno_passato-1));
                        System.out.println("Verdi simulazione: " + simulazione.getVerdi());
                        System.out.println("Verdi sani simulazione: " + simulazione.getVerdi_sani());

                        System.out.println(statistiche.risultato.get(giorno_passato-1));
                        System.out.println();
                        System.out.println();

                        // Mi serve sapere l'ultimo giorno della simulazione dato che in caso di interruzione è minore di giorniSimulazione : basta chiedere la lunghezza degli arraylist
                        //statistiche = simulazione.getDati(); // TEST ritorna le statistiche della simulazione



                        Platform.runLater(new Runnable() {
                            public void run() {
                                String risultato = statistiche.risultato.get(statistiche.risultato.size() - 1);
                                fraseFinale.setText(risultato);
                                window.setScene(sceneFinale);
                            }

                        });
                        //String risultato = statistiche.risultato.get(statistiche.risultato.size() - 1);
                        //fraseFinale.setText(risultato);
                    }
                };

                if(bool){
                    window.setScene(sceneMid);
                    simula.start();
                }
            }

        });

        btnFinale.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                xAxisGoverno.setUpperBound(statistiche.sintomatici.size());
                yAxisGoverno.setUpperBound(Double.parseDouble(getPopolazione().getText()));
                yAxisGoverno.setTickUnit(Double.parseDouble(getPopolazione().getText()) * 0.05);


                xAxisSimulazione.setUpperBound(statistiche.sintomatici.size());
                yAxisSimulazione.setUpperBound(Double.parseDouble(getPopolazione().getText()));
                yAxisSimulazione.setTickUnit(Double.parseDouble(getPopolazione().getText()) * 0.05);

                for(int i = 0; i < statistiche.sintomatici.size(); i++){
/*
                    System.out.println("Giorno " + (i+1));
                    System.out.println("Risorse rimaste: " + statistiche.risorseRimaste.get(i));
                    System.out.println("Morti: " + statistiche.morti.get(i));
                    System.out.println("Sintomatici: " + statistiche.sintomatici.get(i));
                    System.out.println("AsintomaticiGov: " + statistiche.asintomaticiGoverno.get(i));
                    System.out.println("GuaritiGov: " +  statistiche.guaritiGoverno.get(i));
                    System.out.println("VerdiGov: " + statistiche.verdiGoverno.get(i));
                    System.out.println(statistiche.risultato.get(i));
                    System.out.println();
                    System.out.println();
*/
                    mortiGovernoSeries.add(new Coppia(i+1,statistiche.morti.get(i)));
                    sintomaticiGovernoSeries.add(new Coppia(i+1,statistiche.sintomatici.get(i)));
                    asintomaticiGovernoSeries.add(new Coppia(i+1,statistiche.asintomaticiGoverno.get(i)));
                    guaritiGovernoSeries.add(new Coppia(i+1,statistiche.guaritiGoverno.get(i)));
                    verdiGovernoSeries.add(new Coppia(i+1, statistiche.verdiGoverno.get(i)));


                    mortiSimulazioneSeries.add(new Coppia(i+1,statistiche.morti.get(i)));
                    sintomaticiSimulazioneSeries.add(new Coppia(i+1,statistiche.sintomatici.get(i)));
                    asintomaticiSimulazioneSeries.add(new Coppia(i+1,statistiche.asintomaticiSimulazione.get(i)));
                    guaritiSimulazioneSeries.add(new Coppia(i+1,statistiche.guaritiSimulazione.get(i)));
                    verdiSimulazioneSeries.add(new Coppia(i+1, statistiche.verdiSimulazione.get(i)));

                }

                addSeries(guaritiGoverno, guaritiGovernoSeries);
                addSeries(mortiGoverno, mortiGovernoSeries);
                addSeries(asintomaticiGoverno, asintomaticiGovernoSeries);
                addSeries(sintomaticiGoverno, sintomaticiGovernoSeries);
                addSeries(verdiGoverno, verdiGovernoSeries);

                addSeries(guaritiSimulazione, guaritiSimulazioneSeries);
                addSeries(mortiSimulazione, mortiSimulazioneSeries);
                addSeries(asintomaticiSimulazione, asintomaticiSimulazioneSeries);
                addSeries(sintomaticiSimulazione, sintomaticiSimulazioneSeries);
                addSeries(verdiSimulazione, verdiSimulazioneSeries);

                window.setScene(sceneChart);

                //creazione del fil csv
                PrintWriter pw = null;
                File f = new File(filecsv);
                try {
                    pw = new PrintWriter(f);  //si mette a scrivere sul file troncandolo o lo crea
                    pw.println(statistiche.dati);
                    pw.flush();
                    for (int i = 1; i <= simulazione.getGiorno().getValore(); i++){
                        pw.println(statistiche.toCSV(i));
                        pw.flush();
                    }
                } catch (FileNotFoundException fe) {
                    fe.printStackTrace();
                } catch (SecurityException se) {
                    se.printStackTrace();
                } finally {
                    pw.close();
                }
            }
        });


        // Stage - inizialmente visualizziamo la scena iniziale di inserimento parametriì

        stage.setScene(sceneIniziale);
        stage.setTitle("Epidemiological simulator");
        stage.show();

    }


    public static void main(String[] args) {
        launch(args); // come termino launch()
    }

}
