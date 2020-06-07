import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;

public class UiJfx extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        initUi5(stage);
    }


    private void initUI(Stage stage){
    }

    private void initUi2(Stage stage){
        //creating label email
        Text text1 = new Text("Email");

        //creating label password
        Text text2 = new Text("Password");

        //Creating Text Filed for email
        TextField textField1 = new TextField();

        //Creating Text Filed for password
        TextField textField2 = new TextField();

        //Creating Buttons
        Button button1 = new Button("Submit");
        Button button2 = new Button("Clear");

        //Creating a Grid Pane
        GridPane gridPane = new GridPane();

        //Setting size for the pane
        gridPane.setMinSize(400, 200);

        //Setting the padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        //Setting the Grid alignment
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid
        gridPane.add(text1, 0, 0);
        gridPane.add(textField1, 1, 0);
        gridPane.add(text2, 0, 1);
        gridPane.add(textField2, 1, 1);
        gridPane.add(button1, 0, 2);
        gridPane.add(button2, 1, 2);

        //Creating a scene object
        Scene scene = new Scene(gridPane);

        //Setting title to the Stage
        stage.setTitle("Grid Pane Example");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();

    }

    private void initUi5(Stage stage) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UiJfx.class.getResource("/UiJfx.fxml"));  // può generare un eccezione
        Controller controller = loader.getController();

        VBox vBox = loader.<VBox>load();

        Button button = new Button();

        Scene scene = new Scene(vBox,1000,800);
        scene.getStylesheets().add("stylesheet/styles.css");
        stage.setScene(scene);
        stage.setTitle("Prova FXML");
        stage.show();

    }

    private void initUi3(Stage stage) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UiJfx.class.getResource("/UiJfx.fxml"));  // può generare un eccezione
        Controller controller = loader.getController();

        VBox vBox = loader.<VBox>load();

        Button button = new Button();

        Alert alert = new Alert(Alert.AlertType.NONE);


        EventHandler<ActionEvent> event = new
                EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
                        // set alert type
                        alert.setAlertType(Alert.AlertType.ERROR);

                        alert.setTitle("Erroreeeeee");

                        alert.setContentText("Porcozioooooo");

                        // show the dialog
                        alert.show();
                    }
                };

        controller.btn.setOnAction(event);

        Scene scene = new Scene(vBox,1000,800);
        scene.getStylesheets().add("stylesheet/styles.css");
        stage.setScene(scene);
        stage.setTitle("Prova FXML");
        stage.show();




    }

    private void initUi4(Stage stage){

        stage.setTitle("creating alerts");

        // create a button
        Button b = new Button("error alert");


        // create a tile pane
        TilePane r = new TilePane();

        // create a alert
        Alert a = new Alert(Alert.AlertType.NONE);


        // action event
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
                        // set alert type
                        a.setAlertType(Alert.AlertType.ERROR);

                        // set content text
                        a.setContentText("error Dialog");

                        // show the dialog
                        a.show();
                    }
        };

        b.setOnAction(event);

        // add button
        r.getChildren().add(b);

        // create a scene
        Scene sc = new Scene(r, 200, 200);

        // set the scene
        stage.setScene(sc);

        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }

}
