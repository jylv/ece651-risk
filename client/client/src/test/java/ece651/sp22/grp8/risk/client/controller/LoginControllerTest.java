package ece651.sp22.grp8.risk.client.controller;
import ece651.sp22.grp8.risk.GamePrompt;
import ece651.sp22.grp8.risk.Territory;
import ece651.sp22.grp8.risk.client.ClientApp;
import ece651.sp22.grp8.risk.client.ViewFactory;
import javafx.application.Platform;
import javafx.scene.control.Button;
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

public class LoginControllerTest extends ApplicationTest {
    private LoginController controller;
    private final ClientApp clientApp  = mock(ClientApp.class);

    @Override
    public void start(Stage primaryStage) {
        try {
            controller = new LoginController(clientApp, primaryStage, new ViewFactory(clientApp, primaryStage));
            initMock();
        }catch (IOException ignored) {
        }
    }

    /**
     * A helper function to init mock clientApp
     * @throws IOException
     */
    private void initMock() throws IOException {
        when(clientApp.login("test","test")).thenReturn(GamePrompt.OK);
        when(clientApp.login("test","testtt")).thenReturn(GamePrompt.NOUSER);
        when(clientApp.register("test2","test2")).thenReturn(GamePrompt.OK);
        when(clientApp.register("","")).thenReturn(GamePrompt.BADREGISTER);
        when(clientApp.joinGame("5000")).thenReturn(GamePrompt.OK);
        when(clientApp.joinGame("1000")).thenReturn(""+
                "The game with ID 1000 does not exist!\n"+
                "Try another one!\n");
        when(clientApp.getRemainUnits()).thenReturn(8);
        when(clientApp.getNextTerritory()).thenReturn(new Territory("Elantris",4,6,4));
        ArrayList<Long> allGameList = new ArrayList<>();
        allGameList.add(5000L);
        allGameList.add(5001L);
        when(clientApp.getAllGames()).thenReturn(allGameList);
        ArrayList<Long> myGameList = new ArrayList<>();
        when(clientApp.getMyGames()).thenReturn(myGameList);
    }

    
    @Test
    public void test_fail_login(){
        Platform.runLater(() -> {
            try {
                controller.username = new TextField("test");
                controller.password = new TextField("testtt");
                controller.login();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        Text actual = (Text) Window.getWindows().get(0).getScene().lookup("#prompt_content");
        assertEquals(GamePrompt.NOUSER, actual.getText());
    }

    @Test
    public void test_pass_register(){
        Platform.runLater(() -> {
            controller.username = new TextField("test2");
            controller.password = new TextField("test2");
            controller.register();
        });
        WaitForAsyncUtils.waitForFxEvents();
        Text actual = (Text) Window.getWindows().get(0).getScene().lookup("#prompt_content");
        assertEquals(GamePrompt.OK, actual.getText());
    }

    @Test
    public void test_pass_login(){
        Platform.runLater(() -> {
            try {
                controller.username = new TextField("test");
                controller.password = new TextField("test");
                controller.login();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(GamePrompt.OK, controller.printOut);
    }

  @Disabled
    @Test
    public void test_redirect(){
        Platform.runLater(() -> {
            try {
                controller.redirect();
                Button btn = (Button) Window.getWindows().get(0).getScene().lookup("#5000");
                btn.fire();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals("You join the game 5000.\n", controller.printOut);
    }
  @Disabled
    @Test
    public void test_pass_joinGameByBtn(){
        Platform.runLater(() -> {
            try {
                controller.joinGameByBtn("5000");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals("You join the game 5000.\n", controller.printOut);
    }


    @Test
    public void test_fail_joinGameByBtn(){
        Platform.runLater(() -> {
            try {
                controller.joinGameByBtn("1000");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals("The game with ID 1000 does not exist!\n" +
                "Try another one!\n", controller.printOut);
    }


     


}
