/* MachinePlayer.java */

package player;

import list.*;
import player.Board.type;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {
	public final static int COMPUTER_WIN=1;
	public final static int HUMAN_WIN=-1;
	protected type color;
	protected int searchDepth;
	protected Board board;
	public static final boolean COMPUTER=true;
  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
  public MachinePlayer(int color) {
	  this.color=type.typeOfValue(color);
	  searchDepth=3;
	  board=new Board();
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
	  this.color=type.typeOfValue(color);
	  this.searchDepth=searchDepth;
	  board=new Board();
  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
	  Move best=findBest(color);
	  board.makeMove(best, color);
	  return best;
  } 

  // If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.
  public boolean opponentMove(Move m) {
	  if(board.isValidMove(m, opponentColor())){
		  board.makeMove(m, opponentColor());
		  return true;
	  }
	  return false;
  }

  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
  public boolean forceMove(Move m) {
	  if(board.isValidMove(m, color)){
		  board.makeMove(m, color);
		  return true;
	  }
	  return false;
  }
  private type opponentColor(){
	  if(color==type.WHITE)return type.BLACK;
	  return type.WHITE;
  }
  protected Move findBest(type color){
	  BestMove bestMove=minimax(color,this.color==color,searchDepth,Integer.MIN_VALUE,Integer.MAX_VALUE,board);
	  return bestMove.move;
  }
  private BestMove minimax(type color,boolean side,int depth,double alpha,double beta,Board b){
	  BestMove best=new BestMove();
	  BestMove reply;
	  if(b.hasNetwork(color)||b.hasNetwork(opponentColor())||depth==0){
		  best.score=evaluateBoard(b);
		  return best;
	  }
	  if(side==COMPUTER)best.score=alpha;
	  else best.score=beta;
	  try{
		  DList moves=(DList)b.validMoves(color);
		  DListNode moveNode=(DListNode)moves.front();
		  while(moveNode.isValidNode()){
			  Board clone=new Board(b);
			  clone.makeMove((Move)moveNode.item(), color);
			  type nextColor=(color==type.WHITE?type.BLACK:type.WHITE);
			  reply=minimax(nextColor,!side,depth-1,alpha,beta,clone);
			  if((side==COMPUTER)&&(reply.score>best.score)){
				  best.move=(Move)moveNode.item();
				  best.score=reply.score;
				  alpha=reply.score;
			  }else if((side!=COMPUTER)&&(reply.score<best.score)){
				  best.move=(Move)moveNode.item();
				  best.score=reply.score;
				  beta=reply.score;
			  }
			  if(alpha>=beta)return best;
			  moveNode=(DListNode)moveNode.next();
		  }
	  }catch(InvalidNodeException e){
		  System.err.println(e);
	  }
	  return best;
  }
  private double evaluateBoard(Board b){
	  double myScore;
	  double opponentScore;
	  double blackScore=0.0,whiteScore=0.0;
	  double blackGoal1=0.0,blackGoal2=0.0;
	  double whiteGoal1=0.0,whiteGoal2=0.0;
	  if(b.hasNetwork(color))return 100.0;
	  if(b.hasNetwork(opponentColor()))return -100.0;
	  for(int i=1;i<7;i++){
		  if(b.getSquare(0, i)==type.WHITE)whiteGoal1+=1.0;
		  if(b.getSquare(7, i)==type.WHITE)whiteGoal2+=1.0;
		  if(b.getSquare(i, 0)==type.BLACK)blackGoal1+=1.0;
		  if(b.getSquare(i, 7)==type.BLACK)blackGoal2+=1.0;
	  }
	  if(blackGoal1+blackGoal2>0)blackScore=5.0;
	  if(whiteGoal1+whiteGoal2>0)whiteScore=5.0;
	  double whiteConnections=0.0,blackConnections=0.0;
	  double connectionPoint=10.0;
	  for(int y=0;y<8;y++){
		  for(int x=0;x<8;x++){
			  if(b.getSquare(x, y)==type.WHITE)
				  whiteConnections+=(double)(b.currentConnections(x, y)).lenght();
			  else if(b.getSquare(x, y)==type.BLACK)
				  blackConnections+=(double)(b.currentConnections(x, y)).lenght();
		  }
	  }
	  myScore=(whiteConnections*connectionPoint)+whiteScore;
	  opponentScore=(blackConnections*connectionPoint)+blackScore;
	  if(color==type.BLACK){
		  double tmp=myScore;myScore=opponentScore;opponentScore=tmp;
	  }
	  return myScore-opponentScore;
  }
}
