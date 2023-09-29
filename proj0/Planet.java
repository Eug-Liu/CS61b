public class Planet{

    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public double G = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        /** Construct a planet */
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p){
        /** Copy a planet */
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double square(double n){
        return n * n;
    }

    public double calcDistance(Planet p){
        return Math.sqrt(square((this.xxPos - p.xxPos)) + square((this.yyPos - p.yyPos)));
    }

    public double calcForceExertedBy(Planet p){
        double r = calcDistance(p);
        return G * this.mass * p.mass / square(r);
    }

    public double calcForceExertedByX(Planet p){
        double F = calcForceExertedBy(p);
        double r = calcDistance(p);
        return F * Math.sqrt(square((this.xxPos - p.xxPos))) / r;    
    }

    public double calcForceExertedByY(Planet p){
        double F = calcForceExertedBy(p);
        double r = calcDistance(p);
        return F * Math.sqrt(square((this.yyPos - p.yyPos))) / r;    
    }

    public double calcNetForceExertedByX(Planet[] ps){
        double F_net = 0;
        for(int i = 0; i < ps.length; i++){
            if(this.equals(ps[i]))
                continue;
            F_net += calcForceExertedByX(ps[i]);
        }
        return F_net;
    }

    public double calcNetForceExertedByY(Planet[] ps){
        double F_net = 0;
        for(int i = 0; i < ps.length; i++){
            if(this.equals(ps[i]))
                continue;
            F_net += calcForceExertedByY(ps[i]);
        }
        return F_net;
    }

    public void update(double t, double fx, double fy){
        double ax = fx / mass;
        double ay = fy / mass;
        xxVel += t * ax;
        yyVel += t * ay;
        xxPos += t * xxVel;
        yyPos += t * yyVel;
    }    

    public void draw(){
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }

}