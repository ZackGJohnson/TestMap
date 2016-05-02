import java.util.Random;

public class Map
{
	private Room[][] _rooms;
	
	public Map(int maxWidth, int maxHeight)
	{
		_rooms = new Room[maxWidth][maxHeight];
		generateExits(0, 0, "");
        checkConnections();
	}
	
	/*
	 * Returns a room at the given coordinates. It will return
	 * null if a room does not exist at those coordinates.
	 * It will throw an out of bounds exception if the coordinates
	 * are outside of the map.
	 */
	public Room getRoom(int x, int y)
	{
		if (x < 0 || y < 0 || x >= _rooms.length || y >= _rooms[x].length)
		{
			throw new IndexOutOfBoundsException();
		}
		return _rooms[x][y];
	}
	
	/*
	 * Recursively generates a map in a tree-fashion.
	 * A room will always have an exit in the direction determined by 'entranceDirection'.
	 * It then goes through the direction left, right, up, and down and randomly decides
	 * if it should create an exit in that direction. If a room already exists in that direction
	 * it simply sets the exits and moves on. If a room doesn't exist it recursively runs this method
	 * with the entranceDirection being the direction it is coming from relative to the new room.
	 * It should be noted that there is currently a bug (work-around in checkConnections() method)
	 * where sometimes in the up/down direction, if a room already exists, it doesn't set the exit
	 * for the existing room.
	 */
	private void generateExits(int x, int y, String entranceDirection)
	{
		Random randomGenerator = new Random();
		boolean generateUp = false;
		boolean generateRight = false;
		boolean generateDown = false;
		boolean generateLeft = false;
		
		if (entranceDirection.equals("up"))
		{
			generateUp = true;
		}
		else if (entranceDirection.equals("right"))
		{
			generateRight = true;
		}
		else if (entranceDirection.equals("down"))
		{
			generateDown = true;
		}
		else if (entranceDirection.equals("left"))
		{
			generateLeft = true;
		}
		_rooms[x][y] = new Room(generateUp, generateRight, generateDown, generateLeft);
		
		if (x > 0 && !generateLeft)
		{
			_rooms[x][y].setLeftExit(randomGenerator.nextBoolean());
			if (_rooms[x][y].getLeftExit())
			{
				if (this.getRoom(x-1, y) == null)
				{
					generateExits(x-1, y, "right");
				}
				else
				{
					this.getRoom(x-1, y).setRightExit(true);
				}
			}
		}
		
		if (x < _rooms.length-1 && !generateRight)
		{
			_rooms[x][y].setRightExit(randomGenerator.nextBoolean());
			if (_rooms[x][y].getRightExit())
			{
				if (this.getRoom(x+1, y) == null)
				{
					generateExits(x+1, y, "left");
				}
				else
				{
					this.getRoom(x+1, y).setLeftExit(true);
				}
			}
		}
		
		if (y > 0 && !generateUp)
		{
			_rooms[x][y].setUpExit(randomGenerator.nextBoolean());
			if (_rooms[x][y].getUpExit())
			{
				if (_rooms[x][y-1] == null)
				{
					generateExits(x, y-1, "down");
				}
				else
				{
					_rooms[x][y-1].setDownExit(true);
				}
			}
		}
		
		if (y < _rooms[x].length-1 && !generateDown)
		{
			_rooms[x][y].setDownExit(randomGenerator.nextBoolean());
			if (_rooms[x][y].getDownExit())
			{
				if (_rooms[x][y+1] == null)
				{
					generateExits(x, y+1, "up");
				}
				else
				{
					_rooms[x][y+1].setUpExit(true);
				}
			}
		}
	}

	/*
	 * Goes through every room in the map and makes sure the up/down
	 * exits are correct. It is currently a work-around for a bug
	 * in the map generation code.
	 */
    private void checkConnections()
    {
        for (int x = 0; x < _rooms.length; x++)
        {
            for (int y = 0; y < _rooms[0].length; y++)
            {
                if (_rooms[x][y] != null)
                {
                    if (_rooms[x][y].getUpExit())
                    {
                        _rooms[x][y-1].setDownExit(true);
                    }
                    if (_rooms[x][y].getDownExit())
                    {
                        _rooms[x][y+1].setUpExit(true);
                    }
                }
            }
        }
    }
	
	/*
	 * This method prints a graphical representation of the map in the console.
	 * It is purely for testing purposes.
	 * Asterisks represent rooms. Lines represent hallways.
	 */
	public void displayMap()
	{
		String topLine = "";
		String midLine = "";
		String bottomLine = "";
		for (int y = 0; y < _rooms[0].length; y++)
		{
			for (int x = 0; x < _rooms.length; x++)
			{
				if (this.getRoom(x, y) != null)
				{
					if (this.getRoom(x, y).getUpExit())
					{
						topLine += " | ";
					}
					else
					{
						topLine += "   ";
					}
					if (this.getRoom(x, y).getLeftExit())
					{
						midLine += "-";
					}
					else
					{
						midLine += " ";
					}
					midLine += "*";
					if (this.getRoom(x, y).getRightExit())
					{
						midLine += "-";
					}
					else
					{
						midLine += " ";
					}
					if (this.getRoom(x, y).getDownExit())
					{
						bottomLine += " | ";
					}
					else
					{
						bottomLine += "   ";
					}
				}
				else
				{
					topLine += "   ";
					midLine += "   ";
					bottomLine += "   ";
				}
			}
			System.out.println(topLine);
			topLine = "";
			System.out.println(midLine);
			midLine = "";
			System.out.println(bottomLine);
			bottomLine = "";
		}
	}
}
