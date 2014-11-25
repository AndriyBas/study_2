package logic;

public class CPU extends Unit {

	private int cores;
    private double[] coresIntensity;
	
	public CPU(String name, int cores, double intensity, int id) {
		super(name, intensity, id);
		this.cores = cores;
		this.coresIntensity = new double[cores];
	}

	public int getCoresNum(){
		return cores;
	}
	
	public double getCoresIntensity(int coresUsed){
		return coresIntensity[coresUsed-1];
	}
	
	public void setCoresIntensity(int coresUsed, double intensity){
		coresIntensity[coresUsed] = intensity; 
	}
	
}
