import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Game extends Pane {
    private AnimationTimer timer;
    private Canvas canvas;
    private Bird bird;
    private List<Pipe> pipes;
    private boolean gameOver;
    private Button resetButton;
    private BorderPane layout;
    private  final double WIDTH;
    private  final double HEIGHT;
    private static final int PIPE_SPACING = 400;
    private double lastPipeXLocation = 0;
    private boolean gameStarted = false;
    private boolean isBusy=false;
    
	private long lastUpdate = 0;
	private long frameTimes = 0;
	private long frameCount = 0;
	private double fps = 0;
	
	 private Pipe lastCollidedPipe = null; // Store the last collided pipe
	
	private Stage mainStage;
	private int score;

    public Game(Stage primaryStage, double width, double height) {
    	WIDTH = width;
    	HEIGHT = height;
        canvas = new Canvas(width, height);
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK); // Set canvas background to black
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        
        bird = new Bird(WIDTH / 10, HEIGHT / 2);
        pipes = new ArrayList<>();
        gameOver = false;
        layout = new BorderPane();
        setupUI();

        // Add the bird directly to the layout
        layout.getChildren().add(bird.getBody());
        
        this.mainStage = primaryStage;
    }

    private void setupUI() {
        resetButton = new Button("Reset");
        resetButton.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                if (!isGameStarted()) {
                    startGame();  // Method to start the game if not started
                } else if (!isGameOver()) {
                    getBird().jump();  // Make the bird jump if the game is active
                }else {
                	resetGame();
                }
            }
        });

        layout.setCenter(canvas);
        layout.setTop(resetButton);
        getChildren().add(layout);
    }

    public void startGame() {
        if (!gameStarted) {
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    update(now);
                    render();
                }
            };
            timer.start();
            gameStarted = true;
            spawnInitialPipes(5);
        }
    }

    private void spawnInitialPipes(int numPipes) {
    	if(isBusy)return;
    	isBusy = true;
        for (int i = 0; i < numPipes; i++) {
            spawnPipe();
        }
        isBusy = false;
    }

    private void spawnPipe() {
    
        lastPipeXLocation += Pipe.PIPE_WIDTH + PIPE_SPACING;
        Pipe newPipe = new Pipe(lastPipeXLocation, HEIGHT);
        pipes.add(newPipe);
        newPipe.addToPane(layout);
    }

    private void update(long now) {
        if (!gameOver) {
            bird.update();
            if (bird.getBody().getY() > HEIGHT || bird.getBody().getY() < 0) {
                gameOver = true;
                timer.stop();
            }

            pipes.forEach(Pipe::update);
            pipes.removeIf(Pipe::mustRemove);
            checkCollisions();
            
            calculateFPS(now);
        }
    }

    private void render() {
        // Since all entities are using Rectangles that are already part of the scene, no need to redraw them here.
    	this.mainStage.setTitle(String.format("FPS: %.2f\t\t\tScore: %s", fps, score));
    }

    private void checkCollisions() {
    	int numPipesToSpawn = 0;
    	boolean canStopSearching=false;
        for (Pipe pipe : pipes) {
            ECollisionType collisionType = pipe.TestCollision(bird);
            switch (collisionType) {
                case GAMEOVER:
                	System.out.println("GameOver");
                    gameOver = true;
                    timer.stop();
                    canStopSearching=true;
                    break;
                case SPAWN:
                	if (lastCollidedPipe != pipe) {
	                	score += 1;
	                	System.out.println(score);
	                	numPipesToSpawn += 1;
	                	 canStopSearching=true;
	                	 lastCollidedPipe = pipe; // Update last collided pipe
            		}
                    break;
                default:
                    break;
            }
            
            if(canStopSearching) {
            	break;
            }
        }
        
        spawnInitialPipes(numPipesToSpawn);
    }

    public Bird getBird() {
        return bird;
    }

    private void resetGame() {
        bird = new Bird(WIDTH / 10, HEIGHT / 2);
        layout.getChildren().removeIf(node -> node instanceof Rectangle); // This removes all pipes.
        pipes.clear();
        lastPipeXLocation = WIDTH / 2;
        gameOver = false;
        gameStarted = false;
        score = 0;
        layout.getChildren().add(bird.getBody());
        startGame();
    }
    
    private void calculateFPS(long nanoDuration) {
		//only do the calculations when the animation loop has run 1 iteration
		if(lastUpdate>0)
		{
			//get the time difference
			long deltaNanos = nanoDuration - lastUpdate;
			//accumulate the time differences
			frameTimes += deltaNanos;
			//each time this loop runs an iteration it is considered 1 frame
			frameCount++;
			//only calculate the FPS once every 60 frames so we do not tie down processing.
			if(frameCount >= 60)
			{
				// get the frames per second
				fps = 1e9 /(frameTimes / frameCount);
				
			
				//reset our accumulators for the next 60 frames
				frameCount = 0;
				frameTimes = 0;
			}
		}
		//track the animation time
		lastUpdate = nanoDuration;
	}
    
    public boolean isGameStarted() {
        return gameStarted;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
