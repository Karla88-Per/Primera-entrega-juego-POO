package main;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// clase que representa al personaje del juego, con movimiento y cambio de imagen segun direccion
public class Player {
    private static final int BASE_SIZE = 250;
    private static final int BASE_WIDTH = 500;
    private static final int BASE_HEIGHT = 400;
    private static final int BASE_SPEED = 5;
    private static final int MAX_SIZE = 400;

    // posicion actual del personaje
    private int x;
    private int y;

    // dimensiones actuales del panel donde se mueve el personaje
    private int panelWidth = BASE_WIDTH;
    private int panelHeight = BASE_HEIGHT;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private Direction currentDirection = Direction.DOWN;
    private Map<Direction, BufferedImage> spriteMap = new HashMap<>();

    public Player() {
        loadSprites();
        resetPosition();
    }

    // carga las imagenes del personaje desde los archivos en el paquete
    private void loadSprites() {
        try {
            spriteMap.put(Direction.UP,    ImageIO.read(getClass().getResourceAsStream("atras_0.png")));
            spriteMap.put(Direction.DOWN,  ImageIO.read(getClass().getResourceAsStream("frente_1.png")));
            spriteMap.put(Direction.LEFT,  ImageIO.read(getClass().getResourceAsStream("izquierda_2.png")));
            spriteMap.put(Direction.RIGHT, ImageIO.read(getClass().getResourceAsStream("derecha_3.png")));
        } catch (IOException e) {
            System.err.println("⚠️ error al cargar las imagenes del jugador");
            e.printStackTrace();
        }
    }

    // se llama cuando el panel cambia de tamaño, ajusta la posicion proporcionalmente
    public void onPanelResized(int newWidth, int newHeight) {
        // guarda la posicion relativa antes del cambio
        double relX = (double) x / panelWidth;
        double relY = (double) y / panelHeight;

        // actualiza las dimensiones del panel
        this.panelWidth = newWidth;
        this.panelHeight = newHeight;

        // recalcula la posicion usando la misma proporcion
        int newSize = getCurrentSize();
        this.x = (int) (relX * panelWidth);
        this.y = (int) (relY * panelHeight);

        // asegura que el personaje no se salga de los bordes despues del ajuste
        this.x = Math.max(0, Math.min(panelWidth - newSize, x));
        this.y = Math.max(0, Math.min(panelHeight - newSize, y));
    }

    // centra al personaje en el panel actual
    public void resetPosition() {
        int size = getCurrentSize();
        this.x = (panelWidth - size) / 2;
        this.y = (panelHeight - size) / 2;
    }

    // calcula el tamaño actual del personaje segun el ancho del panel, con crecimiento suave y limite
    public int getCurrentSize() {
        double relativeScale = (double) panelWidth / BASE_WIDTH;
        double smoothScale = Math.sqrt(relativeScale);
        int scaledSize = (int) (BASE_SIZE * smoothScale);
        return Math.min(scaledSize, MAX_SIZE);
    }

    // calcula la velocidad actual del personaje segun el tamano del panel
    public int getCurrentSpeed() {
        double relativeScale = (double) panelWidth / BASE_WIDTH;
        double smoothScale = Math.sqrt(relativeScale);
        return (int) Math.max(2, Math.min(20, BASE_SPEED * smoothScale));
    }

    // metodos de movimiento que actualizan la posicion y la direccion
    public void moveUp() {
        int speed = getCurrentSpeed();
        y = Math.max(0, y - speed);
        currentDirection = Direction.UP;
    }

    public void moveDown() {
        int speed = getCurrentSpeed();
        int size = getCurrentSize();
        y = Math.min(panelHeight - size, y + speed);
        currentDirection = Direction.DOWN;
    }

    public void moveLeft() {
        int speed = getCurrentSpeed();
        x = Math.max(0, x - speed);
        currentDirection = Direction.LEFT;
    }

    public void moveRight() {
        int speed = getCurrentSpeed();
        int size = getCurrentSize();
        x = Math.min(panelWidth - size, x + speed);
        currentDirection = Direction.RIGHT;
    }

    // getters para la posicion y la imagen actual
    public int getX() { return x; }
    public int getY() { return y; }
    public Image getCurrentImage() {
        BufferedImage img = spriteMap.get(currentDirection);
        return img != null ? img : null;
    }
}