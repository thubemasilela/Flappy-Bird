import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bird extends Entity {
    private double velocityY;
    private final double gravity = 0.6; // Increased gravity for faster fall
    private final double jumpStrength = -12; // Slightly stronger jump to compensate
    private static final double size = 30;

    public Bird(double initialX, double initialY) {
        super(initialX, initialY, size, size);
        this.velocityY = 0;

        this.body.setOpacity(1);
        this.body.setFill(Color.YELLOW);
    }

    public void jump() {
        velocityY = jumpStrength; // Directly set to jumpStrength for a consistent jump effect
    }

    public void update() {
        velocityY += gravity; // Apply gravity
        this.addToYLocation(velocityY); // Update Y position
    }

    public Rectangle getBody() {
        return super.getBody();
    }
}
