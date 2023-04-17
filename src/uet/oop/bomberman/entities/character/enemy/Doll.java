package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium;
import uet.oop.bomberman.graphics.Sprite;

public class Doll extends Enemy {
	
    /**
     * Khởi tạo đối tượng Doll có tham số x y board
     * @param x vị trí trên bản đồ 
     * @param y vị trí trên bản đồ
     * @param board bảng điều khiển trò chơi
     */
	public Doll(int x, int y, Board board) {
		super(x, y, board, Sprite.doll_dead, Game.getBomberSpeed(), 200);
		_sprite = Sprite.doll_left1;
		_ai = new AIMedium(_board, this);
		_direction  = _ai.calculateDirection();
	}
	
	/**
	 * Phương thức này được sử dụng để chọn sprite cho đối tượng Doll hiện tại dựa trên hướng di chuyển và trạng thái di chuyển hiện tại của nó.
	 * Nó cập nhật biến sprite của đối tượng Doll với sprite thích hợp.
	 * @return none
	 */
	@Override
	protected void chooseSprite() {
        Sprite movingSprite = null;
		switch(_direction) {
			case 0:
			case 1:
			// Nếu Doll đang di chuyển sang phải
				if(_moving) {
					// Đặt sprite thành sprite di chuyển với hình ảnh cho di chuyển sang phải
                    movingSprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, _animate, 60);
                }
				else {
					// Đặt sprite thành sprite đứng yên hướng phải
                    movingSprite = Sprite.doll_left1;
                }
				break;
			case 2:
			case 3:
			// Nếu Doll đang di chuyển sang trái
				if(_moving) {
					// Đặt sprite thành sprite di chuyển với hình ảnh cho di chuyển sang trái
                    movingSprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, _animate, 60);
                }
				else {
					// Đặt sprite thành sprite đứng yên hướng trái
                    movingSprite = Sprite.doll_left1;
                }
                    
				break;
		}
		// Cập nhật biến sprite của đối tượng Doll
        _sprite = movingSprite;
	}
}
