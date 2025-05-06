package yago.m8.uf3.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle {
    int x;
    int y;
    int height;
    int width;

    public Paddle() {
        this.y = 25;
        this.height = 50;
        this.width = 300;
        this.x = Gdx.input.getX();
    }
    public void update() {
        this.x = Gdx.input.getX();
    }
    public void draw(ShapeRenderer shape) {
        shape.rect(x, y, 300, 50);
    }
}
