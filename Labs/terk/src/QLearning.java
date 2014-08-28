import RobotClient.MapGUI;
import java.util.Random;


public class QLearning {
    
    static MapGUI world = new MapGUI();
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

        System.out.println("Goal is at " + goali + "," + goalj + "\t and Robot is at " + roboti + "," + robotj);
        

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
        
        // Set up Rewards for each state
        //      -> Give 100 to the goal location
        //      -> And -1 for the rest of the states
        
        
        double[][] rewards = new double[rowNum][colNum];
        double[][][] Q = new double[rowNum][colNum][4];
      
        
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
