package ece651.sp22.grp8.risk.client;
import java.io.IOException;
import ece651.sp22.grp8.risk.GamePrompt;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class App extends Application {

  @Override
  public void init() {
  }

    @Override
    public void start(Stage primaryStage) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        ClientApp clientApp = new ClientApp(GamePrompt.HOST, GamePrompt.PORT, input, System.out);
        ViewFactory factory = new ViewFactory(clientApp, primaryStage);
        Scene loginScene = factory.generateLoginScene();
        primaryStage.setScene(loginScene);
        primaryStage.show();
        primaryStage.setTitle(GamePrompt.TITLE);
        primaryStage.setAlwaysOnTop(true);
    }

  public static void main(String[] args) {
    launch(args);
  }
}
