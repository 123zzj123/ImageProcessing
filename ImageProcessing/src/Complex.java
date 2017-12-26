
public class Complex {
	private double Real;
	private double Imaginary;
	
	public Complex(double r, double i) {
		// TODO Auto-generated constructor stub
		this.Real = r;
		this.Imaginary = i;
	}
	
	public double GetAbs() {
		return Math.hypot(Real, Imaginary);
	}
	
	public Complex PLUS(Complex complex) {
		return new Complex(Real + complex.Real, Imaginary + complex.Imaginary);
	}
	
	public Complex MINUS(Complex complex) {
		return new Complex(Real - complex.Real, Imaginary - complex.Imaginary);
	}
	
	public Complex Multiple(Complex complex) {
		return new Complex(Real*complex.Real - Imaginary*complex.Imaginary, Imaginary*complex.Real + Real * complex.Imaginary);
	}
	
	public Complex MultipleNum(double temp) {
		return new Complex(Real * temp, Imaginary * temp);
	}
	
	public Complex EXP() {
		return new Complex(Math.exp(Real)*Math.cos(Imaginary), Math.exp(Real)*Math.sin(Imaginary));
	}
	
	public double GetR() {
		return Real;
	}
	
	public double GetI() {
		return Imaginary;
	}
	
	public Complex Getconjugate() {
		return new Complex(Real, -Imaginary);
	}
}
