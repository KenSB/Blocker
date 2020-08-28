import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Blocker extends JPanel implements ActionListener, KeyListener, MouseListener
{
	// the starting positions for each map
	int [] startingPosMap1={650,300,570,300};
	int [] startingPosMap2={1130,300,100,300};
	int [] startingPosMap3={1130,300,100,300};
	int [] startingPosMap4={1150,550,80,80};
	// the obstacles for each map and the border
	int borderWall[]={50,50,1200,600};
	int obstacle1Map2[]={150,150,230,230};
	int obstacle2Map2[]={1020,150,1100,230};
	int obstacle3Map2[]={150,420,230,500};
	int obstacle4Map2[]={1020,420,1100,500};
	int obstacle5Map2[]={520,220,720,420};
	int obstacle1Map3[]={150,150,230,500};
	int obstacle2Map3[]={1020,150,1100,500};
	int obstacle1Map4[]={150,50,200,350};
	int obstacle2Map4[]={50,450,600,500};
	int obstacle3Map4[]={600,150,1200,200};
	int obstacle4Map4[]={1050,300,1100,600};
	// the special color used 
	Color bluelight = new Color(204,204,255);

	int directionP2 = 0;
	int directionP1 = 0;
	int[][] map = new int [100][200];
	int[][] menuAnimations = new int [300][300];
	// the constant variables used
	final int WALL =2;
	final int PLAYERONE=1;
	final int PLAYERTWO=-1;
	final int LEFT=1;
	final int RIGHT=2;
	final int DOWN=3;
	final int UP=4;
	// all the different changing int variables used
	int winner=0;
	int timesWonP1=0;
	int timesWonP2=0;
	int winsNeeded=2;
	int areaClaimedP1=0;
	int areaClaimedP2=0;
	int time=30000;
	int timeDecrement=20;
	int speed = 100;
	int imageNum=0;
	// all the images used
	Image wall= Toolkit.getDefaultToolkit ().getImage ("white.jpg");
	Image playerOneTrail= Toolkit.getDefaultToolkit ().getImage ("blue.jpg");
	Image playerTwoTrail= Toolkit.getDefaultToolkit ().getImage ("orange.jpg");
	Image un= Toolkit.getDefaultToolkit ().getImage ("1.jpg");
	Image deux= Toolkit.getDefaultToolkit ().getImage ("2.jpg");
	Image trois= Toolkit.getDefaultToolkit ().getImage ("3.jpg");
	Image duel= Toolkit.getDefaultToolkit ().getImage ("duel.png");
	Image conquer= Toolkit.getDefaultToolkit ().getImage ("conquer.png");
	Image mapOne= Toolkit.getDefaultToolkit ().getImage ("map1.png");
	Image mapTwo= Toolkit.getDefaultToolkit ().getImage ("map2.png");
	Image mapThree= Toolkit.getDefaultToolkit ().getImage ("map3.png");
	Image mapFour= Toolkit.getDefaultToolkit ().getImage ("map4.png");
	// all the boolean variables used
	boolean gameEnd=false;
	boolean ehhh=false;
	boolean countdown=true;
	boolean borderCreate=true;
	boolean timerOn=true;
	// all the rectangles that are used
	Rectangle r1 = new Rectangle (0, 0, 30, 30);
	Rectangle r2 = new Rectangle (0, 0, 30, 30);
	Rectangle background = new Rectangle (0, 0, 1250, 650);
	Rectangle playButton = new Rectangle(100,300,175,50);
	Rectangle settingButton = new Rectangle(100,400,300,50);
	Rectangle helpButton = new Rectangle(100,500,175,50);
	Rectangle quitButton = new Rectangle(850,500,175,50);
	Rectangle startGMButton = new Rectangle(50,500,275,50);
	Rectangle duelGMButton = new Rectangle(300,150,275,50);
	Rectangle conquerGMButton = new Rectangle(300,300,275,50);
	Rectangle returnButton = new Rectangle(850,500,275,50);
	Rectangle [] amountOfWinsSET = {new Rectangle(50,270,50,50),new Rectangle(100,270,50,50),new Rectangle(150,270,50,50)};
	Rectangle [] timeLimitButtonSET = {new Rectangle(50,450,65,50),new Rectangle(125,450,65,50),new Rectangle(200,450,65,50),new Rectangle(275,450,65,50)};
	Rectangle timeChangeSET = new Rectangle(50,500,275,50);
	Rectangle aR1= new Rectangle (0, 0, 30, 30);
	Rectangle aR2= new Rectangle (0, 0, 30, 30);
	Rectangle aR3= new Rectangle (0, 0, 30, 30);
	Rectangle aR4= new Rectangle (0, 0, 30, 30);
	// directions for the animations
	int directionAniR1 = LEFT;
	int directionAniR2 = RIGHT;
	int directionAniR3 = UP;
	int directionAniR4 = DOWN;
	AudioClip mainMenuMusic;
	AudioClip beep;
	// different enums for the different states of the game
	private enum STATE
	{
		MENU,
		GAMEMENU,
		CONTROLS,
		SETTINGS,
		GAMEDUEL,
		GAMECONQUER
	};
	private enum MAP
	{
		ONE,
		TWO,
		THREE,
		FOUR
	}
	private STATE State = STATE.MENU;
	private STATE holder=STATE.GAMEDUEL;
	private MAP Map = MAP.ONE;
	// the constructor
	public Blocker ()
	{
		mainMenuMusic=Applet.newAudioClip (getCompleteURL ("mainMenuMusic.wav"));
		beep=Applet.newAudioClip (getCompleteURL ("beep.wav"));
		setPreferredSize(new Dimension(1250, 650));
		setLayout (new BoxLayout (this, BoxLayout.PAGE_AXIS));
		setBackground(Color.BLACK);
		//Starting the threads
		createBorder(borderWall);
		MoveRect1 move = new MoveRect1 ();
		MoveRect2 move2 = new MoveRect2 ();
		threadTrail move3=new threadTrail();
		countdownTimer countdownTime=new countdownTimer();
		setStart mover=new setStart();
		timer timers= new timer();
		speedSim speedInc= new speedSim();
		menuAnimator animate = new menuAnimator();
		music menuMusic = new music();
		move.start ();
		move2.start ();
		move3.start();
		countdownTime.start();
		mover.start();
		timers.start();
		speedInc.start();
		animate.start();
		menuMusic.start();
		addKeyListener(this);
		addMouseListener(this);
		this.setFocusable(true);
	}
	public void actionPerformed (ActionEvent event) 
	{
	}
	/*
	 Description: This method checks if the user has pressed a key and runs 
	 an event according to the key pressed.
	 Parameters: This requires a key to be pressed.
	 Return: This has no return so it is set to void.
	 */
	public void keyPressed(KeyEvent e) 
	{
		int keyCode = e.getKeyCode();
		if(State==STATE.GAMEDUEL)
		{
			switch( keyCode ) 
			{ 
			case KeyEvent.VK_UP:
				if(directionP1!=DOWN)
					directionP1=UP;
				break;
			case KeyEvent.VK_DOWN:
				if(directionP1!=UP)
					directionP1=DOWN;
				break;
			case KeyEvent.VK_LEFT:
				if(directionP1!=RIGHT)
					directionP1=LEFT;
				break;
			case KeyEvent.VK_RIGHT :
				if(directionP1!=LEFT)
					directionP1=RIGHT;
				break;
			case KeyEvent.VK_W:
				if(directionP2!=DOWN)
					directionP2=UP;
				break;
			case KeyEvent.VK_S:
				if(directionP2!=UP)
					directionP2=DOWN;
				break;
			case KeyEvent.VK_A:
				if(directionP2!=RIGHT)
					directionP2=LEFT;
				break;
			case KeyEvent.VK_D:
				if(directionP2!=LEFT)
					directionP2=RIGHT;
				break;
			}
		}
		if(State==STATE.GAMECONQUER)
		{
			switch( keyCode ) 
			{ 
			case KeyEvent.VK_UP:
				directionP1=UP;
				break;
			case KeyEvent.VK_DOWN:
				directionP1=DOWN;
				break;
			case KeyEvent.VK_LEFT:
				directionP1=LEFT;
				break;
			case KeyEvent.VK_RIGHT :
				directionP1=RIGHT;
				break;
			case KeyEvent.VK_W:
				directionP2=UP;
				break;
			case KeyEvent.VK_S:
				directionP2=DOWN;
				break;
			case KeyEvent.VK_A:
				directionP2=LEFT;
				break;
			case KeyEvent.VK_D:
				directionP2=RIGHT;
				break;
			}
		}
	}
	/*
	 Description: This method creates borders by setting values on the map to 2.
	 Parameters: This needs an integer array, it contains the x and y values of
	 the top left and the bottom right of the border.
	 Return: This has no return so it is set to void.
	*/
	public void createBorder(int rect[])
	{
		for(int y=rect[1]; y<=rect[3];y+=10)
		{
			map[y/10][rect[0]/10]=WALL;
		}
		for(int y=rect[1]; y<=rect[3];y+=10)
		{
			map[y/10][rect[2]/10]=WALL;
		}
		for(int x=rect[0]; x<=rect[2];x+=10)
		{
			map[rect[1]/10][x/10]=WALL;
		}
		for(int x=rect[0]; x<=rect[2];x+=10)
		{
			map[rect[3]/10][x/10]=WALL;
		}
	}
	/*
	 Description: This method detects a collision between the player and the environment. 
	 Parameters: This needs the player that it is checking and the direction that the player
	 is moving in.
	 Return: This has no return so it is set to void.
	*/
	public void colisionDetect(int player,int direction)
	{
		if(player==1)
		{
			switch(direction)
			{
			case LEFT:
			{
				if(map[r1.y/10][r1.x/10]!=0||map[r1.y/10+1][r1.x/10]!=0||map[r1.y/10+2][r1.x/10]!=0)
				{
					winner=PLAYERTWO;
					gameEnd=true;
				}
				break;
			}
			case RIGHT:
			{
				if(map[r1.y/10][r1.x/10+2]!=0||map[r1.y/10+1][r1.x/10+2]!=0||map[r1.y/10+2][r1.x/10+2]!=0)
				{
					winner=PLAYERTWO;
					gameEnd=true;
				}
				break;
			}
			case DOWN:
			{
				if(map[r1.y/10+2][r1.x/10]!=0||map[r1.y/10+2][r1.x/10+1]!=0||map[r1.y/10+2][r1.x/10+2]!=0)
				{
					winner=PLAYERTWO;
					gameEnd=true;
				}
				break;
			}
			case UP:
			{
				if(map[r1.y/10][r1.x/10]!=0||map[r1.y/10][r1.x/10+1]!=0||map[r1.y/10][r1.x/10+2]!=0)
				{
					winner=PLAYERTWO;
					gameEnd=true;
				}
				break;
			}
			}
		}
		else
		{
			switch(direction)
			{
			case LEFT:
			{
				if(map[r2.y/10][r2.x/10]!=0||map[r2.y/10+1][r2.x/10]!=0||map[r2.y/10+2][r2.x/10]!=0)
				{
					winner=PLAYERONE;
					gameEnd=true;
				}
				break;
			}
			case RIGHT:
			{
				if(map[r2.y/10][r2.x/10+2]!=0||map[r2.y/10+1][r2.x/10+2]!=0||map[r2.y/10+2][r2.x/10+2]!=0)
				{
					winner=PLAYERONE;
					gameEnd=true;
				}
				break;
			}
			case DOWN:
			{
				if(map[r2.y/10+2][r2.x/10]!=0||map[r2.y/10+2][r2.x/10+1]!=0||map[r2.y/10+2][r2.x/10+2]!=0)
				{
					winner=PLAYERONE;
					gameEnd=true;
				}
				break;
			}
			case UP:
			{
				if(map[r2.y/10][r2.x/10]!=0||map[r2.y/10][r2.x/10+1]!=0||map[r2.y/10][r2.x/10+2]!=0)
				{
					winner=PLAYERONE;
					gameEnd=true;
				}
				break;
			}
			}
		}
	}
	/*
	 Description: This method sets all values of a two dimensional array to 0.
	 Parameters: This needs a two dimensional integer array.
	 Return: This has no return so it is set to void.
	*/
	public void clearBoard (int[] [] board)
	{
		for(int row=1;row<board.length-1;row++)
		{
			for(int column=1;column<board[row].length-1;column++)
				board[row][column]=0;
		}
	}
	/*
	 Description: This method calculates the area that each player has claimed 
	 and finds the greater one.
	 Parameters: This does not have any parameters
	 Return: This has no return so it is set to void.
	*/
	public void calculateWinner()
	{
		for (int row = 0 ; row < map.length; row++ )
		{
			for (int column = 0 ; column < map[row].length; column++ )
			{
				if(map[row][column]==PLAYERONE)
				{
					areaClaimedP1++;
				}
				else if(map[row][column]==PLAYERTWO)
				{
					areaClaimedP2++;
				}
			}
		}
		if(areaClaimedP1>areaClaimedP2)
			winner=PLAYERONE;
		else if(areaClaimedP1<areaClaimedP2)
			winner=PLAYERTWO;
	}
	/*
	 Description: This method is called when the game ends and it resets all values.
	 Parameters: This does not have any parameters
	 Return: This has no return so it is set to void.
	*/
	public void gameOver()
	{
		if(winner==PLAYERONE)
		{
			if(State==STATE.GAMEDUEL)
				timesWonP1++;
			JOptionPane.showMessageDialog (this, "Blue Wins!!!",
					"GAME OVER", JOptionPane.INFORMATION_MESSAGE);
			if(State==STATE.GAMECONQUER)
			{
				State=STATE.MENU;
			}
		}
		else if(winner==PLAYERTWO)
		{
			if(State==STATE.GAMEDUEL)
				timesWonP2++;
			JOptionPane.showMessageDialog (this, "Orange Wins!!!",
					"GAME OVER", JOptionPane.INFORMATION_MESSAGE);
			if(State==STATE.GAMECONQUER)
			{
				State=STATE.MENU;
			}
		}
		else
		{
			JOptionPane.showMessageDialog (this, "It is a draw!!!",
					"GAME OVER", JOptionPane.INFORMATION_MESSAGE);
			if(State==STATE.GAMECONQUER)
			{
				State=STATE.MENU;
			}
		}
		if(State==STATE.GAMEDUEL)
		{
			if(timesWonP1==winsNeeded)
			{
				JOptionPane.showMessageDialog (this, "Blue Wins Match!!!!",
						"GAME OVER", JOptionPane.INFORMATION_MESSAGE);
				State=State.MENU;
				timesWonP1=0;
				timesWonP2=0;
			}
			if(timesWonP2==winsNeeded)
			{
				JOptionPane.showMessageDialog (this, "Orange Wins Match!!!!",
						"GAME OVER", JOptionPane.INFORMATION_MESSAGE);
				State=State.MENU;
				timesWonP1=0;
				timesWonP2=0;
			}
		}
		clearBoard(map);
		speed=-25;
		if(Map==MAP.ONE)
		{
			r1.x=startingPosMap1[0];
			r1.y=startingPosMap1[1];
			r2.x=startingPosMap1[2];
			r2.y=startingPosMap1[3];
			directionP1=2;
			directionP2=1;
		}
		else if(Map==MAP.TWO)
		{
			r1.x=startingPosMap2[0];
			r1.y=startingPosMap2[1];
			r2.x=startingPosMap2[2];
			r2.y=startingPosMap2[3];
			directionP1=1;
			directionP2=2;
		}
		else if(Map==MAP.THREE)
		{
			r1.x=startingPosMap3[0];
			r1.y=startingPosMap3[1];
			r2.x=startingPosMap3[2];
			r2.y=startingPosMap3[3];
			directionP1=3;
			directionP2=4;
		}
		else if(Map==MAP.FOUR)
		{
			r1.x=startingPosMap4[0];
			r1.y=startingPosMap4[1];
			r2.x=startingPosMap4[2];
			r2.y=startingPosMap4[3];
			directionP1=4;
			directionP2=3;
		}
		gameEnd=false;
		countdown=true;
		time=60000;
		repaint();
	}
	/*
	 Description: This method draw the trails that follows the two squares
	 Parameters: This needs the player that it is drawing for and the 
	 direction they are going in.
	 Return: This has no return so it is set to void.
	*/
	public void drawTrail(int player, int direction)
	{
		if(State==STATE.GAMEDUEL)
		{
			if(player==PLAYERONE)
				map[r1.y/10+1][r1.x/10+1]=1;
			else if(player==PLAYERTWO)
				map[r2.y/10+1][r2.x/10+1]=-1;
		}
		else if(State==STATE.GAMECONQUER)
		{
			if(player==PLAYERONE)
			{
				if (directionP1 == LEFT||directionP1 == RIGHT)
				{
					map[r1.y/10][r1.x/10+1]=1;
					map[r1.y/10+1][r1.x/10+1]=1;
					map[r1.y/10+2][r1.x/10+1]=1;
				}
				if (directionP1 == DOWN||directionP1 == UP)
				{
					map[r1.y/10+1][r1.x/10]=1;
					map[r1.y/10+1][r1.x/10+1]=1;
					map[r1.y/10+1][r1.x/10+2]=1;
				}
			}
			else if(player==PLAYERTWO)
			{
				if (directionP2 == LEFT||directionP2 == RIGHT)
				{
					map[r2.y/10][r2.x/10+1]=-1;
					map[r2.y/10+1][r2.x/10+1]=-1;
					map[r2.y/10+2][r2.x/10+1]=-1;
				}
				if (directionP2 == DOWN||directionP2 == UP)
				{
					map[r2.y/10+1][r2.x/10]=-1;
					map[r2.y/10+1][r2.x/10+1]=-1;
					map[r2.y/10+1][r2.x/10+2]=-1;
				}
			}
		}
	}/*
	 Description: This method draws the animated trails in the main menu.
	 Parameters: This does not have any parameters.
	 Return: This has no return so it is set to void.
	*/
	public void drawTrailMenu()
	{
		if(aR1.x<1300&&aR1.x>0)
		{
			menuAnimations[aR1.y/10+1][aR1.x/10+1]=1;
		}
		if(aR2.x<1300&&aR2.x>0)
		{
			menuAnimations[aR2.y/10+1][aR2.x/10+1]=-1;

		}
		if(aR3.y<600&&aR3.y>0)
		{
			menuAnimations[aR3.y/10+1][aR3.x/10+1]=1;

		}
		if(aR4.y<600&&aR4.y>0)
		{
			menuAnimations[aR4.y/10+1][aR4.x/10+1]=-1;

		}
	}
	/*
	 Description: This thread moves player one's square
	 Parameters: It is a thread so it has no parameters
	 Return: It is a thread so it has no return types
	*/
	public class MoveRect1 extends Thread
	{
		public void run ()
		{
			while (true)
			{
				if(State==STATE.GAMEDUEL||State==STATE.GAMECONQUER)
				{
					if (directionP1 == LEFT&&r1.x!=50)
					{
						r1.x -= 10;
						drawTrail(1, 1);
					}
					else if (directionP1 == RIGHT&&r1.x!=1180)
					{
						r1.x += 10;
						drawTrail(1, 2);
					}
					else if (directionP1 == DOWN&&r1.y!=580)
					{
						r1.y += 10;					
						drawTrail(1, 3);

					}
					else if (directionP1 == UP&&r1.y!=50)
					{
						r1.y -= 10;
						drawTrail(1, 4);
					}
					if(State==STATE.GAMEDUEL)
					{
						colisionDetect(PLAYERONE,directionP1);
					}
				}
				try
				{
					sleep (75-speed);
				}
				catch (Exception e)
				{
				}
				repaint ();
			}
		}
	}
	/*
	 Description: This thread moves player two's square
	 Parameters: It is a thread so it has no parameters
	 Return: It is a thread so it has no return types
	*/
	public class MoveRect2 extends Thread
	{
		public void run ()
		{
			while (true)
			{
				if(State==STATE.GAMEDUEL||State==STATE.GAMECONQUER)
				{
					if (directionP2 == LEFT&&r2.x!=50)
					{
						r2.x -= 10;
						drawTrail(-1, 1);
					}
					else if (directionP2 == RIGHT&&r2.x!=1180)
					{
						r2.x += 10;
						drawTrail(-1, 2);
					}
					else if (directionP2 == DOWN&&r2.y!=580)
					{
						r2.y += 10;					
						drawTrail(-1, 3);
					}
					else if (directionP2 == UP&&r2.y!=50)
					{
						r2.y -= 10;
						drawTrail(-1, 4);
					}
					if(State==STATE.GAMEDUEL)
					{
						colisionDetect(PLAYERTWO,directionP2);
					}
				}
				try
				{
					sleep (75-speed);
				}
				catch (Exception e)
				{
				}
				repaint ();
			}
		}
	}
	/*
	 Description: This thread simulates an increase in speed as time goes on in the game
	 Parameters: It is a thread so it has no parameters
	 Return: It is a thread so it has no return types
	*/
	public class speedSim extends Thread
	{
		public void run ()
		{
			while (true)
			{
				if(State==STATE.GAMEDUEL&&(!countdown&&speed!=50))
				{
					speed++;
				}
				else if(State==STATE.GAMECONQUER)
				{
					speed=0;
				}
				try
				{
					sleep (1000);
				}
				catch (Exception e)
				{
				}
				repaint ();
			}	
		}
	}
	/*
	 Description: This thread animates the squares in the menu
	 Parameters: It is a thread so it has no parameters
	 Return: It is a thread so it has no return types
	*/
	public class menuAnimator extends Thread
	{
		public void run ()
		{
			int timesRanX=0;
			int timesRanY=0;
			aR1.y=((int)(((Math.random()*500)+100)/10))*10;
			aR1.x=1300;
			aR2.y=((int)(((Math.random()*500)+100)/10))*10;
			aR2.x=-50;
			aR3.x=((int)(((Math.random()*1000)+100)/10))*10;
			aR3.y=700;
			aR4.x=((int)(((Math.random()*1000)+100)/10))*10;
			aR4.y=-50;
			while (true)
			{
				if(State==STATE.MENU)
				{
					aR1.x -= 10;
					aR2.x += 10;
					aR3.y -= 10;
					aR4.y += 10;
					drawTrailMenu();
					timesRanX++;
					timesRanY++;
					if(timesRanX==200)
					{
						timesRanX=0;
						aR1.y=((int)(((Math.random()*500)+100)/10))*10;
						aR1.x=1300;
						aR2.y=((int)(((Math.random()*500)+100)/10))*10;
						aR2.x=-50;
					}
					if(timesRanY==200)
					{
						timesRanY=0;
						aR3.x=((int)(((Math.random()*1000)+100)/10))*10;
						aR3.y=700;
						aR4.x=((int)(((Math.random()*1000)+100)/10))*10;
						aR4.y=-50;
					}
				}
				else
				{
					clearBoard(menuAnimations);
					timesRanX=0;
					timesRanY=0;
					aR1.x=1300;
					aR2.x=-50;
					aR3.y=700;
					aR4.y=-50;
				}
				try
				{
					sleep (50);
				}
				catch (Exception e)
				{
				}
				repaint ();
			}
		}
	}/*
	 Description: This is the paint component and it is responsible for 
	 drawing everything to the screen.
	 Parameters: it needs graphics g to function
	 Return: It returns nothing so it is set to void.
	*/
	public void paintComponent(Graphics g) 
	{
		if(State==STATE.MENU)
		{
			g.setColor(Color.BLACK);
			g.fillRect(background.x,background.y,background.width,background.height);
			super.paintComponent (g);	
			for (int row = 0 ; row < menuAnimations.length; row++ )
			{
				for (int column = 0 ; column < menuAnimations[row].length; column++ )
				{
					if(menuAnimations[row][column]==1)
					{
						g.drawImage (playerOneTrail,column*10, row*10, 10, 10, this);
					}
					else if(menuAnimations[row][column]==-1)
					{
						g.drawImage (playerTwoTrail,column*10, row*10, 10, 10, this);
					}
				}
			}
			g.setColor (Color.blue);
			g.fill3DRect (aR1.x, aR1.y, aR1.width, aR1.height, true);
			g.fill3DRect (aR3.x, aR3.y, aR3.width, aR3.height, true);
			g.setColor (Color.red);
			g.fill3DRect (aR2.x, aR2.y, aR2.width, aR2.height, true);
			g.fill3DRect (aR4.x, aR4.y, aR4.width, aR4.height, true);
			g.setColor(bluelight);
			Graphics2D g2d=(Graphics2D)g;
			Font fnt0 = new Font("OCR A Extended", Font.BOLD,50);
			Font fnt1 = new Font("OCR A Extended", Font.BOLD,30);
			g.setFont(fnt0);
			g.drawString("BL[]CKER", 50, 100);
			g.setFont(fnt0);
			g2d.draw(playButton);
			g.drawString("PLAY", playButton.x+19, playButton.y+45);
			g2d.draw(settingButton);
			g.drawString("SETTINGS", settingButton.x+19, settingButton.y+45);
			g2d.draw(helpButton);
			g.drawString("HELP", helpButton.x+19, helpButton.y+45);
			g2d.draw(quitButton);
			g.drawString("QUIT", quitButton.x+19, quitButton.y+45);
		}
		if(State==STATE.SETTINGS)
		{
			g.setColor(Color.BLACK);
			g.fillRect(background.x,background.y,background.width,background.height);
			Graphics2D g2d=(Graphics2D)g;
			Font fnt0 = new Font("OCR A Extended", Font.BOLD,50);
			Font fnt1 = new Font("Agency FB", Font.BOLD,30);
			g.setFont(fnt0);
			g.setColor(bluelight);
			g.drawString("SETTINGS", 50, 100);
			g.drawString("MAPS",450,70);
			g.drawString("DUEL", 50, 200);
			g.drawString("CONQUER", 50, 375);
			g.setFont(fnt1);
			g.drawString("(only for duel, conquer set to map one)",600,70);
			g.drawString("Best of ", 50, 250);
			g.drawString("Time limit", 50, 425);
			g.drawString("map one", 500, 120);
			g.drawString("map two", 800, 120);
			g.drawString("map three", 500, 320);
			g.drawString("map four", 800, 320);
			g.drawImage(mapOne, 500, 140, 250, 150, this);
			g.drawImage(mapTwo, 800, 140, 250, 150, this);
			g.drawImage(mapThree, 500, 340, 250, 150, this);
			g.drawImage(mapFour, 800, 340, 250, 150, this);
			g.setFont(fnt0);
			g2d.draw(amountOfWinsSET[0]);
			g.drawString("3", amountOfWinsSET[0].x+10, amountOfWinsSET[0].y+45);
			g2d.draw(amountOfWinsSET[1]);
			g.drawString("5", amountOfWinsSET[1].x+10, amountOfWinsSET[1].y+45);
			g2d.draw(amountOfWinsSET[2]);
			g.drawString("7", amountOfWinsSET[2].x+10, amountOfWinsSET[2].y+45);
			g2d.draw(timeLimitButtonSET[0]);
			g.drawString("15", timeLimitButtonSET[0].x, timeLimitButtonSET[0].y+45);
			g2d.draw(timeLimitButtonSET[1]);
			g.drawString("30", timeLimitButtonSET[1].x, timeLimitButtonSET[1].y+45);
			g2d.draw(timeLimitButtonSET[2]);
			g.drawString("45", timeLimitButtonSET[2].x, timeLimitButtonSET[2].y+45);
			g2d.draw(timeLimitButtonSET[3]);
			g.drawString("60", timeLimitButtonSET[3].x, timeLimitButtonSET[3].y+45);
			g2d.draw(returnButton);
			g.drawString("RETURN", returnButton.x+40, returnButton.y+45);
		}
		if(State==STATE.CONTROLS)
		{
			g.setColor(Color.BLACK);
			g.fillRect(background.x,background.y,background.width,background.height);
			Graphics2D g2d=(Graphics2D)g;
			Font fnt0 = new Font("OCR A Extended", Font.BOLD,50);
			Font fnt1 = new Font("Agency FB", Font.BOLD,30);
			
			g.setFont(fnt0);
			g.setColor(bluelight);
			g.drawString("CONTROLS", 50, 100);
			g.drawString("GAME", 50, 260);
			g.setFont(fnt1);
			g.drawString("Player One: Use arrow keys to move the blue square", 50, 150);
			g.drawString("Player Two: Use WASD keys to move the orange square", 50, 200);
			g.drawString("Game Type: DUEL", 50, 310);
			g.drawString("Game Type: CONQUER", 50, 450);
			g.setFont(fnt1);
			g.drawString("Players continuously move while painting a trail behind their respective squares.", 50, 340);
			g.drawString("The trails and walls are obstacles that the players must avoid hitting.", 50, 370);
			g.drawString("The objective is to survive longer than the opponent. (Speed increases with time)", 50, 400);
			g.drawString("Players continuously move while painting a large trail behind their respective squares.", 50, 480);
			g.drawString("The trails serve as markings for claimed area.", 50, 510);
			g.drawString("The player who claims the largest area, within the alloted time, wins.", 50, 540);
			g.setFont(fnt0);
			g2d.draw(returnButton);
			g.drawString("RETURN", returnButton.x+40, returnButton.y+45);
		}
		if(State==STATE.GAMEMENU)
		{
			g.setColor(Color.BLACK);
			g.fillRect(background.x,background.y,background.width,background.height);
			Graphics2D g2d=(Graphics2D)g;
			Font fnt0 = new Font("OCR A Extended", Font.BOLD,50);
			Font fnt1 = new Font("Agency FB", Font.BOLD,30);
			g.setFont(fnt0);
			g.setColor(bluelight);
			g.drawString("SELECT GAME TYPE", 50, 100);
			g.setFont(fnt0);
			g.drawImage(duel, 100, 150, 700, 200, this);
			g.drawImage(conquer, 100, 325, 700, 200, this);
			g2d.draw(duelGMButton);
			g.drawString("Duel", duelGMButton.x+70, duelGMButton.y+45);
			g2d.draw(conquerGMButton);
			g.drawString("Conquer", conquerGMButton.x+25, conquerGMButton.y+45);
			g2d.draw(startGMButton);
			g.drawString("START", startGMButton.x+55, startGMButton.y+45);
			g2d.draw(returnButton);
			g.drawString("RETURN", returnButton.x+40, returnButton.y+45);
		}
		if(State==STATE.GAMEDUEL||State==STATE.GAMECONQUER)
		{
			if(countdown)
			{
				g.setColor(Color.BLACK);
				g.fillRect(background.x,background.y,background.width,background.height);
				if(imageNum==1)
					g.drawImage (trois,600, 300, 100, 100, this);
				else if(imageNum==2)
					g.drawImage (deux,600, 300, 100, 100, this);
				else if(imageNum==3)
					g.drawImage (un,600, 300, 100, 100, this);
			}
			else 
			{
				for (int row = 0 ; row < map.length; row++ )
				{
					for (int column = 0 ; column < map[row].length; column++ )
					{
						if(map[row][column]==WALL)
						{
							g.drawImage (wall,column*10, row*10, 10, 10, this);
						}
					}
				}
				super.paintComponent (g);
				Font fnt3 = new Font("Agency FB", Font.BOLD,40);
				g.setFont(fnt3);
				if(State==STATE.GAMECONQUER)
				{
					g.setColor (Color.white);
					g.drawString ("Time: " + time/1000, 400, 40);
				}
				if(State==STATE.GAMEDUEL)
				{
					g.setColor (Color.white);
					g.drawString ("P1 wins: " + timesWonP1, 450, 40);
					g.drawString ("P2 wins: " + timesWonP2, 700, 40);
				}
				if(ehhh)
				{
					for (int row = 0 ; row < map.length; row++ )
					{
						for (int column = 0 ; column < map[row].length; column++ )
						{
							if(map[row][column]==1)
							{
								g.drawImage (playerOneTrail,column*10, row*10, 10, 10, this);
							}
							else if(map[row][column]==-1)
							{
								g.drawImage (playerTwoTrail,column*10, row*10, 10, 10, this);
							}
							else if(map[row][column]==WALL)
							{
								g.drawImage (wall,column*10, row*10, 10, 10, this);
							}
						}
					}
				}
				g.setColor (Color.blue);
				g.fill3DRect (r1.x, r1.y, r1.width, r1.height, true);
				g.setColor (Color.red);
				g.fill3DRect (r2.x, r2.y, r2.width, r2.height, true);		
			}
		}
	}
	/*
	 Description: This thread resets the pieces continuously until the game starts
	 Parameters: It is a thread so it has no parameters
	 Return: It is a thread so it has no return types
	*/
	public class setStart extends Thread
	{
		public void run ()
		{
			while (true)
			{
				if(countdown||State!=STATE.GAMEDUEL&&State!=STATE.GAMECONQUER)
				{
					if(Map==MAP.ONE||State==STATE.GAMECONQUER)
					{
						r1.x=startingPosMap1[0];
						r1.y=startingPosMap1[1];
						r2.x=startingPosMap1[2];
						r2.y=startingPosMap1[3];
						directionP1=2;
						directionP2=1;
						clearBoard(map);
					}
					else if(Map==MAP.TWO)
					{
						r1.x=startingPosMap2[0];
						r1.y=startingPosMap2[1];
						r2.x=startingPosMap2[2];
						r2.y=startingPosMap2[3];
						directionP1=1;
						directionP2=2;
						clearBoard(map);
					}
					else if(Map==MAP.THREE)
					{
						r1.x=startingPosMap3[0];
						r1.y=startingPosMap3[1];
						r2.x=startingPosMap3[2];
						r2.y=startingPosMap3[3];
						directionP1=3;
						directionP2=4;
						clearBoard(map);
					}
					else if(Map==MAP.FOUR)
					{
						r1.x=startingPosMap4[0];
						r1.y=startingPosMap4[1];
						r2.x=startingPosMap4[2];
						r2.y=startingPosMap4[3];
						directionP1=4;
						directionP2=3;
						clearBoard(map);
					}
				}
				try
				{
					sleep (15);
				}
				catch (Exception e)
				{
				}
			}
		}
	}
	/*
	 Description: This thread acts as the timer that is used in conquer mode
	 Parameters: It is a thread so it has no parameters
	 Return: It is a thread so it has no return types
	*/
	public class countdownTimer extends Thread
	{
		public void run ()
		{
			while (true)
			{
				if(countdown&&(State==STATE.GAMEDUEL||State==STATE.GAMECONQUER))
				{
					beep.play();
					imageNum++;					
					clearBoard(map);
					if(imageNum>3)
					{
						countdown=false;
						imageNum=0;
					}
				}
				else
					gameEnd=false;
				try
				{
					sleep (1000);
				}
				catch (Exception e)
				{
				}
				repaint();
			}
		}
	}
	/*
	 Description: This thread continuously paints the trails and walls during the game
	 Parameters: It is a thread so it has no parameters
	 Return: It is a thread so it has no return types
	*/
	public class threadTrail extends Thread 
	{
		public void run ()
		{
			while (true)
			{
				if(!gameEnd)
				{
					createBorder(borderWall);
					if(Map==MAP.TWO&&State==STATE.GAMEDUEL)
					{
						createBorder(obstacle1Map2);
						createBorder(obstacle2Map2);
						createBorder(obstacle3Map2);
						createBorder(obstacle4Map2);
						createBorder(obstacle5Map2);
					}
					else if(Map==MAP.THREE&&State==STATE.GAMEDUEL)
					{
						createBorder(obstacle1Map3);
						createBorder(obstacle2Map3);

					}
					else if(Map==MAP.FOUR&&State==STATE.GAMEDUEL)
					{
						createBorder(obstacle1Map4);
						createBorder(obstacle2Map4);
						createBorder(obstacle3Map4);
						createBorder(obstacle4Map4);
					}
					ehhh=true;
				}
				else
				{
					ehhh=false;
					gameOver();
				}
				try
				{
					sleep (75);
				}
				catch (Exception e)
				{
				}
				repaint();
			}
		}
	}
	public class music extends Thread 
	{
		public void run ()
		{
			int timePlayed=0;
			while (true)
			{		
				if((State==STATE.GAMEDUEL||State==STATE.GAMECONQUER))
				{
					mainMenuMusic.stop();
					timePlayed=0;
				}
				else if((State!=STATE.GAMEDUEL||State!=STATE.GAMECONQUER)&&timePlayed==0)
				{
					mainMenuMusic.play();
					timePlayed++;
				}
				else
					timePlayed++;
				if(timePlayed==65)
					timePlayed=0;
				try
				{
					sleep (1000);
				}
				catch (Exception e)
				{
				}
			}
		}
	}
	public URL getCompleteURL (String fileName)
	{
		try
		{
			return new URL ("file:" + System.getProperty ("user.dir") + "/" + fileName);
		}
		catch (MalformedURLException e)
		{
			System.err.println (e.getMessage ());
		}
		return null;
	}
	/*
	 Description: This thread is responsible for the 3,2,1 countdown before the game starts
	 Parameters: It is a thread so it has no parameters
	 Return: It is a thread so it has no return types
	*/
	public class timer extends Thread
	{
		public void run ()
		{
			while (true)
			{
				if((!countdown&&State==STATE.GAMECONQUER)&&timerOn)
				{
					time-=timeDecrement;
				}
				if(time<=0)
				{
					calculateWinner();
					gameOver();
				}
				try
				{
					sleep (20);
				}
				catch (Exception e)
				{
				}
				repaint();
			}
		}
	}
	/*
	 Description: This is the main method
	 Parameters: It is the main method so it has no parameters
	 Return: It is the main method so it has no return types
	*/
	public static void main (String [] args)
	{
		JFrame frame = new JFrame ("Blocker");
		Blocker myPanel = new Blocker ();
		frame.add(myPanel);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void keyReleased(KeyEvent arg0) {
	}
	public void keyTyped(KeyEvent arg0) {
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mousePressed(MouseEvent e) 
	{
		int mx=e.getX();
		int my=e.getY();
		if(mx>=playButton.x&&mx<=playButton.x+playButton.width&&State==STATE.MENU)
		{
			if(my>=playButton.y&&my<=playButton.y+playButton.height)
			{
				State=STATE.GAMEMENU;
				repaint();
			}
		}
		if(mx>=settingButton.x&&mx<=settingButton.x+settingButton.width&&State==STATE.MENU)
		{
			if(my>=settingButton.y&&my<=settingButton.y+settingButton.height)
			{
				State=STATE.SETTINGS;
			}
		}
		if(mx>=helpButton.x&&mx<=helpButton.x+helpButton.width&&State==STATE.MENU)
		{
			if(my>=helpButton.y&&my<=helpButton.y+helpButton.height)
			{
				State=STATE.CONTROLS;
			}
		}
		if(mx>=quitButton.x&&mx<=quitButton.x+quitButton.width&&State==STATE.MENU)
		{
			if(my>=quitButton.y&&my<=quitButton.y+quitButton.height)
			{
				System.exit(1);
			}
		}
		if(mx>=startGMButton.x&&mx<=startGMButton.x+startGMButton.width&&State==STATE.GAMEMENU)
		{
			if(my>=startGMButton.y&&my<=startGMButton.y+startGMButton.height)
			{
				State=holder;
			}
		}
		if(mx>=duelGMButton.x&&mx<=duelGMButton.x+duelGMButton.width&&State==STATE.GAMEMENU)
		{
			if(my>=duelGMButton.y&&my<=duelGMButton.y+duelGMButton.height)
			{
				holder=STATE.GAMEDUEL;
				JOptionPane.showMessageDialog (this, "Game set to duel",
						"Game State", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if(mx>=conquerGMButton.x&&mx<=conquerGMButton.x+conquerGMButton.width&&State==STATE.GAMEMENU)
		{
			if(my>=conquerGMButton.y&&my<=conquerGMButton.y+conquerGMButton.height)
			{
				holder=STATE.GAMECONQUER;
				JOptionPane.showMessageDialog (this, "Game set to conquer",
						"Game State", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if(mx>=returnButton.x&&mx<=returnButton.x+returnButton.width&&(State==STATE.GAMEMENU||State==STATE.SETTINGS||State==STATE.CONTROLS))
		{
			if(my>=returnButton.y&&my<=returnButton.y+returnButton.height)
			{
				State=STATE.MENU;
			}
		}
		if(mx>=amountOfWinsSET[0].x&&mx<=amountOfWinsSET[0].x+amountOfWinsSET[0].width&&State==STATE.SETTINGS)
		{
			if(my>=amountOfWinsSET[0].y&&my<=amountOfWinsSET[0].y+amountOfWinsSET[0].height)
			{
				winsNeeded=2;
				JOptionPane.showMessageDialog (this, "Changed to best of 3",
						"Game State", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if(mx>=amountOfWinsSET[1].x&&mx<=amountOfWinsSET[1].x+amountOfWinsSET[1].width&&State==STATE.SETTINGS)
		{
			if(my>=amountOfWinsSET[1].y&&my<=amountOfWinsSET[1].y+amountOfWinsSET[1].height)
			{
				winsNeeded=3;
				JOptionPane.showMessageDialog (this, "Changed to best of 5",
						"Game State", JOptionPane.INFORMATION_MESSAGE);			}
		}
		if(mx>=amountOfWinsSET[2].x&&mx<=amountOfWinsSET[2].x+amountOfWinsSET[2].width&&State==STATE.SETTINGS)
		{
			if(my>=amountOfWinsSET[2].y&&my<=amountOfWinsSET[2].y+amountOfWinsSET[2].height)
			{
				winsNeeded=4;
				JOptionPane.showMessageDialog (this, "Changed to best of 7",
						"Game State", JOptionPane.INFORMATION_MESSAGE);		
			}
		}
		if(mx>=timeLimitButtonSET[0].x&&mx<=timeLimitButtonSET[0].x+timeLimitButtonSET[0].width&&State==STATE.SETTINGS)
		{
			if(my>=timeLimitButtonSET[0].y&&my<=timeLimitButtonSET[0].y+timeLimitButtonSET[0].height)
			{
				time=15000;
				JOptionPane.showMessageDialog (this, "Change time limit to 15 seconds",
						"Game State", JOptionPane.INFORMATION_MESSAGE);			
			}
		}
		if(mx>=timeLimitButtonSET[1].x&&mx<=timeLimitButtonSET[1].x+timeLimitButtonSET[1].width&&State==STATE.SETTINGS)
		{
			if(my>=timeLimitButtonSET[1].y&&my<=timeLimitButtonSET[1].y+timeLimitButtonSET[1].height)
			{
				time=30000;
				JOptionPane.showMessageDialog (this, "Change time limit to 30 seconds",
						"Game State", JOptionPane.INFORMATION_MESSAGE);				}
		}
		if(mx>=timeLimitButtonSET[2].x&&mx<=timeLimitButtonSET[2].x+timeLimitButtonSET[2].width&&State==STATE.SETTINGS)
		{
			if(my>=timeLimitButtonSET[2].y&&my<=timeLimitButtonSET[2].y+timeLimitButtonSET[2].height)
			{
				time=45000;
				JOptionPane.showMessageDialog (this, "Change time limit to 45 seconds",
						"Game State", JOptionPane.INFORMATION_MESSAGE);				}
		}
		if(mx>=timeLimitButtonSET[3].x&&mx<=timeLimitButtonSET[3].x+timeLimitButtonSET[3].width&&State==STATE.SETTINGS)
		{
			if(my>=timeLimitButtonSET[3].y&&my<=timeLimitButtonSET[3].y+timeLimitButtonSET[3].height)
			{
				time=60000;
				JOptionPane.showMessageDialog (this, "Change time limit to 60 seconds",
						"Game State", JOptionPane.INFORMATION_MESSAGE);				}
		}

		if(mx>=500&&mx<=750&&State==STATE.SETTINGS)
		{
			if(my>=100&&my<=290)
			{
				Map=MAP.ONE;
				JOptionPane.showMessageDialog (this, "Active map is map one",
						"Game State", JOptionPane.INFORMATION_MESSAGE);				
			}
		}
		if(mx>=800&&mx<=1050&&State==STATE.SETTINGS)
		{
			if(my>=100&&my<=290)
			{
				Map=MAP.TWO;
				JOptionPane.showMessageDialog (this, "Active map is map two",
						"Game State", JOptionPane.INFORMATION_MESSAGE);			
			}
		}
		if(mx>=500&&mx<=750&&State==STATE.SETTINGS)
		{
			if(my>=300&&my<=490)
			{
				Map=MAP.THREE;
				JOptionPane.showMessageDialog (this, "Active map is map three",
						"Game State", JOptionPane.INFORMATION_MESSAGE);			
			}
		}
		if(mx>=800&&mx<=1050&&State==STATE.SETTINGS)
		{
			if(my>=300&&my<=490)
			{
				Map=MAP.FOUR;
				JOptionPane.showMessageDialog (this, "Active map is map four",
						"Game State", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	public void mouseReleased(MouseEvent arg0){
	}
}