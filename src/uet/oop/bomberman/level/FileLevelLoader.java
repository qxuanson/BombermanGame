package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Doll;
import uet.oop.bomberman.entities.character.enemy.Kondoria;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static char[][] _map;
	
	public FileLevelLoader(Board board, int level) throws LoadLevelException {
		super(board, level);
	}
	
	@Override
	public void loadLevel(int level) {
		try {
			String path = "res/levels/Level" + Integer.toString(level) + ".txt";
			File file = new File(path);
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = reader.readLine();
			String[] token = line.split("\\s+");
			_level = Integer.parseInt(token[0]);
			_height = Integer.parseInt(token[1]);
			_width = Integer.parseInt(token[2]);
			_map = new char[_height][_width];
			for (int i = 0; i < _height; i++) {
				line = reader.readLine();
				for (int j = 0; j < _width; j++) {
					_map[i][j] = line.charAt(j);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createEntities() {
		// thêm Wall
		for (int j = 0; j < _height; j++) {
			for (int i = 0; i < _width; i++) {
				char c = _map[j][i];
				int pos = i + j * _width;
				switch (c) {
					//Wall
					case '#':
						_board.addEntity(pos, new Wall(i, j, Sprite.wall));
						break;
					//Brick
					case '*':
						_board.addEntity(pos, new LayeredEntity(i, j,
								new Grass(i, j, Sprite.grass),
								new Brick(i, j, Sprite.brick)));
						break;
					//BombItem
					case 'b':
						_board.addEntity(pos, new LayeredEntity(i, j,
								new Grass(i, j, Sprite.grass),
								new BombItem(i, j, Sprite.powerup_bombs),
								new Brick(i, j, Sprite.brick)));
						break;
					//FlameItem
					case 'f':
						_board.addEntity(pos, new LayeredEntity(i, j,
								new Grass(i, j, Sprite.grass),
								new FlameItem(i, j, Sprite.powerup_flames),
								new Brick(i, j, Sprite.brick)));
						break;
					//SpeedItem
					case 's':
						_board.addEntity(pos, new LayeredEntity(i, j,
								new Grass(i, j, Sprite.grass),
								new SpeedItem(i, j, Sprite.powerup_speed),
								new Brick(i, j, Sprite.brick)));
						break;
					//Character
					case 'p':
						_board.addCharacter(new Bomber(Coordinates.tileToPixel(i),
								Coordinates.tileToPixel(j) + Game.TILES_SIZE, _board));
						Screen.setOffset(0, 0);
						_board.addEntity(pos, new Grass(i, j, Sprite.grass));
						break;
					//Balloon
					case '1':
						_board.addCharacter(new Balloon(Coordinates.tileToPixel(i),
								Coordinates.tileToPixel(j) + Game.TILES_SIZE, _board));
						Screen.setOffset(0, 0);
						_board.addEntity(pos, new Grass(i, j, Sprite.grass));
						break;
					//Oneal
					case '2':
						_board.addCharacter(new Oneal(Coordinates.tileToPixel(i),
								Coordinates.tileToPixel(j) + Game.TILES_SIZE, _board));
						Screen.setOffset(0, 0);
						_board.addEntity(pos, new Grass(i, j, Sprite.grass));
						break;
					case '3':
						_board.addCharacter(new Kondoria(Coordinates.tileToPixel(i),
								Coordinates.tileToPixel(j) + Game.TILES_SIZE, _board));
						Screen.setOffset(0, 0);
						_board.addEntity(pos, new Grass(i, j, Sprite.grass));
						break;
					case '4':
						_board.addCharacter(new Doll(Coordinates.tileToPixel(i),
								Coordinates.tileToPixel(j) + Game.TILES_SIZE, _board));
						Screen.setOffset(0, 0);
						_board.addEntity(pos, new Grass(i, j, Sprite.grass));
						break;
					//Portal
					case 'x':
						_board.addEntity(pos, new LayeredEntity(i, j,
								new Grass(i, j, Sprite.grass),
								new Portal(i, j, Sprite.portal, _board),
								new Brick(i, j, Sprite.brick)));
						break;
					default:
						_board.addEntity(pos, new Grass(i, j, Sprite.grass));
				}
			}
		}
	}

}
