package SpiritRunner;

public class Background extends Object {
    private double parallax; // higher parallax means slower speed of the background

    public Background(int x, int y, double parallax){
        width = 800;
        height = Main.getGameHeight();
        posX = (double)x;
        posY = (double)y;
        speedX = 0;
        speedY = 0;
        this.parallax = parallax;
    }

    public void reset(int x, int y, double parallax){
        width = 800;
        height = Main.getGameHeight();
        posX = (double)x;
        posY = (double)y;
        speedX = 0;
        speedY = 0;
        this.parallax = parallax;
    }

    public void update() {
        if (posX - Main.getScroll()/parallax <= -width){
            posX = width + Main.getScroll()/parallax;
        }
    }
    public double getParallax() {
        return parallax;
    }
}
