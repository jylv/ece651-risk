package ece651.sp22.grp8.risk.client.controller;

import ece651.sp22.grp8.risk.GamePrompt;
import ece651.sp22.grp8.risk.client.ClientApp;
import ece651.sp22.grp8.risk.client.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {
    ClientApp clientApp;
    Stage primaryStage;
    ViewFactory factory;
    @FXML TextField username;
    @FXML TextField password;
    String printOut; //for test

    public LoginController(ClientApp clientApp, Stage primaryStage, ViewFactory factory) {
        this.clientApp = clientApp;
        this.primaryStage = primaryStage;
        this.factory = factory;
    }

    public void register() {
        printNRecord("register.\n");
        String result = clientApp.register(username.getText(),password.getText());
        factory.generatePopupWindow(primaryStage,"Prompt",result).show();
    }

    public void login() throws IOException {
        printNRecord("login.\n");
        String result = clientApp.login(username.getText(),password.getText());
        printNRecord(result);
        if(result.equals(GamePrompt.OK)){
            redirect();
        }else{
            factory.generatePopupWindow(primaryStage,"Prompt",result).show();
        }
    }

    private void printNRecord(String str){
        printOut = str;
        System.out.print(str);
    }

    void redirect() throws IOException {
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

    public void joinGameByBtn(String gameID) throws IOException {
        String result = clientApp.joinGame(gameID);
        if (result.equals(GamePrompt.REJOIN)||result.equals(GamePrompt.NEWGAME)) {
            printNRecord("You join the game " + gameID + ".\n");
            primaryStage.setScene(factory.generateAction(result));
            primaryStage.show();
        } else {
            printNRecord(result);
            Stage stage = factory.generatePopupWindow(primaryStage, "Notice", result);
            stage.show();
        }
    }
}
