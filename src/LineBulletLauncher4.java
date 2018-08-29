import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class LineBulletLauncher4 extends GraphicsProgram
{
	// CONSTANTS
	private final int NOZ_LEN = 100;
	
	// INSTANCE VARIABLES
	private GLine canon;
	private GLabel lab, canBasePosLab, canMouthPosLab,
				   deltaXLab, deltaYLab, angleLab, mouseLab;
	
	private int canonBaseX = 680;
	private int canonBaseY = 650;	
	private int mouseX, mouseY;
	private int deltaX, deltaY; 	// distance from mouse to origin
	private double canonMouthX = canonBaseX;
	private double canonMouthY = canonBaseY - NOZ_LEN;
	private double angle;	
	
	public void keyTyped(KeyEvent ke)
	{//lab.setLabel("a");
		if (ke.getKeyChar() == 'a')
		{
			canon.move(-1, 0);
			--canonBaseX;
		}
		else if (ke.getKeyChar() == 'd')
		{
			canon.move(1, 0);
			++canonBaseX;
		}
		else if (ke.getKeyChar() == 'w')
		{
			canon.move(0, -1);
			--canonBaseY;
		}
		else if(ke.getKeyChar() == 's')
		{
			canon.move(0, 1);
			++canonBaseY;
		}
		
			
		canBasePosLab.setLabel("canonBase: " + canonBaseX + ", " + canonBaseY);
	}
	
	// INIT
	public void init()
	{
		canon = 			new GLine(canonBaseX, canonBaseY, canonBaseX, canonBaseY-NOZ_LEN);
		lab = 				new GLabel("label", 1200, 20);
		canBasePosLab = 	new GLabel("canonBase: 680, 650", 10, 20);
		canMouthPosLab = 	new GLabel("canonMouth: 680, 550", 10, 40);
		
		mouseLab = 			new GLabel("mousePos: ", 10, 70);
		deltaXLab = 		new GLabel("deltaX: ", 10, 90);
		deltaYLab = 		new GLabel("deltaY: ", 10, 110);
		angleLab = 			new GLabel("angle: ", 10, 130);
		
		add(canon);		
		add(mouseLab);
		add(deltaXLab);
		add(deltaYLab);
		add(angleLab);
		add(canBasePosLab);
		add(canMouthPosLab);
		add(lab);		
		
		addKeyListeners();
		addMouseListeners();	
	}
	
	public void mouseMoved(MouseEvent me)
	{		
		mouseX = me.getX();
		mouseY = me.getY();
		deltaX = mouseX - canonBaseX;
		deltaY = (mouseY - canonBaseY) * -1;
		// updates labels
		mouseLab.setLabel("mousePos: " + mouseX + ", " + mouseY);
		deltaXLab.setLabel("deltaX: " + deltaX);
		deltaYLab.setLabel("deltaY: " + deltaY);
		
		// position the canon mouth
		{
			if (deltaX == 0) 	// we don't want division of zero in getting the angle
			{
				if (deltaY >= 0)
				{
					canonMouthX = canonBaseX;
					canonMouthY = canonBaseY - NOZ_LEN;	
				}
				else return;
			}	
			else if (deltaY == 0)
			{
				if (deltaX > 0) {canonMouthX = canonBaseX + NOZ_LEN;}
				else if (deltaX < 0) {canonMouthX = canonBaseX - NOZ_LEN;}
				canonMouthY = canonBaseY;				
			}
			else if (deltaY <= 0) {lab.setLabel("out of scope!");}
			else setCanonMouth(); 				// in position to fire
		}	
		
		canon.setEndPoint(canonMouthX, canonMouthY);
	}
	
	private void setCanonMouth()
	{
		setAngle();
		setCanonMouthPos();		
		canMouthPosLab.setLabel("canonMouth: " + canonMouthX + ", " + canonMouthY);
	}
	
	private void setAngle()
	{
		angle = Math.atan((double) deltaY/deltaX);
		double degAngle = Math.toDegrees(angle);
		
		// this checks if your mouse is
		// in the 2nd quadrant and makes
		// makes the angle equal to the complementary angle
		if (degAngle < 0)
		{
			degAngle = 180+degAngle;
			angle = Math.toRadians(degAngle);
		}
		
		angleLab.setLabel("angle: " + degAngle);
	}
	
	private void setCanonMouthPos()
	{
		canonMouthX = canonBaseX + (NOZ_LEN * Math.cos(angle));
		canonMouthY = canonBaseY - (NOZ_LEN * Math.sin(angle));
	}	
}

