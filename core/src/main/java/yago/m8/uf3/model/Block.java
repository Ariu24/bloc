package yago.m8.uf3.model;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

public class Block {
    int x;
    int y;
    int width;
    int height;
    public boolean destroyed = false;
    public boolean alomostDestroyed = false;
    Color color = Color.WHITE;

    public Block(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(ShapeRenderer shape) {
        Color c = this.alomostDestroyed ? Color.GRAY : Color.WHITE;
        shape.setColor(c);
        shape.rect(x, y, width, height);
    }

}
