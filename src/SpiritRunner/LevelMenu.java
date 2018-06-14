package SpiritRunner;

import java.io.*;
import java.util.ArrayList;

public class LevelMenu extends Menu{

    private static int levelNumber = 0;
    private String highscoresPath;
    private ArrayList<Integer> highscores = new ArrayList<Integer>();
    public static ArrayList<Double> jumps;
    private static int noButtonsX = 5, noButtonsY = 3;

    public LevelMenu() throws IOException {
        //highscoresPath = new File("").getAbsolutePath();
        highscoresPath = "X:/Gierka"; //TODO Something wrong with absolute path reading;
        highscoresPath = highscoresPath.replace('\\', '/');
        highscoresPath = highscoresPath + "/src/SpiritRunner/data/highscores.txt";
        loadHighscores();
        noButtons = noButtonsX*noButtonsY;
        buttons = new ArrayList<>();
        for (int i = 0; i < noButtonsY; i++)
            for(int j = 0; j < noButtonsX; j++)
                buttons.add(new Button(Main.getGameWidth() / noButtonsX * j + 30, Main.getGameHeight() / noButtonsY * i + 20, Main.getGameWidth() / 8, Main.getGameHeight() / 8, String.format("%02d", i * noButtonsX + j + 1)));
    }

    public void pressButton(int mousePosX, int mousePosY) {
        for (int i = 0; i < highscores.size() + 1; i++)
            if (buttons.get(i).pressButton(mousePosX,mousePosY)){
                levelNumber = i+1;
                reset(levelNumber, false);
            }
    }

    public void reset(int levelNumber, boolean replay) {
        Main.setLevelStart(false);
        Main.getPlayer().reset();
        Main.getLevel().reset();
        Main.getBg1().reset(0,0, Main.getBg1().getParallax());
        Main.getBg2().reset(Main.getBg1().getWidth(), 0, Main.getBg2().getParallax());
        Main.getBg2_1().reset(0,0, Main.getBg2_1().getParallax());
        Main.getBg2_2().reset(Main.getBg2_1().getWidth(), 0, Main.getBg2_2().getParallax());
        Main.getLevel().start(levelNumber);
        if (!replay) {
            jumps = new ArrayList<>();
            Main.setGameState(Main.GameState.GAME);
        } else {
            Main.setGameState(Main.GameState.REPLAY);
        }
    }

    public void loadHighscores() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(highscoresPath));
        while (true) {
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }
            //System.out.println(line);
            highscores.add(Integer.parseInt(line));
        }
    }

    public void updateHighscores(int score) throws IOException {
        if (score > highscores.get(levelNumber-1)) {
            highscores.set(levelNumber-1, score);
            try {
                FileWriter fw = new FileWriter(highscoresPath);
                BufferedWriter out = new BufferedWriter(fw);
                for(int s : highscores) {
                    out.write(String.valueOf(s));
                    out.write(System.lineSeparator());
                }
                out.flush();
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void reset(boolean replay) {
        reset(levelNumber, replay);
    }
    public void resetInfinite() {
        reset(0, false);
        Main.setGameState(Main.GameState.INFINITEGAME);
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

    public static int getLevelNumber() { return levelNumber; }
    public static void setLevelNumber(int _levelNumber) { levelNumber = _levelNumber; }
}
