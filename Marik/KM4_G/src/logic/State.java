package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class State {

	private ArrayList<Double> pList = new ArrayList<Double>();
	private ArrayList<State> sList = new ArrayList<State>();
	private int[][] state;
 	private Controller controller;
	private int events;
	private double probability;
	
	public State(int[][] state, Controller cont){
		this.controller = cont;
		this.state = state;

	}
	
	public void addState(State state, double prob){
		sList.add(state);
		pList.add(prob);
	}
	
	public int[][] getOwnState(){
		return state;
	}
	
	public int getEvents(){
		return events;
	}
	
	public void setProbability(double p){
		this.probability = p;
	}
	
	public double getProbability(){
		return probability;
	};
	
	
	public void step(){
		if(controller.counter-- > 0){
			double gen = 0;
			for(Double prob : pList)
				gen += prob;
			double r = Math.random()*gen;
			
			double p = 0;
			Iterator it = pList.iterator();
			int i = -1;
			while(it.hasNext() && p <= r){
				p += (Double)it.next();
				i ++;
			}
			events++;
			sList.get(i).step();
		}
			
	}
	
	
}
