package ece651.sp22.grp8.risk.client.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import ece651.sp22.grp8.risk.client.ClientApp;
import ece651.sp22.grp8.risk.client.ClientGame;
import ece651.sp22.grp8.risk.client.ViewFactory;
import ece651.sp22.grp8.risk.*;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;


@ExtendWith(ApplicationExtension.class)
public class ActionControllerTest extends ApplicationTest {
  private ActionController controller;
  private final ClientApp clientApp = mock(ClientApp.class);
  private final HumanPlayer player = mock(HumanPlayer.class);
  @Override
  public void start(Stage stage) {
    try {
      initMock();
      ViewFactory factory = new ViewFactory(clientApp, stage);
      controller = new ActionController(this.clientApp, stage, factory, "");
      closeInitWindow();
      controller.playerID = new Label();
      controller.upgradePlayerMax = new Button();
      controller.techLevel = new Button();

      // for Map
      controller.ElantrisDetail = new Label();
      controller.NarniaDetail = new Label();
      controller.GondorDetail = new Label();
      controller.MordorDetail = new Label();
      controller.ScadrialDetail = new Label();
      controller.MidkemiaDetail = new Label();
      controller.HogwartsDetail = new Label();
      controller.LasagnaDetail = new Label();
      controller.OzDetail = new Label();
      controller.RosharDetail = new Label();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void closeInitWindow() {
    Stage windows = (Stage) Window.getWindows().get(0);
    windows.close();
  }

  private void initMock() {
    when(clientApp.getPlayer()).thenReturn(this.player);
    when(clientApp.getMap()).thenReturn(generateMockMap());
    when(clientApp.getRemainUnits()).thenReturn(8);
    when(clientApp.getNextTerritory()).thenReturn(new Territory("Elantris", 4, 6, 4));
    mockPlayer(1000, "Red", 16, 10, 5000, 16, false);
  }

  private Map generateMockMap() {
    MapFactory factory = new MapFactory();
    Map mockMap = factory.makeMapForTwo();
    return mockMap;
  }

  private Player mockPlayer(long playerID, String color, int techLevel, int foodAmount, long gameID, int techAmount,
      boolean hasUpgrade) {
    when(player.getColor()).thenReturn(color);
    when(player.getTechLevel()).thenReturn(techLevel);
    when(player.getPlayerID()).thenReturn(playerID);
    when(player.getFoodAmount()).thenReturn(foodAmount);
    when(player.getGameID()).thenReturn(gameID);
    when(player.getTechAmount()).thenReturn(techAmount);
    when(player.isHasUpgrade()).thenReturn(hasUpgrade);
    return player;
  }

  // Testing Below are Testing for Top Layer
  @Test
  public void test_onPlayerIDHoverExit() {
    Platform.runLater(() -> {
      controller.onPlayerIDButtonExit();
      ;
    });
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(controller.playerID.visibleProperty().getValue(), false);
  }

  @Disabled
  @Test
  public void test_onPlayerIDHover() {
    Platform.runLater(() -> {
      controller.onPlayerIDButtonHover();
      ;
    });
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(controller.playerID.visibleProperty().getValue(), true);
  }

  @Disabled
  @Test
  public void test_onMaxLevelButtomClick() {
    Platform.runLater(() -> {
      controller.onMaxLevelButtonClick();
    });
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(true, controller.upgradePlayerMax.visibleProperty().getValue());
  }




  // Testing below are for Map Operation
  @Disabled
  @Test
  public void test_onElantrisDetailHover() {
    Platform.runLater(() -> {
      controller.onElantrisDetailHover();
      ;
    });
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(true, controller.ElantrisDetail.visibleProperty().getValue());
  }

  @Disabled
  @Test
  public void test_onElantrisDetailHoverExit() {
    Platform.runLater(() -> {
      controller.onElantrisDetailHoverExit();
    });
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(false, controller.ElantrisDetail.visibleProperty().getValue());
  }

  @Disabled
  @Test
  public void test_onNarniaDetailHover() {
    Platform.runLater(() -> {
      controller.onNarniaDetailHover();
    });
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(true, controller.NarniaDetail.visibleProperty().getValue());
  }

  @Disabled
  @Test
  public void test_onNarniaDetailHoverExit() {
    Platform.runLater(() -> {
      controller.onNarniaDetailHoverExit();
    });

    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(false, controller.NarniaDetail.visibleProperty().getValue());
  }

  @Disabled
  @Test
  public void test_onCondorDetailHover() {
    Platform.runLater(() -> {
      controller.onCondorDetailHover();
    });

    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(true, controller.GondorDetail.visibleProperty().getValue());
  }

  @Disabled
  @Test
  public void test_onCondorDetailHoverExit() {
    Platform.runLater(() -> {
      controller.onCondorDetailHoverExit();
    });

    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(false, controller.GondorDetail.visibleProperty().getValue());
  }

  @Disabled
  @Test
  public void test_onOzDetailHover() {
    Platform.runLater(() -> {
      controller.onOzDetailHover();
    });

    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(true, controller.OzDetail.visibleProperty().getValue());
  }

  @Disabled
  @Test
  public void test_onOzDetailHoverExit() {
    Platform.runLater(() -> {
      controller.onOzDetailHoverExit();
    });
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(false, controller.OzDetail.visibleProperty().getValue());
  }

  @Disabled
  @Test
  public void test_onMidkemiaHover() {
    Platform.runLater(() -> {
      controller.onMidkemiaHover();
    });
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(true, controller.MidkemiaDetail.visibleProperty().getValue());
  }

  @Disabled
  @Test
  public void test_onMidkemiaHoverExit() {
    Platform.runLater(() -> {
      controller.onMidkemiaHoverExit();
    });
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(false, controller.MidkemiaDetail.visibleProperty().getValue());
  }

  @Disabled
  @Test
  public void test_onLasganaHover() {
    Platform.runLater(() -> {
      controller.onLasganaHover();
    });

    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(true, controller.LasagnaDetail.visibleProperty().getValue());
  }

  @Disabled
  @Test
  public void test_onLasganaHoverExit() {
    Platform.runLater(() -> {
      controller.onLasganaHoverExit();
    });

    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(false, controller.LasagnaDetail.visibleProperty().getValue());
  }

  @Disabled
  @Test
  public void test_ScadrialHover() {
    Platform.runLater(() -> {
      controller.onScadrialHover();
    });
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(true, controller.ScadrialDetail.visibleProperty().getValue());
  }

  @Disabled
  @Test
  public void test_ScadrialHoverExit() {
    Platform.runLater(() -> {
      controller.onScadrialHoverExit();
    });
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(false, controller.ScadrialDetail.visibleProperty().getValue());
  }

  @Disabled
  @Test
  public void test_onHogwartsHover() {
    Platform.runLater(() -> {
      controller.onHogwartsHover();
    });
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(true, controller.HogwartsDetail.visibleProperty().getValue());
  }

  @Disabled
  @Test
  public void test_onHogwartsHoverExit() {
    Platform.runLater(() -> {
      controller.onHogwartsHoverExit();
    });
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(false, controller.HogwartsDetail.visibleProperty().getValue());
  }

  @Disabled
  @Test
  public void test_onMordorHover() {
    Platform.runLater(() -> {
      controller.onMordorHover();
    });
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(true, controller.MordorDetail.visibleProperty().getValue());
  }

  @Disabled
  @Test
  public void test_onMordorHoverExit() {
    Platform.runLater(() -> {
      controller.onMordorHoverExit();
    });
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(false, controller.MordorDetail.visibleProperty().getValue());
  }

  @Disabled
  @Test
  public void test_onRosharHover() {
    Platform.runLater(() -> {
      controller.onRosharHover();
    });
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(true, controller.RosharDetail.visibleProperty().getValue());
  }

  @Disabled
  @Test
  public void test_onRosharHoverExit() {
    Platform.runLater(() -> {
      controller.onRosharHoverExit();
    });
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(false, controller.RosharDetail.visibleProperty().getValue());
  }

}

 
