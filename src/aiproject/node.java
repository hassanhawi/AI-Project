
package aiproject;

import java.util.ArrayList;

public class node{
    
    static node[][]a;
    static int AgentI,AgentJ,GoalI,GoalJ;

    private double distance;
    private char state;
    
    private double cost;

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    private int i;
    private int j;
    
    
    private String path="";
    private double f;

    private char action;

    public char getAction() {
        return action;
    }

    public void setAction(char action) {
        this.action = action;
    }
    
    
    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }
    

    public node(char state, int i, int j) {
        this.state = state;
        this.i = i;
        this.j = j;
        
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
       
    public static void intializeMap(int r, int c)
    {
        a= new node[r][c];
    }
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "node{" + "distance=" + distance + ", state=" + state + ", i=" + i + ", j=" + j + ", path=" + path + ", f=" + f + ", action=" + action + '}';
    }
    
    public static node getAgent()
    {
        return getNode(AgentI, AgentJ);
    }

    public static node getGoal()
    {
        return getNode(GoalI, GoalJ);
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }
    
    public static node getNode(int i , int j)
    {
        return a[i][j];
    }
  

    public void calculateDistance()
    {
      this.distance = (Math.hypot(this.getI()-GoalI, this.getJ()-GoalJ));
    }
    
}
