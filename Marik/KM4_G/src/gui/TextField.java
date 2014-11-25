package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class TextField extends JTextField {
	
	private boolean isOK;
	private GUnit gu;
	
	public TextField(GUnit gu){
		this.gu = gu;
		
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				gu.repaint();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}
	
	boolean isOK(){
		return isOK;
	}

	
	@Override
	public void paint(Graphics g) {
		
		boolean isOK = true;
		
		super.paint(g);
		try{
			Double.parseDouble(getText());
		}catch(NumberFormatException e){
			isOK = false;
			g.setColor(new Color(255,0,0,100));
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}
}
