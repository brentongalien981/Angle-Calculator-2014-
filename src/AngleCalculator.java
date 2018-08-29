//import acm.graphics.*;
import acm.program.*;
//import acm.io.*;
//import acm.util.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.applet.*;

public class AngleCalculator extends ConsoleProgram {
	int orgX = 680;
	int orgY = 650;
	int nozLen = 100;
	double nozX, nozY;
	int deltaX, deltaY;
	double radAngle, degAngle;
	
	public void run()
	{		
		while (true)
		{

			int mouseX = readInt("mouseX: ");
			int mouseY = readInt("mouseY: ");
			
			deltaX = mouseX - orgX;
			deltaY = orgY - mouseY;
			
			println("deltaX: " + deltaX);
			println("deltaY: " + deltaY);
			
			if (deltaX == 0)
			{
				nozX = orgX;
				nozY = orgY - nozLen; 
			}
			else if (deltaY <= 0) {println("your mouse is out of bounds");}
			
			else
			{
				setAngles();
				nozX = orgX + (nozLen * Math.cos(radAngle));
				nozY = orgY - (nozLen * Math.sin(radAngle));
			}		
			
			println("\nnozX: " + nozX);
			println("nozY: " + nozY + "\n");
		}
	}
	private void setAngles()
	{
		radAngle = Math.atan((double) deltaY/deltaX);
		degAngle = Math.toDegrees(radAngle);
		
		if (degAngle < 0)
		{
			println("angle was negative.");
			degAngle = 180+degAngle;
			println("The complementary angle is: " + degAngle);
			radAngle = Math.toRadians(degAngle);
		}
		println();
		println("Angle in degrees: " + degAngle);
		println("Angle in radians: " + radAngle);
	}
}
