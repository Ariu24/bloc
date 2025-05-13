package yago.m8.uf3.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import yago.m8.uf3.Screens.GameScreen;

public class Ball {
    int x;
    int y;
    int size;
    int xSpeed;
    int ySpeed;
    Color color;
    Random r = new Random();
    boolean esCopia;
    Sound gameOverSound;

    public Ball(int x, int y, int size, int xSpeed, int ySpeed, boolean esCopia, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.esCopia = esCopia;
        this.color = color;
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("GameOver.mp3"));
    }

    public void update(Paddle paddle, ArrayList<Block> blocks) {
        x += xSpeed;
        y += ySpeed;

        // Colisión con bordes de la pantalla
        if ((x + size) > Gdx.graphics.getWidth() || (x - size) < 0) {
            xSpeed = -xSpeed;
        }
        if ((y + size) > Gdx.graphics.getHeight()) {
                ySpeed = -ySpeed;
        } else if((y - size) < 0){
            if(!this.esCopia){
                gameOverSound.play();
                closeGame(3);
                gameOverSound.dispose();
            }
        }
        checkPaddleCollision(paddle);
        checkBlocksCollision(blocks);
    }

    public void checkPaddleCollision(Paddle paddle) {
        if(collidesWith(paddle)){
            ySpeed = Math.abs(ySpeed);
        }
    }

    public boolean checkBlocksCollision(ArrayList<Block> blocks) {
        for (Block block : blocks) {
            if (!block.alomostDestroyed && collidesWithBlock(block)) {
                block.alomostDestroyed = true;
                ySpeed = -ySpeed;
                return false;
            } else if (!block.destroyed && collidesWithBlock(block) && block.alomostDestroyed) {
                block.destroyed = true;
                ySpeed = -ySpeed;
                if(r.nextInt() % 2 == 0){
                    return true;
                }
                return false;
            }
        }
        return false;
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

    public static void closeGame(int seconds) {
        GameScreen.MUSIC.dispose();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Gdx.app.exit();
            }
        }, seconds);
    }
}
