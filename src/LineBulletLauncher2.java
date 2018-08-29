import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class LineBulletLauncher2 extends GraphicsProgram
{
	// INSTANCE VARIABLES
	private GLine l;
	private GLabel lab;
	
	private int deltaX, deltaY; 	// distance from mouse to origin
	private double nozX, nozY, angle;
	
	// CONSTANTS
	private final int ORG_X = 680;
	private final int ORG_Y = 650;
	private final int NOZ_LEN = 100;
	
	// INIT
	public void init()
	{
		l = new GLine(ORG_X, ORG_Y, ORG_X, ORG_Y-NOZ_LEN);
		lab = new GLabel("label", 100, 100);
		
		add(lab);
		add(l);
		addMouseListeners();
	}
	
	public void mouseMoved(MouseEvent me)
	{
		deltaX = me.getX() - ORG_X;
		deltaY = ORG_Y - me.getY();
		
		if (deltaX == 0) { restNoz(); }
		else if (deltaY <= 0) { return; }
		else readyToFire();// if nozzle is in scope
		
		l.setEndPoint(nozX, nozY);
	}
	
	private void readyToFire()
	{
		setAngle();
		setNozX();
		setNozY();
		lab.setLabel("Click to Fire.");
	}
	
	// positions nozzle at 90 degrees
	private void restNoz()
	{
		nozX = ORG_X;
		nozY = ORG_Y - NOZ_LEN;
		lab.setLabel("Your at rest.");
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
	}
	
	private void setNozY() { nozY = ORG_Y - (NOZ_LEN * Math.sin(angle)); }	
	private void setNozX() { nozX = ORG_X + (NOZ_LEN * Math.cos(angle)); }
}

