package ece651.sp22.grp8.risk.client.controller;

import ece651.sp22.grp8.risk.GamePrompt;
import ece651.sp22.grp8.risk.client.ClientApp;
import ece651.sp22.grp8.risk.client.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GalleryController implements Initializable {
    ClientApp clientApp;
    Stage primaryStage;
    ViewFactory factory;
    @FXML TextField joinGameID;
    public GalleryController(ClientApp clientApp, Stage primaryStage, ViewFactory factory) {
        this.clientApp = clientApp;
        this.primaryStage = primaryStage;
        this.factory = factory;
    }

    public void newGame() throws IOException {
        ChoiceBox<String> choiceBox = (ChoiceBox<String>) primaryStage.getScene().lookup("#choiceBox");
        clientApp.createGame(choiceBox.getValue());
        ArrayList<Long> allGameList = clientApp.getAllGames();
        ArrayList<Long> myGameList = clientApp.getMyGames();
        Scene scene = factory.generateGallery(allGameList, myGameList);
        primaryStage.setScene(scene);
        for(long gameID : allGameList){
            Button btn = (Button) scene.lookup("#"+gameID);
            btn.setOnAction((event) -> {
                try {
                    joinGameByBtn(String.valueOf(gameID));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        primaryStage.show();
    }

    private void joinGameByBtn(String gameID) throws IOException {
        String result = clientApp.joinGame(gameID);
        if (result.equals(GamePrompt.REJOIN)||result.equals(GamePrompt.NEWGAME)) {
            primaryStage.setScene(factory.generateAction(result));
            primaryStage.show();
        } else {
            Stage stage = factory.generatePopupWindow(primaryStage, "Notice", result);
            stage.show();
        }
    }

    public void joinGameByID() throws IOException {
        joinGameByBtn(joinGameID.getText());
    }

    void redirect() throws IOException {
        primaryStage.setScene(factory.generateAction(""));
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
