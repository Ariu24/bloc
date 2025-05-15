package yago.m8.uf3.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

public class WinScreen implements Screen {
    private Game game;
    private Texture background;
    private SpriteBatch batch;
    private boolean allowClick = false;
    private boolean timerStarted = false;

    public WinScreen(Game game) {
        this.game = game;
    }
    @Override
    public void show() {
        background = new Texture(Gdx.files.internal("pantallawin.png"));
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        if (!timerStarted) {
            timerStarted = true;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    allowClick = true;
                }
            }, 5);
        }

        if (allowClick && Gdx.input.isTouched()) {
            game.setScreen(new SplashScreen(game));
            dispose();
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
        background.dispose();
        batch.dispose();
    }
}
