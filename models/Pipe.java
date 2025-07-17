package models;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Pipe {
	private double xLocation;
	private boolean mustRemove;
	//A pipe will consist of 3 sections.
	private PipeSection top;
	private PipeSection middle;
	private PipeSection bottom;
	
	private static Random rand = new Random();
	

    public static final double PIPE_WIDTH = 50;

    private final double pipeGapHeight = 200;
    private static final double speed = -3;

    public Pipe(double x, double sceneHeight) {
    	xLocation = x;
    	double paneHeight = sceneHeight;
    	double maxTopHeight = paneHeight/2;
    	double topHeight = rand.nextDouble(maxTopHeight);
    	top = new PipeSection(false, x, 0, PIPE_WIDTH, topHeight);
    	middle = new PipeSection(true, x, topHeight, PIPE_WIDTH, pipeGapHeight);
    	double bottomY = topHeight + pipeGapHeight;
    	double bottomHeight = paneHeight - bottomY;
    	bottom = new PipeSection(false, x, bottomY, PIPE_WIDTH, bottomHeight);
    	
       
    }
    
    public void addToPane(Pane pane) {
    	// Add the pipe sections to the pane
    	 pane.getChildren().addAll(top.getBody(), middle.getBody(), bottom.getBody());
    	
    }

    public void update() {
    	
    	xLocation += speed;
    	
    	if(this.xLocation + PIPE_WIDTH < 0) {
			this.mustRemove = true;
			return;
		}
    	
        top.addToXLocation(speed);
        middle.addToXLocation(speed);
        bottom.addToXLocation(speed);
    }


    public ECollisionType TestCollision(Entity other) {
    	//if it was the top or bottom, gameover
    	if(top.checkCollision(other) || bottom.checkCollision(other)) {
    		return ECollisionType.GAMEOVER;
    		//if it was the middle, spawn a new pipe
    	}else if(middle.checkCollision(other)) {
    	
    		return ECollisionType.SPAWN;
    	}
    	return ECollisionType.NONE;
    }
    
	public boolean mustRemove() {
		return this.mustRemove;
	}
	

    
}
