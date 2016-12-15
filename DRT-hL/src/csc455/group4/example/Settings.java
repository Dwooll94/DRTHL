package csc455.group4.example;

class Settings {
    private int a, b, c;
    
    public Settings(){
    	a= 0; b= 0; c = 0;
    }

    public void setA(int x) {
        a = x;
    }

    public void setB(int x){
        b = x;
    }

    public void setC(int x){
        c = x;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    public double getCalculatedConfig(){
    	
        // Bug inserted to see if unit tests can find it!
        if (a == 10 || Math.abs(a) + Math.abs(b)+ Math.abs(c) > 1073741823) {
        	System.out.println("Boom");
        	a= 0;
        	b= 0;
        	c = 0;
            
        }

        double aa = (double) a;
        double bb = (double) b;
        double cc = (double) c;
        return 10.0 / (c - b - a);
    }
}