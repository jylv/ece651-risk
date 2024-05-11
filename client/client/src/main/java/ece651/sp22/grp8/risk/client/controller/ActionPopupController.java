package ece651.sp22.grp8.risk.client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

import ece651.sp22.grp8.risk.AbstractAction;
import ece651.sp22.grp8.risk.Attack;
import ece651.sp22.grp8.risk.Move;
import ece651.sp22.grp8.risk.Player;
import ece651.sp22.grp8.risk.Territory;
import ece651.sp22.grp8.risk.UpUnit;
import ece651.sp22.grp8.risk.client.ClientApp;
import ece651.sp22.grp8.risk.client.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ActionPopupController implements Initializable {
  Stage primaryStage;
  ClientApp clientApp;
  String promptTitle;

  @FXML
  Label title;
  @FXML
  ChoiceBox<String> source;
  @FXML
  ChoiceBox<String> destination;
  @FXML
  ChoiceBox<String> units;
  @FXML
  TextField amounts;
  @FXML
  Button back;
  @FXML
  Button go;
  @FXML
  Label errorMsg;
  @FXML
  Label sourceLabel;
  @FXML
  Label destLabel;
  @FXML
  Label unitsLabel;

  static AbstractAction choices;
  static Stage windows;

  @Override
  public void initialize(URL location, ResourceBundle resource) {
    title.setText(promptTitle);
    ArrayList<String> ownedTerriorities = getSourceTerriorities();
    source.getItems().addAll(ownedTerriorities);
    String[] level = generateUnitChoice();
    units.getItems().addAll(level);
    errorMsg.setVisible(false);

    go.setOnMouseClicked((e) -> {
      boolean valid = checkInput();
      if (valid) {
        choices = packChoices();
      }
      if (valid) {
        windows.close();
      }
    });

    back.setOnMouseClicked((e) -> {
      choices = null;
      windows.close();
    });


    if (title.getText().equals("Upgrade")) {
      destLabel.setText("Old Level: ");
      unitsLabel.setText("New Level: ");
    }
  }

  private boolean checkInput() {
    boolean valid = false;
    if (source.getValue() == null) {
      errorMsg.setText("choose source");
    } else if (destination.getValue() == null) {
      if (title.getText() == "Upgrade") {
        errorMsg.setText("choose upgrade level");
      }
      errorMsg.setText("choose destination");
    } else if (units.getValue() == null) {
      errorMsg.setText("choose units level");
    } else if (!isNumeric(amounts.getText())) {
      // TODO: check whether the terriority have enough amounts
      errorMsg.setText("type in correct amounts of units :(");
    } else {
      valid = true;
    }
    if (!valid) {
      errorMsg.setVisible(true);
    }
    return valid;
  }

  boolean isNumeric(String strNum) {
    if (strNum == null) {
        return false;
    }
    try {
        double d = Double.parseDouble(strNum);
    } catch (NumberFormatException nfe) {
        return false;
    }
    return true;
  }

  @FXML
  private AbstractAction packChoices() {
    String action = title.getText();
    String chooseSource = source.getValue();
    String chooseDestination = destination.getValue();
    String chooseUnitsLevel = units.getValue();
    String chooseAmounts = amounts.getText();


    if (action.equals("Move")) {
      Move ret = new Move(clientApp.getPlayer().getPlayerID(), chooseSource, chooseDestination,
          Integer.parseInt(chooseUnitsLevel), Integer.parseInt(chooseAmounts));
      return ret;
    }


    else if (action.equals("Attack")) {
      Attack attack = new Attack(clientApp.getPlayer().getPlayerID(), chooseSource, chooseDestination,
          Integer.parseInt(chooseUnitsLevel), Integer.parseInt(chooseAmounts));
      return attack;
    }


    else if (action.equals("Upgrade")) {
      UpUnit up = new UpUnit(clientApp.getPlayer().getPlayerID(), chooseSource,
              Integer.parseInt(chooseDestination), Integer.parseInt(chooseAmounts) ,
              Integer.parseInt(chooseUnitsLevel)); // TODO: refactor variable name
      return up;
    }

    return null;
  }

  public ActionPopupController(Stage primaryStage, ClientApp clientApp, String title) {
    this.primaryStage = primaryStage;
    this.clientApp = clientApp;
    this.promptTitle = title;
    choices = null;
  }

  private ArrayList<String> getSourceTerriorities() {
    Player player = clientApp.getPlayer();
    ArrayList<String> ownedTerriorities = new ArrayList<>();
    HashMap<String, ArrayList<Territory>> groups = clientApp.getMap().getGroups();

    ArrayList<Territory> territories = groups.get(player.getColor());
    for (Territory territory : territories) {
      ownedTerriorities.add(territory.getName());
    }

    return ownedTerriorities;
  }

  @FXML
  private String[] generateUnitChoice() {
    String[] unitLevel = { "0", "1", "2", "3", "4", "5", "6" };
    return unitLevel;
  }

  @FXML
  public static AbstractAction onDisplay(Stage primaryStage, ViewFactory factory, String action) throws IOException {
    Scene scene = factory.generateChoicePopupWindow(primaryStage, action);
    windows = new Stage();
    windows.setScene(scene);
    windows.setAlwaysOnTop(true);
    windows.initModality(Modality.APPLICATION_MODAL);
    windows.showAndWait();

    return choices;
  }

  @FXML
  public void onDestinationClicked() {
    destination.getItems().clear();
    if (title.getText().equals("Upgrade")) {
      destination.getItems().addAll(generateUnitChoice());
    }

    else if (title.getText().equals("Move")) {
      getOwnedTerriorityToDes();
    }

    else if (title.getText().equals("Attack")) {
      ArrayList<String> neighbors = getNeighbor(source.getValue());
      destination.getItems().addAll(neighbors);
    }
  }

  @FXML
  private void getOwnedTerriorityToDes() {
    String playerColor = clientApp.getPlayer().getColor();
    ArrayList<Territory> moveable = clientApp.getMap().getGroups().get(playerColor);
    ArrayList<String> toAdd = new ArrayList<>();
    for (Territory t : moveable) {
      toAdd.add(t.getName());
    }
    destination.getItems().addAll(toAdd); // TODO: refactor to terriority owned by self
  }

  private ArrayList<String> getNeighbor(String source) {
    ArrayList<String> neighbors = clientApp.getMap().getTerritoriesMap().get(source);
    return neighbors;
  }

}
