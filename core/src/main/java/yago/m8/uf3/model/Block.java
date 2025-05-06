package yago.m8.uf3.model;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Block {
    int x;
    int y;
    int width;
    int height;
    public boolean destroyed = false;

    public Block(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(   ShapeRenderer shape){
        shape.rect(x, y, width, height);
    }
}
