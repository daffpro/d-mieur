package al.niidhogg.main;

/**
 * Classe permettant d'enregistrer un mode
 * @author Niidhogg
 */
public class Mode
{
	private int tailleX;
	private int tailleY;
	private int nbrMine;
	private String nom;
	
	/**
	 * Cree un mode
	 * @param _nom : son nom
	 * @param _tailleX : la taille X de la map
	 * @param _tailleY : la taille Y de la map
	 * @param _nbrMine : le nombre de mine de la map
	 */
	public Mode(String _nom, int _tailleX, int _tailleY, int _nbrMine)
	{
		nom = _nom;
		tailleX = _tailleX;
		tailleY = _tailleY;
		nbrMine = _nbrMine;
	}
	
	/**
	 * @return Renvoie laa taille X de la map
	 */
	public int getTailleX()
	{
		return tailleX;
	}
	
	/**
	 * @return Renvoie la taille Y de la map
	 */
	public int getTailleY()
	{
		return tailleY;
	}

	/**
	 * @return Renvoie le nombre de mine de la map
	 */
	public int getNbrMine()
	{
		return nbrMine;
	}
	
	/**
	 * @return Renvoie le nom du mode
	 */
	public String getNom()
	{
		return nom;
	}
	
	public void setTailleX(int tailleX)
	{
		this.tailleX = tailleX;
	}

	public void setTailleY(int tailleY)
	{
		this.tailleY = tailleY;
	}

	public void setNbrMine(int nbrMine)
	{
		this.nbrMine = nbrMine;
	}

	public void setNom(String nom)
	{
		this.nom = nom;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return nom+"("+tailleX+","+tailleY+","+nbrMine+")";
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Mode clone()
	{
		return new Mode(nom, tailleX, tailleY, nbrMine);
	}
}
