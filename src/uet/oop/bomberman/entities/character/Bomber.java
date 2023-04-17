package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.utility.SoundManager;

import java.util.Iterator;
import java.util.List;


public class Bomber extends Character {

    private List<Bomb> _bombs;
    protected Keyboard _input;

    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo,
     * cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update()
     */
    protected int _timeBetweenPutBombs = 0;

    public SoundManager _bomberDieSound =  new SoundManager("sound/just_died.wav",0);

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        _bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right;
    }

    @Override
    public void update() {
        clearBombs();
        if (!_alive) {
            afterKill();
            return;
        }

        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--;

        animate();

        calculateMove();

        detectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (_alive) {
            chooseSprite();
        } else {
            if(_timeAfter > 0) {
                _sprite = Sprite.player_dead1;
                _animate = 0;
            } else {
                _sprite = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, _animate, 60);
            }
        }
        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }

    /**
     * Kiểm tra xem có đặt được bom hay không? nếu có thì đặt bom tại vị trí hiện tại của Bomber
     */
    private void detectPlaceBomb() {
        if ((_input.space) && (_timeBetweenPutBombs < 0) && (Game.getBombRate() > 0)) {
            placeBomb(this.getXTile(), this.getYTile());
            _timeBetweenPutBombs = 20;
            Game.addBombRate(-1);
        }
    }

    protected void placeBomb(int x, int y) {
        Bomb b = new Bomb(x, y, _board);
        _board.addBomb(b);
    }

    private void clearBombs() {
        Iterator<Bomb> bs = _bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    @Override
    public void kill() {
        if (!_alive) return;
        _alive = false;
        _bomberDieSound.play(1);
    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        else {
            _board.endGame();
        }
    }

    @Override
    protected void calculateMove() {
        int x = 0, y = 0;
        if (_input.up) y--;//len
        if (_input.down) y++;//xuong
        if (_input.left) x--;//trai
        if (_input.right) x++;//phai

        if (x!=0||y!=0)
        {
            move(x* Game.getBomberSpeed(), y *Game.getBomberSpeed());
            _moving = true;
        } else _moving = false;
    }

    @Override
    public boolean canMove(double x, double y) {
        for (int c = 0; c<4; c ++){
            double xt = ((_x+x) + c % 2 * 11) / 16;
            double yt = ((_y+y) + c / 2 * 12 - 13) / 16;

            Entity a = _board.getEntity(xt,yt,this);
            if (! a.collide(this)) return false;
        }
        return true;
    }

    @Override
    public void move(double xa, double ya) {
        if (canMove(xa, ya)) {
            _x += xa;
            _y += ya;
        }
        if (xa > 0) {
            _direction = 1;
        } else if (xa < 0) {
            _direction = 3;
        } else if (ya > 0) {
            _direction = 2;
        } else {
            _direction = 0;
        }
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Flame || e instanceof Enemy){
            kill();
        }

        return true;
    }

    private void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.player_up;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }
}
