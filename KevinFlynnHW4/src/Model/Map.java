package Model;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Class created to store, retrieved and determine actions of the collected state
 * 
 * @author Kevin
 *
 */
public class Map {
	private static double FAIL_STATE_REWARD = -100;
	private static double GOAL_STATE_REWARD = 1;
	private static double INITALIZED_VALUE = 0.0;
	private State startState;
	private State goalState;
	private static int MAX_X = 12;
	private static int MAX_Y = 3;
	private  HashMap<String, State> states;
	private HashMap<String, State[]> stateDirections;
	private  HashMap<String, State> FailStates;
	private static int UP = 0;
	private static int DOWN = 1;
	private static int LEFT = 2;
	private static int RIGHT = 3;
	private static String[] OPITIMALPATH = { "0 3" , "0 2" , "1 2", "2 2 " , " 3 2", "4 2", "5 2" ,
			"6 2" , "7 2", "8 2", "9 2", "10 2", "11 2", "12 2"};

	
	/**
	 * Initializes a instance a object of the map class
	 * 
	 */
	public Map() {
		this.states = new HashMap<>();
		this.stateDirections = new HashMap<>();
		this.FailStates = new HashMap<>();
		 this.startState = new State(0.0,0,3);
		 this.goalState = new State(0.0, 12,3);
		 this.loadStates();
		 this.loadAllStates();
	}
	
	/**
	 * gets the start state
	 *  
	 * @return start state
	 */
	public State GetStartState() {
		return this.startState;
	}
	
	private void loadStates() {
		for(int y=0; y< MAX_Y+ 1; y++) {
			for(int x=0; x< MAX_X + 1; x++) {
				State state = new State(INITALIZED_VALUE, x, y);
				if( x > 0 && x < MAX_X && y== MAX_Y) {
					this.FailStates.put(state.toString(),state);
				}
				this.states.put(state.toString(),state);
				State[] directions = {null,null,null,null};
				this.stateDirections.put(state.toString(), directions);
			}
		}
	}
	
	
	private void loadAllStates() {
		for(String key: this.stateDirections.keySet()) {
			State currState = this.states.get(key);
			State[] directions = {this.getUp(currState), 
					this.getDown(currState), this.getLeft(currState),
					this.getRight(currState)};
			this.stateDirections.replace(key, directions);
		}
	}
	
	
	private State getLeft(State currState) {
		int x = currState.getX() - 1;
		String key = x + " " + currState.getY();
		if(this.states.containsKey(key)) {
			return this.states.get(key);
		}		
		return null;
	}
	private State getRight(State currState) {
		int x = currState.getX() + 1;
		String key = x + " " + currState.getY();
		if(this.states.containsKey(key)) {
			return this.states.get(key);
		}		
		return null;
	}
	
	private State getDown(State currState) {
		int y = currState.getY() + 1;
		String key = currState.getX() + " " + y;
		if(this.states.containsKey(key)) {
			return this.states.get(key);
		}
		return null;
	}

	
	
	
	/**
	 * updates the state passed in
	 * 
	 * @param state the state you wish to alter
	 * @param newValue is the value you wish to update
	 */
	public void updateState(State state, double newValue, int direction) {
		
		State[] values = this.stateDirections.get(state.toString());
		State currState = values[direction];
		currState.setValue(newValue);
		values[direction] = currState;
		this.stateDirections.replace(state.toString(), values);
	}
	
	/**
	 * Determines the reward for a given state
	 * @param nextState is the state you wish to to determine 
	 * @return
	 */
	public double DetermineReward(State nextState) {
		double reward = -1;
		if(this.FailStates.containsKey(nextState.toString())) {
			reward = FAIL_STATE_REWARD;
		}
		else if(nextState.toString().equals(this.goalState.toString())){
			reward = GOAL_STATE_REWARD;
		}
		return reward;
	}
	
	/**
	 * gets a random value between 0 and 3 then checks the current state has a valid state in the 
	 * that given direction if it is not it recursively finds another value
	 * 
	 * @param currState is the state you are currently
	 * @return a random value between o and 3
	 */
	public int determineExploreAction(State currState) {
		Random random = new Random();
		int action = random.nextInt(4);
		if(this.stateDirections.get(currState.toString())[action] == null) {
			action = determineExploreAction(currState);
		}
		
		return action;
	}
	
	/**
	 * Determines the next action the current state should take
	 * 
	 * @param currState is the state the current agent is at
	 * @return the determined action
	 */
	public int determineNextAction(State currState) {
	
		int action = -200;
		double maxValue = -200;
		String key = currState.toString();
		if(this.stateDirections.get(key)[DOWN] != null&& this.stateDirections.get(key)[DOWN].getValue() >= maxValue) {
			action = DOWN;
			maxValue =this.stateDirections.get(currState.toString())[DOWN].getValue();
		}
		if(this.stateDirections.get(key)[RIGHT] != null && this.stateDirections.get(key)[RIGHT].getValue() >= maxValue) {
			action = RIGHT;
			maxValue =this.stateDirections.get(currState.toString())[RIGHT].getValue();
		}
		if(this.stateDirections.get(key)[LEFT] != null && this.stateDirections.get(key)[LEFT].getValue() >= maxValue) {
			action = LEFT;
			maxValue =this.stateDirections.get(currState.toString())[LEFT].getValue();
		}
		if(this.stateDirections.get(key)[UP] != null && this.stateDirections.get(key)[UP].getValue() >= maxValue) {
			action = UP;
		}
			
		return action;
	}
	
	/**
	 * gets the largest state that is in the direction of the state  
	 * @param currState is the state you are currently in
	 * @return largest value that is a direction of the state 
	 */
	public double getLargestValue(State currState) {
		double maxValue = -200;
		String key = currState.toString();
		if(this.states.containsValue(this.getUp(currState))&& this.stateDirections.get(key)[UP].getValue() >= maxValue) {
			maxValue =this.stateDirections.get(currState.toString())[UP].getValue();
		}
		if(this.states.containsValue(this.getDown(currState)) && this.stateDirections.get(key)[DOWN].getValue() >= maxValue) {
			maxValue =this.stateDirections.get(currState.toString())[DOWN].getValue();
		}
		if(this.states.containsValue(this.getLeft(currState)) && this.stateDirections.get(key)[LEFT].getValue() >= maxValue) {
			maxValue =this.stateDirections.get(currState.toString())[LEFT].getValue();
		}
		if(this.states.containsValue(this.getRight(currState)) && this.stateDirections.get(key)[RIGHT].getValue() >= maxValue) {
			maxValue =this.stateDirections.get(currState.toString())[RIGHT].getValue();
		}
			
		return maxValue;
	}
	
	/**
	 * gets the next state based on the direction and the currentState
	 * @param direction the direction you want to the next to be 
	 * @param currState the current state you are at
	 * @return the state in the given direction
	 */
	public State nextAction(int direction,State currState)  {
		String key = currState.toString();
        if(UP == direction) {
        	System.out.println("Going Up");

        }
        else if(DOWN == direction) {
        	System.out.println("Going Down");

        }
        else if(LEFT == direction) {
        	System.out.println("Going Left");

        }
        else if(RIGHT == direction){
        	System.out.println("Going Right");
        }
   
       return this.stateDirections.get(key)[direction];

	}
	
	
	private State getUp(State currState) {
		int y = currState.getY() - 1;
		String key = currState.getX() + " " + y;
		if(this.states.containsKey(key)) {
			return this.states.get(key);
		}	
		return null;
	}
	

}
