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
	
		// TODO: read values at beginning of file to
		// find the radius

		Scanner scan = new Scanner(new File(fname));
		scan.nextInt();
		double rad = scan.nextDouble();
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
		// TODO: read # bodies, store in nb
		int nb = s.nextInt();          // # bodies to be read
		s.nextLine();
		s.nextLine();
		// TODO: Create array that can store nb CelestialBodies
		CelestialBody [] cBodyArray = new CelestialBody[nb];
		// TODO: read and ignore radius
		for (int k = 0; k < nb; k++) {
			String nextLine = s.nextLine();
			Scanner scanDelim = new Scanner(nextLine);
			double x = scanDelim.nextDouble();
			double y = scanDelim.nextDouble();
			double xv = scanDelim.nextDouble();
			double yv = scanDelim.nextDouble();
			double m = scanDelim.nextDouble();
			String name = scanDelim.next();
			CelestialBody cb = new CelestialBody(x, y, xv, yv, m, name);
			cBodyArray[k] = cb;
			// TODO: read data for each body
			// TODO: construct new body object and add to array

		}

		s.close();

		// TODO: return array of body objects read
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

		// TODO: for music/sound, uncomment next line

		//StdAudio.play("images/2001.wav");

		// run simulation until over

		for(double t = 0.0; t < totalTime; t += dt) {
			
			// TODO: create double arrays xforces and yforces
			//       to hold forces on each body
			double [] xforces = new double[bodies.length];
			double [] yforces = new double[bodies.length];
			// TODO: loop over all bodies
			// TODO: calculates netForcesX and netForcesY and store in
			//       arrays xforces and yforces

			for(int k=0; k < bodies.length; k++) {
				// code here
				xforces[k] = bodies[k].calcNetForceExertedByX(bodies);
				yforces[k] = bodies[k].calcNetForceExertedByY(bodies);
  			}

			// TODO: loop over all bodies and call update
			//       with dt and corresponding xforces and yforces values
			for(int k=0; k < bodies.length; k++){
				// code here
				bodies[k].update(dt, xforces[k], yforces[k]);
			}

			StdDraw.clear();
			StdDraw.picture(0,0,"images/starfield.jpg");
			
			// TODO: loop over all bodies and call draw on each one

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
