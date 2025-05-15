package yago.m8.uf3.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import yago.m8.uf3.model.Ball;
import yago.m8.uf3.model.Block;
import yago.m8.uf3.model.Paddle;


public class GameScreen implements Screen {
    private Game game;
    ShapeRenderer shape;
    List<Ball> balls;
    Paddle paddle;
    ArrayList<Block> blocks = new ArrayList<>();
    Random r = new Random();
    public static Music MUSIC;
    Sound winS;

    public GameScreen(Game game) {
        shape = new ShapeRenderer();
        balls = new ArrayList<>();
        paddle = new Paddle();
        winS = Gdx.audio.newSound(Gdx.files.internal("Win.mp3"));
        MUSIC = Gdx.audio.newMusic(Gdx.files.internal("GameScreen.mp3"));
        MUSIC.setLooping(true);
        MUSIC.play();
        // Velocidad reducida para evitar que la pelota atraviese bloques
        balls.add(new Ball(1050, 250, 50, 8, 5, false, Color.WHITE));

        int blockWidth = 210;
        int blockHeight = 50;
        for (int y = Gdx.graphics.getHeight() / 2; y < Gdx.graphics.getHeight(); y += blockHeight + 10) {
            for (int x = 0; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
                if(r.nextInt()%2==0){
                    blocks.add(new Block(x, y, blockWidth, blockHeight,true));
                } else {
                    blocks.add(new Block(x, y, blockWidth, blockHeight,false));
                }
            }
        }
        this.game = game;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        paddle.update();
        removeDestroyedBlocks();
        List<Ball> newBalls = new ArrayList<>();
        for (Ball ball : balls) {
            ball.update(paddle, blocks);
            if (ball.checkBlocksCollision(blocks)) {
                Ball newBall = new Ball(ball.x, ball.y, 50, 8, 6, true, Color.VIOLET);
                newBalls.add(newBall);
            }
        }
        balls.addAll(newBalls);

        shape.begin(ShapeRenderer.ShapeType.Filled);

        if (blocks.isEmpty()) {
            Gdx.app.postRunnable(() -> {
                winS.play();
                game.setScreen(new WinScreen(game));
                dispose();
            });
        }

        for (Block block : blocks) {
            block.draw(shape);
        }

        paddle.draw(shape);

        for (Ball ball : balls) {
            ball.draw(shape);
        }

        shape.end();
    }

    private void removeDestroyedBlocks() {
        Iterator<Block> iterator = blocks.iterator();
        while (iterator.hasNext()) {
            Block block = iterator.next();
            if (block.destroyed) {
                iterator.remove();
            }
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        shape.dispose();
        MUSIC.dispose();
        winS.dispose();
    }
}
