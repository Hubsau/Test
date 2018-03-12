package de.hubsau.tryjump.gamestate;

import java.util.ArrayList;

public class GameStateManager {

	private static GameState gameState;
	private static ArrayList<GameState> gamestates = new ArrayList<>();

	public GameStateManager() {
		gamestates.add(new LobbyState());
		gamestates.add(new IngameState());
		gamestates.add(new EndFightState());

	}

	public static void setGameState(int id) {
		if (gameState != null)
			gameState.end();
		gameState = gamestates.get(id);
		gameState.start();

	}

	public static GameState getGameState() {
		return gameState;
	}

}
