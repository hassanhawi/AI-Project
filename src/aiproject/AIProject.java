package aiproject;

import java.io.Console;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class AIProject {
    
    static Random rand = new Random();
    static boolean[][]visited;
    
    public static void main(String[] args) {
        
        int AgentI,AgentJ,GoalI,GoalJ;
        
        System.out.println("*******************************************************");
        System.out.println("*                                                     *");
        System.out.println("*  Hassan Hawi - CSCI475 Project 1- Maze PathFinder   *");
        System.out.println("*                                                     *");
        System.out.println("*******************************************************");
        
        
        System.out.println();System.out.println();
        
        char flagCoordinate,flagPrint;
        int rMap,cMap;
        Scanner s = new Scanner(System.in);
        
        System.out.println("Enter the size of the map.");
        rMap=s.nextInt();
        cMap=s.nextInt();
        node.intializeMap(rMap, cMap);
        
        for(int i=0;i<node.a.length;i++)
            for(int j=0;j<node.a[0].length;j++)
                node.a[i][j]= new node('E', i,j );
        
        
        
        
        
        for(int i=1;i<(node.a[0].length-1);i++)
        {
            node.a[(node.a.length-1)/2][i].setState('O');
        }
        
        for(int i=1;i<(node.a.length-1);i++)
        {
            node.a[i][(node.a[0].length-1)/2].setState('O');
        }
        
        
        System.out.println("Enter S to set agent and goal coordinate.");
        System.out.println("Enter R to Randomize agent and goal coordinate.");
        System.out.println("Any other key to exit.");
        
        flagCoordinate= s.next().charAt(0);
        
        System.out.println("Do you want to print the Map ?");
        System.out.println("Enter Y for yes and N for no");
        flagPrint= s.next().charAt(0);
        
        
        if(flagCoordinate=='S')
        {
            do{
                System.out.print("Enter a valid coordinate for the agent:");
                System.out.print(" i between 0 and "+(node.a.length-1));
                System.out.println(" j between 0 and "+(node.a[0].length-1));
                
                AgentI=s.nextInt();
                AgentJ=s.nextInt();
            }while (!setAgentCoordinate(AgentI, AgentJ));
            
            System.out.println("Agent is on cell ("+AgentI+","+AgentJ+")");
            
            
            do{
                System.out.print("Enter a valid coordinate for the goal:");
                System.out.print(" i between 0 and "+(node.a.length-1));
                System.out.println(" j between 0 and "+(node.a[0].length-1));
                System.out.println("Agent and Goal can not be on the same cell ");
                
                GoalI=s.nextInt();
                GoalJ=s.nextInt();
            }while (!setGoalCoordinate(GoalI, GoalJ));
            
            System.out.println("Goal is on cell ("+GoalI+","+GoalJ+")");
            System.out.println();
            
            if(flagPrint=='Y')
            {
                printArray();
            }
            
            
        }
        else if(flagCoordinate=='R')
        {
            System.out.println(setRandomAgentCoordinate());
            System.out.println(setRandomGoalCoordinate());
            System.out.println();
            
            if(flagPrint=='Y')
            {
                printArray();
                CalculateDistance();
            }
        }
        
        
        System.out.println();
        System.out.println();
        
        
        visited= new boolean[node.a.length][node.a[0].length];
        
        
        
        for(int i=0;i<node.a.length;i++)
            for(int j=0;j<node.a[0].length;j++)
            {
                visited[i][j] = (node.a[i][j]).getState()=='O';
            }
        
        
        
        
        BFS();
        for(int i=0;i<node.a.length;i++)
            for(int j=0;j<node.a[0].length;j++)
            {
                visited[i][j] = (node.a[i][j]).getState()=='O';
            }
        
        aStar();

        String x = s.next();
    }
    

    public static void printArray()
    {
        for(int i=0;i<node.a.length;i++)
        {
            for(int j=0;j<node.a[0].length;j++)
                System.out.print(node.a[i][j].getState()+" ");
            System.out.println();
        }
    }
    
    
    public static void CalculateDistance()
    {
        for(int i=0;i<node.a.length;i++)
        {
            for(int j=0;j<node.a[0].length;j++)
            {
                (node.a[i][j]).calculateDistance();
            }
        }
    }
    
    public static boolean setAgentCoordinate(int i,int j)
    {
        if(i>=node.a.length || j>=node.a[0].length || i<0 || j<0)
        {
            return false;
        }
        ((node)(node.a[i][j])).setState('A');
        node.AgentI=i;node.AgentJ=j;
        return true;
    }
    
    public static String setRandomAgentCoordinate()
    {
        int  i = rand.nextInt(node.a.length-1) + 0;
        int  j = rand.nextInt(node.a[0].length-1) + 1;
        ((node)(node.a[i][j])).setState('A');
        
        node.AgentI=i;node.AgentJ=j;
        
        return "Agent is on cell ("+i+","+j+")";
    }
    
    public static boolean setGoalCoordinate(int i,int j)
    {
        if(i>=node.a.length || j>=node.a[0].length || i<0 || j<0 || node.a[i][j].getState()=='A')
        {
            return false;
        }
        ((node)(node.a[i][j])).setState('G');
        node.GoalI = i; node.GoalJ = j;
        return true;
    }
    
    public static String setRandomGoalCoordinate()
    {
        int i,j;
        do{
            
            i = rand.nextInt(node.a.length-1) + 0;
            j = rand.nextInt(node.a[0].length-1) + 1;
            
        }while(((node)(node.a[i][j])).getState()=='A');
        
        ((node)(node.a[i][j])).setState('G');
        node.GoalI = i; node.GoalJ = j;
        
        return "Goal is on cell ("+i+","+j+")";
    }
    
    public static ArrayList<node> successorFuncBFS(node p,String f)
    {
        ArrayList<node> arr = new ArrayList<>();
        int n =node.a.length;
        int m=node.a[0].length;
        
        
        // moving up
        if (p.getI() - 1 >= 0 && visited[p.getI() - 1][p.getJ()] == false)
        {
            node t1=node.getNode(p.getI()-1, p.getJ());
            t1.setAction('U');
            t1.setPath(f+" U");
            visited[p.getI() - 1][p.getJ()] = true;
            arr.add(t1);
        }
        
        // moving right
        if (p.getJ() + 1 < m && visited[p.getI()][p.getJ() + 1] == false) {
            
            node t1= node.getNode(p.getI(), p.getJ()+1);
            t1.setAction('R');
            t1.setPath(f+" R");
            visited[p.getI()][p.getJ()+1] = true;
            arr.add(t1);
            
        }
        
        // moving down
        if (p.getI() + 1 < n && visited[p.getI() + 1][p.getJ()] == false)
        {
            node t1 = node.getNode(p.getI()+1, p.getJ());
            t1.setPath(f+" D");
            t1.setAction('D');
            visited[p.getI() + 1][p.getJ()] = true;
            arr.add(t1);
        }
        
        // moving left
        if (p.getJ() - 1 >= 0 && visited[p.getI()][p.getJ() - 1] == false)
        {
            node t1 = node.getNode(p.getI(), p.getJ()-1);
            t1.setPath(f+" L");
            t1.setAction('L');
            visited[p.getI()][p.getJ()-1] = true;
            arr.add(t1); 
        }
        return arr;
    }
    
    public static ArrayList<node> successorFuncAStar(node p,String f,double cost)
    {
        ArrayList<node> arr = new ArrayList<>();
        int n =node.a.length;
        int m=node.a[0].length;
        
        
        // moving up
        if (p.getI() - 1 >= 0 && visited[p.getI() - 1][p.getJ()] == false)
        {
            node t1=node.getNode(p.getI()-1, p.getJ());
            t1.setAction('U');
            t1.setPath(f+" U");
            t1.setCost(cost+10);
            t1.setF((cost+10)+t1.getDistance());
            visited[p.getI() - 1][p.getJ()] = true;
            arr.add(t1);
        }
        
        // moving right
        if (p.getJ() + 1 < m && visited[p.getI()][p.getJ() + 1] == false) {
            
            node t1= node.getNode(p.getI(), p.getJ()+1);
            t1.setAction('R');
            t1.setPath(f+" R");
            t1.setCost(cost+50);
            t1.setF((cost+50)+t1.getDistance());
            visited[p.getI()][p.getJ()+1] = true;
            arr.add(t1);
        }
        
        // moving down
        if (p.getI() + 1 < n && visited[p.getI() + 1][p.getJ()] == false)
        {
            node t1 = node.getNode(p.getI()+1, p.getJ());
            t1.setPath(f+" D");
            t1.setCost(cost+10);
            t1.setF((cost+10)+t1.getDistance());
            t1.setAction('D');
            visited[p.getI() + 1][p.getJ()] = true;
            arr.add(t1);
            
            
        }
        
        // moving left
        if (p.getJ() - 1 >= 0 && visited[p.getI()][p.getJ() - 1] == false)
        {
            node t1 = node.getNode(p.getI(), p.getJ()-1);
            t1.setPath(f+" L");
            t1.setCost(cost+50);
            t1.setF((cost+50)+t1.getDistance());
            t1.setAction('L');
            visited[p.getI()][p.getJ()-1] = true;
            arr.add(t1);
            
            
        }
        return arr;
    }
    
    public static void BFS()
    {
        ArrayList<node> arr = new ArrayList<>();
        
        arr.add(node.getAgent());
        visited[node.AgentI][node.AgentJ]=true;
        int count=0;
        double t1=System.currentTimeMillis();
        while(arr.size()!=0 && arr.get(0).getState()!='G')
        {
            ArrayList<node> temp ;
            
            node t = arr.remove(0);
            temp=successorFuncBFS(t,t.getPath());
            arr.addAll(temp);
            count++;
            if(arr.size()==0)
            {
                System.out.println("There is no path to the goal using BFS.");
                System.out.println(count+" nodes expanded using BFS");
                return;
            }
        }
        double t2=System.currentTimeMillis();
        
        
        
        String path=(arr.get(0)).getPath();
        System.out.println("I expanded "+count+" Nodes using BFS");
        
        int x = path.replace(" ", "").length();
        
        path= path.replaceFirst("^ *", "");
        System.out.println(path);
        double t3=t2-t1;
        
        System.out.println("Using BFS I took about "+t3+" milliseconds");
    }
    
    
    public static void aStar()
    {
        ArrayList<node> arr = new ArrayList<>();
        node.getAgent().setCost(0);
        arr.add(node.getAgent());
        
        
        
        visited[node.AgentI][node.AgentJ]=true;
        
        int count=0;
        double t1=System.currentTimeMillis();
        while(arr.size()!=0)
        {
            double min= Double.MAX_VALUE;
            int index=-1;
            
            for(int i=0;i<arr.size();i++)
            {
                if(arr.get(i).getDistance()<min)
                {
                    min=arr.get(i).getDistance();
                    index=i;
                }
            }
            
            if(arr.get(index).getState()=='G')
            {
                System.out.println("Goal");
                break;
            }
            
            
            ArrayList<node> temp ;
            
            node t = arr.remove(index);
            double x = t.getCost();
            temp=successorFuncAStar(t,t.getPath(),x);
            arr.addAll(temp);
            count++;
            if(arr.size()==0)
            {
                System.out.println("There is no path to the goal using A*.");
                System.out.println(count+" nodes expanded using A*");
                return;
            }
        }
        double t2=System.currentTimeMillis();
        
        
        
        String path=(node.getGoal()).getPath();
        System.out.println("I expanded "+count+" Nodes using A*");
        
        
        
        path= path.replaceFirst("^ *", "");
        System.out.println("Using A* cost= "+node.getGoal().getCost());
        System.out.println(path);
        double t3=t2-t1;
        
        System.out.println("Using A* I took about "+t3+" milliseconds");
    }
}


