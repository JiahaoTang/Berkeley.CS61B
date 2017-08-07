public class NBody {
	public static double readRadius(String file_path){
		In file = new In(file_path);
		int number = file.readInt();
		return file.readDouble();
	}
	
	public static int readNumber(String file_path){
		In file = new In(file_path);
		return file.readInt();
	}
	

	public static Planet[] readPlanets(String file_path){
		//Read the number of planets.
		int number = readNumber(file_path);

		//Create a array of planets.
		Planet allPlanets[] = new Planet[number];
		int counter = 0;

		In file = new In(file_path);
		file.readInt();
		file.readDouble();

		while(counter < number){
			double xxPos = file.readDouble();
			double yyPos = file.readDouble();
			double xxVel = file.readDouble();
			double yyVel = file.readDouble();
			double mass = file.readDouble();
			String imgFileName = file.readString();
			Planet p = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
			allPlanets[counter] = p;
			counter += 1;
		}
		return allPlanets;
	}

	public static void main(String[] args){
		//Collecting all needed input.
		Double T =  Double.parseDouble(args[0]);
		Double dt =  Double.parseDouble(args[1]);
		String filename = args[2];
		int number = readNumber(filename);
		double Radius = readRadius(filename);

		//Drawing the Background.

		/*Input the background image.*/
		String Background_img = "images/starfield.jpg";

		/*Set the scale of the background.*/
		StdDraw.setScale(-Radius, Radius);

		/*Clears the drawing window*/
		//StdDraw.clear();

		/*Stamps the background image at the center of the universe.*/
		//StdDraw.picture(0, 0, Background_img);

		/*Shows the background image on the screen.*/
		//StdDraw.show();

		/*Draw all planets on the background.*/
		//Take all planets from the file.
		Planet[] planets = readPlanets(filename);

		double time = 0;

		while (time <= T){
			double[] xForce = new double[number];
			double[] yForce = new double[number];

			int count = 0;

			for(Planet p : planets){
				xForce[count] = p.calcNetForceExertedByX(planets);
				yForce[count] = p.calcNetForceExertedByY(planets);
				count += 1;
			}

			count = 0;

			for(Planet p : planets){
				p.update(dt, xForce[count], yForce[count]);
				count += 1;
			}

			/*Clear the screen*/
			StdDraw.clear();

			/*Draw background image*/
			StdDraw.picture(0, 0, Background_img);

			/*Draw all planets on the background.*/
			for(Planet p : planets){
				p.draw();
			}

			/*Pause the animation for 10 milliseconds.*/
			StdDraw.show(10);

			/*Increasing the time.*/
			time = time + dt;
		}
	}
}
