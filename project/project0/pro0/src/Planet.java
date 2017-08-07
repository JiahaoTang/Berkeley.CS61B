import java.lang.Math;

public class Planet{
		public double xxPos;
		public double yyPos;
		public double xxVel;
		public double yyVel;
		public double mass;
		public String imgFileName;

	public Planet(double xP, double yP, double xV,
	              double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}
	private Planet pCopy;

	public Planet(Planet p){
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet p){
		double delta_x = xxPos - p.xxPos;
		double delta_y = yyPos - p.yyPos;
		double distance = Math.sqrt(Math.pow(delta_x, 2) + Math.pow(delta_y, 2));
		return distance;
	}

	public double calcForceExertedBy(Planet p){
		return 6.67e-11 * mass * p.mass / Math.pow(calcDistance(p), 2);
	}

	public double calcForceExertedByX(Planet p){
		double delta_x = xxPos - p.xxPos;
		return - calcForceExertedBy(p) * delta_x / calcDistance(p);
	}

	public double calcForceExertedByY(Planet p){
		double delta_y = yyPos - p.yyPos;
		return - calcForceExertedBy(p) * delta_y / calcDistance(p);
	}

	public double calcNetForceExertedByX(Planet[] allPlanets){
		double ForceX = 0;
		for (Planet p : allPlanets){
			if (!this.equals(p)){
				ForceX = ForceX + calcForceExertedByX(p);
			}
		}
		return ForceX;
	}

	public double calcNetForceExertedByY(Planet[] allPlanets){
		double ForceY = 0;
		for (Planet p : allPlanets){
			if (!this.equals(p)){
				ForceY = ForceY + calcForceExertedByY(p);
			}
		}
		return ForceY;
	}

	public void update(double delta_t, double x_Force, double y_Force){
		double a_x = x_Force / mass;
		double a_y = y_Force / mass;
		//Exerted by other planets, the obj's attributes will change, not returning a new velocity and position.
		xxVel = xxVel + a_x * delta_t;
		yyVel = yyVel + a_y * delta_t;
		xxPos = xxPos + xxVel * delta_t;
		yyPos = yyPos + yyVel * delta_t;
	}

	/*Draw the planet on the universe.*/
	public void draw(){
		/*Input the image of the planet.*/
		String img = "images/" + imgFileName;

		/*Stamps the copy of the image on the backgound*/
		StdDraw.picture(xxPos, yyPos, img);

		/*Show the image on the screen*/
		StdDraw.show();
	}

}
