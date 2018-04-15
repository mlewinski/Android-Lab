package psm.edu.proj02;

/**
 * Created by lewin on 20.03.2018.
 */

public class CPlate {
    protected double F = 0;
    protected double R = 0;
    protected double H = 0;
    protected final double Ni = 0.3;
    protected final double E = 2.1E5;

    public CPlate(double f, double r, double h) {
        F = f;
        R = r;
        H = h;
    }

    public double getF() {
        return F;
    }

    public void setF(double f) {
        F = f;
    }

    public double getR() {
        return R;
    }

    public void setR(double r) {
        R = r;
    }

    public double getH() {
        return H;
    }

    public void setH(double h) {
        H = h;
    }

    public double calculateUMax(){
        return (F/(H*H))*((Ni+1)*(0.485*Math.log(R/H)+0.52)+0.48);
    }

    public double calculateSigma(){
        return (F*R*R*(3+Ni))/(16*Math.PI*((E*H*H*H)/12*(1-Ni*Ni))*(1+Ni));
    }
}
