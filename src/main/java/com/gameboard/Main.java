package com.gameboard;

import com.graph.struct.TreeSearch;

public class Main {

	public static void main(String[] args){
		HighLevelGameboard gameboard=new HighLevelGameboard(false, 0);
		TreeSearch t =new TreeSearch();
		t.search(gameboard);
		//gameboard.displayBoard();
	}
}

