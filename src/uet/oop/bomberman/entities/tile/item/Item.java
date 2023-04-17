package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.entities.tile.Tile;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utility.SoundManager;

public abstract class Item extends Tile {

	public Item(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	public SoundManager _itemSound = new SoundManager("sound/item.wav",0);
}
