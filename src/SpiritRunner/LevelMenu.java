package SpiritRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LevelMenu extends Menu{

    private int levelNumber = 0;
    private ArrayList<Integer> highscores = new ArrayList<Integer>();
    private static int noButtonsX = 5, noButtonsY = 3;

    public LevelMenu() throws IOException {
        loadHighscores();
        noButtons = noButtonsX*noButtonsY;
        buttons = new ArrayList<>();
        for (int i = 0; i < noButtonsY; i++)
            for(int j = 0; j < noButtonsX; j++)
                buttons.add(new Button(Main.getGameWidth() / noButtonsX * j, Main.getGameHeight() / noButtonsY * i, Main.getGameWidth() / 8, Main.getGameHeight() / 8, String.format("%02d", i * noButtonsX + j + 1)));
    }

    public void pressButton(int mousePosX, int mousePosY) {
        for (int i = 0; i < highscores.size() + 1; i++)
            if (buttons.get(i).pressButton(mousePosX,mousePosY)){
                levelNumber = i;
                reset(levelNumber);
            }
    }

    public void reset(int levelNumber) {
        Main.setLevelStart(false);
        Main.getPlayer().reset();
        Main.getLevel().reset();
        Main.getBg1().reset(0,0, Main.getBg1().getParallax());
        Main.getBg2().reset(Main.getBg1().getWidth(), 0, Main.getBg2().getParallax());
        Main.getBg2_1().reset(0,0, Main.getBg2_1().getParallax());
        Main.getBg2_2().reset(Main.getBg2_1().getWidth(), 0, Main.getBg2_2().getParallax());
        Main.getLevel().start(levelNumber+1);
        Main.setGameState(Main.GameState.GAME);
    }

    public void loadHighscores() throws IOException {
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.replace('\\', '/');
        filePath = filePath + "/src/SpiritRunner/data/highscores.txt";
        System.out.println(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        while (true) {
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }
            System.out.println(line);
            highscores.add(Integer.parseInt(line));
        }
    }

    public void reset() {
        reset(levelNumber);
    }

    public void update() {
        //TODO MENU ANIMATIONS
    }

    public static int getNoButtonsX() {
        return noButtonsX;
    }
    public static int getNoButtonsY() {
        return noButtonsY;
    }
    public ArrayList<Integer> getHighscores() {
        return highscores;
    }

    public int getLevelNumber() { return levelNumber; }
    public void setLevelNumber(int levelNumber) { this.levelNumber = levelNumber; }
}
