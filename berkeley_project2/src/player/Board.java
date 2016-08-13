package player;

import java.util.HashMap;
import java.util.Map;

import list.*;

public class Board {
	public enum type{
		EMPTY,BLACK,WHITE;
		public static type typeOfValue(int v){
			if(v==-1)return EMPTY;
			if(v==0)return BLACK;
			return WHITE;
		}
	}
	private type[][] gameBoard = new type[8][8];
	private int whitePiecesLeft=10;
	private int blackPiecesLeft=10;
	public enum corners{
		BOTTOM_RIGHT(7,7),TOP_RIGHT(7,0),BOTTOM_LEFT(0,7),TOP_LEFT(0,0);
		private int x,y;
		private corners(int x,int y){
			this.x=x;
			this.y=y;
		}
	}
	private enum direction{
		UP_DIRECTION(-1,0),UP_RIGHT_DIRECTION(-1,1),RIGHT_DIRECTION(0,1),DOWN_RIGHT_DIRECTION(1,1),
		DOWN_DIRECTIOIN(1,0),DOWN_LEFT_DIRECTION(1,-1),LEFT_DIRECTION(0,-1),UP_LEFT_DIRECTION(-1,-1),
		INVALID(10,10);
		private int x,y;
		private direction(int x,int y){
			this.x=x;
			this.y=y;
		}
	}
	public Board(){
		for(int x=0;x<8;x++){
			for(int y=0;y<8;y++){
				gameBoard[x][y]=type.EMPTY;
			}
		}
	}
	public Board(Board b){
		for(int x=0;x<8;x++){
			for(int y=0;y<8;y++){
				gameBoard[x][y]=b.gameBoard[x][y];
			}
		}
		whitePiecesLeft=b.whitePiecesLeft;
		blackPiecesLeft=b.blackPiecesLeft;
	}
	private boolean isCorner(int x,int y){
		for(corners corner:corners.values()){
			if(corner.x==x&&corner.y==y)return true;
		}
		return false;
	}
	private int[][] neighbors(int x,int y){
		int[][] n=new int[8][2];
		int idx=0;
		for(direction d:direction.values()){
			if(d==direction.INVALID)break;
			n[idx++]=new int[]{x+d.x,y+d.y};
		}
		return n;
	}
	protected type getSquare(int x,int y){
		if(x<0||y<0||x>7||y>7)return type.EMPTY;
		return gameBoard[x][y];
	}
	private boolean isClustered(Move m,type color){
		int count=0;
		int[][] n=neighbors(m.x1,m.y1);
		if(m.moveKind==Move.ADD){
			for(int i=0;i<8;i++){
				if(getSquare(n[i][0],n[i][1])==color){
					count++;
					int[][] otherNeighbors=neighbors(n[i][0],n[i][1]);
					for(int k=0;k<8;k++){
						if(getSquare(otherNeighbors[k][0],otherNeighbors[k][1])==color)count++;
					}
				}
			}
			if(count>1)return true;
		}
		else if(m.moveKind==Move.STEP){
			Board temp=new Board(this);
			temp.gameBoard[m.x2][m.y2]=type.EMPTY;
			Move addMove = new Move(m.x1,m.y1);
			return temp.isClustered(addMove, color);
		}
		return false;
	}
	private boolean inGoalArea(type playerColor){
		boolean goalarea1=false;
		boolean goalarea2=false;
		if(playerColor==type.WHITE){
			for(int y=1;y<7;y++){
				if(getSquare(0,y)==type.WHITE)goalarea1=true;
				if(getSquare(7,y)==type.WHITE)goalarea2=true;
			}
		}
		if(playerColor==type.BLACK){
			for(int x=1;x<7;x++){
				if(getSquare(x,0)==type.BLACK)goalarea1=true;
				if(getSquare(x,7)==type.BLACK)goalarea2=true;
			}
		}
		return goalarea1&&goalarea2;
	}
	private DList goalPieces(type playerColor,int x){
		DList piecesInGoalArea = new DList();
		for(int y=1;y<7;y++){
			if(playerColor==type.WHITE){
				if(getSquare(x,y)==type.WHITE)piecesInGoalArea.insertBack(new int[]{x,y});
			}else{
				if(getSquare(y,x)==type.BLACK)piecesInGoalArea.insertBack(new int[]{y,x});
			}
		}
		return piecesInGoalArea;
	}
	private boolean onBoard(int x,int y){
		if(x<0||x>7)return false;
		if(y<0||y>7)return false;
		return true;
	}
	protected DList currentConnections(int x,int y){
		type piece=getSquare(x,y);
		type opp=type.EMPTY;
		if(piece==type.WHITE)opp=type.BLACK;
		if(piece==type.BLACK)opp=type.WHITE;
		DList connections=new DList();
		for(direction d:direction.values()){
			int i=x+d.x,j=y+d.y;
			while(onBoard(i,j)){
				if(getSquare(i,j)==piece){
					connections.insertFront(new int[]{i,j});
					break;
				}
				if(getSquare(i,j)==opp)break;
				i+=d.x;j+=d.y;
			}
		}
		return connections;
	}
	private direction direction(int[] first,int[] next){
		int deltax=next[0]-first[0],deltay=next[1]-first[1];
		if(deltax>0)deltax=1;
		else if(deltax<0)deltax=-1;
		if(deltay>0)deltay=1;
		else if(deltay<0)deltay=-1;
		for(direction d:direction.values()){
			if(deltax==d.x&&deltay==d.y)return d;
		}
		return direction.INVALID;
	}
	private boolean travel(int[] startCoord,int piecesUsed,DList endGoals,DList connections,DList visited,direction notTurn){
		if(piecesUsed>9)return false;
		if(endGoals.within(startCoord)){
			if(piecesUsed>5)return true;
			return false;
		}
		if(visited.within(startCoord))return false;
		DListNode otherCurrent=(DListNode)connections.front();
		try{
			while(otherCurrent.isValidNode()){
				direction cannotTravel=direction(startCoord,(int[])otherCurrent.getItem());
				if(cannotTravel==notTurn){
					otherCurrent=(DListNode)otherCurrent.next();continue;
				}
				visited.insertBack(startCoord);
				int[] nextCoord=(int[])otherCurrent.getItem();
				DList nextConnections=currentConnections(nextCoord[0],nextCoord[1]);
				if(travel(((int[])otherCurrent.getItem()),piecesUsed+1,endGoals,nextConnections,visited,cannotTravel)){
					return true;
				}else{
					otherCurrent=(DListNode)otherCurrent.next();
					visited.back().remove();
				}
			}
		}catch(InvalidNodeException e){
			System.err.println(e);
		}
		return false;
	}
	protected boolean hasNetwork(type playerColor){
		if(playerColor==type.WHITE){
			if(whitePiecesLeft>4||!inGoalArea(playerColor))return false;
		}
		if(playerColor==type.BLACK){
			if(blackPiecesLeft>4||!inGoalArea(playerColor))return false;
		}
		DList startGoals=goalPieces(playerColor,0);
		DList endGoals=goalPieces(playerColor,7);
		DListNode current=(DListNode)startGoals.front();
		try{
			while(current.isValidNode()){
				int[] startCoord=(int[])current.getItem();
				DList connections=currentConnections(startCoord[0],startCoord[1]);
				DList visited=new DList();
				if(travel(startCoord,1,endGoals,connections,visited,direction.INVALID))return true;
				else current=(DListNode)current.next();
			}
		}catch(InvalidNodeException e){
			System.err.println(e);
		}
		return false;
	}
	private boolean isGoalPosition(Move m,type color){
		if(color==type.WHITE){
			if((m.x1==0)||(m.x1==7))return true;
		}else if(color==type.BLACK){
			if((m.y1==0)||(m.y1==7))return true;
		}
		return false;
	}
	protected boolean isValidMove(Move m,type playerColor){
		if(m.moveKind==Move.STEP){
			if(m.x1==m.x2&&m.y1==m.y2)return false;
		}
		if(onBoard(m.x1,m.y1)){
			if(getSquare(m.x1,m.y1)!=type.EMPTY)return false;
			if(isCorner(m.x1,m.y1))return false;
			if(playerColor==type.BLACK){
				if(m.moveKind==Move.ADD&&blackPiecesLeft==0)return false;
				if(isGoalPosition(m,type.WHITE)||isClustered(m,playerColor))return false;
				return true;
			}else if(playerColor==type.WHITE){
				if(m.moveKind==m.ADD&&whitePiecesLeft==0)return false;
				if(isGoalPosition(m,type.BLACK)||isClustered(m,playerColor))return false;
				return true;
			}
		}
		return false;
	}
	protected DList validMoves(type playerColor){
		DList moves=new DList();
		int chips=0;
		if(playerColor==type.WHITE)chips=whitePiecesLeft;
		else if(playerColor==type.BLACK)chips=blackPiecesLeft;
		if(chips>0){
			for(int i=0;i<8;i++){
				for(int j=0;j<8;j++){
					Move m=new Move(i,j);
					if(isValidMove(m,playerColor))moves.insertBack(m);
				}
			}
		}else{
			DList positions=new DList();
			for(int j=0;j<8;j++){
				for(int i=0;i<8;i++){
					if(getSquare(i,j)==playerColor)positions.insertBack(new int[]{i,j});
				}
			}
			for(int j=0;j<8;j++){
				for(int i=0;i<8;i++){
					DListNode posNode=(DListNode)positions.front();
					try{
						while(posNode.isValidNode()){
							int[] coords=(int[])posNode.item();
							Move m=new Move(i,j,coords[0],coords[1]);
							if(isValidMove(m,playerColor))moves.insertBack(m);
							posNode=(DListNode)posNode.next();
						}
					}catch(InvalidNodeException e){
						System.err.println(e);
					}
				}
			}
		}
		return moves;
	}
	protected void makeMove(Move m,type playerColor){
		if(m.moveKind==Move.ADD){
			gameBoard[m.x1][m.y1]=playerColor;
			if(playerColor==type.WHITE)whitePiecesLeft--;
			if(playerColor==type.BLACK)blackPiecesLeft--;
		}else if(m.moveKind==Move.STEP){
			gameBoard[m.x2][m.y2]=type.EMPTY;
			gameBoard[m.x1][m.y1]=playerColor;
		}
	}
}
