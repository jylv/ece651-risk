package ece651.sp22.grp8.risk.client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

import ece651.sp22.grp8.risk.AbstractAction;
import ece651.sp22.grp8.risk.ActionResult;
import ece651.sp22.grp8.risk.GamePrompt;
import ece651.sp22.grp8.risk.Leave;
import ece651.sp22.grp8.risk.LeavePacket;
import ece651.sp22.grp8.risk.Map;
import ece651.sp22.grp8.risk.Player;
import ece651.sp22.grp8.risk.Territory;
import ece651.sp22.grp8.risk.client.ClientApp;
import ece651.sp22.grp8.risk.client.ViewFactory;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ActionController implements Initializable {

  ClientApp clientApp;
  Stage primaryStage;
  ViewFactory factory;
  Integer unitLevel;

  @FXML
  Button playerName;
  @FXML
  Button techLevel;
  @FXML
  Button foodResources;
  @FXML
  Button techResources;
  @FXML
  Label playerID;
  @FXML
  Button upgradePlayerMax;
  @FXML
  Button Elantris;
  @FXML
  Label ElantrisDetail;
  @FXML
  Button Narnia;
  @FXML
  Label NarniaDetail;
  @FXML
  Button Midkemia;
  @FXML
  Label MidkemiaDetail;
  @FXML
  Button Oz;
  @FXML
  Label OzDetail;
  @FXML
  Button Lasagna;
  @FXML
  Label LasagnaDetail;
  @FXML
  Button Gondor;
  @FXML
  Label GondorDetail;
  @FXML
  Button Mordor;
  @FXML
  Label MordorDetail;
  @FXML
  Button Scadrial;
  @FXML
  Label ScadrialDetail;
  @FXML
  Button Hogwarts;
  @FXML
  Label HogwartsDetail;
  @FXML
  Button Roshar;
  @FXML
  Label RosharDetail;
  @FXML
  Label message;
  @FXML
  Button messageBut;
  @FXML
  Pane messagePane;
  @FXML
  Button MoveButton;
  @FXML
  Button AttackButton;
  @FXML
  Button upgradeButton;
  @FXML
  Button switchButton;
  @FXML
  Button ProceedButton;
  @FXML
  Button ExitGameButton;
  @FXML
  Button loseUpdate;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    invisibleAllClickedElements();
    initialAllElementVisible(false);
    try {
      ActionResult ar = new ActionResult(clientApp.getPlayer().getPlayerID(), "Play Game");
      eachTurnInitial(ar);
    } catch (IOException | ClassNotFoundException ignored) {
    }
  }

  @FXML
  private void disableAllButton() {
    techLevel.setDisable(true);
    foodResources.setDisable(true);
    techResources.setDisable(true);
    MoveButton.setDisable(true);
    AttackButton.setDisable(true);
    upgradeButton.setDisable(true);
    switchButton.setDisable(true);
    ProceedButton.setDisable(true);
    ExitGameButton.setDisable(true);
  }

  @FXML
  private void enableAllButton() {
    techLevel.setDisable(false);
    foodResources.setDisable(false);
    techResources.setDisable(false);
    MoveButton.setDisable(false);
    AttackButton.setDisable(false);
    upgradeButton.setDisable(false);
    switchButton.setDisable(false);
    ProceedButton.setDisable(false);
    ExitGameButton.setDisable(false);
  }

  @FXML
  public void onContinueClicked() {
    enableAllButton();
    messageBut.setVisible(false);
    updateView();
  }


  public ActionController(ClientApp clientApp, Stage primaryStage, ViewFactory factory, String joinResult)
      throws IOException {
    this.clientApp = clientApp;
    this.primaryStage = primaryStage;
    this.factory = factory;
    this.unitLevel = 7;
    if(joinResult.equals(GamePrompt.NEWGAME)){
      showInitWindow();
    }
  }

  public void showInitWindow() throws IOException {
    int remainUnits = clientApp.getRemainUnits();
    Territory nextTerritory = clientApp.getNextTerritory();
    Stage stage = factory.generateInitUnitWindow(primaryStage, remainUnits, nextTerritory);
    stage.showAndWait();
    clientApp.setInitialize();
  }

  private void initialAllElementVisible(boolean visible) {
    upgradePlayerMax.setVisible(visible);
    playerID.setVisible(visible);
    ElantrisDetail.setVisible(visible);
    NarniaDetail.setVisible(visible);
    MidkemiaDetail.setVisible(visible);
    OzDetail.setVisible(visible);
    LasagnaDetail.setVisible(visible);
    GondorDetail.setVisible(visible);
    MordorDetail.setVisible(visible);
    ScadrialDetail.setVisible(visible);
    HogwartsDetail.setVisible(visible);
    RosharDetail.setVisible(visible);
  }

  @FXML
  private void updateView() {
    Player playerinfo = clientApp.getPlayer();
    playerID.setText("Player ID: " + clientApp.getPlayer().getPlayerID());
    playerName.setText("Player Color: " + playerinfo.getColor());
    techLevel.setText("Tech Level: " + String.valueOf(playerinfo.getTechLevel()));
    updateTerrioritiesView();
    updateResourceView();

  }

  @FXML
  private void updateResourceView() {
    int totalFood = clientApp.getPlayer().getFoodAmount();
    int totalTech = clientApp.getPlayer().getTechAmount();
    foodResources.setText("Food: " + totalFood);
    techResources.setText("Tech: " + totalTech);
  }

  @FXML
  private void updateTerrioritiesView() {
    Map recvMap = clientApp.getMap();
    Set<Territory> territories = recvMap.getTerritorySet();
    for (Territory t : territories) {
      String selector = "#" + t.getName() + "Detail";
      Label territory = (Label) primaryStage.getScene().lookup(selector);
      Button color = (Button) primaryStage.getScene().lookup("#" + t.getName());

      if (territory != null) {
        String info = getTerritoryInfo(t);
        territory.setText(info);
      }

      if (color != null) {
        color.setStyle("-fx-background-color: " + t.getOwner().getColor() + ";");
      }
    }
  }

  private String getTerritoryInfo(Territory t) {
    String info = t.getName() + "\n" + "size: " + t.size + "\n" + "Food: " + t.foodProd + "\n" + "Tech: " + t.techProd
        + "\n";
    HashMap<Integer, Integer> unitInfo = t.getUnitMap();
    for (int i = 0; i < unitLevel; i++) {
      String unitsDetail = "Level " + i + ": " + unitInfo.get(i);
      info += unitsDetail + "\n";
    }
    return info;
  }

  private void invisibleAllClickedElements() {
    upgradePlayerMax.setVisible(false);
  }

  @FXML
  public void onPlayerIDButtonHover() {
    playerID.setVisible(true);
    updateView();
  }

  @FXML
  public void onPlayerIDButtonExit() {
    playerID.setVisible(false);
    updateView();
  }

  @FXML
  public void onMaxLevelButtonClick() {
    invisibleAllClickedElements();
    upgradePlayerMax.setVisible(true);
    updateView();
  }

  @FXML
  public void onElantrisDetailHover() {
    ElantrisDetail.setVisible(true);
    updateView();
  }

  @FXML
  public void onElantrisDetailHoverExit() {
    ElantrisDetail.setVisible(false);
    updateView();
  }

  @FXML
  public void onNarniaDetailHover() {
    NarniaDetail.setVisible(true);
    updateView();
  }

  @FXML
  public void onNarniaDetailHoverExit() {
    NarniaDetail.setVisible(false);
    updateView();
  }

  @FXML
  public void onCondorDetailHover() {
    GondorDetail.setVisible(true);
    updateView();
  }

  @FXML
  public void onCondorDetailHoverExit() {
    GondorDetail.setVisible(false);
    updateView();
  }

  @FXML
  public void onOzDetailHover() {
    OzDetail.setVisible(true);
    updateView();
  }

  @FXML
  public void onOzDetailHoverExit() {
    OzDetail.setVisible(false);
    updateView();
  }

  @FXML
  public void onMidkemiaHover() {
    MidkemiaDetail.setVisible(true);
    updateView();
  }

  @FXML
  public void onMidkemiaHoverExit() {
    MidkemiaDetail.setVisible(false);
    updateView();
  }

  @FXML
  public void onLasganaHover() {
    LasagnaDetail.setVisible(true);
    updateView();
  }

  @FXML
  public void onLasganaHoverExit() {
    LasagnaDetail.setVisible(false);
    updateView();
  }

  @FXML
  public void onScadrialHover() {
    ScadrialDetail.setVisible(true);
    updateView();
  }

  @FXML
  public void onScadrialHoverExit() {
    ScadrialDetail.setVisible(false);
    updateView();
  }

  @FXML
  public void onHogwartsHover() {
    HogwartsDetail.setVisible(true);
    updateView();
  }

  @FXML
  public void onHogwartsHoverExit() {
    HogwartsDetail.setVisible(false);
    updateView();
  }

  @FXML
  public void onMordorHover() {
    MordorDetail.setVisible(true);
    updateView();
  }

  @FXML
  public void onMordorHoverExit() {
    MordorDetail.setVisible(false);
    updateView();
  }

  @FXML
  public void onRosharHover() {
    RosharDetail.setVisible(true);
    updateView();
  }

  @FXML
  public void onRosharHoverExit() {
    RosharDetail.setVisible(false);
    updateView();
  }

  @FXML
  public void onAttackClicked() throws IOException {
    invisibleAllClickedElements();
    AbstractAction result = ActionPopupController.onDisplay(primaryStage, factory, "Attack");
    if (result != null) {
      doAction("A", result);
    }
    message.setText(clientApp.getAttackInfo());
  }

  @FXML
  public void onMoveClicked() throws IOException {
    invisibleAllClickedElements();
    AbstractAction packet = ActionPopupController.onDisplay(primaryStage, factory, "Move");
    if (packet != null) {
      doAction("M", packet);
    } // null means user undo the action
  }

  @FXML
  private void doAction(String action, AbstractAction packet) throws IOException {
    String result = clientApp.playOneTurn(action, packet);
    if (result.equals(GamePrompt.OK)) {
      updateView();
    } else {
      showAlert("Invalid", result);
    }
  }

  @FXML
  public void onExitClicked() throws IOException {
    invisibleAllClickedElements();
    Scene scene = factory.generateBackorGoScene("Are you sure :(", "Exit");
    createWindow(scene);
  }

  @FXML
  public void onProccedClicked() {
    invisibleAllClickedElements();
    message.setText("Waiting for others, when ready a continue buttom would appear :)");
    disableAllButton();
    // do asynchronize call
    AsyncRequest worker = new AsyncRequest("Proceed");
    worker.start();
  }

  @FXML
  public void handleActionResult(ActionResult result) throws IOException, ClassNotFoundException {
    if (result.getGameStatus().equals("END")) {
      message.setText(clientApp.showGameResult());
      disableAllButton();
    }

    else if (result.getGameStatus().equals("WIN")) {
      message.setText(GamePrompt.WIN + "\n" + clientApp.showGameResult());
      disableAllButton();
    }

    else if (result.getGameStatus().equals("CONTINUE")) {
      eachTurnInitial(result);
    }

    else if (result.getGameStatus().equals("LOSE")) {
      boolean continueWatch = BackorGoController.display("You Lose, Continue to Watch Gmae?", factory, primaryStage);
      ExitGameButton.setVisible(true);
      if (continueWatch) {
        clientApp.utility.sendPacket(new LeavePacket(new Leave(clientApp.getPlayer().getPlayerID(), false)));
        loseUpdate.setVisible(true);
        AsyncRequest worker = new AsyncRequest("LosePhase");
        worker.start();
      }
//      disableAllButton();
      ExitGameButton.setVisible(true);
    }
  }

  @FXML
  public void watchGame() {
    AsyncRequest worker = new AsyncRequest("LosePhase");
    worker.start();
    message.setText("Updating map, waiting others winner to commit :)");
  }

  @FXML
  public void eachTurnInitial(ActionResult result) throws IOException, ClassNotFoundException {
    clientApp.setMap();
    clientApp.updatePlayer();
    messagePane.setVisible(true);
    message.setText(clientApp.getResult(result));
    messageBut.setVisible(true);
    disableAllButton();
    upgradePlayerMax.setDisable(false);
  }

  @FXML
  public void onUpgradeClicked() throws IOException {
    invisibleAllClickedElements();
    Scene scene = factory.generateBackorGoScene("Let's go strong", "UpgradeLimit");
    createWindow(scene);
    upgradePlayerMax.setDisable(true);
    updateView();
  }

  @FXML
  public void onUpgradeUnitClicked() throws IOException {
    invisibleAllClickedElements();
    AbstractAction packet = ActionPopupController.onDisplay(primaryStage, factory, "Upgrade");
    if (packet != null) {
      doAction("UU", packet);
    }
  }

  @FXML
  public void createWindow(Scene scene) {
    Stage window = new Stage();
    window.initOwner(primaryStage);
    window.setAlwaysOnTop(true);
    window.initModality(Modality.APPLICATION_MODAL);
    window.setScene(scene);
    window.show();
  }
  
  @FXML
  public void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.initOwner(primaryStage);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
  }

  @FXML
  public void handleLeave(Leave leave) throws IOException, ClassNotFoundException {
    if (!leave.getLeave()) {
      clientApp.setMap();
      clientApp.updatePlayer();
      message.setText("up to date map :)");
      updateView();
    }

    else {
      primaryStage.close(); // TODO: exit game more elegantly for UX
    }
  }

  // TODO: refactor to be more generic
  private class AsyncRequest extends Service<AbstractAction> {
    String asyncAction;

    public AsyncRequest(String action) {
      this.asyncAction = action;
      setOnSucceeded(s -> {
        try {
          if (asyncAction.equals("Proceed")) {
            ActionResult ar = (ActionResult) s.getSource().getValue();
            handleActionResult(ar);
          }

          else if (asyncAction.equals("LosePhase")) {
            Leave leave = (Leave) s.getSource().getValue();
            handleLeave(leave);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    }

    @Override
    protected Task<AbstractAction> createTask() {
      return new Task<AbstractAction>() {
        @Override
        protected AbstractAction call() throws Exception {
          if (asyncAction.equals("Proceed")) {
            ActionResult ar = clientApp.handleCommit();
            return ar;
          }

          else if (asyncAction.equals("LosePhase")) {
            Leave leave = (Leave) clientApp.utility.recvPacket().getObject();
            return leave;
          }

          return null;
        }
      };
    }
  }
}
