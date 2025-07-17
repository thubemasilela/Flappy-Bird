import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Main extends Application {
	private final int WIDTH = 800;
	private final int HEIGHT = 600;


    @Override
    public void start(Stage primaryStage) {
        Game game = new Game(primaryStage, WIDTH, HEIGHT);
        Scene scene = new Scene(game, WIDTH, HEIGHT);

        primaryStage.setTitle("Flappy Bird");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    
}
