package al.niidhogg.ensemble;

/**
 * Cree un intervalle du type: [borneInf ; borneSup[
 * @author Niidhogg
 */
public class Intervalle extends Nombre
{
	/**
	 * Cree un intervalle du type: [borneInf ; borneSup[
	 * @param borneInf : la borne inferieur inclus
	 * @param borneSup : la borne exterieur exclus
	 */
	public Intervalle(double borneInf, double borneSup)
	{
		if(borneInf <= borneSup)
		{
			this.a = borneInf;
			this.b = borneSup;
		}
		else
		{
			this.a = borneSup;
			this.b = borneInf;
		}
	}
	
	/**
	 * Renvoie true si le nombre se trouve dans l'intervalle
	 * @param nbr
	 * @param nbr2
	 * @return Renvoie true ou false
	 */
	public boolean isIn(double nbr)
	{
		if(nbr >= a && nbr < b)
			return true;
		else return false;
	}
	
	/**
	 * @deprecated
	 */
	public boolean isIn(double nbr, double nbr2)
	{
		if(nbr >= a && nbr < b)
			return true;
		else return false;
	}
}
