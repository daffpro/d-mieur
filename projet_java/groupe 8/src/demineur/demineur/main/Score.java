package al.niidhogg.main;

import java.io.Serializable;

public class Score implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private int point;
	
	public Score(String name, int point)
	{
		this.name = name;
		this.point = point;
	}
	
	public String toString()
	{
		return name+": "+point;
	}
	
	public Score copy()
	{
		return new Score(name+"", point);
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getPoint()
	{
		return point;
	}
	public void setPoint(int point)
	{
		this.point = point;
	}
}
