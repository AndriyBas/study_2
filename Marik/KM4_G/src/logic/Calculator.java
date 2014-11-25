package logic;

import java.util.ArrayList;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class Calculator extends Thread {
	
	private volatile boolean finished;
	private ArrayList<int[][]> nodes;
	private ArrayList<double[]> edges;
	private double dt;
	private ArrayList<Unit> uList;
	
	public void init(ArrayList<int[][]> nodes, ArrayList<double[]> edges, double dt, ArrayList<Unit> uList){
		this.nodes = nodes;
		this.edges = edges;
		this.dt = dt;
		this.uList = uList;
	}
	
	public boolean isFinished(){
		return finished;
	}
	
	@Override
	public void run() {
		
		finished = false;
		
		double[][] matrix = new double[nodes.size()][nodes.size()];
		
		for(int i = 0; i < edges.size(); i++){
			if(edges.get(i)[0] == edges.get(i)[1])
				edges.remove(edges.get(i));
		}

		
		for(int i = 0; i < nodes.size(); i++){
			ArrayList<double[]> temp = new ArrayList<double[]>();
			for(int j = 0; j < edges.size(); j++){
				if(edges.get(j)[0] == i)
					temp.add(edges.get(j));
			}
			double sum = 0;
			for(int k = 0; k < temp.size(); k++){
				temp.get(k)[2] = temp.get(k)[2]*temp.get(k)[3];
				sum += temp.get(k)[2];
				temp.get(k)[3] = 1 - Math.exp(-temp.get(k)[2]*dt);
			}
			edges.add(new double[]{i, i, 0, 1 - Math.exp(-sum*dt)});
		}
	
		//================
		for(int i = 0; i < matrix[0].length; i++)
			matrix[0][i] = 1;
		
		for(int i = 1; i < matrix[0].length; i++){
			for(int j = 0; j < edges.size(); j++){
				if(edges.get(j)[0] != edges.get(j)[1]){
					if(edges.get(j)[0] == i)
						matrix[i][i] -= edges.get(j)[3];
					else
						if(edges.get(j)[1] == i)
							matrix[i][(int)edges.get(j)[0]] = edges.get(j)[3];
				}
			}
		}
		
		double[] constants = new double[nodes.size()];
		constants[0] = 1;
		
		RealMatrix coefficients = new Array2DRowRealMatrix(matrix ,false);
		DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
		RealVector vector = new ArrayRealVector(constants, false);
		RealVector solution = solver.solve(vector);

		double[] prob = solution.toArray();

//		double summ = 0;
//		for(int i = 0; i < prob.length; i++){
//			summ += prob[i];
//			System.out.println(prob[i]);
//			}
//		System.out.println("sum = " + summ);

		double max = prob[0];
		int index = 0 ;
		for(int i = 0; i < prob.length; i++){
			if(max < prob[i]){
				max = prob[i];
				index = i;
			}
		}
		
		System.out.println("=================================");
		System.out.println("Найбільш ймовірний стан :");
		System.out.println("Номер = " + index);
		System.out.println("Ймовірність = " + max);
		for(int j = 0; j < nodes.get(index).length - 1; j++)
			System.out.print("("+nodes.get(index)[j][0]+","+nodes.get(index)[j][1]+","+nodes.get(index)[j][2]+")");
		System.out.println("\n=================================");
		
		
		
		
		
		
		
		
		
		
		
/*		
		int cnt = 0;
		for(int i = 0; i < edges.size(); i ++){
			if(edges.get(i)[0] == index && edges.get(i)[0] != edges.get(i)[1]){
				for(int j = 0; j < nodes.get(index).length - 1; j++)
					System.out.print("("+nodes.get((int) edges.get(i)[1])[j][0]+","+nodes.get((int) edges.get(i)[1])[j][1]+","+nodes.get((int) edges.get(i)[1])[j][2]+")");
				System.out.println();
				cnt ++;
			}
		}
		System.out.println(cnt);
*/		
		
		for(int j = 0; j < uList.size(); j++){
			if(uList.get(j).getName().compareTo("ЦП") == 0){
				for(int k = 0; k < ((CPU)uList.get(j)).getCoresNum(); k++){
					double intens = 0;
					for(int i = 0; i < nodes.size(); i++){
						if(nodes.get(i)[j][1] == (k+1))
							intens += prob[i];
					}
					((CPU)uList.get(j)).setCoresIntensity(k, Math.abs(intens));
				}
			}
			else{
				double intens = 0;
				for(int i = 0; i < nodes.size(); i++){
					if(nodes.get(i)[j][1] == 1)
						intens += prob[i];
				}
				uList.get(j).setIntensity(Math.abs(intens));
			}		
		}
		finished = true;
	}
	
}
