package ece651.sp22.grp8.risk.client.controller;

import ece651.sp22.grp8.risk.GamePrompt;
import ece651.sp22.grp8.risk.Territory;
import ece651.sp22.grp8.risk.client.ClientApp;
import ece651.sp22.grp8.risk.client.ViewFactory;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class GalleryControllertTest extends ApplicationTest {
  private GalleryController controller;
  private final ClientApp clientApp = mock(ClientApp.class);
  ViewFactory factory;
  Stage primaryStage;

  @Override
  public void start(Stage primaryStage) throws IOException {
    factory = new ViewFactory(clientApp, primaryStage);
    Scene scene = factory.generateGallery(new ArrayList<>(), new ArrayList<>());
    primaryStage.setScene(scene);
    this.primaryStage = primaryStage;
    controller = new GalleryController(clientApp, primaryStage, factory);
    initMock();
  }

  /**
   * A helper function to init mock clientApp
   * 
   * @throws IOException
   */
  private void initMock() throws IOException {
    ArrayList<Long> allGameList = new ArrayList<>();
    allGameList.add(5000L);
    allGameList.add(5001L);
    when(clientApp.getAllGames()).thenReturn(allGameList);
    ArrayList<Long> myGameList = new ArrayList<>();
    when(clientApp.getMyGames()).thenReturn(myGameList);
    when(clientApp.getRemainUnits()).thenReturn(8);
    when(clientApp.getNextTerritory()).thenReturn(new Territory("Elantris", 4, 6, 4));
    when(clientApp.joinGame("5000")).thenReturn(GamePrompt.OK);
    when(clientApp.joinGame("5001")).thenReturn("The game with ID 5001 has already started!\n" + "Try another one!\n");
    when(clientApp.joinGame("1000")).thenReturn("The game with ID 1000 does not exist!\n" + "Try another one!\n");

  }

  @Disabled
  @Test
  public void test_pass_new_game() {
    Platform.runLater(() -> {
      try {
        controller.newGame();
        Button btn = (Button) controller.primaryStage.getScene().lookup("#5000");
        btn.fire();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
    Label actual = (Label) Window.getWindows().get(1).getScene().lookup("#title");
    assertEquals("Initialize Unit", actual.getText()); // go to next page
  }

  @Test
  public void test_fail_new_game() {
    Platform.runLater(() -> {
      try {
        controller.newGame();
        Button btn = (Button) controller.primaryStage.getScene().lookup("#5001");
        btn.fire();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
    Text actual = (Text) Window.getWindows().get(1).getScene().lookup("#prompt_content");
    assertEquals("The game with ID 5001 has already started!\n" + "Try another one!\n", actual.getText());
  }
  @Disabled
  @Test
  void test_redirect() {
    Platform.runLater(() -> {
      try {
        controller.redirect();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
    Scene scene = primaryStage.getScene();
  }

  @Test
  void test_joinGameByID() {
    Platform.runLater(() -> {
      controller.joinGameID = new TextField("1000");
      try {
        controller.joinGameByID();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
    Text actual = (Text) Window.getWindows().get(0).getScene().lookup("#prompt_content");
    assertEquals("The game with ID 1000 does not exist!\n" + "Try another one!\n", actual.getText());
  }

}

