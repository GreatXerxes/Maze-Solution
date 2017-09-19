package maze.solution.utils;

/**
 *
 * @author Ciall McArdle
 */

public class Coordinate {

	private int x, y;
	
	public Coordinate(int _x, int _y)
	{
		x = _x;
		y = _y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public String toString()
	{
		String s;
		s = "(" + getX() + ", " + getY() + ")";
		return s;
	}
}
