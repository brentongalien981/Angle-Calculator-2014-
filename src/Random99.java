import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.applet.*;
import java.awt.event.*;

public class Random99 extends Applet implements Runnable {
	Image img;
	Canvas c;
	int x, y;
	Thread t;
	static int count = 0;
	
	public void init() {
		//img = getImage(getDocumentBase(), "ai.jpg");
		
		int left, up, right, down;
		left = 37;
		up = 38;
		right = 39;
		down = 40;
		add(new LineBulletLauncher());
		
		//addMouseMotionListener(new MouseHandler());
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				//left
				if (ke.getKeyCode() == 37) { x-=5; 	repaint(); }
				//up
				else if (ke.getKeyCode() == 38) { y-=5; 	repaint(); }
				//right
				else if (ke.getKeyCode() == 39) { x+=5; 	repaint(); }
				//down
				else if (ke.getKeyCode() == 40) { y+=5; 	repaint(); }
				else {
					selectThread(count);
					++count;
				}
				
			}
		});
		
	}
	
	int ballX = 0;
	int ballY = 600;
	
	public void selectThread(int threadNum) {
		Thread t1, t2, t3;
		switch(threadNum) {
		case 0:
			t1 = new Thread(this);
			t1.start();
			break;
		case 1:
			t2 = new Thread(this);
			t2.start();
			break;
		case 2:
			t3 = new Thread(this);
			t3.start();
			break;
		}
		
	}
	
	public void run() {
		ballX = 0;
		ballY = 600;
		for (int i=0; i<100; i++) {
			ballX += 5;
			ballY -= 2;
			repaint();
			try { Thread.sleep(50); } catch(InterruptedException e) {}
		}
	}
	
	public void paint(Graphics g) {
		g.drawRect(x, y, 30, 30);
		if (count == 1) {
			g.drawOval(ballX, ballY, 10, 10);
		}
	}
	
	/*class MouseHandler extends MouseAdapter {		
		public void mouseDragged(MouseEvent me) {
			x = me.getX();
			y = me.getY();
			repaint();
		}
	}
	*/
}
