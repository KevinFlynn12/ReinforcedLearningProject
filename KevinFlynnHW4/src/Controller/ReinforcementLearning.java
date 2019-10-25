package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import Model.Map;
import Model.State;
/**
 * Class created to store reinforcement learning algorithms
 * 
 * @author Kevin
 *
 */
public class ReinforcementLearning {
	private static double EPISILON = 0.1;
	private static double ALPHA = 0.5;
	private static double GAMMA_DISCOUNT = 0.9;
	private static String[] OPITIMALPATH = { "0 3" , "0 2" , "1 2", "2 2 " , " 3 2", "4 2", "5 2" ,
			"6 2" , "7 2", "8 2", "9 2", "10 2", "11 2", "12 2"};

	/**
	 * Iterates over the map multiple times and determine the most optimal map for the agent to traverse
	 * 
	 */
	public void QLearning(){
		Map env = new Map();
		int steps = 0;
		int timesCalled = 0;

		for(int i=0; i<500; i++) {
			State agent = env.GetStartState(); 
			
			boolean done = false;
			System.out.println("Begin Fresh run");
			while(!done) {
				if(steps == 25) {
				//	return;
				}
				int action = eGreedyPolicy(env, agent);
				State nextState = env.nextAction(action, agent);
				int reward = (int)env.DetermineReward(nextState);
				double  td_target = reward + GAMMA_DISCOUNT * env.getLargestValue(nextState);
				double 	td_error = td_target - nextState.getValue();
			
				double newValue = nextState.getValue() + ALPHA * td_error;
				//System.out.println("Current Coordinates: " + agent.getX() + " " + agent.getY());
				//System.out.println("New Coordinates: " + nextState.getX() + " " + nextState.getY() + " New Value: " + newValue);
				if(agent.getX() == 0 && agent.getY() == 2) {
					System.out.println(agent.getValue());
				}
				env.updateState(agent, newValue, action);
				if(reward == -100 || reward == 1.0 ) {
					done = true;
				}
				else {
					agent = nextState;
				}
				steps++;
			}
		}
	}
	
	
	private int eGreedyPolicy(Map map, State currState) {
		Random random = new Random();
		double decide_explore_exploit  = Math.random();
		int action = -1;
		if(decide_explore_exploit < EPISILON) {
			action = map.determineExploreAction(currState);
			
		}
		else {
			action = map.determineNextAction(currState); 
		}
		
		return action;
	}
}
