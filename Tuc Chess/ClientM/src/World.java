import java.util.ArrayList;
import java.util.Random;


public class World
{
	private String[][] board = null;
	private int rows = 7;
	private int columns = 5;
	private int myColor = 0;
	private ArrayList<String> availableMoves = null;
	private int rookBlocks = 3;		// rook can move towards <rookBlocks> blocks in any vertical or horizontal direction
	private int nTurns = 0;
	private int nBranches = 0;
	private int noPrize = 9;
	
	public World()
	{
		board = new String[rows][columns];
		
		/* represent the board
		
		BP|BR|BK|BR|BP
		BP|BP|BP|BP|BP
		--|--|--|--|--
		P |P |P |P |P 
		--|--|--|--|--
		WP|WP|WP|WP|WP
		WP|WR|WK|WR|WP
		*/
		
		// initialization of the board
		for(int i=0; i<rows; i++)
			for(int j=0; j<columns; j++)
				board[i][j] = " ";
		
		// setting the black player's chess parts
		
		// black pawns
		for(int j=0; j<columns; j++)
			board[1][j] = "BP";
		
		board[0][0] = "BP";
		board[0][columns-1] = "BP";
		
		// black rooks
		board[0][1] = "BR";
		board[0][columns-2] = "BR";
		
		// black king
		board[0][columns/2] = "BK";
		
		// setting the white player's chess parts
		
		// white pawns
		for(int j=0; j<columns; j++)
			board[rows-2][j] = "WP";
		
		board[rows-1][0] = "WP";
		board[rows-1][columns-1] = "WP";
		
		// white rooks
		board[rows-1][1] = "WR";
		board[rows-1][columns-2] = "WR";
		
		// white king
		board[rows-1][columns/2] = "WK";
		
		// setting the prizes
		for(int j=0; j<columns; j++)
			board[rows/2][j] = "P";
		
		availableMoves = new ArrayList<String>();
	}
	
	public void setMyColor(int myColor)
	{
		this.myColor = myColor;
	}
	
	public String selectAction()
	{
		availableMoves = new ArrayList<String>();
				
		if(myColor == 0)		// I am the white player
			this.whiteMoves();
		else					// I am the black player
			this.blackMoves();
		
		// keeping track of the branch factor
		nTurns++;
		nBranches += availableMoves.size();
		return this.ourmove();
		
	}
	
	private void whiteMoves()
	{
		String firstLetter = "";
		String secondLetter = "";
		String move = "";
				
		for(int i=0; i<rows; i++)
		{
			for(int j=0; j<columns; j++)
			{
				firstLetter = Character.toString(board[i][j].charAt(0));
				
				// if it there is not a white chess part in this position then keep on searching
				if(firstLetter.equals("B") || firstLetter.equals(" ") || firstLetter.equals("P"))
					continue;
				
				// check the kind of the white chess part
				secondLetter = Character.toString(board[i][j].charAt(1));
				
				if(secondLetter.equals("P"))	// it is a pawn
				{
					// check if it can move towards the last row
					if(i-1 == 0 && (Character.toString(board[i-1][j].charAt(0)).equals(" ") 
							         || Character.toString(board[i-1][j].charAt(0)).equals("P")))
					{
						move = Integer.toString(i) + Integer.toString(j) + 
						       Integer.toString(i-1) + Integer.toString(j);
						
						availableMoves.add(move);
						continue;
					}
					
					// check if it can move one vertical position ahead
					firstLetter = Character.toString(board[i-1][j].charAt(0));
					
					if(firstLetter.equals(" ") || firstLetter.equals("P"))
					{
						move = Integer.toString(i) + Integer.toString(j) + 
							   Integer.toString(i-1) + Integer.toString(j);
						
						availableMoves.add(move);
					}
					
					// check if it can move crosswise to the left
					if(j!=0 && i!=0)
					{
						firstLetter = Character.toString(board[i-1][j-1].charAt(0));
						
						if(firstLetter.equals("W") || firstLetter.equals(" ") || firstLetter.equals("P"))
							continue;
						
						move = Integer.toString(i) + Integer.toString(j) + 
							   Integer.toString(i-1) + Integer.toString(j-1);
						
						availableMoves.add(move);
					}
					
					// check if it can move crosswise to the right
					if(j!=columns-1 && i!=0)
					{
						firstLetter = Character.toString(board[i-1][j+1].charAt(0));
						
						if(firstLetter.equals("W") || firstLetter.equals(" ") || firstLetter.equals("P"))
							continue;
						
						move = Integer.toString(i) + Integer.toString(j) + 
							   Integer.toString(i-1) + Integer.toString(j+1);
						
						availableMoves.add(move);
					}
				}
				else if(secondLetter.equals("R"))	// it is a rook
				{
					// check if it can move upwards
					for(int k=0; k<rookBlocks; k++)
					{
						if((i-(k+1)) < 0)
							break;
						
						firstLetter = Character.toString(board[i-(k+1)][j].charAt(0));
						
						if(firstLetter.equals("W"))
							break;
						
						move = Integer.toString(i) + Integer.toString(j) + 
							   Integer.toString(i-(k+1)) + Integer.toString(j);
						
						availableMoves.add(move);
						
						// prevent detouring a chesspart to attack the other
						if(firstLetter.equals("B") || firstLetter.equals("P"))
							break;
					}
					
					// check if it can move downwards
					for(int k=0; k<rookBlocks; k++)
					{
						if((i+(k+1)) == rows)
							break;
						
						firstLetter = Character.toString(board[i+(k+1)][j].charAt(0));
						
						if(firstLetter.equals("W"))
							break;
						
						move = Integer.toString(i) + Integer.toString(j) + 
							   Integer.toString(i+(k+1)) + Integer.toString(j);
						
						availableMoves.add(move);
						
						// prevent detouring a chesspart to attack the other
						if(firstLetter.equals("B") || firstLetter.equals("P"))
							break;
					}
					
					// check if it can move on the left
					for(int k=0; k<rookBlocks; k++)
					{
						if((j-(k+1)) < 0)
							break;
						
						firstLetter = Character.toString(board[i][j-(k+1)].charAt(0));
						
						if(firstLetter.equals("W"))
							break;
						
						move = Integer.toString(i) + Integer.toString(j) + 
							   Integer.toString(i) + Integer.toString(j-(k+1));
						
						availableMoves.add(move);
						
						// prevent detouring a chesspart to attack the other
						if(firstLetter.equals("B") || firstLetter.equals("P"))
							break;
					}
					
					// check of it can move on the right
					for(int k=0; k<rookBlocks; k++)
					{
						if((j+(k+1)) == columns)
							break;
						
						firstLetter = Character.toString(board[i][j+(k+1)].charAt(0));
						
						if(firstLetter.equals("W"))
							break;
						
						move = Integer.toString(i) + Integer.toString(j) + 
							   Integer.toString(i) + Integer.toString(j+(k+1));
						
						availableMoves.add(move);
						
						// prevent detouring a chesspart to attack the other
						if(firstLetter.equals("B") || firstLetter.equals("P"))
							break;
					}
				}
				else // it is the king
				{
					// check if it can move upwards
					if((i-1) >= 0)
					{
						firstLetter = Character.toString(board[i-1][j].charAt(0));
						
						if(!firstLetter.equals("W"))
						{
							move = Integer.toString(i) + Integer.toString(j) + 
								   Integer.toString(i-1) + Integer.toString(j);
								
							availableMoves.add(move);	
						}
					}
					
					// check if it can move downwards
					if((i+1) < rows)
					{
						firstLetter = Character.toString(board[i+1][j].charAt(0));
						
						if(!firstLetter.equals("W"))
						{
							move = Integer.toString(i) + Integer.toString(j) + 
								   Integer.toString(i+1) + Integer.toString(j);
								
							availableMoves.add(move);	
						}
					}
					
					// check if it can move on the left
					if((j-1) >= 0)
					{
						firstLetter = Character.toString(board[i][j-1].charAt(0));
						
						if(!firstLetter.equals("W"))
						{
							move = Integer.toString(i) + Integer.toString(j) + 
								   Integer.toString(i) + Integer.toString(j-1);
								
							availableMoves.add(move);	
						}
					}
					
					// check if it can move on the right
					if((j+1) < columns)
					{
						firstLetter = Character.toString(board[i][j+1].charAt(0));
						
						if(!firstLetter.equals("W"))
						{
							move = Integer.toString(i) + Integer.toString(j) + 
								   Integer.toString(i) + Integer.toString(j+1);
								
							availableMoves.add(move);	
						}
					}
				}			
			}	
		}
	}
	
	private void blackMoves()
	{
		String firstLetter = "";
		String secondLetter = "";
		String move = "";
				
		for(int i=0; i<rows; i++)
		{
			for(int j=0; j<columns; j++)
			{
				firstLetter = Character.toString(board[i][j].charAt(0));
				
				// if it there is not a black chess part in this position then keep on searching
				if(firstLetter.equals("W") || firstLetter.equals(" ") || firstLetter.equals("P"))
					continue;
				
				// check the kind of the white chess part
				secondLetter = Character.toString(board[i][j].charAt(1));
				
				if(secondLetter.equals("P"))	// it is a pawn
				{
					// check if it is at the last row
					if(i+1 == rows-1 && (Character.toString(board[i+1][j].charAt(0)).equals(" ")
										  || Character.toString(board[i+1][j].charAt(0)).equals("P")))
					{
						move = Integer.toString(i) + Integer.toString(j) + 
						       Integer.toString(i+1) + Integer.toString(j);
						
						availableMoves.add(move);
						continue;
					}
					
					// check if it can move one vertical position ahead
					firstLetter = Character.toString(board[i+1][j].charAt(0));
					
					if(firstLetter.equals(" ") || firstLetter.equals("P"))
					{
						move = Integer.toString(i) + Integer.toString(j) + 
							   Integer.toString(i+1) + Integer.toString(j);
						
						availableMoves.add(move);
					}
					
					// check if it can move crosswise to the left
					if(j!=0 && i!=rows-1)
					{
						firstLetter = Character.toString(board[i+1][j-1].charAt(0));
						
						if(firstLetter.equals("B") || firstLetter.equals(" ") || firstLetter.equals("P"))
							continue;
						
						move = Integer.toString(i) + Integer.toString(j) + 
							   Integer.toString(i+1) + Integer.toString(j-1);
						
						availableMoves.add(move);
					}
					
					// check if it can move crosswise to the right
					if(j!=columns-1 && i!=rows-1)
					{
						firstLetter = Character.toString(board[i+1][j+1].charAt(0));
						
						if(firstLetter.equals("B") || firstLetter.equals(" ") || firstLetter.equals("P"))
							continue;
						
						move = Integer.toString(i) + Integer.toString(j) + 
							   Integer.toString(i+1) + Integer.toString(j+1);
						
						availableMoves.add(move);
					}
				}
				else if(secondLetter.equals("R"))	// it is a rook
				{
					// check if it can move upwards
					for(int k=0; k<rookBlocks; k++)
					{
						if((i-(k+1)) < 0)
							break;
						
						firstLetter = Character.toString(board[i-(k+1)][j].charAt(0));
						
						if(firstLetter.equals("B"))
							break;
						
						move = Integer.toString(i) + Integer.toString(j) + 
							   Integer.toString(i-(k+1)) + Integer.toString(j);
						
						availableMoves.add(move);
						
						// prevent detouring a chesspart to attack the other
						if(firstLetter.equals("W") || firstLetter.equals("P"))
							break;
					}
					
					// check if it can move downwards
					for(int k=0; k<rookBlocks; k++)
					{
						if((i+(k+1)) == rows)
							break;
						
						firstLetter = Character.toString(board[i+(k+1)][j].charAt(0));
						
						if(firstLetter.equals("B"))
							break;
						
						move = Integer.toString(i) + Integer.toString(j) + 
							   Integer.toString(i+(k+1)) + Integer.toString(j);
						
						availableMoves.add(move);
						
						// prevent detouring a chesspart to attack the other
						if(firstLetter.equals("W") || firstLetter.equals("P"))
							break;
					}
					
					// check if it can move on the left
					for(int k=0; k<rookBlocks; k++)
					{
						if((j-(k+1)) < 0)
							break;
						
						firstLetter = Character.toString(board[i][j-(k+1)].charAt(0));
						
						if(firstLetter.equals("B"))
							break;
						
						move = Integer.toString(i) + Integer.toString(j) + 
							   Integer.toString(i) + Integer.toString(j-(k+1));
						
						availableMoves.add(move);
						
						// prevent detouring a chesspart to attack the other
						if(firstLetter.equals("W") || firstLetter.equals("P"))
							break;
					}
					
					// check of it can move on the right
					for(int k=0; k<rookBlocks; k++)
					{
						if((j+(k+1)) == columns)
							break;
						
						firstLetter = Character.toString(board[i][j+(k+1)].charAt(0));
						
						if(firstLetter.equals("B"))
							break;
						
						move = Integer.toString(i) + Integer.toString(j) + 
							   Integer.toString(i) + Integer.toString(j+(k+1));
						
						availableMoves.add(move);
						
						// prevent detouring a chesspart to attack the other
						if(firstLetter.equals("W") || firstLetter.equals("P"))
							break;
					}
				}
				else // it is the king
				{
					// check if it can move upwards
					if((i-1) >= 0)
					{
						firstLetter = Character.toString(board[i-1][j].charAt(0));
						
						if(!firstLetter.equals("B"))
						{
							move = Integer.toString(i) + Integer.toString(j) + 
								   Integer.toString(i-1) + Integer.toString(j);
								
							availableMoves.add(move);	
						}
					}
					
					// check if it can move downwards
					if((i+1) < rows)
					{
						firstLetter = Character.toString(board[i+1][j].charAt(0));
						
						if(!firstLetter.equals("B"))
						{
							move = Integer.toString(i) + Integer.toString(j) + 
								   Integer.toString(i+1) + Integer.toString(j);
								
							availableMoves.add(move);	
						}
					}
					
					// check if it can move on the left
					if((j-1) >= 0)
					{
						firstLetter = Character.toString(board[i][j-1].charAt(0));
						
						if(!firstLetter.equals("B"))
						{
							move = Integer.toString(i) + Integer.toString(j) + 
								   Integer.toString(i) + Integer.toString(j-1);
								
							availableMoves.add(move);	
						}
					}
					
					// check if it can move on the right
					if((j+1) < columns)
					{
						firstLetter = Character.toString(board[i][j+1].charAt(0));
						
						if(!firstLetter.equals("B"))
						{
							move = Integer.toString(i) + Integer.toString(j) + 
								   Integer.toString(i) + Integer.toString(j+1);
								
							availableMoves.add(move);	
						}
					}
				}			
			}	
		}
	}
	
	private String selectRandomAction()
	{		
		Random ran = new Random();
		int x = ran.nextInt(availableMoves.size());
		
		return availableMoves.get(x);
	}
	
	public double getAvgBFactor()
	{
		return nBranches / (double) nTurns;
	}
	
	public void makeMove(int x1, int y1, int x2, int y2, int prizeX, int prizeY)
	{
		String chesspart = Character.toString(board[x1][y1].charAt(1));
		
		boolean pawnLastRow = false;
		
		// check if it is a move that has made a move to the last line
		if(chesspart.equals("P"))
			if( (x1==rows-2 && x2==rows-1) || (x1==1 && x2==0) )
			{
				board[x2][y2] = " ";	// in a case an opponent's chess part has just been captured
				board[x1][y1] = " ";
				pawnLastRow = true;
			}
		
		// otherwise
		if(!pawnLastRow)
		{
			board[x2][y2] = board[x1][y1];
			board[x1][y1] = " ";
		}
		
		// check if a prize has been added in the game
		if(prizeX != noPrize)
			board[prizeX][prizeY] = "P";
	}
	
	private String ourmove(){
		int best_row = 0;  // Best row index         
		int best_col = 0;  // Best column index  
		int best_row_old = 0;  // Best row index         
		int best_col_old = 0;  // Best column index
		int new_score = 0; // Score for current move
		int my_score=0;
	    int score = -100; //Minimum opponent score
	    int x1,x2,y1,y2;
	    String[][] temp_board = null; //copy of board 
	    temp_board = new String[rows][columns];
	    ArrayList<String> av_moves = null;// Local valid moves array 
	    av_moves= new ArrayList<String>();
		//create copy of board and moves
		for(int i = 0; i < rows; i++) {
	         for(int j = 0; j < columns; j++){
	           temp_board[i][j] = board[i][j];			
			}
		}
		for(int i =0; i< availableMoves.size(); i++) {
			av_moves.add(availableMoves.get(i));
		}
		
	   // Go through all valid moves 
		for(int i = 0; i < av_moves.size(); i++){
	       // Restore board and clear availableMoves
	       for(int k = 0; k < rows; k++) {
	         for(int j = 0; j < columns; j++) {
	           board[k][j] = temp_board[k][j];
	         }
	       }
	       availableMoves.clear();
	       my_score=0;
	       //decode move
	       String action = av_moves.get(i);
	       x1 = Integer.parseInt(Character.toString(action.charAt(0)));
	       y1 = Integer.parseInt(Character.toString(action.charAt(1)));
	       x2 = Integer.parseInt(Character.toString(action.charAt(2)));
	       y2 = Integer.parseInt(Character.toString(action.charAt(3)));
	       
	       //Try this move 
	       if(Character.toString(board[x2][y2].charAt(0)).equals("P")){//present
	    	   my_score++;
	       }else if((Character.toString(board[x2][y2].charAt(0)).equals("B")) || Character.toString(board[x2][y2].charAt(0)).equals("W")) {
	    	   String chesspart = Character.toString(board[x2][y2].charAt(1)); //what kind of enemy we are about to capture
		       if(chesspart.equals("P")) {
		    	   my_score++;
		       }else if(chesspart.equals("R")) {
		    	   my_score+=3;
		       }else if(chesspart.equals("K")) {
		    	   my_score+=8;
		       }
	       }
	       
	       board[x2][y2] = board[x1][y1];
		   board[x1][y1] = " ";
		   if( (x1==rows-2 && x2==rows-1) || (x1==1 && x2==0) ) { //last row
				board[x2][y2] = " ";	
				my_score++;
		   }

	       // find valid moves for the opponent after this move
		   if(myColor == 0)		// I am the white player
			   this.blackMoves();
			else					// I am the black player
				this.whiteMoves();

	       //find the score for the opponents best move 
	       new_score = best_move();
	       new_score = Evaluation(my_score,new_score);
	       
	       if(new_score>score){                     
	         score = new_score;  
	         best_row = x2; 
	         best_col = y2;
	         best_row_old = x1; 
	         best_col_old = y1;  
	       }else if(new_score == score) {
	    	   if(myColor == 0) {
	    		   if((Character.toString(board[x2][y2].charAt(0)).equals("B")) && (Character.toString(board[best_row][best_col].charAt(0)).equals("") || Character.toString(board[best_row][best_col].charAt(0)).equals("P") ) ) {
	    			   best_row=x2;
	    			   best_col=y2;
	    			   best_row_old = x1; 
	    		       best_col_old = y1; 
	    		   }
	    	   }else {
	    		   if((Character.toString(board[x2][y2].charAt(0)).equals("W")) && (Character.toString(board[best_row][best_col].charAt(0)).equals("") || Character.toString(board[best_row][best_col].charAt(0)).equals("P") ) ) {
	    			   best_row=x2;
	    			   best_col=y2;
	    			   best_row_old = x1; 
	    		       best_col_old = y1; 
	    		   }
	    	   }
	       }
	     }
		// Restore board 
		for(int i = 0; i < rows; i++) {
	         for(int j = 0; j < columns; j++){
	           board[i][j] = temp_board[i][j];			
			}
		}

	   // Make the best move
		String move = Integer.toString(best_row_old) + Integer.toString(best_col_old) + 
				   Integer.toString(best_row) + Integer.toString(best_col);
		return move;
	}

	private int best_move(){
		int new_score = 0; // Score for current move  
	    int score = 0; //Maximum opponent score
	    int x1,x2,y1,y2;
	    String[][] temp_board = null; //copy of board 
	    temp_board = new String[rows][columns];
	    
	    //create copy of board 
	    for(int i = 0; i < rows; i++) {
	         for(int j = 0; j < columns; j++){
	           temp_board[i][j] = board[i][j];			
			}
		}
	 // Go through all valid moves 
 		for(int i = 0; i < availableMoves.size(); i++){
 	     // Restore board and clear availableMoves
 	       for(int k = 0; k < rows; k++) {
 	         for(int j = 0; j < columns; j++) {
 	           board[k][j] = temp_board[k][j];
 	         }
 	       }
 	       new_score=0;
 	       //decode move
 	       String action = availableMoves.get(i);
 	       x1 = Integer.parseInt(Character.toString(action.charAt(0)));
 	       y1 = Integer.parseInt(Character.toString(action.charAt(1)));
 	       x2 = Integer.parseInt(Character.toString(action.charAt(2)));
 	       y2 = Integer.parseInt(Character.toString(action.charAt(3)));
 	       
 	       //Try this move 
 	      if(Character.toString(board[x2][y2].charAt(0))=="P"){//present
 	    	 new_score++;
	       }else if((Character.toString(board[x2][y2].charAt(0))=="B") || Character.toString(board[x2][y2].charAt(0))=="W") {
	    	   String chesspart = Character.toString(board[x2][y2].charAt(1)); //what kind of enemy we are about to capture
		       if(chesspart.equals("P")) {
		    	   new_score++;
		       }else if(chesspart.equals("R")) {
		    	   new_score+=3;
		       }else if(chesspart.equals("K")) {
		    	   new_score+=8;
		       }
	       }
 	       
 	       board[x2][y2] = board[x1][y1];
 		   board[x1][y1] = " ";
 		   if( (x1==rows-2 && x2==rows-1) || (x1==1 && x2==0) ) { //last row
 				board[x2][y2] = " ";	
 				new_score++;
 		   }
	       if(score<new_score)         
	        score = new_score;  
	     }
 		for(int k = 0; k < rows; k++) {
	         for(int j = 0; j < columns; j++) {
	           board[k][j] = temp_board[k][j];
	         }
	       }
	   return score;                  
	}
	
	private int Evaluation(int playerscore,int enemyscore) {
		int score=0;
		int whitePawnValue=0;
		int blackPawnValue=0;
		for(int i = 0; i < rows; i++) {
	         for(int j = 0; j < columns; j++){
	        	 if( Character.toString(board[i][j].charAt(0)) == "W") {
	        		 if(Character.toString(board[i][j].charAt(1)).equals("P")) {
	        			 whitePawnValue++;
	        		 }else if(Character.toString(board[i][j].charAt(1)).equals("R")) {
	        			 whitePawnValue+=3;
	        		 }else if(Character.toString(board[i][j].charAt(1)).equals("K")) {
	        			 whitePawnValue+=8;
	        		 }
	        	 }else if ( Character.toString(board[i][j].charAt(0)) == "B") {
	        		 if(Character.toString(board[i][j].charAt(1)).equals("P")) {
	        			 blackPawnValue++;
	        		 }else if(Character.toString(board[i][j].charAt(1)).equals("R")) {
	        			 blackPawnValue+=3;
	        		 }else if(Character.toString(board[i][j].charAt(1)).equals("K")) {
	        			 blackPawnValue+=8;
	        		 }
	        	 }
	        }
		}
		if(myColor == 0)		// I am the white player
			score=(playerscore+whitePawnValue)-(enemyscore+blackPawnValue);
		else					// I am the black player
			score=(playerscore+blackPawnValue)-(enemyscore+whitePawnValue);
		
		return score;
	}

}
