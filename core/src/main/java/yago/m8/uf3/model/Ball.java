package yago.m8.uf3.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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
    public void update(Paddle paddle) {
        x += xSpeed;
        y += ySpeed;
        if ((x + size) > Gdx.graphics.getWidth() || (x-size) < 0) {
            xSpeed = -xSpeed;
        }
        if ((y + size) > Gdx.graphics.getHeight() || (y-size) < 0) {
            ySpeed = -ySpeed;
        }
        checkCollision(paddle);
    }

    public void checkCollision(Paddle paddle) {
        if(collidesWith(paddle)){
            ySpeed = -ySpeed;
        }
    }
    private boolean collidesWith(Paddle paddle) {
        if((y-size)<=(paddle.y+paddle.height) && (y+size)>=(paddle.y + paddle.height)){
            if((x-size)>=(paddle.x)&&(x+size)<=(paddle.x+paddle.width)){
                return  true;
            }
        }
        return  false;
    }

    public void draw(ShapeRenderer shape) {
        shape.setColor(color);
        shape.circle(x, y, size);
    }
}
