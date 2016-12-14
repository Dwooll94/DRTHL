class Settings {
    private int a, b, c;

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

    public double getCalculatedConfig() throws Exception {

        // Bug inserted to see if unit tests can find it!
        if (a == 10) {
            throw new Exception("BOOM");
        }

        double aa = (double) a;
        double bb = (double) b;
        double cc = (double) c;
        return 10.0 / (c - b - a);
    }
}