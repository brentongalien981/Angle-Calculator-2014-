import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class LineBulletLauncher extends GraphicsProgram {
	GLine l;
	GLabel lab;
	
	double y;
	double x;
	int nozLen = 100;
	int xOrg = 680;
	int yOrg = 650;
	
	private double vx, vy, m;
	private double rise, run;
	
	private AudioClip laser = MediaTools.loadAudioClip("laser.wav");
	private AudioClip hitPipe = MediaTools.loadAudioClip("hitPipe.wav");
	private AudioClip gameOverLaugh = MediaTools.loadAudioClip("gameOverLaugh.wav");
	private AudioClip newBallLaugh = MediaTools.loadAudioClip("newBallLaugh.wav");
	
	private void setBulletPath()
	{
		setRise();
		setRun();
		
		// for X velocity (vx)
		if (run > 0) {vx = 1;}
		else if (run < 0) {vx = -1;}
		else {vx = 0;}
		
		// for vy
		if (vx == 0) { vy = -1; }
		else
		{
			// for slope (m)
			m = rise/run;
			vy = m*vx;
		}			
	}
	
	private void setRise() { rise = nozEndY-yOrg;	}
	private void setRun() { run = nozEndX-xOrg; }
	
	//boolean paused = false;
	boolean bulletIsReady = true;
	
	public void init() {
		l = new GLine(xOrg, yOrg, xOrg, yOrg-nozLen);
		lab = new GLabel("label", 100, 100);
		add(lab);
		add(l);
		addMouseListeners();
	}
	
	synchronized public void mouseClicked(MouseEvent me) {
		if (nozEndX >= xMin && nozEndX <= xMax)
		{
			setBulletPath();
			laser.play();
			notify();
			lab.setLabel("FIRE IN THE HOLE!!!");
		}
		else
		{
			lab.setLabel("NOZZLE IS BEYOND SCOPE");
			gameOverLaugh.play();
		}
	}
	
	public void run()
	{
		int N_BULLETS = 5;
		GOval bullet[] = new GOval[N_BULLETS];
		
		synchronized(this)
		{
			try {
				for (int b=0; b<N_BULLETS; b++)
				{
					wait();
					bullet[b] = new GOval(nozEndX, nozEndY, 20, 20);
					add(bullet[b]);
					
					while (true)
					{
						bullet[b].move(vx, vy);
						pause((long)1/2);
						if (bullet[b].getY() < 0) 
						{
							remove(bullet[b]);
							hitPipe.play();
							break;
						}
					}
				}
			}
			catch (InterruptedException exc) {}
		}
	}
	
	public void mouseMoved(MouseEvent me) {
		nozEndX = me.getX();
		double xLen = nozEndX - xOrg;	// to get the "a" variable for pythagorean theorem
		
		// y-int of the origin minus  the length of 
		// the "b" variable in pythagorean theorem
		nozEndY = yOrg - Math.sqrt( (nozLen*nozLen) - (xLen*xLen) );	
		
		// the scope of the FireLauncher -- radius of the semi-circle
		
		
		if (nozEndX >= xMin && nozEndX <= xMax) {
			l.setEndPoint(nozEndX, nozEndY);
			showStatus(nozEndX + ", " + nozEndY);
		}
	}
	int xMin = xOrg - nozLen;
	int xMax = xOrg + nozLen;
	
	private double nozEndY = 650;
	private double nozEndX = 680;
}

