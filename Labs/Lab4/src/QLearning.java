import RobotClient.MapGUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Arrays;


public class QLearning {

	public double QFunction(double state, int action){
		double qValue = 0.0;
		
		return qValue;
	}
	
    static MapGUI world = new MapGUI("maze.txt");
    static Random rand = new Random();
    static int rowNum;
    static int colNum;
    
    public static void main(String[] args){
    
        while (world.getMap() == null) {
            try {
                Thread.sleep(200);
            } catch (Exception ex) {
            }
        }

        int[][] map = world.getMap();
        rowNum = map.length;
        colNum = map[0].length;
        int roboti = world.getRobotLocation()[0];
        int robotj = world.getRobotLocation()[1];
        int goali = world.getGoal()[0];
        int goalj = world.getGoal()[1];
        
        //Hard insert goal into map
        map[goali][goalj] = 2;

        System.out.println("Goal is at " + goali + "," + goalj + "\t and Robot is at " + roboti + "," + robotj);
        
        //Used for debugging
        for(int i = 0; i < rowNum; i++){
        	for(int j = 0; j < colNum; j++){
        		System.out.print(map[i][j]);
        	}
        	System.out.println();
        }
        
        
        

        // The code below only enumerates the map for visualization
        int id = 1;
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                if (!world.isWall(i, j)) {
                    world.setId(id, i, j);
                    world.setText("" + id, i, j);
                    id++;
                }
            }
        }
        
        
        // Each cell (including obstacles) is a state (rowNum*colNum states)
        // Moving in each coordinate direction is an action (4 actions)
        
        // Useful variables:
        //      -> roboti, robotj: robot location from bottom left corner
        //      -> goali, goalj: goal location from bottom left corner
        //      -> rowNum, colNum: number of rows and columns in the map
              
        
        double[][] rewards = new double[rowNum][colNum];
        double[][][] Q = new double[rowNum][colNum][4];
        double currentStateValue = 0.0;
        //double nextStateValue = 0.0;
        double maxStateValue = 0.0;
        double currentReward = 0.0;
        
        
        // Set up Rewards for each state
        //      -> Give 100 to the goal location
        //		-> -100 for walls
        //      -> And -1 for the rest of the states
        
       for(int i = 0; i < rowNum; i++){
    	   for(int j = 0; j < colNum; j++){
    		   if(i == goali && j == goalj)
    			   rewards[i][j] = 100;
    		   else 
    			   rewards[i][j] = -1;
    	   }
       }
       
       //Hard insert goal into rewards
       rewards[goali][goalj] = 100.00;
      
       System.out.println();
       
       //Used for debugging
       for(int i = 0; i < rowNum; i++){
       	for(int j = 0; j < colNum; j++){
       		System.out.print(rewards[i][j] + " ");
       	}
       	System.out.println();
       }
       
       //Q-Table Construction
       //double[][][] Q = new double[rowNum][colNum][4];
       /*
        * Each row -> A State:	s0
        * 						s1
        * 						s2
        * Each col -> an Action:	N E S W
        */
       
       // Initialize Q-table to zero's
       for(int i = 0; i < rowNum; i++)
    	   for(int j = 0; j < colNum; j++)
    		   for(int k = 0; k < 4; k++)
    			   Q[i][j][k] = 0.0;
       
       // Simulation       
       int action = 2;
       int maxSteps = 400;
       int stepCounter = 0;
       double gamma = .4;
       double alpha = 0.8;
       int maxEpisodeLength = 50;
   	   int startX = world.getRobotLocation()[0];
   	   int startY = world.getRobotLocation()[1];
   	   int firstAction;
   	   //int startZ;
   	List<Integer> intList = new ArrayList<Integer>();

       //beginning of q-learning
       //for each episode
       while (stepCounter < maxSteps){
           boolean episodeGoal = false;
           boolean validStartPos = false;
           startX = 0;
           startY = 0;
           action = 2;
           
           world.moveRobot(startX, startY, 0);
           
           firstAction = action;
           
           
           int episodeLength = 0;
           
    	   stepCounter++; 
    	System.out.println("stepCounter = " + stepCounter);
    	
    	
    	//select a random initial state
    	
    	/*
    	while(validStartPos == false){
    		episodeGoal = false;
    		startX = rand.nextInt(9);
    		startY = rand.nextInt(9);
    		
    		if(world.getId(startX, startY) == 0)
    			validStartPos = false;
    		else{
    			roboti = startX;
    			robotj = startY;
    			validStartPos = true;

    		}
    	}*/
    	roboti = 0;
    	//startX = roboti;
    	//System.out.println("OS STARTX = " + startX);
    	robotj = 0;
    	//startY = robotj;
    	//world.moveRobot(roboti, robotj, 0);

    	//firstAction = rand.nextInt(4); 
    	firstAction = 2;
    	int[] movementArray = {-1, -1 , -1, -1};
    	
    	
    	
    	currentStateValue = Q[startX][startY][firstAction];
       
       // do while the goal state hasn't been reached
       while(episodeGoal == false && episodeLength < maxEpisodeLength){
       	//startX = roboti;
       	//System.out.println("IS STARTX = " + startX);
       	//startY = robotj;
    	   episodeLength++;
    	   System.out.println("EPISODE LENGTH = " + episodeLength);
    	   
    	   for(int i = 0; i < 4; i++)
    		   movementArray[i] = -1;
      
       
       //GET POSSIBLE ACTIONS FROM CURRENT STATE 
       
       //along bottom wall
       if(roboti == 0){
    	   //bottom left corner
    	   if(robotj == 0){
    		   if(!world.isWall(roboti + 1, robotj))
    			   movementArray[0] = 0;
    			   //System.out.println("MOVE UP");
    		   if(!world.isWall(roboti, robotj + 1))
    			   movementArray[3] = 3;
    			   //System.out.println("MOVE RIGHT");
    	   }
    	   //bottom right corner
    	   else if(robotj == colNum - 1){
    		   if(!world.isWall(roboti + 1, colNum - 1))
    			   movementArray[0] = 0;
    			   //System.out.println("MOVE UP");
    		   if(!world.isWall(roboti, robotj - 1))
    			   movementArray[2] = 2;
    			   //System.out.println("MOVE LEFT");
    	   }
    	   //on bottom wall but not on a corner
    	   else{
    		   if(!world.isWall(roboti + 1, robotj))
    			   movementArray[0] = 0;
    			   //System.out.println("MOVE UP");
    		   if(!world.isWall(roboti, robotj - 1))
    			   movementArray[2] = 2;
    			   //System.out.println("MOVE LEFT");
    		   if(!world.isWall(roboti, robotj + 1))
    			   movementArray[3] = 3;
    			   //System.out.println("MOVE RIGHT");
    	   }
       }
       
       //along top wall
       else if(roboti == rowNum - 1){
    	   //top left corner
    	   if(robotj == 0){
    		   if(!world.isWall(roboti - 1, robotj))
    			   movementArray[1] = 1;
    			   //System.out.println("MOVE DOWN");
    		   if(!world.isWall(roboti, robotj + 1))
    			   movementArray[3] = 3;
    			   //System.out.println("MOVE RIGHT");
    	   }
    	   //top right corner
    	   else if(robotj == colNum - 1){
    		   if(!world.isWall(roboti - 1, robotj))
    			   movementArray[1] = 1;
    			   //System.out.println("MOVE DOWN");
    		   if(!world.isWall(roboti, robotj - 1))
    			   movementArray[2] = 2;
    			   //System.out.println("MOVE LEFT");
    	   }
    	   //along top wall but not on corner
    	   else{
    		   if(!world.isWall(roboti - 1, robotj))
    			   movementArray[1] = 1;
    			   //System.out.println("MOVE DOWN");
    		   if(!world.isWall(roboti, robotj - 1))
    			   movementArray[2] = 2;
    			   //System.out.println("MOVE LEFT");
    		   if(!world.isWall(roboti, robotj + 1))
    			   movementArray[3] = 3;
    			   //System.out.println("MOVE RIGHT");
    	   }
       }
       
       //left wall not on a corner
       else if(robotj == 0){
    	   if(!world.isWall(roboti - 1, robotj))
    		   movementArray[1] = 1;
    		   //System.out.println("MOVE DOWN");
    	   if(!world.isWall(roboti, robotj + 1))
    		   movementArray[3] = 3;
    		   //System.out.println("MOVE RIGHT");
    	   if(!world.isWall(roboti + 1, robotj))
    		   movementArray[0] = 0;
    		   //System.out.println("MOVE UP");
       }
       
       //right wall not on a corner
       else if(robotj == colNum - 1){
    	   if(!world.isWall(roboti + 1, robotj))
    		   movementArray[0] = 0;
    		   //System.out.println("MOVE UP");
		   if(!world.isWall(roboti, robotj - 1))
			   movementArray[2] = 2;
			   //System.out.println("MOVE LEFT");
		   if(!world.isWall(roboti - 1, robotj))
			   movementArray[1] = 1;
			   //System.out.println("MOVE DOWN");
       }
       //anywhere inside grid not on a wall
       else{
    	   if(!world.isWall(roboti + 1, robotj))
    		   movementArray[0] = 0;
    		   //System.out.println("MOVE UP");
		   if(!world.isWall(roboti, robotj - 1))
			   movementArray[2] = 2;
			   //System.out.println("MOVE LEFT");
		   if(!world.isWall(roboti, robotj + 1))
			   movementArray[3] = 3;
			   //System.out.println("MOVE RIGHT");
		   if(!world.isWall(roboti - 1, robotj))
			   movementArray[1] = 1;
			   //System.out.println("MOVE DOWN");
       }
       
       //array list for the possible movements
       //this is a copy of movementArray to have the ability
       // to use the built-in CONTAINS function
       
       intList.clear();
       for(int index = 0; index < movementArray.length; index++){
    		   intList.add(movementArray[index]);
    		   System.out.println("Added " + intList.get(index) + " to intList");

       }
       
       //select one among all possible actions for the current state
       
       //choose random action from array of valid possible actions
       action = rand.nextInt(4);
       System.out.println("Maybe Action = " + action);
       
       boolean getAction = true;
       boolean tempVar = true;
       
       while(getAction == true){
    	   getAction = false;
    	   
    	   if(intList.contains(action)){
    		   tempVar = false;
    	   }
    	   else{
    		   action = rand.nextInt(4);
    		   tempVar = true;
    	   }
    	   
    	   getAction = tempVar;
       }
       
       System.out.println("Action chosen = " + action);
       
       //move to new location (take action)
       if(action == 0)
    	   //UP
    	   world.moveRobot(roboti + 1, robotj, 90);
       else if(action == 1)
    	   //DOWN
    	   world.moveRobot(roboti - 1, robotj, 270);
       else if(action == 2)
    	   //LEFT
    	   world.moveRobot(roboti, robotj - 1, 180);
       else
    	   //RIGHT
    	   world.moveRobot(roboti, robotj + 1, 0); 
       
       //s'
       roboti = world.getRobotLocation()[0];
       robotj = world.getRobotLocation()[1];
       
       //observe reward r(s')
       currentReward = rewards[roboti][robotj];
       System.out.println("Reward(s') = " + rewards[roboti][robotj]);
       
       /*
       //Q(s,a)
       System.out.println("currentStateValue = " + currentStateValue);
       currentStateValue = Q[roboti][robotj][action];*/

       
       try {
		Thread.sleep(5);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} //end of the movement function 
       

       
       //observe next state s'
       
       
       //clear movementArray again
       //movementArray = {-1, -1 , -1, -1};
       for(int i = 0; i < 4; i++)
    	   movementArray[i] = -1;  
       
       /*
        * 0 = up
        * 1 = down
        * 2 = left
        * 3 = right
        */
        
       //GET POSSIBLE ACTIONS FROM CURRENT STATE 
       
       //along bottom wall
       if(roboti == 0){
    	   //bottom left corner
    	   if(robotj == 0){
    		   if(!world.isWall(roboti + 1, robotj))
    			   movementArray[0] = 0;
    			   //System.out.println("MOVE UP");
    		   if(!world.isWall(roboti, robotj + 1))
    			   movementArray[3] = 3;
    			   //System.out.println("MOVE RIGHT");
    	   }
    	   //bottom right corner
    	   else if(robotj == colNum - 1){
    		   if(!world.isWall(roboti + 1, colNum - 1))
    			   movementArray[0] = 0;
    			   //System.out.println("MOVE UP");
    		   if(!world.isWall(roboti, robotj - 1))
    			   movementArray[2] = 2;
    			   //System.out.println("MOVE LEFT");
    	   }
    	   //on bottom wall but not on a corner
    	   else{
    		   if(!world.isWall(roboti + 1, robotj))
    			   movementArray[0] = 0;
    			   //System.out.println("MOVE UP");
    		   if(!world.isWall(roboti, robotj - 1))
    			   movementArray[2] = 2;
    			   //System.out.println("MOVE LEFT");
    		   if(!world.isWall(roboti, robotj + 1))
    			   movementArray[3] = 3;
    			   //System.out.println("MOVE RIGHT");
    	   }
       }
       
       //along top wall
       else if(roboti == rowNum - 1){
    	   //top left corner
    	   if(robotj == 0){
    		   if(!world.isWall(roboti - 1, robotj))
    			   movementArray[1] = 1;
    			   //System.out.println("MOVE DOWN");
    		   if(!world.isWall(roboti, robotj + 1))
    			   movementArray[3] = 3;
    			   //System.out.println("MOVE RIGHT");
    	   }
    	   //top right corner
    	   else if(robotj == colNum - 1){
    		   if(!world.isWall(roboti - 1, robotj))
    			   movementArray[1] = 1;
    			   //System.out.println("MOVE DOWN");
    		   if(!world.isWall(roboti, robotj - 1))
    			   movementArray[2] = 2;
    			   //System.out.println("MOVE LEFT");
    	   }
    	   //along top wall but not on corner
    	   else{
    		   if(!world.isWall(roboti - 1, robotj))
    			   movementArray[1] = 1;
    			   //System.out.println("MOVE DOWN");
    		   if(!world.isWall(roboti, robotj - 1))
    			   movementArray[2] = 2;
    			   //System.out.println("MOVE LEFT");
    		   if(!world.isWall(roboti, robotj + 1))
    			   movementArray[3] = 3;
    			   //System.out.println("MOVE RIGHT");
    	   }
       }
       
       //left wall not on a corner
       else if(robotj == 0){
    	   if(!world.isWall(roboti - 1, robotj))
    		   movementArray[1] = 1;
    		   //System.out.println("MOVE DOWN");
    	   if(!world.isWall(roboti, robotj + 1))
    		   movementArray[3] = 3;
    		   //System.out.println("MOVE RIGHT");
    	   if(!world.isWall(roboti + 1, robotj))
    		   movementArray[0] = 0;
    		   //System.out.println("MOVE UP");
       }
       
       //right wall not on a corner
       else if(robotj == colNum - 1){
    	   if(!world.isWall(roboti + 1, robotj))
    		   movementArray[0] = 0;
    		   //System.out.println("MOVE UP");
		   if(!world.isWall(roboti, robotj - 1))
			   movementArray[2] = 2;
			   //System.out.println("MOVE LEFT");
		   if(!world.isWall(roboti - 1, robotj))
			   movementArray[1] = 1;
			   //System.out.println("MOVE DOWN");
       }
       //anywhere inside grid not on a wall
       else{
    	   if(!world.isWall(roboti + 1, robotj))
    		   movementArray[0] = 0;
    		   //System.out.println("MOVE UP");
		   if(!world.isWall(roboti, robotj - 1))
			   movementArray[2] = 2;
			   //System.out.println("MOVE LEFT");
		   if(!world.isWall(roboti, robotj + 1))
			   movementArray[3] = 3;
			   //System.out.println("MOVE RIGHT");
		   if(!world.isWall(roboti - 1, robotj))
			   movementArray[1] = 1;
			   //System.out.println("MOVE DOWN");
       }
       
       //array list for the possible movements
       //this is a copy of movementArray to have the ability
       // to use the built-in CONTAINS function
       intList.clear();
       for(int index = 0; index < movementArray.length; index++){
    		   intList.add(movementArray[index]);
       }
       //finished observing next state
       
       //next possible actions (a') are stored in intList
       
       //Get max Q => Q(s',a')
       List<Double> maxQues = new ArrayList<Double>();
       for(int i = 0; i < intList.size(); i++){
    	   if(intList.get(i) != -1){
    		   maxQues.add(Q[roboti][robotj][i]);
    		   System.out.println("next Q added : " + Q[roboti][robotj][i]);
    		   System.out.println("the getI = " + intList.get(i));
    	   }
       }
       
       //get the max new state => Q(s',a')
       maxStateValue = Collections.max(maxQues);
       System.out.println("maxStateValue (lrgst of prev) = " + maxStateValue);

       Q[startX][startY][firstAction] = currentStateValue + alpha *
    		   (currentReward +  gamma * maxStateValue - currentStateValue);
       
       currentStateValue = maxStateValue;
       startX = world.getRobotLocation()[0];
       startY = world.getRobotLocation()[1];
       firstAction = action;
       
       if(roboti == goali && robotj == goalj){
    	   episodeGoal = true;
    	   System.out.println("GOAL!!!!!!!!!!");
       }
       
       }
       
       
       

    	 
        
        //------- Q-LEARNING ALGORITHM -----------------------------------
        // Define alpha = 0.65, gamma = 0.99
        // Repeat for number of episodes (e.g. 150 steps)
        //      Initialize s to where the robot is located
        //      Repeat until state s reaches the goal state
        //          Choose an action a from state s using policy derived from Q (e.g. random or greedy)
        //          Take the action a, observe reward r(s') and next state s'
        //          Q(s,a) <- Q(s,a) + alpha[r+gamma max_a' Q(s',a') - Q(s,a)]
        //          s <- s'
        //      end
        // end
        //----------------------------------------------------------------
        
       
       
       
       
        
        // Find the path to the goal starting from robot location
        // and choosing the action that has maximum Q-value in the current state.
        
        // Some of the useful functions to implement:
        //      -> getNextState(currentState, action) returns the next state by
        //                      selecting action from the currentState.
        //      -> chooseAction(currentState) returns an action (random or using
        //                      heuristics) from the current state.
        //      -> maxAction(Q-values) returns the action that has maximum Q-value
        //                      in a state
       }
        
    }
    
}