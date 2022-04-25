package al.niidhogg.main;

import java.awt.GridLayout;

import javax.swing.JPanel;

import al.niidhogg.demineur.Map;
import al.niidhogg.demineur.MineException;

/**
 * Classe permettant de cree un jeu
 * @author Niidhogg
 */
public class Game extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private Map map;
	
	/**
	 * Cree le jeu
	 * @param x : taille x de la map
	 * @param y : taille y de la map
	 * @param m : nombre de mine
	 * @throws MineException
	 */
	public Game(int x, int y, int m) throws MineException
	{
		this.setLayout(new GridLayout(1, 1));
		
		map = new Map(x, y, m);
		this.add(map);
	}
	
	/**
	 * Cree le jeu
	 * @param _map : la carte
	 */
	public Game(Map _map)
	{
		this.setLayout(new GridLayout(1, 1));
		
		map = _map;
		this.add(map);
	}
	
	/**
	 * @return Renvoie une reference vers la carte
	 */
	public Map getMap()
	{
		return map;
	}
}
