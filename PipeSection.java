import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PipeSection extends Entity {
	//determine if you are a trigger to load more pipes or simply an obstacle
	private boolean isTrigger;


	
	public PipeSection(boolean isTrigger, double xLocation, double yLocation, double width, double height) {
		super(xLocation, yLocation, width, height);
		this.isTrigger = isTrigger;
		
		//setup how the pipes will look
		if(isTrigger) {
			this.body.setOpacity(0);
		}else {
			this.body.setOpacity(1);
			this.body.setFill(Color.GREEN);
		}
	}
	
	public void updateXLocation(double speed)
	{
		this.addToXLocation(speed);
		
	}
	
	public Rectangle getBody() {
	    return this.body;
	}
	

	

}
