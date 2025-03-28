package fr.pgah;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Face {
  public static final int WIDTH = 83;
  public static final int HEIGHT = 95;
  private BitmapFont scorefont;
  private Texture texture;
  private Rectangle bounds;

  public Face(String txtName) {
    this.texture = new Texture(txtName);
    this.scorefont = new BitmapFont();
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
}
