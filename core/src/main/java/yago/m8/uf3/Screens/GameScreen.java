package yago.m8.uf3.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

import yago.m8.uf3.model.Ball;
import yago.m8.uf3.model.Block;
import yago.m8.uf3.model.Paddle;

public class GameScreen implements Screen {
    ShapeRenderer shape;
    Ball ball;
    Paddle paddle;
    ArrayList<Block> blocks = new ArrayList<>();
    public GameScreen(SplashScreen atari){
        shape = new ShapeRenderer();
        ball = new Ball(150, 200, 50, 12, 5);
        paddle = new Paddle();
        int blockWidth = 210;
        int blockHeight = 50;
        for (int y = Gdx.graphics.getHeight()/2; y < Gdx.graphics.getHeight(); y += blockHeight + 10) {
            for (int x = 0; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
                blocks.add(new Block(x, y, blockWidth, blockHeight));
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ball.update(paddle);
        paddle.update();
        shape.begin(ShapeRenderer.ShapeType.Filled);
        ball.draw(shape);
        paddle.draw(shape);
        for (Block block : blocks) {
            block.draw(shape);
        }
        shape.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
