package Model;
/**
 * Class created to store currState as well as value associated with it 
 * 
 * @author Kevin
 *
 */
public class State {
	private double Value;
	private int X;
	private int Y;
	

	
	/**
	 * Initializes a object of the state class
	 * 
	 * @param value value stored in state
	 * @param x is x coordinate
	 * @param y is y coordinate
	 */
	public State(double value, int x, int y) {
		this.Value = value;
		this.X = x;
		this.Y = y;
	}



	/**
	 * Gets the value of the state
	 * 
	 * @return value of the state 
	 */
	public double getValue() {
		return Value;
	}



	/**
	 * sets the vlaue of the state
	 * 
	 * @param value new value
	 */
	public void setValue(double value) {
		this.Value = value;
	}

	/**
	 * gets the x coordinate of the state 
	 * 
	 * @return x Coordinate
	 */
	public int getX() {
		return X;
	}
	
	/**
	 * gets the y coordinate 
	 * 
	 * @return y coordinate
	 */
	public int getY() {
		return Y;
	}


	/**
	 * retrieves the default toString() for a given state
	 * 
	 * @return to string
	 */
	public String toString() {
		return this.getX() + " " + this.getY();
	}
	
	
}
