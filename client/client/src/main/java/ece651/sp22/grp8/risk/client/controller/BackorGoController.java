package ece651.sp22.grp8.risk.client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import ece651.sp22.grp8.risk.GamePrompt;
import ece651.sp22.grp8.risk.SwitchPacket;
import ece651.sp22.grp8.risk.UpTech;
import ece651.sp22.grp8.risk.client.ClientApp;
import ece651.sp22.grp8.risk.client.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BackorGoController implements Initializable {
  ClientApp clientApp;
  Stage primaryStage;
  ViewFactory factory;
  String prompt;
  String go;
  String back;
  String status;
  static boolean result;
  static Stage windows;
  @FXML
  Label PromptWord;
  @FXML
  Button BackButton;
  @FXML
  Button GoButton;
  @FXML
  Label boxStatus;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.PromptWord.setText(prompt);
    this.BackButton.setText(back);
    this.GoButton.setText(go);
    this.boxStatus.setText(status);
    if (boxStatus.getText().equals("UpgradeLimit")) {
      BackButton.setVisible(false);
    }
    if (boxStatus.getText().equals("Proceed")) {
      BackButton.setVisible(false);
    }
  }

  public BackorGoController(ClientApp clientApp, Stage primaryStage, ViewFactory factory, String prompt,
      String goButStr, String backButStr, String status) {
    this.clientApp = clientApp;
    this.primaryStage = primaryStage;
    this.factory = factory;
    this.prompt = prompt;
    this.go = goButStr;
    this.back = backButStr;
    this.status = status;
  }

  @FXML
  public void onExitButClicked() {
    Stage window = (Stage) BackButton.getScene().getWindow();
    window.close();
    result = false;
  }

  @FXML
  public void onYesClicked() throws IOException, ClassNotFoundException {
    doActionBasedOnStatus();
    if (status.equals("LosePhase")) {
      result = true;
    }

    else {
      result = false;
    }
  }

  @FXML
  private void showAlertBox(String title, String result) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.initOwner(boxStatus.getScene().getWindow());
    alert.setTitle(title);
    alert.setContentText(result);
    alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> {
    });
  }

  @FXML
  public void doActionBasedOnStatus() throws IOException, ClassNotFoundException {
    if (Objects.equals(boxStatus.getText(), "UpgradeLimit")) {
      UpTech ut = new UpTech(clientApp.getPlayer().getPlayerID(), false);
      String result = clientApp.playOneTurn("UT", ut);
      if (!result.equals(GamePrompt.OK)) {
        showAlertBox("Upgrade fail", result);
      }
      closeWindow();
    }

    else if (boxStatus.getText().equals("Proceed")) {
      closeWindow();
    }

    else if (Objects.equals(boxStatus.getText(), "Exit")) {
      onExitButClicked();// close the window
      clientApp.utility.sendPacket(new SwitchPacket(GamePrompt.BACKTOGALLERY));
      ArrayList<Long> allGameList = clientApp.getAllGames();
      ArrayList<Long> myGameList = clientApp.getMyGames();
      Scene scene = factory.generateGallery(allGameList, myGameList);
      primaryStage.setScene(scene);
      for (long gameID : allGameList) {
        Button btn = (Button) scene.lookup("#" + gameID);
        btn.setOnAction((event) -> {
          try {
            joinGameByBtn(String.valueOf(gameID));
          } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
          }
        });
      }
      primaryStage.show();
    }
    closeWindow();
  }

  private void joinGameByBtn(String gameID) throws IOException, ClassNotFoundException {
    String result = clientApp.joinGame(gameID);
    if (result.equals(GamePrompt.REJOIN)||result.equals(GamePrompt.NEWGAME)) {
      primaryStage.setScene(factory.generateAction(result));
      primaryStage.show();
    } else {
      Stage stage = factory.generatePopupWindow(primaryStage, "Notice", result);
      stage.show();
    }
  }

  /**
   * Close current popup Window
   */
  @FXML
  private void closeWindow() {
    Stage window = (Stage) boxStatus.getScene().getWindow();
    window.close();
  }

  /**
   * Detect User Click OK or No
   **/
  @FXML
  public static boolean display(String prompt, ViewFactory factory, Stage primaryWindows) throws IOException {
    Scene scene = factory.generateBackorGoScene(prompt, "LosePhase");
    windows = new Stage();
    windows.setScene(scene);
    windows.setAlwaysOnTop(true);
    windows.initModality(Modality.APPLICATION_MODAL);
    windows.initOwner(primaryWindows);
    windows.showAndWait();
    return result;
  }
}
