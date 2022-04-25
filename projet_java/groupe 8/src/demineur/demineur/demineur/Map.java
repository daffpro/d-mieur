package al.niidhogg.demineur;

import java.awt.GridLayout;
import java.io.Serializable;
import java.util.Random;

import javax.swing.JPanel;

import al.niidhogg.ensemble.Intervalle;
import al.niidhogg.ensemble.Intervalle2D;

/**
 * La map du demineur
 * @author Niidhogg
 */
public class Map extends JPanel implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int tailleX;
	private int tailleY;
	private int nbrMine;
	
	private Case[][] map;

	private boolean dead;
	
	public long deb;
	public long pauseT = 0;
	private long timeStamp = 0;
	private int nbrFlag;
	
	/**
	 * Cree une Map
	 * @param tailleX : la taille X de la map
	 * @param tailleY : la taille Y de la map
	 * @param nbrMine : le nombre de la mine qui se trouve sur la carte
	 * @throws MineException
	 */
	public Map(int tailleX, int tailleY, int nbrMine) throws MineException
	{
		this.tailleX = tailleX;
		this.tailleY = tailleY;
		this.nbrMine = nbrMine;
		
		dead = false;
		this.setLayout(new GridLayout(tailleY, tailleX));
		
		if(nbrMine < tailleX*tailleY-9);
		else throw new MineException();//Si il y a trop de mine pour la carte
		
		map = new Case[tailleY][tailleX];
		
		//DONNER TOUTE LES REFERENCES DES CASES QUI SONT AUTOURS
		for(int j=0;j<tailleY;j++)
		{
			for(int i=0;i<tailleX;i++)
			{
				map[j][i] = new Case();
				this.add(map[j][i]);
				
				map[j][i].setCoord(i, j);
				
				if(i > 0)
				{
					map[j][i].set(Case.OUEST, map[j][i-1]);
					map[j][i-1].set(Case.EST, map[j][i]);
				}
				if(j > 0)
				{
					map[j][i].set(Case.NORD, map[j-1][i]);
					map[j-1][i].set(Case.SUD, map[j][i]);
				}
				if(i > 0 && j > 0)
				{
					map[j][i].set(Case.NORD_OUEST, map[j-1][i-1]);
					map[j-1][i-1].set(Case.SUD_EST, map[j][i]);
				}
				if(i < tailleX-1 && j > 0)
				{
					map[j][i].set(Case.NORD_EST, map[j-1][i+1]);
					map[j-1][i+1].set(Case.SUD_OUEST, map[j][i]);
				}
			}
		}
	}
	
	/**
	 * Placer les mines sur la carte.<br />
	 * Intervalle ou aucune mine ne peut apparaitre: [[xInterdit-1, xInterdit+1],[yInterdit-1, yInterdit+1]]
	 * @param xInterdit
	 * @param yInterdit
	 */
	public void mine(int xInterdit, int yInterdit)
	{
		Intervalle2D inter = new Intervalle2D(
				new Intervalle(xInterdit-1, xInterdit+2), 
				new Intervalle(yInterdit-1, yInterdit+2)
				);
		
		//ON PLACE LES MINES
		for(int i=0;i<nbrMine;i++)
		{
			int x = new Random().nextInt(tailleX);
			int y = new Random().nextInt(tailleY);
			
			if(!inter.isIn(x, y) && map[y][x].getNumero() != Case.MINE)//SI LA MINE N'EST PAS DS L'INTERVALLE INTERDIT ET QU'IL N'Y EN A PAS DEJA UNE ICI
			{
				map[y][x].setNumero(Case.MINE);
				map[y][x].mine();//ON AUGMENTE DE 1 LES NUMEROS DES CASES ADJACENTE
			}
			else i--;
		}
	}
	
	/**
	 * Demine toute la case de la map
	 */
	public void demineAll()
	{
		for(int j=0;j<tailleY;j++)
		{
			for(int i=0;i<tailleX;i++)
			{
				//Si il y a un drapeau a l'endroit ou il n'y a pas de mine
				if(map[j][i].getCouverture() == Case.FLAG && map[j][i].getNumero() != Case.MINE)
					map[j][i].setNumero(Case.CROIX);
				
				map[j][i].setCouverture(Case.RIEN);
			}
		}
	}
	
	/**
	 * @return Renvoie true si tout est demine
	 */
	public boolean isWon()
	{
		for(int j=0;j<tailleY;j++)
		{
			for(int i=0;i<tailleX;i++)
			{
				if(map[j][i].getCouverture() != Case.RIEN && map[j][i].getNumero() != Case.MINE)
					return false;
			}
		}
		
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	public String toString()
	{
		String str = "";
		
		for(int j=0;j<tailleY;j++)
		{
			for(int i=0;i<tailleX;i++)
				str += map[j][i].toString();
			
			str += "\n";
		}
		
		return str;
	}
	
	/**
	 * @return renvoie true si on est tomber sur une mine
	 */
	public boolean isDead()
	{
		return dead;
	}
	
	public void dead()
	{
		dead = true;
	}
	
	public void setDead(boolean dead)
	{
		this.dead = dead;
	}
	
	public long getTimeStamp()
	{
		return timeStamp;
	}
	
	public void setTimeStamp(long timeStamp)
	{
		this.timeStamp = timeStamp;
	}
	
	public int getNbrFlag()
	{
		return nbrFlag;
	}
	
	public void setNbrFlag(int nbrFlag)
	{
		this.nbrFlag = nbrFlag;
	}
	
	/**
	 * @return Renvoie le nombre de mine
	 */
	public int getNbrMine()
	{
		return nbrMine;
	}
}
