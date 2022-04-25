package al.niidhogg.ensemble;

/**
 * Crer un intervalle à deux dimenssion
 * @author Niidhogg
 */
public class Intervalle2D extends Nombre
{
	private Intervalle a;
	private Intervalle b;
	
	/**
	 * Cree un couple d'intervalle
	 * @param a : le premiere intervalle
	 * @param b : le second intervalle
	 */
	public Intervalle2D(Intervalle a, Intervalle b)
	{
		this.a = a;
		this.b = b;
	}
	
	/**
	 * Renvoie true si nbr se trouve dans le premiere intervalle ET si nbr2 se trouve dans le second
	 */
	public boolean isIn(double nbr, double nbr2)
	{
		if(a.isIn(nbr) && b.isIn(nbr2))
			return true;
		else return false;
	}
}
