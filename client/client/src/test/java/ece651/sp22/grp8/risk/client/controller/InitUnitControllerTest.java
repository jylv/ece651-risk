package ece651.sp22.grp8.risk.client.controller;

import ece651.sp22.grp8.risk.GamePrompt;
import ece651.sp22.grp8.risk.Territory;
import ece651.sp22.grp8.risk.client.ClientApp;
import ece651.sp22.grp8.risk.client.ViewFactory;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InitUnitControllerTest extends ApplicationTest {
    private final ClientApp clientApp  = mock(ClientApp.class);
    ViewFactory factory;
    Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws IOException {
        factory = new ViewFactory(clientApp, primaryStage);
        Scene scene = factory.generateGallery(new ArrayList<>(),new ArrayList<>());
        primaryStage.setScene(scene);
        this.primaryStage = primaryStage;
        initMock();
    }

    private InitUnitController generateController(int remainUnits, Territory nextTerritory) {
        return new InitUnitController(clientApp, primaryStage, null,factory,remainUnits,nextTerritory);
    }


    /**
     * A helper function to init mock clientApp
     * @throws IOException
     */
    private void initMock() throws IOException {
        ArrayList<Long> allGameList = new ArrayList<>();
        allGameList.add(5000L);
        allGameList.add(5001L);
        when(clientApp.getAllGames()).thenReturn(allGameList);
        ArrayList<Long> myGameList = new ArrayList<>();
        when(clientApp.getMyGames()).thenReturn(myGameList);
        when(clientApp.getNextTerritory()).thenReturn(new Territory("Elantris",4,6,4));
        when(clientApp.joinGame("5000")).thenReturn(GamePrompt.OK);
        when(clientApp.joinGame("5001")).thenReturn("The game with ID 5001 has already started!\n" +
        "Try another one!\n");
        when(clientApp.joinGame("1000")).thenReturn("The game with ID 1000 does not exist!\n" +
                "Try another one!\n");
        when(clientApp.getRemainUnits()).thenReturn(2);
        when(clientApp.initialUnits(clientApp.getNextTerritory(),6,Integer.parseInt("2"))).thenReturn(GamePrompt.OK);
        when(clientApp.initialUnits(clientApp.getNextTerritory(),11,Integer.parseInt("2"))).thenReturn(GamePrompt.LACK_UNITS);
        when(clientApp.initialUnits(clientApp.getNextTerritory(),8,Integer.parseInt("2"))).thenReturn(GamePrompt.INITIAL_END_EARLY);
        when(clientApp.initialUnits(clientApp.getNextTerritory(),-1,Integer.parseInt("2"))).thenThrow(new NumberFormatException());

    }

/*
    @Test
    public void test_setInitUnit_OK(){
        Territory nextTerritory = clientApp.getNextTerritory();
        InitUnitController controller = generateController(6,nextTerritory);
        Platform.runLater(() -> {
            controller.unitNum = new TextField("2");
            controller.unitPrompt = new Label();
            controller.setInitUnit();
        });
        WaitForAsyncUtils.waitForFxEvents();
        //8-6=2
        assertEquals("You remain 2 units.\n" +
                "How many units do you want to\nput on "+nextTerritory.getName()+" ?\n",  controller.unitPrompt.getText()); //go to next page
    }

    @Test
    public void test_setInitUnit_lack(){
        Territory nextTerritory = clientApp.getNextTerritory();
        InitUnitController controller = generateController(11,nextTerritory);
        Platform.runLater(() -> {
            controller.unitNum = new TextField("2");
            controller.unitPrompt = new Label();
            controller.setInitUnit();
        });
        WaitForAsyncUtils.waitForFxEvents();
        //8-6=2
        Text text = (Text) Window.getWindows().get(0).getScene().lookup("#prompt_content");
        assertEquals(GamePrompt.LACK_UNITS, text.getText()); //prompt
    }

    @Test
    public void test_setInitUnit_early(){
        Territory nextTerritory = clientApp.getNextTerritory();
        InitUnitController controller = generateController(8,nextTerritory);
        Platform.runLater(() -> {
            controller.unitNum = new TextField("2");
            controller.windowStage = new Stage();
            controller.unitPrompt = new Label();
            controller.setInitUnit();
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(0, Window.getWindows().size()); //all windows are closed
    }

    @Test
    public void test_setInitUnit_fail(){
        Territory nextTerritory = clientApp.getNextTerritory();
        InitUnitController controller = generateController(-1,nextTerritory);
        Platform.runLater(() -> {
            controller.unitNum = new TextField("2");
            controller.unitPrompt = new Label();
            controller.setInitUnit();
        });
        WaitForAsyncUtils.waitForFxEvents();
        //8-6=2
        Text text = (Text) Window.getWindows().get(0).getScene().lookup("#prompt_content");
        assertEquals(GamePrompt.INVALIDINPUT, text.getText()); //prompt
    }

 */

}
