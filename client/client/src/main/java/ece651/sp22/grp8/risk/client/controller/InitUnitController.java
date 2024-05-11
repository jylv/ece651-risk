package ece651.sp22.grp8.risk.client.controller;

import ece651.sp22.grp8.risk.GamePrompt;
import ece651.sp22.grp8.risk.Territory;
import ece651.sp22.grp8.risk.client.ClientApp;
import ece651.sp22.grp8.risk.client.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InitUnitController {
    @FXML Label unitPrompt;
    @FXML TextField unitNum;
    private final ClientApp clientApp;
    private final Stage primaryStage;
    Stage windowStage;
    private final ViewFactory factory;
    private int remainUnits;
    private Territory nextTerritory;

    public InitUnitController(ClientApp clientApp, Stage primaryStage, Stage windowStage, ViewFactory factory, int remainUnits, Territory nextTerritory)  {
        this.clientApp = clientApp;
        this.primaryStage = primaryStage;
        this.windowStage = windowStage;
        this.factory = factory;
        this.remainUnits = remainUnits;
        this.nextTerritory = nextTerritory;
    }

    public void setInitUnit() {
        String result;
        try{
            result = clientApp.initialUnits(nextTerritory,remainUnits,Integer.parseInt(unitNum.getText()));
            if(result.equals(GamePrompt.OK)){
                this.remainUnits = clientApp.getRemainUnits();
                this.nextTerritory = clientApp.getNextTerritory();
                unitPrompt.setText("You remain "+remainUnits+" units.\n" +
                        "How many units do you want to\nput on "+nextTerritory.getName()+" ?\n");
            }else if(result.equals(GamePrompt.LACK_UNITS)){
                factory.generatePopupWindow(primaryStage,"Notice",result).show();
            }else if(result.equals(GamePrompt.INITIAL_END_EARLY)){
                windowStage.close();
            }
        }catch(NumberFormatException e){
            factory.generatePopupWindow(primaryStage,"Notice",GamePrompt.INVALIDINPUT).show();
        }
    }
}
