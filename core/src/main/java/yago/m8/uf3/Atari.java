package yago.m8.uf3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Game;
import java.util.ArrayList;

import yago.m8.uf3.Screens.GameScreen;
import yago.m8.uf3.Screens.SplashScreen;
import yago.m8.uf3.model.Ball;
import yago.m8.uf3.model.Block;
import yago.m8.uf3.model.Paddle;

public class Atari extends Game {


    @Override
    public void create() {
        setScreen(new SplashScreen(this));
    }


}
