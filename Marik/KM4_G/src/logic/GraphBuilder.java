package logic;

import java.util.ArrayList;

public class GraphBuilder {

	public Controller buildGraph(ArrayList<int[][]> nodes, ArrayList<double[]> edges, double dt){
		ArrayList<State> states = new ArrayList<State>();
		Controller cont = new Controller(null);
		
		for(int i = 0; i < nodes.size(); i++){
			states.add(new State(nodes.get(i), cont));
		}
		for(int i = 0; i < nodes.size(); i++){
			ArrayList<double[]> temp = new ArrayList<double[]>();
				for(int j = 0; j < edges.size(); j++){
					if((edges.get(j)[0] == i) && ((edges.get(j)[0] != edges.get(j)[1])))
						temp.add(edges.get(j));
				}
				double sum = 0;
				for(int k = 0; k < temp.size(); k++){
					temp.get(k)[2] = temp.get(k)[2]*temp.get(k)[3];
					sum += temp.get(k)[2];
					temp.get(k)[3] = 1 - Math.exp(-temp.get(k)[2]*dt);
				}
				temp.add(new double[]{i, i, 0, 1 - Math.exp(-sum*dt)});
		
				for(int p = 0; p < temp.size(); p++){
					states.get(i).addState(states.get((int)temp.get(p)[1]), temp.get(p)[3]);
				}
		}
		cont.setStates(states);
		return cont;
	}
	
	
	
}
