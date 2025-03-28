package fr.pgah;

import java.util.ArrayList;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class WantedGame extends ApplicationAdapter {

  private SpriteBatch batch;
  // private Face luigi;
  // private Face mario;
  // private Face wario;
  // private Face yoshi;
  private ArrayList<Face> facesList;
  private Texture wantedTxt;
  private Vector2 loseCoord;
  private boolean win;
  private boolean lose;
  private BitmapFont font;
  private Vector2 click;
  private boolean gameInPlay;
  private boolean endGame;
  private Timer timer;
  private int timeRemaining;
  private Vector2 timerCoord;
  private Vector2 levelCoord;
  private int level;
  private Task task;
  ShapeRenderer shapeRenderer;

  @Override
  public void create() {
    facesList = new ArrayList<>();
    facesList.add(new Face("luigi"));
    facesList.add(new Face("mario"));
    facesList.add(new Face("wario"));
    facesList.add(new Face("yoshi"));
    // luigi = new Face("luigi.png");
    // mario = new Face("mario.png");
    // wario = new Face("wario.png");
    // yoshi = new Face("yoshi.png");
    batch = new SpriteBatch();
    // wantedTxt = new Texture("mario.png");
    win = false;
    lose = false;
    gameInPlay = true;
    endGame = false;
    timer = new Timer();
    timeRemaining = 200;
    timerCoord = new Vector2(750, 980);
    levelCoord = new Vector2(20, 980);
    level = 0;
    font = new BitmapFont();
    font.getData().setScale(3f);
    shapeRenderer = new ShapeRenderer();
    generateLevel();

    task = new Task() {
      @Override
      public void run() {
        timeRemaining -= 1;
        if (timeRemaining <= 0) {
          timeRemaining = 0;
          timer.stop();
          endGame = true;
        }
      }
    };
    timer.scheduleTask(task, 1, 1);
  }

  private void generateLevel() {
    level++;
    for (Face f : facesList) {
      f.setBounds(generateRandomBounds());
    }
    // luigi.setBounds(generateRandomBounds());
    // mario.setBounds(generateRandomBounds());
    // wario.setBounds(generateRandomBounds());
    // yoshi.setBounds(generateRandomBounds());
  }

  private Rectangle generateRandomBounds() {
    int x = MathUtils.random(0, 800 - Face.WIDTH);
    int y = MathUtils.random(0, 600 - Face.HEIGHT);
    return new Rectangle(x, y, Face.WIDTH, Face.HEIGHT);
  }

  @Override
  public void render() {
    captureInputs();
    updateState();
    drawNewFrame();
  }

  private void captureInputs() {
    click = null;
    if (Gdx.input.isTouched()) {
      click = new Vector2(Gdx.input.getX(), Gdx.input.getY());
      // il faut "retourner" la coordonnée Y
      click.y = Gdx.graphics.getHeight() - click.y;
    }
  }

  private void updateState() {
    // Évite de mettre à jour le score plusieurs fois par tour
    if (win || lose || endGame) {
      return;
    }

    if (click != null) {
      for (Face f : facesList) {
        if (f.contains(click) && !f.estMario()) {
          lose = true;
          // pour savoir plus tard où afficher "PERDU"
          loseCoord = new Vector2(click.x, click.y);
          timeRemaining -= 5;
          if (timeRemaining <= 0) {
            timeRemaining = 0;
            endGame = true;
          }
        } else {

        }
      }
      if (mario.contains(click)) {
        win = true;
        timeRemaining += 5;
        if (timeRemaining > 50) {
          timeRemaining = 50;
        }
      }
    }
  }

  private void drawNewFrame() {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Efface l'écran

    // Zone du haut - Panel d'informations

    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(Color.FIREBRICK);
    shapeRenderer.rect(0, Gdx.graphics.getHeight() - 400, Gdx.graphics.getWidth(), 400);
    shapeRenderer.end();
    batch.begin();
    font.draw(batch, String.valueOf(timeRemaining), timerCoord.x, timerCoord.y);
    font.draw(batch, String.valueOf(level), levelCoord.x, levelCoord.y);
    batch.draw(wantedTxt, 317, 705, Face.WIDTH * 2, Face.HEIGHT * 2);

    // Zone de jeu

    if (endGame) {
      font.draw(batch, "PERDU !", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
      batch.end();
      return;
    }
    if (!win && !lose && gameInPlay) {
      luigi.draw(batch);
      mario.draw(batch);
      wario.draw(batch);
      yoshi.draw(batch);
    }
    if (lose && !win) {
      String loseMessage = "-5";
      font.draw(batch, loseMessage, loseCoord.x, loseCoord.y);
      mario.draw(batch);
    }
    if (win) {
      mario.drawPlus5(batch);
    }
    if (gameInPlay && (win || lose)) {
      gameInPlay = false;
      timer.stop();
      Timer.schedule(new Task() {
        @Override
        public void run() {
          resetScreen();
          timer.start();
        }
      }, 2);
    }

    batch.end();
  }

  private void resetScreen() {
    win = false;
    lose = false;
    generateLevel();
    gameInPlay = true;
  }

  @Override
  public void dispose() {
    batch.dispose();
    luigi.dispose();
    mario.dispose();
    wario.dispose();
    yoshi.dispose();
    font.dispose();
    shapeRenderer.dispose();
  }
}
