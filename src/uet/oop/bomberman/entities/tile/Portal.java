package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Tile {
	private Board board;

	public Portal(int x, int y, Sprite sprite, Board board) {
		super(x, y, sprite);
		this.board=board;
	}

	@Override
	public boolean collide(Entity e) {
		if (e instanceof Bomber)
		{
			if (board.detectNoEnemies()==false) return false;
			if (board.detectNoEnemies() && e.getXTile() == getX() && e.getYTile() == getY())
			{
				board.nextLevel();

			}
			return true;
		}
		return false;
	}

}
