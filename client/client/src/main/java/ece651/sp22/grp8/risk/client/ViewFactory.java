package ece651.sp22.grp8.risk.client;
import ece651.sp22.grp8.risk.GamePrompt;
import ece651.sp22.grp8.risk.Territory;
import ece651.sp22.grp8.risk.client.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewFactory {
  ClientApp clientapp;
  Stage primaryStage;

  public ViewFactory(ClientApp clientapp, Stage primaryStage) {
    this.clientapp = clientapp;
    this.primaryStage = primaryStage;
  }

  /**
   * generate and return a login scene
   *
   * @return A scene which contains the buttons for users to log in.
   * @throws IOException
   */
  public Scene generateLoginScene() throws IOException {
    URL xmlResources = getClass().getResource("/ui/login.fxml");
    URL cssResources = getClass().getResource("/ui/login.css");
    FXMLLoader loader = new FXMLLoader(xmlResources);
    HashMap<Class<?>, Object> controllers = new HashMap<>();
    controllers.put(LoginController.class, new LoginController(this.clientapp, this.primaryStage, this));
    loader.setControllerFactory(controllers::get);
    VBox p = loader.load();
    Scene scene = new Scene(p, 1280, 840);
    // assert cssResources != null;
    scene.getStylesheets().add(cssResources.toString());
    return scene;
  }

  public Scene generateGallery(ArrayList<Long> allGameList,ArrayList<Long> myGameList) throws IOException {
    URL xmlResources = getClass().getResource("/ui/gallery.fxml");
    URL cssResources = getClass().getResource("/ui/gallery.css");
    FXMLLoader loader = new FXMLLoader(xmlResources);
    HashMap<Class<?>, Object> controllers = new HashMap<>();
    controllers.put(GalleryController.class, new GalleryController(this.clientapp, this.primaryStage, this));
    loader.setControllerFactory(controllers::get);
    FlowPane pane = loader.load();
    String btnContent;
    for (long gameID : allGameList) {
      if(myGameList.contains(gameID)){
        btnContent = "Rejoin";
      }else{
        btnContent = "Join";
      }
      pane.getChildren().add(generateGameCard(String.valueOf(gameID),btnContent));
    }
    HBox choiceBoxPane = (HBox) pane.lookup("#choiceBoxPane");
    ChoiceBox<String> choiceBox = new ChoiceBox<>();
    choiceBox.getItems().addAll("2", "3", "4", "5");
    choiceBox.setId("choiceBox");
    choiceBox.setValue("2");
    choiceBoxPane.getChildren().add(choiceBox);
    Scene scene = new Scene(pane, 900, 400);
    // assert cssResources != null;
    scene.getStylesheets().add(cssResources.toString());
    return scene;
  }

  private String mapToLoad() {
    String ret = "";
    int playerNum = clientapp.getPlayerNum();
    if (playerNum == 2 || playerNum == 4) {
      ret = "/ui/map2.fxml";
    }
    else if (playerNum == 3) {
      ret = "/ui/map3.fxml";
    }
    else if (playerNum == 5) {
      ret = "/ui/map5.fxml";
    }

    return ret;
  }


  public Scene generateAction(String joinResult) throws IOException {
    clientapp.setMap();
    clientapp.setInitialPara();
    String toLoad = mapToLoad();
    URL xmlResources = getClass().getResource(toLoad);
    URL cssResources = getClass().getResource("/ui/map.css");
    FXMLLoader loader = new FXMLLoader(xmlResources);
    HashMap<Class<?>, Object> controllers = new HashMap<>();
    controllers.put(ActionController.class, new ActionController(this.clientapp, this.primaryStage, this, joinResult));
    loader.setControllerFactory(controllers::get);
    GridPane pane = loader.load();
    Scene scene = new Scene(pane, 1280,800);
    // assert cssResources != null;
    scene.getStylesheets().add(cssResources.toString());
    return scene;
  }

  /**
   * Generate a game card showing gameID
   * 
   * @param gameID
   * @return
   */
  private Node generateGameCard(String gameID, String btnContent) {
    VBox pane = new VBox();
    pane.getStyleClass().add("card");
    pane.setPrefWidth(160);
    pane.setPrefHeight(158);
    pane.setAlignment(Pos.CENTER);
    Text text = new Text("Game ID : " + gameID + "\n");
    pane.getChildren().add(text);
    Button btn = new Button(btnContent);
    btn.setId(gameID);
    pane.getChildren().add(btn);
    return pane;
  }

  public Stage generatePopupWindow(Stage primaryStage, String title, String content) {
    Stage stage = new Stage();
    stage.setTitle(title);
    stage.initOwner(primaryStage);
    stage.setResizable(false);
    stage.initModality(Modality.APPLICATION_MODAL);
    VBox dialogBox = new VBox(20);
    Text info = new Text(content);
    info.setFont(Font.font(20));
    info.setId("prompt_content");
    dialogBox.getChildren().add(info);
    Scene dialogScene = new Scene(dialogBox, 500, 200);
    stage.setScene(dialogScene);
    return stage;
  }

  /**
   * @param: prompt for the popup message
   * @param: status for this box (support upgrade TechLimit, exit game, Procceed
   **/
  public Scene generateBackorGoScene(String Prompt, String status) throws IOException {
    URL xmlResources = getClass().getResource("/ui/GamePrompt.fxml");
    FXMLLoader loader = new FXMLLoader(xmlResources);
    HashMap<Class<?>, Object> controllers = new HashMap<>();
    controllers.put(BackorGoController.class, new BackorGoController(clientapp, primaryStage, this,Prompt, "Yes", "No", status));
    loader.setControllerFactory(controllers::get);
    return new Scene(loader.load(), 600, 400);
  }


  public Scene generateChoicePopupWindow(Stage primaryStage, String action) throws IOException {
    URL xmlResource = getClass().getResource("/ui/action.fxml");
    FXMLLoader loader = new FXMLLoader(xmlResource);
    HashMap<Class<?>, Object> controllers = new HashMap<>();
    controllers.put(ActionPopupController.class, new ActionPopupController(primaryStage, clientapp, action));
    loader.setControllerFactory(controllers::get);
    return new Scene(loader.load(), 600, 400);
  }

    public Stage generateInitUnitWindow(Stage primaryStage, int remainUnits, Territory nextTerritory) throws IOException {
        Stage stage = new Stage();
        stage.setTitle(GamePrompt.TITLE);
        stage.initOwner(primaryStage);
        stage.setResizable(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        URL xmlResources = getClass().getResource("/ui/initUnit.fxml");
        URL cssResources = getClass().getResource("/ui/initUnit.css");
        FXMLLoader loader = new FXMLLoader(xmlResources);
        HashMap<Class<?>, Object> controllers = new HashMap<>();
        controllers.put(InitUnitController.class, new InitUnitController(this.clientapp, primaryStage, stage,this,remainUnits,nextTerritory));
        loader.setControllerFactory(controllers::get);
        Scene scene = new Scene(loader.load(), 600, 400);
        Label prompt = (Label) scene.lookup("#unitPrompt");
        prompt.setText("You remain "+remainUnits+" units.\n" +
                "How many units do you want to\nput on "+nextTerritory.getName()+" ?\n");
        // assert cssResources != null;
        scene.getStylesheets().add(cssResources.toString());
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        return stage;
    }
}
