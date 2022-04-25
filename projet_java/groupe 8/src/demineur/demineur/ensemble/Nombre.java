package al.niidhogg.ensemble;

/**
 * Classe abstraite permettant d'assembler les intervalles et les ensemble
 * @author Niidhogg
 */
public abstract class Nombre
{
	protected double a;
	protected double b;
	
	/**
	 * Renvoie vrai ou faux selon que les nombres soit dans l'intervalle(ensemble, couple...) ou pas
	 * @param nbr
	 * @param nbr2
	 * @return Renvoie true ou false
	 */
	public abstract boolean isIn(double nbr, double nbr2);
}
