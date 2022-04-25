package al.niidhogg.demineur;

import java.awt.Image;
import java.util.ArrayList;

import al.niidhogg.main.Mode;
import al.niidhogg.son.AePlayWave;

/**
 * Fiche des informations du jeu
 * @author Niidhogg
 */
public class Info
{
	/* Les informations sur la carte */
	public static ArrayList<Mode> mode = new ArrayList<Mode>();
	public static String design;
	public static int tailleBloc;
	public static int tailleX;
	public static int tailleY;
	public static int nbrMine;
	
	/* Les images */
	public static Image img1;
	public static Image img2;
	public static Image img3;
	public static Image img4;
	public static Image img5;
	public static Image img6;
	public static Image img7;
	public static Image img8;
	public static Image imgMine;
	public static Image imgMineX;
	public static Image imgFlag;
	public static Image imgInt;
	public static Image imgCroix;
	public static Image imgOverCase;
	
	/* Les sons */
	public static AePlayWave sonExp = null;
	public static AePlayWave sonDemBlk = null;
}
