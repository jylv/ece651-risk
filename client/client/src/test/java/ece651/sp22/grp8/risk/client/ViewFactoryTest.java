package ece651.sp22.grp8.risk.client;

import ece651.sp22.grp8.risk.GamePrompt;
import ece651.sp22.grp8.risk.Territory;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ViewFactoryTest extends ApplicationTest {
  private final ClientApp clientApp = mock(ClientApp.class);
  ViewFactory factory;
  Stage primaryStage;

  @Override
  public void start(Stage primaryStage) {
    try {
      this.primaryStage = primaryStage;
      factory = new ViewFactory(clientApp, primaryStage);
      initMock();
    } catch (IOException ignored) {
    }
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
    myGameList.add(5000L);
    when(clientApp.getMyGames()).thenReturn(myGameList);
    when(clientApp.getRemainUnits()).thenReturn(8);
    when(clientApp.getNextTerritory()).thenReturn(new Territory("Elantris", 4, 6, 4));
  }

  @Test
  void test_generateGallery() throws IOException {
    ArrayList<Long> allGameList = clientApp.getAllGames();
    ArrayList<Long> myGameList = clientApp.getMyGames();
    Scene scene = factory.generateGallery(allGameList, myGameList);
    assertEquals(900, scene.getWidth());
    assertEquals(400, scene.getHeight());
    ChoiceBox<String> choiceBox = (ChoiceBox<String>) scene.lookup("#choiceBox");
    assertEquals("2", choiceBox.getValue());
    ArrayList<String> expectedItems = new ArrayList<String>();
    expectedItems.add("2");
    expectedItems.add("3");
    expectedItems.add("4");
    expectedItems.add("5");
    assertEquals(expectedItems, choiceBox.getItems());
    assertEquals(4, scene.getRoot().getChildrenUnmodifiable().size()); // 2 base cards+ 2 game cards
  }

    /*
    @Test
    void test_generateGallery() throws IOException {
        ArrayList<Long> allGameList = clientApp.getAllGames();
        ArrayList<Long> myGameList = clientApp.getMyGames();
        Scene scene = factory.generateGallery(allGameList, myGameList);
        assertEquals(900,scene.getWidth());
        assertEquals(400,scene.getHeight());
        ChoiceBox<String> choiceBox = (ChoiceBox<String>) scene.lookup("#choiceBox");
        assertEquals("2",choiceBox.getValue());
        ArrayList<String> expectedItems = new ArrayList<String>();
        expectedItems.add("2");
        expectedItems.add("3");
        expectedItems.add("4");
        expectedItems.add("5");
        assertEquals(expectedItems,choiceBox.getItems());
        assertEquals(4,scene.getRoot().getChildrenUnmodifiable().size()); //2 base cards+ 2 game cards
    }

  @Test
  void test_generateBackorGoScene() {
    Platform.runLater(() -> {
      try {
        Scene scene = factory.generateBackorGoScene("Are you sure :(", "Exit");
        assertEquals(600, scene.getWidth());
        assertEquals(400, scene.getHeight());
        // TODO:
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }
  @Disabled
  @Test
  void test_generateChoicePopupWindow() {
    Platform.runLater(() -> {
      try {
        String action = "test";
        Scene scene = factory.generateChoicePopupWindow(primaryStage, action);
        assertEquals(600, scene.getWidth());
        assertEquals(400, scene.getHeight());
        // TODO:
        // TODO: LOAD exception, action.fxml may be problematic
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  @Test
  void test_generatePopupWindow() {
    String content = "test";
    String title = "Notice";
    Platform.runLater(() -> {
      Stage stage = factory.generatePopupWindow(primaryStage, title, content);
      Text actual = (Text) stage.getScene().lookup("#prompt_content");
      assertEquals(content, actual.getText());
      assertEquals(title, stage.getTitle());
    });

  }

  @Test
  void test_generateInitUnitWindow() {
    Platform.runLater(() -> {
      int remainUnits = clientApp.getRemainUnits();
      Territory nextTerritory = clientApp.getNextTerritory();
      Stage stage = null;
      try {
        stage = factory.generateInitUnitWindow(primaryStage, remainUnits, nextTerritory);
      } catch (IOException e) {
        e.printStackTrace();
      }
      assertEquals(600, stage.getScene().getWidth());
      assertEquals(400, stage.getScene().getHeight());
      assertEquals(GamePrompt.TITLE, stage.getTitle());
      Label prompt = (Label) stage.getScene().lookup("#unitPrompt");
      assertEquals("You remain " + remainUnits + " units.\n" + "How many units do you want to\nput on "
          + nextTerritory.getName() + " ?\n", prompt.getText());
    });
  }

     */

}
