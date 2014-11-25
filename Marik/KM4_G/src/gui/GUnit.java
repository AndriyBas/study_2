package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUnit extends Container{
	private String name;
	private double t;
	private double[] p; 
	private TextField[] pf;
	private TextField tf;
	private String[] info;
	private JPanel pan;
	
	/**
	 * @param name ім я вузла
	 * @param t частота роботи
	 * @param p ймовірності виходів
	 * @param info інфо про виходи - ймовірності
	 */
	public GUnit(String name, double t, double[] p, String[] info){
		this.name = name;
		this.t = t;
		this.p = p;
		this.info = info;
			
		build();
	}
	
	void build(){	
		setLayout(new BorderLayout());
		pan = new JPanel();
		pan.setBackground(new Color(0,0,0,0));
		pan.setBorder(BorderFactory.createTitledBorder(name));
		add(pan, BorderLayout.CENTER);
		
		tf = new TextField(this);
		tf.setText(String.valueOf(t));
		pf = new TextField[p.length];
		
		for(int i = 0; i < pf.length; i++){
			pf[i] = new TextField(this);
			pf[i].setText(String.valueOf(p[i]));
		}
		
		pan.setLayout(new GridLayout(pf.length + 1, 2));
		pan.add(new JLabel("Частота роботи"));
		pan.add(tf);
		for(int i = 0; i < pf.length; i++){
			pan.add(new JLabel("--------> "+info[i]));
			pan.add(pf[i]);
		}	
	}

	public boolean isOK(){
		boolean isOK = true;
		double psum = 0;
		
		try{
			Double.parseDouble(tf.getText());
			for(TextField p : pf)
				psum += Double.parseDouble(p.getText());
		}catch(NumberFormatException e){
			isOK = false;
		}
		
		if(psum != 1)
			isOK= false;
		return isOK;
	}
	
	public double getIntensity(){
		double t = 0;
		try{
			t = Double.parseDouble(tf.getText());
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		return t;
	}
	
	public double getProbTo(String unit){
		for(int i = 0; i < info.length; i ++)
			if(unit == info[i])
				return Double.parseDouble(pf[i].getText());
		return 0;
	}
	
	public String getName(){
		return this.name;
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(3));
		g.drawRect(0, 0, getWidth(), getHeight());
		
		if(isOK())
			g.setColor(new Color(102, 205 , 0, 55));
		else
			g.setColor(new Color(255, 10 , 10, 255));
		g.fillRect(0, 0, getWidth(), getHeight());

		super.paint(g);
		
		
	}
	
	
}
