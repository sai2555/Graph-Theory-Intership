package com.saimaddhi.graph;
import edu.princeton.cs.algs4.StdDraw;

/**
 * Represents the Add Edge button on the UI
 * @author saimaddhi
 *
 */
public class AddEdgeButton {
	/**
	 * This is the x Coordinate of the button
	 */
	private double xCoor;
	/**
	 * This is the y coordinate of the button
	 */
	private double yCoor;
	/**
	 * This is the radius of the button
	 */
	private double radius;
	/**
	 * This boolean reflects if the mouse is over the button
	 */
	private boolean lightGray;
	/**
	 * This boolean is true if the button was clicked and still in the clicked state
	 */
	private boolean clicked;
	/**
	 * True if the button is allowed to be clicked
	 */
	private boolean isClickable;
	/**
	 * This is true if the button is being draw for the first time on the UI
	 */
	private boolean init;
	/**
	 * Constructor that initializes the button with x, y, and, a radius
	 * @param xCoor x coordinate of button
	 * @param yCoor y coordinate of button
	 * @param radius radius of the button
	 */
	public AddEdgeButton(double xCoor, double yCoor, double radius) {
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		this.radius = radius;
		this.lightGray = true;
		this.clicked = false;
		this.isClickable = true;
		this.init = true;
	}
	/**
	 * In the duration of the program, this method runs and tracks the mouses position in relation to the button
	 * @return returns true if the button is still in a clicked state
	 */
	public boolean checkMouse() {
		if(clicked) {
			if(Math.abs(StdDraw.mouseX() - xCoor) < 50 && Math.abs(StdDraw.mouseY() - yCoor) < 50) {
				if(init || !lightGray) {
					StdDraw.setPenColor(StdDraw.DARK_GRAY);
					StdDraw.filledCircle(xCoor, yCoor, 50);
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.circle(xCoor, yCoor, 50);
					StdDraw.setPenColor(StdDraw.WHITE);
					StdDraw.text(xCoor, yCoor, "+E");
					lightGray = true;
				}
			} else {
				if(init || lightGray) {
					isClickable = true;
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.filledCircle(xCoor, yCoor, radius);
					StdDraw.setPenColor(StdDraw.WHITE);
					StdDraw.text(xCoor, yCoor, "+E");
					lightGray = false;
				}
			}
			if(isClickable && StdDraw.mousePressed() && lightGray) {
				StdDraw.setPenColor(StdDraw.WHITE);
				draw();
				clicked = false;
				lightGray = false;
				isClickable = false;
				return false;
			} else {
				return true;
			}
		} else {
			if(Math.abs(StdDraw.mouseX() - xCoor) < radius && Math.abs(StdDraw.mouseY() - yCoor) < radius) {
				if(init || !lightGray) {
					lightGray = true;
					StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
					draw();
				}
			} else {
				if(init || lightGray) {
					isClickable = true;
					lightGray = false;
					StdDraw.setPenColor(StdDraw.WHITE);
					draw();
				}
			}
			if(isClickable && StdDraw.mousePressed() && lightGray) {
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.filledCircle(xCoor, yCoor, radius);
				StdDraw.setPenColor(StdDraw.WHITE);
				StdDraw.text(xCoor, yCoor, "+E");
				clicked = true;
				lightGray = false;
				isClickable = false;
				return true;
			} else {
				return false;
			}
		}	
	}
	/**
	 * This method is called the by the Grapher class which draws the UI. The method resets the button, state and color and all
	 */
	public void reset() {
		this.init = true;
		this.clicked = false;
		this.isClickable = false;
		this.checkMouse();
		this.init = false;
	}
	/**
	 * This is the draw method for the button that draws the button based on x, y, and radius, parameters
	 */
	public void draw() {
		StdDraw.filledCircle(xCoor, yCoor, radius);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.circle(xCoor, yCoor, radius);
		StdDraw.text(xCoor, yCoor, "+E");
	}
}
