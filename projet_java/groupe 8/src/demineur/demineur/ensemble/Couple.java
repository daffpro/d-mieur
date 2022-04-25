package al.niidhogg.ensemble;

/**
 * Un couple de nombre
 * @author Niidhogg
 */
public class Couple extends Nombre
{
	/**
	 * Crer un couple de nombre avec a et b
	 * @param a
	 * @param b
	 */
	public Couple(double a, double b)
	{
		this.a = a;
		this.b = b;
	}
	
	/**
	 * Renvoie vrai si les deux nombres sont les nombres du couple
	 */
	public boolean isIn(double nbr, double nbr2)
	{
		if(nbr == a && nbr2 == b)
			return true;
		else return false;
	}
	
}
