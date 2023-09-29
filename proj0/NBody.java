public class NBody{
    public static double readRadius(String path){
        In in = new In(path);
        int first_data = in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String path){
         
        In in = new In(path);
        int number = in.readInt();
        double second_data = in.readDouble();
        Planet[] planets = new Planet[number];
        for(int i = 0; i < number; i++){
            double xPos = in.readDouble();
            double yPos = in.readDouble();
            double xVel = in.readDouble();
            double yVel = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            planets[i] = new Planet(xPos, yPos, xVel, yVel, m, img);
        }
        
        return planets;
    }

    public static void main(String[] args){
        /* fundational setting */
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] planets = readPlanets(filename);
        double radius = readRadius(filename);

        /* initialize StdDraw */
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0, 0, "images/starfield.jpg");
        
        /* settles planets */
        for(int i = 0; i < planets.length; i++){
                planets[i].draw();
            }

        /* animation part */
        StdDraw.enableDoubleBuffering();
        double[] xForces = new double[planets.length];
        double[] yForces = new double[planets.length];
        for(int time = 0; time < T; time += dt){
            StdDraw.clear();     
            
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for(int i = 0; i < planets.length; i++){
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }

            for(int i = 0; i<planets.length; i++)
                planets[i].update(dt, xForces[i], yForces[i]);
                
            for(int i = 0; i<planets.length; i++)
                planets[i].draw();
            

            StdDraw.show();
            StdDraw.pause(10);
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
       
        

        
    }
}