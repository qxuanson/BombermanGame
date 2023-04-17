package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Screen;


public class Flame extends Entity {

	protected Board _board; // đối tượng Board hiện tại của game.
	protected int _direction; // hướng của Flame, có giá trị từ 0 đến 3 tương ứng với các hướng lên, phải, xuống, trái.
	private int _radius; // bán kính cực đại của Flame.
	protected int xOrigin, yOrigin; // tọa độ ban đầu của Flame.

	public FlameSegment[] _flameSegments = new FlameSegment[0];

	/**
	 *
	 * @param x hoành độ bắt đầu của Flame
	 * @param y tung độ bắt đầu của Flame
	 * @param direction là hướng của Flame
	 * @param radius độ dài cực đại của Flame
	 */
	public Flame(int x, int y, int direction, int radius, Board board) {
		xOrigin = x;
		yOrigin = y;
		_x = x;
		_y = y;
		_direction = direction;
		_radius = radius;
		_board = board;
		createFlameSegments(); // phương thức dùng để tạo ra các FlameSegment, mỗi segment ứng với một đơn vị độ dài của Flame.
	}

	/**
	 * Tạo các FlameSegment, mỗi segment ứng một đơn vị độ dài
	 */
	private void createFlameSegments() {
		/**
		 * tính toán độ dài Flame, tương ứng với số lượng segment
		 */
		// xóa tất cả các segment có trong danh sách sử dụng trong trường hợp cần cập nhật lại danh sách
		/**
		 * tính toán độ dài Flame, tương ứng với số lượng segment
		 */
		_flameSegments = new FlameSegment[calculatePermitedDistance()];

		/**
		 * biến last dùng để đánh dấu cho segment cuối cùng
		 */
		boolean last;

		int x = (int)_x;
		int y = (int)_y;
		for (int i = 0; i < _flameSegments.length; i++)
		{
			if (i ==_flameSegments.length - 1) last = true;
			else last = false;

			switch (_direction)
			{
				case 0: y--; break;
				case 1: x++; break;
				case 2: y++; break;
				case 3: x--; break;
			}

			_flameSegments[i]= new FlameSegment(x,y,_direction,last);
		}
	}

	/**
	 * Tính toán độ dài của Flame, nếu gặp vật cản là Brick/Wall, độ dài sẽ bị cắt ngắn
	 * @return
	 */
	private int calculatePermitedDistance() {
		int permitedDistance = 0;
		int x = (int) _x, y = (int) _y;
		// kiểm tra từng vị trí trên đường Flame
		while (permitedDistance < _radius) {

			switch (_direction) {
				case 0:
					y--;
					break;
				case 1:
					x++;
					break;
				case 2:
					y++;
					break;
				case 3:
					x--;
					break;
			}
			// nếu gặp vật cản thì dừng lại
			Entity e = _board.getEntity(x, y, null);
			if (e.collide(this) == false) {
				break;
			}
			permitedDistance++;
		}
		return permitedDistance;
	}

	/**
	 * Phương thức trả về segment tại vị trí (x,y) trên màn hình.
	 * @param x
	 * @param y
	 * @return
	 */
	public FlameSegment flameSegmentAt(int x, int y) {
		for (FlameSegment segment : _flameSegments) {
			if (segment.getX() == x && segment.getY() == y) {
				return segment;
			}
		}
		return null;
	}

	@Override
	public void update() {
	}

	@Override
	/**
	 * Hiển thị Flame lên màn hình
	 */
	public void render(Screen screen) {
		for (FlameSegment segment : _flameSegments) {
			segment.render(screen);
		}
	}

	@Override
	/**
	 * Xử lý va chạm với bomber. Đối tượng có vị trí chính là vị trí Bomb đã nổ.
	 */
	public boolean collide(Entity e) {
		return true;
	}

}
