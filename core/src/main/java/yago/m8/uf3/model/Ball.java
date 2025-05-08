package yago.m8.uf3.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;
import java.util.Iterator;

public class Ball {
    int x;
    int y;
    int size;
    int xSpeed;
    int ySpeed;
    Color color = Color.WHITE;

    public Ball(int x, int y, int size, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public void update(Paddle paddle, ArrayList<Block> blocks) {
        x += xSpeed;
        y += ySpeed;

        // Colisión con bordes de la pantalla
        if ((x + size) > Gdx.graphics.getWidth() || (x - size) < 0) {
            xSpeed = -xSpeed;
        }
        if ((y + size) > Gdx.graphics.getHeight() || (y - size) < 0) {
            ySpeed = -ySpeed;
        }

        // Comprobar colisión con paddle
        checkPaddleCollision(paddle);

        // Comprobar colisión con los bloques
        checkBlocksCollision(blocks);
    }

    public void checkPaddleCollision(Paddle paddle) {
        if(collidesWith(paddle)){
            ySpeed = Math.abs(ySpeed);
        }
    }

    public void checkBlocksCollision(ArrayList<Block> blocks) {
        for (Block block : blocks) {
            if (!block.alomostDestroyed && collidesWithBlock(block)) {
                block.alomostDestroyed = true;
                ySpeed = -ySpeed;
                break;
            } else if (!block.destroyed && collidesWithBlock(block) && block.alomostDestroyed) {
                block.destroyed = true;
                ySpeed = -ySpeed;
                break;
            }
        }
    }


    private boolean collidesWith(Paddle paddle) {
        if ((y - size) <= (paddle.y + paddle.height) && (y + size) >= paddle.y) {
            if ((x + size) >= paddle.x && (x - size) <= (paddle.x + paddle.width)) {
                return true;
            }
        }
        return false;
    }

    private boolean collidesWithBlock(Block block) {
        return (x + size >= block.x && x - size <= block.x + block.width &&
            y + size >= block.y && y - size <= block.y + block.height);
    }

    public void draw(ShapeRenderer shape) {
        shape.setColor(color);
        shape.circle(x, y, size);
    }
}
