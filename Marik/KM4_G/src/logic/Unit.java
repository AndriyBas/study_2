package logic;

import java.util.ArrayList;

public class Unit {
	
	private String name;
	protected double intensity;
	protected long tasksDone;
	protected ArrayList<Unit> uList;
	protected ArrayList<Double> pList;
	protected int id;
	
	
	public Unit(String name, double intensity, int id){
		uList = new ArrayList<Unit>();
		pList = new ArrayList<Double>();
		this.name = name;
		this.id = id;
		this.intensity = intensity;
	}
	
	public void link(Unit u, double possibility){
		uList.add(u);
		pList.add(possibility);
	}
		
	public double getIntensity(){
		return this.intensity;
	}
	
	public void setIntensity(double intens){
		this.intensity = intens;
	}	

	public String getName(){
		return name;
	}
	
	public int getOutputSize(){
		return uList.size();
	}
	
	public Unit getUnitAt(int i){
		return uList.get(i);
	}

	public ArrayList<Unit> getUnits(){
		return uList;
	}
	
	public int getId(){
		return this.id;
	}
	
	
	public double getPossibilityTo(int i){
		return pList.get(i);
	}
}

