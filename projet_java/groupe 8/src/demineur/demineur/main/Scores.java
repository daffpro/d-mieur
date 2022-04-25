package al.niidhogg.main;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Simple ArrayList pour sauvegarder les scores
 * @author Niidhogg
 */
public class Scores extends ArrayList<Score> implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public void triDecroissant()
	{
		for(int i=1;i<this.size();i++)
		{
			
		}
	}
}
