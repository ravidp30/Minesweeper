package mines;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.LayoutStyle.ComponentPlacement;



public class Mines {
	//boolean flag =true;
	private int cnt=0;	
	private int height,width,numMines;
	public Set<Point> game=new HashSet<Point>();
	private boolean showAll=false;
	private boolean LocationOpen = false;

	
	public Mines(int height, int width, int numMines) {//Constructor
		this.height=height;//height
		this.width=width; //width
		this.numMines=numMines;//numbers of Mines	
		int cnt=numMines;
		int i ,j;
		Point m;
		for( i =0;i<height;i++)//create the board
			for( j=0 ;j<width;j++)
			{
				m=new Point(i, j);
				game.add(m);
			}
		Random random= new Random();//add mines randomly
		while (cnt > 0) 
		{
			i = random.nextInt(height);
			j = random.nextInt(width);
			for(Point p: game)
			{
				if(p.i==i && p.j==j)
					if (!p.IsMine)
					{
						addMine(i,j);
						cnt--;
					}
			}
		}
	}
	public class Point  {//class of each Point
		private int i,j;
		public boolean IsMine=false;//set true - if we got Mine in the current location  
		public boolean IsFlag=false;//set true- if we got Flag in the current location 
		public boolean IsOpen=false;//set true- if the current location is open already
		public Point(int i ,int j) {
			this.i=i;
			this.j=j;
		}

		@Override
		public int hashCode() {
			return i*10+j* 31;
		}
	}
		
	public boolean addMine(int i, int j) {//adding Mine in the current location
	for(Point t: game)
		if(t.i==i && t.j==j)
			if(t.IsMine==true) return false;//return false if we got Mine already in this location
			   else {
				   t.IsMine=true;//set true -> we put a new Mine 
			   }
	return false;///Didn't find the current point in board

	}
		

	
	
	public boolean open(int i, int j) {
		//change this current location to open 
		//and open all neighbors that have no mines around
		
		for(Point p: game)
		{
			if(p.i==i && p.j==j)
			{
				if(p.IsOpen==true) return false;//this location is already open
				if(p.IsMine==true)// if in this location we got Mine 
				{
				p.IsOpen=false;//change the location to open
				return false;//there is a Mine there
				}
				else//no Mine in there
				{
				p.IsOpen=true;//change this location to Open
				}	
			}
		}
		if(CheckNeighbors(i, j))
		{
			if(i<height-1 && j!=0 ) open(i+1, j-1); //up right
			if(i<height-1) open(i+1, j); //right
			if(i<height-1 && j<width-1 ) open(i+1, j+1); //down right
			if(j<width-1) open(i, j+1); //down
			if(i>0 && j<width-1 )open(i-1, j+1);//left down	
			if(i>0 ) open(i-1, j);//left 
			if(i>0 && j>0 ) open(i-1, j-1);// left up
			if(j>0 ) open(i, j-1);//up
		}
		return true;					
	
	}
public boolean CheckNeighbors(int i, int j) {
		//function that check the neighbors if there is a Mines
		//return true if we didn't found a mine ,else- false
	cnt=0;
	boolean temp=true;
	for(Point p: game)
	{
		if(p.i==i+1 && p.j==j) //>right
		{
			if(p.IsMine==true)
			{
				cnt++;
				temp= false;
			}
			
		}
		if(p.j==j-1 && p.i==i) //+up
		{
			if(p.IsMine==true)
			{
				cnt++;
				temp= false;
			}
			
		}
		if(p.i==i+1 && p.j==j-1) //right+up
		{
			if(p.IsMine==true)
			{
				cnt++;
				temp= false;
			}
			
		}
		if(p.i==i  && p.j==j+1)  //down
		{
			if(p.IsMine==true)
			{
				cnt++;
				temp= false;
			}
			
		}
		
		if(p.i==i+1 && p.j==j+1)  //down + right
		{
			if(p.IsMine==true)
			{
				cnt++;
				temp= false;
			}
			
		}
		if(p.j==j+1 && p.i==i-1)  //left+ down
		{
			if(p.IsMine==true)
			{
				cnt++;
				temp= false;
			}
			
		}
		if(p.i==i-1 && p.j==j)  //left
		{
			if(p.IsMine==true)
			{
				cnt++;
				temp= false;
			}
			
		}
		if(p.i==i-1 && p.j==j-1)  //left+up
		{
			if(p.IsMine==true)
			{
				cnt++;
				temp= false;
			}
		}
	}	
		return temp;
}

	public void toggleFlag(int x, int y) {
		//put Flag in the current location or put it off if there is a Flag already 
		for(Point t: game)
		{
			if(t.i==x && t.j==y)
			{
			  if(t.IsFlag==true)//if flag=true ->we got flag already
			   {
				t.IsFlag=false;//change flag to off
			   }
			  	else t.IsFlag=true;//set flag in the current location
			}
		}
	}
	public boolean isDone() {
		//return true if all the Mines are closes.
		for(Point p: game) 
		{
			if(p.IsOpen &&p.IsMine) return false;//if open && got mine -- false
			if(!p.IsOpen && !p.IsMine) return false;//if not open and not a mine
		}	
		return true;
	}
	
	public String get(int i, int j) {
		for(Point p: game)
		{
			if(p.i==i && p.j==j)
			{
				if(showAll)
				{
					if(p.IsMine) return "X";
					CheckNeighbors(i, j);
					if(cnt==0) return " ";//if count of Minds == 0 return ""
					else return ""+cnt;//else return the amount of cnt
				}
				if(p.IsOpen==false)//if the location is close
				{
					if(p.IsFlag==true)
						return ("F");//return "F" if the current location is close and we got flag on hem
					else return(".");
				}
				else
				{
					if(p.IsMine==true)//if there is a Mine
						return ("X");
					else {
						cnt=0;
						CheckNeighbors(i, j);
						if(cnt==0) return " ";//if count of Minds == 0 return ""
						else return ""+cnt;//else return the amount of cnt
					}
				}
			}
				
		}
		return "" ;
	}
	public boolean returnOpen(int i,int j) {
		
		for(Point p : game)
			if(p.i==i && p.j==j)
			{
				if(p.IsOpen) return true;
			}
		return false;
	}
	
	
	public void setShowAll(boolean showAll) {
		this.showAll=showAll;	
				
	}

	public String toString() {
		StringBuilder S= new StringBuilder();
		for(int i =0; i<height ;i++)
		{
			
			for(int j =0; j<width; j++)
			{
				S.append(get(i, j));
				if(j==width-1)S.append("\n");
			}
		}
		
		return S.toString();
	}


	
	
	
}

