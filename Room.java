
public class Room
{
	private boolean _upExit, _rightExit, _downExit, _leftExit;
	private Object contents;
	
	public Room(boolean up, boolean right, boolean down, boolean left)
	{
		_upExit = up;
		_rightExit = right;
		_downExit = down;
		_leftExit = left;
	}
	
	public boolean getUpExit()
	{
		return _upExit;
	}
	
	public boolean getRightExit()
	{
		return _rightExit;
	}
	
	public boolean getLeftExit()
	{
		return _leftExit;
	}
	
	public boolean getDownExit()
	{
		return _downExit;
	}
	
	public void setUpExit(boolean exit)
	{
		_upExit = exit;
	}
	
	public void setRightExit(boolean exit)
	{
		_rightExit = exit;
	}
	
	public void setLeftExit(boolean exit)
	{
		_leftExit = exit;
	}
	
	public void setDownExit(boolean exit)
	{
		_downExit = exit;
	}
	
	public Object getContents()
	{
		return contents;
	}
}
