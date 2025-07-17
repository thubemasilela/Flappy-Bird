package models;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Entity {
	protected Rectangle body;
	
	

	public Entity(double xLocation, double yLocation, double width, double height) {
		super();
		this.body = new Rectangle(xLocation, yLocation, width, height);

	}
	
	public boolean checkCollision(Entity other) {
		 return this.body.getBoundsInParent().intersects(other.body.getBoundsInParent());
    
	}
	
	public void addToXLocation(double amount) {
		double newValue = amount + this.body.getX();
		this.body.setX(newValue);
	}
	
	public void addToYLocation(double amount) {
		double newValue = amount + this.body.getY();
		this.body.setY(newValue);
	}
	
	public double getXLocation() {
		return this.body.getX();
	}
	
	public double getWidth() {
		return this.body.getWidth();
	}

	public Rectangle getBody() {
		return body;
	}
	
	
}
