package fr.pgah;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Face {
  public static final int WIDTH = 83;
  public static final int HEIGHT = 95;
  private static BitmapFont scorefont;
  private String name;
  private Texture texture;
  private Rectangle bounds;

  public Face(String name) {
    this.name = name;
    this.texture = new Texture(name + ".png");
    Face.scorefont = new BitmapFont();
    scorefont.getData().setScale(3f);
  }

  public void setBounds(Rectangle bounds) {
    this.bounds = bounds;
  }

  public boolean contains(Vector2 point) {
    return bounds.contains(point);
  }

  public void draw(SpriteBatch batch) {
    batch.draw(texture, bounds.getX(), bounds.getY(), WIDTH, HEIGHT);
  }

  public void drawPlus5(SpriteBatch batch) {
    scorefont.draw(batch, "+5", bounds.getX(), bounds.getY() + HEIGHT);
  }

  public void dispose() {
    texture.dispose();
    scorefont.dispose();
  }

  public boolean estMario() {
    return name.equals("mario");
    // if (name.equals("mario")) {
    // return true;
    // } else {
    // return false;
    // }
  }
}
