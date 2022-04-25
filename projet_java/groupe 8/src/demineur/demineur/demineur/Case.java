package al.niidhogg.demineur;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

import al.niidhogg.ensemble.Intervalle;
import al.niidhogg.graph.Dialog;
import al.niidhogg.son.AePlayWave;

/**
 * Une case de la map du demineur
 * @author Niidhogg
 */
public class Case extends JButton implements MouseListener, Serializable
{
	private static final long serialVersionUID = 1L;
	
	/**Indique une case vide*/
	public static final int VIDE = 0;
	/**Indique une case avec une mine */
	public static final int MINE = 9;
	/**Indique une case ou il n'y a pas de drapeau normalement */
	public static final int CROIX = 10;
	
	/** Indique qu'il n'y a pas de couverture sur la case */
	public static final int RIEN = 0;
	/** Indique qu'il y a une simple couverture sur la case */
	public static final int BLOCK = 1;
	/** Indique qu'il y a un drapeau sur la case */
	public static final int FLAG = 2;
	/** Indique qu'il y a un point d'interogation sur la case */
	public static final int INT = 3;
	
	/*
	 * Points cardinaux, pour ce reperer
	 */
	public static final int NORD_OUEST = 0;
	public static final int NORD = 1;
	public static final int NORD_EST = 2;
	public static final int OUEST = 3;
	public static final int EST = 4;
	public static final int SUD_OUEST = 5;
	public static final int SUD = 6;
	public static final int SUD_EST = 7;
	
	public static final Intervalle INTERVALLE_NUMERO = new Intervalle(1, 9);
	
	private static int nbrFlag;
	
	private static boolean first;
	private static Map m;
	
	private static int taille = Info.tailleBloc;
	
	private int numero;
	private int couverture;
	
	//Les cases qui sont autours
	private Case[] map;
	
	private int x;
	private int y;
	
	private transient Image imgMine = Info.imgMine;
	
	//Couleur des cases
	private Color fondColor = new Color(200, 200, 200);
	private Color overColor = Color.GRAY;
	private Color borderColor = new Color(60, 60, 100);
	private Color overBorderColor = new Color(188, 188, 228);
	
	public static boolean stop;

	/**
	 * Cree un Case pour le demineur
	 */
	public Case()
	{
		this.numero = VIDE;
		this.couverture = BLOCK;
		
		map = new Case[8];
		first = true;
		nbrFlag = 0;
		stop = false;
		
		this.setText("");
		
		this.addMouseListener(this);
		
		this.setPreferredSize(new Dimension(taille, taille));
		
		this.setBorder(BorderFactory.createLineBorder(borderColor));
		
		this.setIcon(new Icon()
		{
			public int getIconHeight()
			{
				return Info.tailleBloc;
			}
			
			public int getIconWidth()
			{
				return Info.tailleBloc;
			}
			
			public void paintIcon(Component arg0, Graphics g, int arg2, int arg3)
			{
				g.drawImage(Info.imgOverCase, 0, 0, Info.tailleBloc, Info.tailleBloc, null);
			}
			
		});
	}
	
	/**
	 * Cree un case pour le demineur avec numero
	 * @param numero : le nombre qui est indiquer sous la case
	 * @see #VIDE
	 * @see #MINE
	 */
	public Case(int numero)
	{
		this.numero = numero;
		this.couverture = BLOCK;
		
		map = new Case[8];
		first = true;
		nbrFlag = 0;
		stop = false;

		this.setBackground(overColor);
		
		if(numero == VIDE)
			this.setText("");
		else if(numero == MINE)
			this.setText("*");
		else if(INTERVALLE_NUMERO.isIn(numero))
			this.setText(new Integer(numero).toString());
		
		this.addMouseListener(this);
		this.setPreferredSize(new Dimension(20, 20));
		
		this.setIcon(new Icon()
		{
			public int getIconHeight()
			{
				return Info.tailleBloc;
			}
			
			public int getIconWidth()
			{
				return Info.tailleBloc;
			}
			
			public void paintIcon(Component arg0, Graphics g, int arg2,
					int arg3)
			{
				g.drawImage(Info.imgOverCase, 0, 0, Info.tailleBloc, Info.tailleBloc, null);
			}
			
		});
	}
	
	/**
	 * Si la case contient une mine les cases allentour verrons leur nombre augmenter de 1
	 */
	public void mine()
	{
		if(numero == MINE)
		{
			for(int i=0;i<8;i++)
			{
				if(map[i] != null)
				{
					if(map[i].getNumero() != MINE)
						map[i].incrNumero();
				}
			}
		}
	}
	
	/**
	 * Methode permettant de demine une zone ou il n'y a que des cases vide
	 * @param playSon : true pour jouer le son
	 */
	public void demineVide(boolean playSon)
	{
		if(playSon)
		{
			Info.sonDemBlk.start();
			Info.sonDemBlk = new AePlayWave(Info.design+"/demineBlanc.son");
		}
		
		//Deminer les cases voisine qui sont vide
		
		HashSet<Case> elmt = new HashSet<Case>();
		
		for(int i=0;i<8;i++)
			elmt.add(map[i]);
		
		while(elmt.iterator().hasNext())
		{
			Case c = elmt.iterator().next();
			
			if(c != null)
			{
				if(c.getCouverture() != RIEN)
				{
					if(c.getCouverture() == FLAG)//Si il y un drapeau sur le passage
						nbrFlag--;
					
					c.setCouverture(RIEN);
					c.setBackground(fondColor);
					if(c.getNumero() == VIDE)//Si la case est vide on ajoute toute les cases autour qui ne sont pas decouvert
					{
						for(int i=0;i<8;i++)
						{
							if(c.map[i] != null)
							{
								if(c.map[i].getCouverture() != RIEN)
									elmt.add(c.map[i]);
							}
						}
					}
				}
			}
			
			elmt.remove(c);//On supprime cette elmt du HashSet
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g)
	{
		if(couverture == FLAG)
		{
			super.paintComponent(g);
			g.drawImage(Info.imgFlag, 0, 0, this.getWidth(), this.getHeight(), this);
		}
		else if(couverture == INT)
		{
			super.paintComponent(g);
			g.drawImage(Info.imgInt, 0, 0, this.getWidth(), this.getHeight(), this);
		}
		else if(couverture == BLOCK)
			super.paintComponent(g);
		else
		{
			g.setColor(super.getBackground());
			g.fillRect(0, 0, taille, taille);
			
			if(numero == VIDE);
			else if(numero == MINE)
				g.drawImage(imgMine, 0, 0, this.getWidth(), this.getHeight(), this);
			else if(numero == CROIX)
				g.drawImage(Info.imgCroix, 0, 0, this.getWidth(), this.getHeight(), this);
			else if(numero == 1)
				g.drawImage(Info.img1, 0, 0, this.getWidth(), this.getHeight(), this);
			else if(numero == 2)
				g.drawImage(Info.img2, 0, 0, this.getWidth(), this.getHeight(), this);
			else if(numero == 3)
				g.drawImage(Info.img3, 0, 0, this.getWidth(), this.getHeight(), this);
			else if(numero == 4)
				g.drawImage(Info.img4, 0, 0, this.getWidth(), this.getHeight(), this);
			else if(numero == 5)
				g.drawImage(Info.img5, 0, 0, this.getWidth(), this.getHeight(), this);
			else if(numero == 6)
				g.drawImage(Info.img6, 0, 0, this.getWidth(), this.getHeight(), this);
			else if(numero == 7)
				g.drawImage(Info.img7, 0, 0, this.getWidth(), this.getHeight(), this);
			else if(numero == 8)
				g.drawImage(Info.img8, 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}
	
	/**
	 * Donner la position de la case dans la map
	 * @param x : position x
	 * @param y : position y
	 * @see Map
	 */
	public void setCoord(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Permet de referencer les cases se trouvant autour de celle ci
	 * @param position : un point cardinal
	 * @param block : une reference vers la case
	 */
	public void set(int position, Case block)
	{
		map[position] = block;
	}
	
	/**
	 * Renvoie une reference de la case se trouvant au point cardinal position
	 * @param position : le point cardinal
	 * @return Renvoie une reference de Case
	 */
	public Case get(int position)
	{
		return map[position];
	}
	
	/**
	 * @return Renvoie le numero indiquer sur la case
	 * @see #MINE
	 * @see #VIDE
	 */
	public int getNumero()
	{
		return numero;
	}
	
	/**
	 * Change le numero de la case
	 * @param numero : le nouveau numero
	 * @see #MINE
	 * @see #VIDE
	 */
	public void setNumero(int numero)
	{
		this.numero = numero;
	}
	
	/**
	 * Fait augmenter le numero de 1
	 */
	public void incrNumero()
	{
		numero++;
	}
	
	/**
	 * Change la couverture de la case
	 * @param couverture : la nouvelle couverture
	 * @see #FLAG
	 * @see #INT
	 * @see #RIEN
	 * @see #BLOCK
	 */
	public void setCouverture(int couverture)
	{
		this.couverture = couverture;
		this.setBackground(fondColor);
	}

	/**
	 * @return Renvoie le type de couverture de la case
	 * @see #FLAG
	 * @see #INT
	 * @see #RIEN
	 * @see #BLOCK
	 */
	public int getCouverture()
	{
		return couverture;
	}
	
	/**
	 * @return Renvoie la position X dans la map
	 * @see Map
	 */
	public int getPosX()
	{
		return x;
	}
	
	/**
	 * @return Renvoie la position Y dans la map
	 * @see Map
	 */
	public int getPosY()
	{
		return y;
	}
	
	/**
	 * @return Retourne le nombre de case adjacente qui ne sont pas deminer
	 */
	public int nbrFlag()
	{
		int i=0;
		for(Case c : map)
		{
			if(c != null)//Si on est pas a bord
			{
				if(c.getCouverture() == FLAG)
					i++;
			}
		}
		
		return i;
	}
	
	/**
	 * On est tomber sur une mine, boom !
	 */
	public void boom()
	{
		Info.sonExp.start();
		Info.sonExp = new AePlayWave(Info.design+"/explosion.son");
		
		imgMine = Info.imgMineX;
		m.dead();
		
		m.demineAll();
	}
	
	/**
	 * Indiquer dans quel map se trouve la case
	 * @param map : Une reference de Map
	 * @see Map
	 */
	public static void setMap(Map map)
	{
		first = true;
		m = map;
	}
	
	
	public static boolean isStarted()
	{
		return !first;
	}

	public static void setFirst()
	{
		first = true;
	}
	
	public static void setFirst(boolean b)
	{
		first = b;
	}
	
	public static int getNbrFlag()
	{
		return nbrFlag;
	}
	
	public static void setNbrFlag(int nbrFlag)
	{
		Case.nbrFlag = nbrFlag;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	public String toString()
	{
		if(couverture == FLAG)
			return "$";
		else if(couverture == BLOCK)
			return " ";
		else
		{
			if(numero == VIDE)
				return ".";
			else if(numero == MINE)
				return "*";
			else if(INTERVALLE_NUMERO.isIn(numero))
				return new Integer(numero).toString();
			else return "n";
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent event)
	{
		if(!stop)
		{
			m.setVisible(false);

			if(event.getButton() == MouseEvent.BUTTON3)//Si clique droit on met drapeau ou un point d'interogation
			{
				if(couverture == FLAG)//Si il y a un drapeau
				{
					couverture = INT;
					nbrFlag--;
				}
				else if(couverture == BLOCK)//Si il n'y a que un bloc
				{
					couverture = FLAG;
					nbrFlag++;
				}
				else if(couverture == INT)//Si il y a un ?
					couverture = BLOCK;
			}
			else if(event.getButton() == MouseEvent.BUTTON1)//Si clique gauche on demine
			{
				this.setBackground(fondColor);

				if(first)//Si c'est la premiere fois qu'on clique il faut mettre les mines sur la carte
				{
					//Generer la map apres le clique en indiquant x et y permet de ne pas tomber sur une mine au depard
					m.mine(x, y);
					first = false;
				}

				if(couverture == FLAG)//Si un drapeau a été enlevé
					nbrFlag--;

				if(couverture != RIEN)
				{
					if(numero == MINE)//On est tomber sur un mine
					{
						boom();
					}
					else if(numero == VIDE)//On demine une zone de vide
					{
						couverture = RIEN;

						try
						{
							demineVide(true);
						}
						catch(StackOverflowError e)
						{
							Dialog.erreur(this, "Une erreur java dut au trop grand nombre de case à été déclancher.");
							System.exit(0);
						}
					}
					else
						couverture = RIEN;//On demine juste une case
				}
			}
			else if(event.getButton() == MouseEvent.BUTTON2)//Si clique du millieu, action special
			{
				if(couverture == RIEN && numero != VIDE && numero != MINE)//Si il n'y a plus de couverture et que ce n'est ni une mine, ni une case vide
				{
					if(nbrFlag() == numero)//Si le nombre de drapeau est egal au numero
					{
						for(int i=0;i<8;i++)
						{
							if(map[i] != null)
							{
								if(map[i].getCouverture() == BLOCK)
								{
									map[i].setCouverture(RIEN);

									if(map[i].getNumero() == VIDE)//Si on demine un vide
										map[i].demineVide(true);

									if(map[i].getNumero() == MINE)//Si on a demine une mine
									{
										if(!m.isDead())//Si on est pas deja tomber sur une mine
											boom();

										map[i].imgMine = Info.imgMineX;
									}
								}
							}
						}
					}
				}
			}

			if(m.isDead())//Si on perd
			{
				m.setDead(false);
				Case.setFirst();
				stop = true;

				m.repaint();
				m.setVisible(true);

				Dialog.danger(this, "Vous avez perdu !");
			}
			else if(m.isWon())//Si on gagne
			{
				m.demineAll();
				m.setDead(false);
				Case.setFirst();
				stop = true;

				m.repaint();
				m.setVisible(true);

				Dialog.info(this, "Vous avez gagnez !");
				
				/*TODO
				//Double point = new Double((((Info.nbrMine/Info.tailleX*Info.tailleY)*4)/new Double(temps.getText()))*100);
				Double point = new Double(Info.nbrMine*Info.nbrMine/Info.tailleX*Info.tailleY); 
				String name = Dialog.prompt(this, "Veuillez indiquer votre prenom: ", "Enregistrement du score");
				score.add(new Score(name, point.intValue()));
				
				ObjectOutputStream oos;
				
				try //On enregistre le score
				{
					oos = new ObjectOutputStream(new FileOutputStream("score"));
					
					oos.writeObject(score);
					oos.flush();
					
					oos.close();
				}
				catch (FileNotFoundException e1){Dialog.erreur(null, "Impossible de sauvegarder les scores.");}
				catch (IOException e1){Dialog.erreur(null, "Impossible de sauvegarder les scores.");}
				
				Case.setFirst(false);
				*/
			}
			else
			{
				m.repaint();
				m.setVisible(true);
			}
		}
	}
	
	public void mouseEntered(MouseEvent event)
	{
		if(this.couverture != 0)
			this.setBorder(BorderFactory.createLineBorder(overBorderColor));
	}
	
	public void mouseExited(MouseEvent event)
	{
		this.setBorder(BorderFactory.createLineBorder(borderColor));
	}
	
	public void mouseClicked(MouseEvent event){}
	public void mousePressed(MouseEvent event){}
}
