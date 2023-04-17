package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.enemy.ai.AILow;
import uet.oop.bomberman.graphics.Sprite;

public class Kondoria extends Enemy {
	
    /**
     * Khởi tạo đối tượng Kondoria có tham số x y board
     * @param x vị trí trên bản đồ 
     * @param y vị trí trên bản đồ
     * @param board bảng điều khiển trò chơi
     */
	public Kondoria(int x, int y, Board board) {
		super(x, y, board, Sprite.kondoria_dead, Game.getBomberSpeed(), 150);
		_sprite = Sprite.kondoria_left1;
		_ai = new AILow();
		_direction  = _ai.calculateDirection();
	}
	
	/**
	 * Phương thức này được sử dụng để chọn sprite cho đối tượng Kondoria hiện tại dựa trên hướng di chuyển và trạng thái di chuyển hiện tại của nó.
	 * Nó cập nhật biến sprite của đối tượng Kondoria với sprite thích hợp.
	 * @return none
	 */
	@Override
	protected void chooseSprite() {
        Sprite movingSprite = null;
		switch(_direction) {
			case 0:
			case 1:
			// Nếu Kondoria đang di chuyển sang phải
				if(_moving) {
					// Đặt sprite thành sprite di chuyển với hình ảnh cho di chuyển sang phải
                    movingSprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, _animate, 60);
                }
				else {
					// Đặt sprite thành sprite đứng yên hướng phải
                    movingSprite = Sprite.kondoria_left1;
                }
				break;
			case 2:
			case 3:
			// Nếu Kondoria đang di chuyển sang trái
				if(_moving) {
					// Đặt sprite thành sprite di chuyển với hình ảnh cho di chuyển sang trái
                    movingSprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, _animate, 60);
                }
				else {
					// Đặt sprite thành sprite đứng yên hướng trái
                    movingSprite = Sprite.kondoria_left1;
                }
                    
				break;
		}
		// Cập nhật biến sprite của đối tượng Kondoria
        _sprite = movingSprite;
	}
}
