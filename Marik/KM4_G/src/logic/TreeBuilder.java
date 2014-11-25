package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TreeBuilder extends Thread{

	
	private ArrayList<int[][]> nodes ;
	private ArrayList<double[]> edges;
	private int nodeCtr;
	private int us; // units size
	private int coresNmb;
	private ArrayList<Unit> uList;
	private int[][] startNode;
	private boolean finished = false;
	
	public TreeBuilder(){
	}
	
	public void init(ArrayList<Unit> uList, int tasksNmb){
		nodes = new ArrayList<int[][]>();
		edges = new ArrayList<double[]>();
		us = uList.size();
		this.uList = uList;
		
		for(Unit u : uList)
			if(u instanceof CPU)
				coresNmb = ((CPU)u).getCoresNum();
		
		startNode = new int[us + 1][3];
		
		if(tasksNmb > coresNmb)
			startNode[0] = new int[]{tasksNmb - coresNmb, coresNmb, 0};
		else
			startNode[0] = new int[]{0, tasksNmb, coresNmb - tasksNmb};
		
		for(int i = 1; i < startNode.length - 1; i++)
			startNode[i][2] = 1;
		
	}
	
	
	private void buildNode(int[][] node){
				
		//creating new nodes
		for(Unit u : uList)
		for(int i = 0; i < u.getOutputSize(); i++){	
			Unit child = u.getUnitAt(i);
			int[][] newnode = getNewNode(node, u, child);
			double[] newedge;
			int num = getNodeNumber(newnode);
			if(num == -1){
				newedge = new double[]{nodeCtr, ++nodeCtr, u.getIntensity(), u.getPossibilityTo(i)};
				nodes.add(newnode);
				buildNode(newnode);	
			}
			else 
				newedge = new double[]{nodeCtr, num, u.getIntensity(), u.getPossibilityTo(i)};
			if(! edgeExists(newedge))
				edges.add(newedge);
		}
	}
	
	private int[][] getNewNode(int[][] oldnode, Unit ufrom, Unit uto){
		int[][] newnode = new int[us + 1][3];
		for(int i = 0; i < newnode.length - 1; i++)
			for(int j = 0; j < newnode[i].length; j++)
				newnode[i][j] = oldnode[i][j];
		//from
		if(newnode[ufrom.getId()][1] != 0){
			newnode[ufrom.getId()][1] --;
			if(ufrom.getName().compareTo("ÖÏ") == 0 && newnode[ufrom.getId()][2] < coresNmb)
				newnode[ufrom.getId()][2] ++;
			else 
				newnode[ufrom.getId()][2] = 1;	
			if((newnode[ufrom.getId()][2] != 0) && (newnode[ufrom.getId()][0] != 0)){
				newnode[ufrom.getId()][0] --;
				newnode[ufrom.getId()][1] ++;
				newnode[ufrom.getId()][2] --;
			}
		
		
			//to
			newnode[uto.getId()][0]++;
			if(newnode[uto.getId()][2] != 0){
				newnode[uto.getId()][0] --;
				newnode[uto.getId()][1] ++;
				newnode[uto.getId()][2] --;
			}
		
		}
		
		newnode[newnode.length - 1][0] = ufrom.getId();
		return newnode;
	}
	
	private int getNodeNumber(int[][] node){
		int index = -1;
		
		l:for(int[][] n : nodes){
			for(int j = 0; j < node.length - 1; j++){
				for(int k = 0; k < node[j].length; k++)
					if(n[j][k] != node[j][k]) continue l;
			}
			index = nodes.indexOf(n);
			return index;
		}
		return index;
	}
	
	private boolean edgeExists(double[] edge){
		for(int i = 0; i < edges.size(); i++){
			if((edges.get(i)[0] == edge[0]) && (edges.get(i)[1] == edge[1]) )
				return true;
		}
		return false;
	}
	
	private void printNode(int[][] node){
		for(int j = 0; j < node.length; j++)
			System.out.print("("+node[j][0]+","+node[j][1]+","+node[j][2]+")");
	}
	
	public ArrayList<int[][]> getNodes(){
		return nodes;
	}
	
	public ArrayList<double[]> getEdges(){
		return edges;
	}
	
	public int getNodesNum(){
		return nodes.size();
	}
	
   public int getEdgesNum(){
	   return edges.size();
   }
   
   public boolean isFinished(){
	   return finished;
   }
   
   public void run() {
	    nodes.add(startNode);
		buildNode(startNode);
		finished = true;
   };
   
}
