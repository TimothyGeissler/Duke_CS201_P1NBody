/**
 * @author Timothy Geissler
 * 
 * Simulation program for the NBody assignment
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class  NBody {
	
	/**
	 * Read the specified file and return the radius
	 * @param fname is name of file that can be open
	 * @return the radius stored in the file
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static double readRadius(String fname) throws FileNotFoundException  {
		Scanner s = new Scanner(new File(fname));
		//scan through file, get 1st data point (rad)
		s.nextInt();
		double rad = s.nextDouble();
		s.close();
		return rad;
	}
	
	/**
	 * Read all data in file, return array of Celestial Bodies
	 * read by creating an array of Body objects from data read.
	 * @param fname is name of file that can be open
	 * @return array of Body objects read
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static CelestialBody[] readBodies(String fname) throws FileNotFoundException {

		Scanner s = new Scanner(new File(fname));
		//Scan file, # of bodies is 1st int
		int nb = s.nextInt();          // # bodies to be read
		s.nextLine(); //skip 2 lines
		s.nextLine();
		CelestialBody [] cBodyArray = new CelestialBody[nb]; //array of CelestialBody objects
		for (int k = 0; k < nb; k++) {
			String nextLine = s.nextLine(); //get next line
			Scanner scanDelim = new Scanner(nextLine); //create scanner to split by spaces
			double x = scanDelim.nextDouble();
			double y = scanDelim.nextDouble();
			double xv = scanDelim.nextDouble();
			double yv = scanDelim.nextDouble();
			double m = scanDelim.nextDouble();
			String name = scanDelim.next();
			CelestialBody cb = new CelestialBody(x, y, xv, yv, m, name); //create new object
			cBodyArray[k] = cb; //add to array
		}
		s.close(); //close scanner
		return cBodyArray;
	}
	public static void main(String[] args) throws FileNotFoundException{
		double totalTime = 39447000.0;
		double dt = 25000.0;

		String fname= "./data/planets.txt";

		if (args.length > 2) {
			totalTime = Double.parseDouble(args[0]);
			dt = Double.parseDouble(args[1]);
			fname = args[2];
		}	
		
		CelestialBody[] bodies = readBodies(fname);
		double radius = readRadius(fname);

		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0,0,"images/starfield.jpg");
		//Audio:
		//StdAudio.play("images/2001.wav");

		// run simulation until over

		for(double t = 0.0; t < totalTime; t += dt) {

			//  arrays to hold forces on each body
			double [] xforces = new double[bodies.length];
			double [] yforces = new double[bodies.length];

			//loop of al bodies & calculate net forces on X & y, store in arrays
			for(int k=0; k < bodies.length; k++) {
				xforces[k] = bodies[k].calcNetForceExertedByX(bodies); //save net forces at corresponding index
				yforces[k] = bodies[k].calcNetForceExertedByY(bodies);
  			}

			// Update all CelestialBody objects w/ new forces data
			for(int k=0; k < bodies.length; k++){
				bodies[k].update(dt, xforces[k], yforces[k]);
			}

			StdDraw.clear();
			StdDraw.picture(0,0,"images/starfield.jpg");
			
			//Draw each CelestialBody on screen
			for(CelestialBody b : bodies){
				// code here
				b.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);

		}
		
		// prints final values after simulation
		
		System.out.printf("%d\n", bodies.length);
		System.out.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
		    System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		   		              bodies[i].getX(), bodies[i].getY(), 
		                      bodies[i].getXVel(), bodies[i].getYVel(), 
		                      bodies[i].getMass(), bodies[i].getName());	
		}
	}
}
