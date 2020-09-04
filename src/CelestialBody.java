

/**
 * Celestial Body class for NBody
 * @author ola
 *
 */
public class CelestialBody {

	private double myXPos;
	private double myYPos;
	private double myXVel;
	private double myYVel;
	private double myMass;
	private String myFileName;

	/**
	 * Create a Body from parameters	
	 * @param xp initial x position
	 * @param yp initial y position
	 * @param xv initial x velocity
	 * @param yv initial y velocity
	 * @param mass of object
	 * @param filename of image for object animation
	 */
	// Parametized constructor of CelestialBody object
	public CelestialBody(double xp, double yp, double xv, double yv, double mass, String filename){
		this.myXPos = xp;
		this.myYPos = yp;
		this.myXVel = xv;
		this.myYVel = yv;
		this.myMass = mass;
		this.myFileName = filename;
	}



	/**
	 * Copy constructor: copy instance variables from one
	 * body to this body
	 * @param b used to initialize this body
	 */
	public CelestialBody(CelestialBody b){
		this.myXPos = b.getX();
		this.myYPos = b.getY();
		this.myXVel = b.getXVel();
		this.myYVel = b.getYVel();
		this.myMass = b.getMass();
		this.myFileName = b.myFileName;
	}

	// get x pos of object
	public double getX() {
		return this.myXPos;
	}
	// get y pos of object
	public double getY() {
		return this.myYPos;
	}
	// get x velocity of object
	public double getXVel() {
		return this.myXVel;
	}
	/**
	 * Return y-velocity of this Body.
	 * @return value of y-velocity.
	 */
	// get y velocity of object
	public double getYVel() {
		return this.myYVel;
	}
	// get mass of object
	public double getMass() {
		return this.myMass;
	}

	public String getName() {
		return myFileName;
	}

	/**
	 * Return the distance between this body and another
	 * @param b the other body to which distance is calculated
	 * @return distance between this body and b
	 */
	public double calcDistance(CelestialBody b) {
		// Using pythag to zolve
		return Math.sqrt(Math.pow(this.myXPos - b.getX(), 2) + Math.pow(this.myYPos - b.getY(), 2));
	}

	/**
	 * Calculate force exerted by another object on this one
	 * @param b the other Celestial Body
	 * @return force exerted
	 */
	public double calcForceExertedBy(CelestialBody b) {
		// law of universal gravitation
		return (6.67*Math.pow(10, -11)) * ((b.getMass() * this.getMass()) / Math.pow(this.calcDistance(b), 2));
	}

	/**
	 * Calculate x value of force exerted by another object on this one
	 * @param b the other Celestial Body
	 * @return force exerted in x direction
	 */
	public double calcForceExertedByX(CelestialBody b) {
		// Force formula
		return calcForceExertedBy(b) * ((b.getX() - this.myXPos) / this.calcDistance(b));
	}
	/**
	 * Calculate y value of force exerted by another object on this one
	 * @param b the other Celestial Body
	 * @return force exerted in y direction
	 */
	public double calcForceExertedByY(CelestialBody b) {
		// Force formula
		return calcForceExertedBy(b) * ((b.getY() - this.myYPos) / this.calcDistance(b));
	}

	/**
	 * Calculate net forces exerted by all CelestialBodies in x direction
	 * @param bodies all bodies to be considered
	 * @return sum of forces in x direction acting on this object
	 */
	public double calcNetForceExertedByX(CelestialBody[] bodies) {
		// Sums forces
		double sum = 0.0;
		for (CelestialBody b: bodies) {
			if (!b.equals(this)) {
				sum = sum + this.calcForceExertedByX(b);
			}
		}
		return sum;
	}

	/**
	 * Calculate net forces exerted by all CelestialBodies in y direction
	 * @param bodies all bodies to be considered
	 * @return sum of forces in y direction acting on this object
	 */
	public double calcNetForceExertedByY(CelestialBody[] bodies) {
		//sums forces
		double sum = 0.0;
		for (CelestialBody b: bodies) {
			if (!b.equals(this)) {
				sum = sum + this.calcForceExertedByY(b);
			}
		}
		return sum;
	}

	/**
	 * Update this CelestialBody object with new data after calculating new position & velocity
	 * @param deltaT change in time
	 * @param xforce force applied in x direction
	 * @param yforce force applied in y direction
	 */
	public void update(double deltaT, double xforce, double yforce) {
		// updates this object with new data
		double aX = xforce / this.myMass; //a = fx / m
		double aY = yforce / this.myMass;// a = fy / m

		double nvx = this.myXVel + deltaT * aX; //v1 = v2 + dt * a
		double nvy = this.myYVel + deltaT * aY;

		double nx = this.myXPos + deltaT * nvx; //x = x2 + dt * v1
		double ny = this.myYPos + deltaT * nvy;

		//update values
		this.myXPos = nx;
		this.myYPos = ny;
		this.myXVel = nvx;
		this.myYVel = nvy;
	}

	/**
	 * Draws this planet's image at its current position
	 */
	public void draw() {
		StdDraw.picture(myXPos,myYPos,"images/"+myFileName);
	}
}
