package al.niidhogg.ensemble;

import java.util.ArrayList;

public class Ensemble extends ArrayList<Nombre>
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Ajouter un nombre a l'ensemble
	 * @param nbr
	 */
	public void add(double nbr)
	{
		this.add(new Intervalle(nbr, nbr));
	}
	
	/**
	 * Ajouter un intervalle a l'ensemble
	 * @param interv : L'intervalle a ajouter
	 */
	public void add(Intervalle interv)
	{
		this.add(interv);
	}
	
	/**
	 * Ajouter un ensemble à l'ensemble
	 * @param ens : L'ensemble a ajouter
	 */
	public void add(Ensemble ens)
	{
		this.add(ens);
	}
	
	/**
	 * Renvoie true si le nombre se trouve dans l'ensemble
	 * @param nbr
	 * @param nbr2
	 * @return Renvoie true ou false
	 */
	public boolean isIn(double nbr)
	{
		for(int i=0;i<size();i++)
		{
			if(get(i).isIn(nbr, 0))
				return true;
		}
		
		return false;
	}
}
