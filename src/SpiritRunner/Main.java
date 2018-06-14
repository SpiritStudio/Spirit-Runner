package SpiritRunner;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class Main extends Applet implements Runnable, KeyListener, MouseListener {
    public enum GameState {
        MAINMENU, LEVELMENU, GAME, INFINITEGAME, EXIT, GAMEOVERMENU, LEVELPASSEDMENU, REPLAY
    }

    private static GameState gameState = GameState.MAINMENU;

    private static int gameWidth = 800, gameHeight = 480;

    private Image image, background, background2, logo, playButton, infiniteModeButton, exitButton;

    private static Animation character;

    private MainMenu mainMenu;
    private static LevelMenu levelMenu;
    private GameOverMenu gameOverMenu;
    private LevelPassedMenu levelPassedMenu;

    public static int noTiles = 6;
    public static int noObjects = 3;
    public static int noDecorations = 2;

    public static Image tiles[], objects[];

    private Graphics second;
    private static Font gameFont = new Font("Haettenschweiler", Font.PLAIN, 28);
    private static Font gameFontSmall = new Font("Haettenschweiler", Font.PLAIN, 20);
    private String baseString;
    private static Background bg1, bg2, bg2_1, bg2_2;
    private static int scroll = 0, scrollSpeed = 0;

    private static boolean levelStart = false;

    private static Level level;
    private static Player player;

    private static double time = System.currentTimeMillis(), timeForJumps = System.currentTimeMillis();

    @Override
    public void init() {
        setSize(gameWidth, gameHeight);

        scroll = 0;
        scrollSpeed = 0;
        tiles = new Image[noTiles];
        objects = new Image[noObjects + noDecorations];
        character = new Animation();

        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        Frame frame = (Frame) this.getParent().getParent();
        frame.setTitle("Spirit Runner");
        //baseString = new File("").getAbsolutePath();
        baseString = "X:/Gierka"; //TODO Something wrong with absolute path reading;
        baseString = baseString.replace('\\', '/');
        baseString = "file:/" + baseString + "/src/SpiritRunner/"; // TODO YOU KNOW WHAT
        try {
            logo = getImage(new URL(baseString + "/data/splash.png"));
            playButton = getImage(new URL(baseString + "/data/playnow.png"));
            infiniteModeButton = getImage(new URL(baseString + "/data/infintemode.png"));
            exitButton = getImage(new URL(baseString + "/data/exit.png"));
            background = getImage(new URL(baseString + "/data/background.png"));
            background2 = getImage(new URL(baseString + "/data/background2.png"));

            //Animation class description in its own source code (first addState, then addFrame)
            //1st state - staying, 2nd state - running, 3rd state - jumping, 4th state - hanging
            character.addState(2, 30);
            character.addFrame(getImage(new URL(baseString + "/data/character.png")));
            character.addFrame(getImage(new URL(baseString + "/data/character_standing2.png")));
            character.addState(3, 4);  //running state
            character.addFrame(getImage(new URL(baseString + "/data/character.png")));
            character.addFrame(getImage(new URL(baseString + "/data/character2.png")));
            character.addFrame(getImage(new URL(baseString + "/data/character3.png")));
            character.addState(1, 60); // jumping state
            character.addFrame(getImage(new URL(baseString + "data/character_jumping.png")));
            character.addState(2, 10); // hanging state
            character.addFrame(getImage(new URL(baseString + "data/character_hanging.png")));
            character.addFrame(getImage(new URL(baseString + "data/character_hanging2.png")));

            character.setState(0);

            for (int i = 0; i < noTiles; i++) {
                tiles[i] = getImage(new URL(baseString + "/data/tile"+Integer.toString(i+1)+".png"));
            }
            for (int i = 0; i < noObjects + noDecorations; i++) {
                objects[i] = getImage(new URL(baseString + "/data/object"+Integer.toString(i+1)+".png"));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public void start() {
        mainMenu = new MainMenu();
        try {
            levelMenu = new LevelMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameOverMenu = new GameOverMenu();
        levelPassedMenu = new LevelPassedMenu();
        bg1 = new Background(0, 0, 4.);
        bg2 = new Background(bg1.getWidth(), 0, 4.);
        bg2_1 = new Background(0, 0, 3.);
        bg2_2 = new Background(bg2_1.getWidth(), 0, 3.);
        level = new Level();
        player = new Player();
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    public void run() {
        while (gameState != GameState.EXIT) {
            time = System.nanoTime();
            if (gameState == GameState.GAME || gameState == GameState.INFINITEGAME || gameState == GameState.REPLAY) {
                if (player.getPosX() >= level.getWidth()*Tile.getTileWidth() - player.getWidth()){
                    gameState = GameState.LEVELPASSEDMENU;
                    try { levelMenu.updateHighscores(player.getScore()); }
                    catch (IOException e) { e.printStackTrace(); }
                } else {
                    scroll = (int) player.getPosX() - 30;

                    if (scroll < 0) scroll = 0;
                    if (scroll > level.getWidth() * Tile.getTileWidth() - gameWidth)
                        scroll = Tile.getTileWidth() * level.getWidth() - gameWidth;

                    bg1.update();
                    bg2.update();
                    bg2_1.update();
                    bg2_2.update();
                    level.update(player);
                    player.update(gameState==GameState.REPLAY);

                    character.update();
                }
            }
            else if (gameState == GameState.MAINMENU) {
                mainMenu.update();
            }
            else if (gameState == GameState.LEVELMENU){
                levelMenu.update();
            }
            else if (gameState == GameState.LEVELPASSEDMENU){
                levelPassedMenu.update();
            }
            else if (gameState == GameState.GAMEOVERMENU){
                gameOverMenu.update();
            }

            if (gameState == GameState.INFINITEGAME && levelStart) {
                player.setSpeedX(Player.initialSpeed + (player.getPosX()/2000));
            }

            repaint();
            time = (System.nanoTime() - time)/1000000.0;
            //System.out.println("Time for one frame: " + Double.toString(time));
            try {
                  if (time < 17.0)
                         Thread.sleep((int) (17.0 - time));
                  else
                         Thread.sleep((int) (17.0));
            } catch (InterruptedException e) {
                  e.printStackTrace();
            }
        }
        System.exit(0); //TODO IS THIS A GOOD IDEA?
    }

    @Override
    public void update(Graphics g) {
        if (image == null) {
            image = createImage(this.getWidth(), this.getHeight());
            second = image.getGraphics();
        }

        second.setColor(getBackground());
        second.fillRect(0, 0, getWidth(), getHeight());
        second.setColor(getForeground());
        paint(second);

        g.drawImage(image, 0, 0, this);
    }

    @Override
    public void paint(Graphics g) {
        if (gameState == GameState.GAME || gameState == GameState.INFINITEGAME || gameState == GameState.REPLAY) {
            g.drawImage(background, (int) bg1.getPosX() - (int)(scroll/bg1.getParallax()), (int) bg1.getPosY(), this);
            g.drawImage(background, (int) bg2.getPosX() - (int)(scroll/bg2.getParallax()), (int) bg2.getPosY(), this);
            g.drawImage(background2, (int) bg2_1.getPosX() - (int)(scroll/bg2_1.getParallax()), (int) bg2_1.getPosY(), this);
            g.drawImage(background2, (int) bg2_2.getPosX() - (int)(scroll/bg2_2.getParallax()), (int) bg2_2.getPosY(), this);
            paintLevel(g);
            character.paint(g, (int)player.getPosX() - scroll, (int)player.getPosY(), this);
            g.setColor(Color.WHITE);
            g.drawString(String.format("Coins: %04d", player.getScore()), gameWidth-120, 30);
            g.drawString(String.format("Beers: %02d", player.getBeerCounter()), gameWidth-105, 70);
        }
        else if (gameState == GameState.MAINMENU) {
            // TODO - menu drawing
            g.drawImage(logo, 0, 0, this);
            paintButtons(g, mainMenu);
        }
        else if (gameState == GameState.LEVELMENU){
            paintButtons(g, levelMenu);
        }
        else if (gameState == GameState.GAMEOVERMENU) {
            paintButtons(g, gameOverMenu);
        }
        else if (gameState == GameState.LEVELPASSEDMENU){
            paintButtons(g, levelPassedMenu);
        }
    }

    private void paintLevel(Graphics g) {
        for (int i = 0; i < level.getTilearray().size(); i++) {
            Tile t = (Tile) level.getTilearray().get(i);
            g.drawImage(t.getTileImage(), (int)t.getPosX() - scroll, (int)t.getPosY(), this);
        }
        for (int i = 0; i < level.getObjectarray().size(); i++) {
            CollidableObject o  = (CollidableObject) level.getObjectarray().get(i);
            g.drawImage(o.getImage(), (int)o.getPosX() - scroll, (int)o.getPosY(), this);
        }
        for (int i = 0; i < level.getDecorationarray().size(); i++) {
            Decoration d = (Decoration) level.getDecorationarray().get(i);
            g.drawImage(d.getImage(), (int)d.getPosX() - scroll, (int)d.getPosY(), this);
        }
    }

    private void paintButtons (Graphics g, Menu menu) {
        for (int i = 0; i < menu.getNoButtons(); i++) {
            Button b = menu.getButtons().get(i);
            g.setColor(new Color(0.7f,0f,0f,.5f ));
            g.fillRect((int)b.getPosX(), (int)b.getPosY(), (int)b.getWidth(), (int)b.getHeight());
            g.setColor(Color.BLACK);
            g.setFont(gameFont);
            printCenteredString(g, b.getText(), b.getWidth(), (int)b.getPosX(), (int)b.getPosY()+34);
            g.setColor(Color.WHITE);
            printCenteredString(g, b.getText(), b.getWidth(), (int)b.getPosX(), (int)b.getPosY()+30);

        }
    }

    private void paintButtons (Graphics g, LevelMenu menu) {
        for (int i = 0; i < menu.getNoButtons(); i++) {
            Button b = menu.getButtons().get(i);
            if (i <= menu.getHighscores().size())
                g.setColor(new Color(0.7f,0f,0f,.5f ));
            else
                g.setColor(new Color(0.2f,0f,0f,.5f ));
            g.fillRect((int)b.getPosX(), (int)b.getPosY(), (int)b.getWidth(), (int)b.getHeight());
            g.setColor(Color.BLACK);
            g.setFont(gameFont);
            printCenteredString(g, b.getText(), b.getWidth(), (int)b.getPosX(), (int)b.getPosY()+36);
            g.setColor(Color.WHITE);
            printCenteredString(g, b.getText(), b.getWidth(), (int)b.getPosX(), (int)b.getPosY()+30);

            if (i < menu.getHighscores().size()) {
                g.setFont(gameFontSmall);
                g.setColor(Color.BLACK);
                printCenteredString(g, String.format("%04d", menu.getHighscores().get(i)), b.getWidth(), (int) b.getPosX(), (int) b.getPosY() + 59);
                g.setColor(Color.WHITE);
                printCenteredString(g, String.format("%04d", menu.getHighscores().get(i)), b.getWidth(), (int) b.getPosX(), (int) b.getPosY() + 55);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            //    scrollSpeed = -8;
                break;

            case KeyEvent.VK_UP:
                if (levelStart) {
                    player.jump();
                    LevelMenu.jumps.add(System.currentTimeMillis()-timeForJumps);
                }
                else {
                    player.setSpeedX(Player.initialSpeed);
                    levelStart = true;
                    timeForJumps = System.currentTimeMillis();
                }
                break;

            case KeyEvent.VK_DOWN:
                /*if (player.isHanging()) {
                    player.setInAir(true);
                    player.setPosY(player.getPosY() + 1);
                }*/
                break;

            case KeyEvent.VK_RIGHT:
            //    scrollSpeed = 8;
                break;

            case KeyEvent.VK_ESCAPE:
                gameState = GameState.MAINMENU;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                scrollSpeed = 0;
                break;

            case KeyEvent.VK_RIGHT:
                scrollSpeed = 0;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (gameState == GameState.MAINMENU) {
            mainMenu.pressButton(e.getX(), e.getY());
        }
        else if (gameState == GameState.LEVELMENU){
            levelMenu.pressButton(e.getX(), e.getY());
        }
        else if (gameState == GameState.GAMEOVERMENU){
            gameOverMenu.pressButton(e.getX(), e.getY());
        }
        else if (gameState == GameState.LEVELPASSEDMENU){
            levelPassedMenu.pressButton(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public static Background getBg1() {
        return bg1;
    }
    public static Background getBg2() {
        return bg2;
    }
    public static Background getBg2_1() {
        return bg2_1;
    }
    public static Background getBg2_2() {
        return bg2_2;
    }

    public static int getScroll() {
        return scroll;
    }
    public static int getNoTiles() {
        return noTiles;
    }

    public static GameState getGameState() {
        return gameState;
    }
    public static void setGameState(GameState gameState) {
        Main.gameState = gameState;
    }

    public static int getGameHeight() {
        return gameHeight;
    }
    public static int getGameWidth() {
        return gameWidth;
    }
    public static Level getLevel() {
        return level;
    }
    public static Player getPlayer() {
        return player;
    }
    public static boolean isLevelStart() {
        return levelStart;
    }
    public static void setLevelStart(boolean levelStart) {
        Main.levelStart = levelStart;
    }
    public static LevelMenu getLevelMenu() {
        return levelMenu;
    }
    public static int getNoDecorations() { return noDecorations; }
    public static int getNoObjects() { return noObjects; }
    public static double getTimeForJumps() { return timeForJumps;   }
    public static Animation getCharacter() { return character; }
    public static boolean getLevelStart() {return levelStart;}

    private void printCenteredString(Graphics g, String s, int width, int XPos, int YPos){
        int stringLen = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        int start = width/2 - stringLen/2;
        g.drawString(s, start + XPos, YPos);
    }


}