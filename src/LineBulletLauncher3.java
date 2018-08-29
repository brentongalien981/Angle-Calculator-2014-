import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class LineBulletLauncher3 extends GraphicsProgram
{
	// CONSTANTS
	private final int ORG_X = 680;
	private final int ORG_Y = 650;
	private final int NOZ_LEN = 100;
	private final int N_BULLETS = 10;
	
	// INSTANCE VARIABLES
	private GLine l;
	private GLabel lab;
	private GOval bullet[];
	
	// for the bullet path
	private double vx, vy, m;
	private double rise, run;
	
	private int deltaX, deltaY; 	// distance from mouse to origin
	private double nozX, nozY, angle;
	private boolean outOfBounds = false;
	private boolean noMoreBullets = false;
	
	// AUDIO EFFECTS
	private AudioClip laser = MediaTools.loadAudioClip("laser.wav");
	private AudioClip hitPipe = MediaTools.loadAudioClip("hitPipe.wav");
	private AudioClip gameOverLaugh = MediaTools.loadAudioClip("gameOverLaugh.wav");
	private AudioClip newBallLaugh = MediaTools.loadAudioClip("newBallLaugh.wav");
	
	
	private void setBulletPath()
	{
		setRise();
		setRun();
		
		// for X velocity (vx) change of x position of the bullet
		
		if (run > 0) {vx = 1;}    		// bullet moves towards 1st quadrant
		else if (run < 0) {vx = -1;} 	// bullet moves towards 2nd quadrant
		else if (run == 0)				// if the nozzle is at 90 deg rest position
		{
			vx = 0;
			vy = -1; 	// bullet just moves up
			return; 	// no need to calculate slope
		}							
		
		// after if-else-if, calculate slope (m)
		m = rise/run;
		vy = m*vx;			
	}
	
	private void setRise() { rise = nozY - ORG_Y; }
	private void setRun() { run = nozX - ORG_X; }
	
	// INIT
	public void init()
	{
		l = new GLine(ORG_X, ORG_Y, ORG_X, ORG_Y-NOZ_LEN);
		lab = new GLabel("label", 100, 100);
		bullet = new GOval[N_BULLETS];
		
		add(lab);
		add(l);
		addMouseListeners();
	}
	
	synchronized public void mouseClicked(MouseEvent me) {
		if (noMoreBullets) { lab.setLabel("DUDE, YOU GOT NO MORE BULLETS"); }
		else 	// we still have bullets!!! keep firing!!!
		{
			if (outOfBounds)
			{
				lab.setLabel("NOZZLE IS BEYOND SCOPE");
				gameOverLaugh.play();
			}
			else
			{
				setBulletPath();
				laser.play();
				notify();
				lab.setLabel("FIRE IN THE HOLE!!!");
			}
		}
	}
	
	// RUN
	synchronized public void run()
	{
		fireBullet();
	}
	
	private void fireBullet()
	{
		try {
			for (int b=0; b<N_BULLETS; b++)
			{
				wait();
				bullet[b] = new GOval(nozX, nozY, 20, 20);
				bullet[b].setFilled(true);
				add(bullet[b]);
				
				while (true)
				{
					bullet[b].move(vx, vy);
					pause((long)1/2);
					
					// removes bullet when beyond screen
					if (bullet[b].getY() < -20 || bullet[b].getX() < -20 || bullet[b].getX() > 1366) 
					{
						remove(bullet[b]);
						hitPipe.play();
						break;
					}
				}
			}
			noMoreBullets = true;
		}
		catch (InterruptedException exc) {}
	}

	public void mouseMoved(MouseEvent me)
	{
		if (outOfBounds) outOfBounds = false; 	 // 
		deltaX = me.getX() - ORG_X;
		deltaY = ORG_Y - me.getY();
		
		if (deltaX == 0) { restNoz(); }
		else if (deltaY <= 0)
		{
			outOfBounds = true;
			return;
		}
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
	
	private void setNozX() { nozX = ORG_X + (NOZ_LEN * Math.cos(angle)); }
	private void setNozY() { nozY = ORG_Y - (NOZ_LEN * Math.sin(angle)); }	
}

