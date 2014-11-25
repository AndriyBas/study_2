package logic;

import java.util.ArrayList;

public class Controller extends Thread {
 
	
	private ArrayList<logic.State> sList = new ArrayList<logic.State>();
	int counter = 1000;
	int counterStorage = counter;
	private boolean finished = false;
	
	public Controller(ArrayList<logic.State> sList){
		this.sList = sList;
	}
	
	public void setStates(ArrayList<logic.State> sList){
		this.sList = sList;
	}
	
	public void setEventNum(int num){
		if(num > 0)
			this.counterStorage = this.counter = num;
	}
	
	public int getEventsNum(){
		return counter;
	}
		
	public boolean isFinished(){
		return finished;
	}
	
	public ArrayList<logic.State> getStates(){
		return sList;
	}
	
	
	@Override
	public void run() {
		sList.get(0).step();
		for(int i = 0; i < sList.size(); i++){
			sList.get(i).setProbability((double)sList.get(i).getEvents() / (double)counterStorage);
		}
		finished = true;
	}
}
