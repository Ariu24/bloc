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
    public int x;
    public int y;
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
        // Actualizar posición en X
        x += xSpeed;

        // Verificar colisiones laterales inmediatamente
        if ((x + size) > Gdx.graphics.getWidth() || (x - size) < 0) {
            xSpeed = -xSpeed;
            x += xSpeed; // Corregir posición inmediatamente
        }

        // Actualizar posición en Y
        y += ySpeed;

        // Verificar colisiones verticales con la pantalla
        if ((y + size) > Gdx.graphics.getHeight()) {
            ySpeed = -ySpeed;
            y += ySpeed; // Corregir posición inmediatamente
        } else if((y - size) < 0){
            if(!this.esCopia){
                gameOverSound.play();
                closeGame(3);
                gameOverSound.dispose();
            } else {
                // Si es una copia, solo rebota en lugar de terminar el juego
                ySpeed = -ySpeed;
                y += ySpeed;
            }
        }

        // Verificar colisión con el paddle
        checkPaddleCollision(paddle);
    }

    public void checkPaddleCollision(Paddle paddle) {
        if(collidesWith(paddle)){
            // Invierte la dirección vertical solo si la pelota viene desde arriba
            if (ySpeed < 0) {
                ySpeed = -ySpeed;
                // Reposicionar la pelota justo encima del paddle para evitar que quede atrapada
                y = paddle.y + paddle.height + size;
            }
        }
    }

    // Método original para verificar si debe crear un powerup
    public boolean checkBlocksCollision(ArrayList<Block> blocks) {
        boolean createPowerUp = false;

        for (Block block : blocks) {
            if (!block.destroyed && collidesWithBlock(block)) {
                if (!block.alomostDestroyed) {
                    // Primera colisión con el bloque
                    block.alomostDestroyed = true;
                    ySpeed = -ySpeed; // Invertir dirección
                } else {
                    // Segunda colisión, bloque destruido
                    block.destroyed = true;
                    ySpeed = -ySpeed; // Invertir dirección

                    // 50% de probabilidad de crear powerup
                    if (r.nextInt(100) < 50) {
                        createPowerUp = true;
                    }
                }
                // Salir del bucle después de encontrar una colisión
                return createPowerUp;
            }
        }
        return createPowerUp;
    }

    // Verificar colisiones con bloques en el eje X
    public boolean checkBlocksCollisionX(ArrayList<Block> blocks) {
        for (Block block : blocks) {
            if (!block.destroyed && collidesWithBlockX(block)) {
                xSpeed = -xSpeed;
                x += xSpeed; // Corregir posición inmediatamente

                if (!block.alomostDestroyed) {
                    block.alomostDestroyed = true;
                } else {
                    block.destroyed = true;
                }
                return true;
            }
        }
        return false;
    }

    // Verificar colisiones con bloques en el eje Y
    public boolean checkBlocksCollisionY(ArrayList<Block> blocks) {
        for (Block block : blocks) {
            if (!block.destroyed && collidesWithBlockY(block)) {
                ySpeed = -ySpeed;
                y += ySpeed; // Corregir posición inmediatamente

                if (!block.alomostDestroyed) {
                    block.alomostDestroyed = true;
                } else {
                    block.destroyed = true;
                }
                return true;
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

    // Detección de colisión específica para el eje X
    private boolean collidesWithBlockX(Block block) {
        boolean colisionHorizontal =
            ((x + size) >= block.x && (x - size) <= (block.x + block.width)) &&
                ((y + size) > block.y && (y - size) < (block.y + block.height));

        // Verificar si la colisión es principalmente horizontal
        boolean colisionDesdeIzquierda = (x + size) >= block.x && (x + size - Math.abs(xSpeed)) < block.x;
        boolean colisionDesdeDerecha = (x - size) <= (block.x + block.width) && (x - size + Math.abs(xSpeed)) > (block.x + block.width);

        return colisionHorizontal && (colisionDesdeIzquierda || colisionDesdeDerecha);
    }

    // Detección de colisión específica para el eje Y
    private boolean collidesWithBlockY(Block block) {
        boolean colisionVertical =
            ((y + size) >= block.y && (y - size) <= (block.y + block.height)) &&
                ((x + size) > block.x && (x - size) < (block.x + block.width));

        // Verificar si la colisión es principalmente vertical
        boolean colisionDesdeArriba = (y - size) <= (block.y + block.height) && (y - size + Math.abs(ySpeed)) > (block.y + block.height);
        boolean colisionDesdeAbajo = (y + size) >= block.y && (y + size - Math.abs(ySpeed)) < block.y;

        return colisionVertical && (colisionDesdeArriba || colisionDesdeAbajo);
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
