package ece651.sp22.grp8.risk.client.controller;

import ece651.sp22.grp8.risk.client.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import static org.mockito.Mockito.mock;

import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class BackorGoControllerTest extends ApplicationTest {
  private BackorGoController controller;
  private final ClientApp clientApp = mock(ClientApp.class);

  @Override
  public void start(Stage stage) {
    controller = new BackorGoController(clientApp, stage, new ViewFactory(clientApp, stage), "Awesome Test", "Yes", "No", "UpgradeLimit");
  }
}
