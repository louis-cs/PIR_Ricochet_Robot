package com.gameboard;

import com.graph.struct.TreeSearch;

public class Main {

	public static void main(String[] args){
		HighLevelGameboard gameboard=new HighLevelGameboard();
		TreeSearch.search(gameboard);
	}
}

