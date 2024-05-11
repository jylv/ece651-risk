package ece651.sp22.grp8.risk.client;

import java.io.IOException;

import ece651.sp22.grp8.risk.GamePrompt;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.stage.Window;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import javafx.stage.Stage;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class AppTest extends ApplicationTest {
  Stage stage;
@Disabled
  @Test
  public void test_start()  {
    App app = new App();
    Platform.runLater(() -> {
      try {
        stage  = new Stage();
        app.start(stage);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(GamePrompt.TITLE,stage.getTitle()); //check title
  }

  @Disabled
  @Test
  public static void main(String[] args){    new Thread(new Runnable() {
    @Override
    public void run() {
      try {
        Thread.sleep(1000); //close after 1 seconds
      }
      catch (InterruptedException ignored) {
      }
      System.exit(0);
    }
  }).start();
    App.main(args);
    assertEquals(0, Window.getWindows().size()); //make sure it can be closed
  }
}
