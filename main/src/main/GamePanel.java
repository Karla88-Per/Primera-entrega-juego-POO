package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// panel principal del juego que contiene el fondo, el personaje y los objetos interactivos
public class GamePanel extends JPanel implements KeyListener {
    public static final int DEFAULT_WIDTH = 500;
    public static final int DEFAULT_HEIGHT = 400;

    private Player player;
    private Image backgroundImage;
    private JLabel subtitle;
    private List<GameObject> objects = new ArrayList<>();

    // constructor que recibe la etiqueta para los mensajes
    public GamePanel(JLabel subtitle) {
        this.subtitle = subtitle;
        this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        player = new Player();

        // carga la imagen de fondo desde el paquete
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("fondo.jpeg"));
        } catch (IOException e) {
            System.err.println("⚠️ no se pudo cargar la imagen de fondo: fondo.jpeg");
            e.printStackTrace();
        }

        // ---- SANTI ----
        // crea los objetos interactivos en las posiciones solicitadas
        createInteractiveObjects();

        // ---- SANTI ----
        // agrega un listener para detectar clics del mouse en los objetos
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (GameObject obj : objects) {
                    if (obj.contains(e.getX(), e.getY())) {
                        subtitle.setText(obj.message);
                        break;
                    }
                }
            }
        });

        // escucha los cambios de tamaño de la ventana para ajustar posicion y escala
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                player.onPanelResized(getWidth(), getHeight());
                createInteractiveObjects();
            }
        });

        // timer para redibujar el panel continuamente
        Timer timer = new Timer(16, e -> repaint());
        timer.start();
    }

    // ---- SANTI ----
    // metodo que crea los tres objetos interactivos en posiciones proporcionales
    private void createInteractiveObjects() {
        int w = getWidth();
        int h = getHeight();

        // calcula un tamaño adecuado para los objetos segun el ancho de la ventana
        int objSize = Math.min(60, Math.max(40, w / 10));

        // posicion del objeto central
        int centerX = w / 2 - objSize / 2;
        int centerY = h / 2 - objSize / 2;

        // posiciones de los dos objetos en la derecha
        int rightX = w - objSize - 20;
        int rightY1 = h / 2 - objSize - 10;
        int rightY2 = h / 2 + 10;

        // limpia la lista y agrega los nuevos objetos
        objects.clear();
        objects.add(new GameObject(centerX, centerY, objSize, objSize, "¡Genial! Has encontrado el cofre. El código es 162837"));
        objects.add(new GameObject(rightX, rightY1, objSize, objSize, "Has descubierto una llave dorada brillante"));
        objects.add(new GameObject(rightX, rightY2, objSize, objSize, "Un pergamino antiguo con símbolos mágicos"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // dibuja el fondo escalado al tamaño actual del panel
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        // ---- SANTI ----
        // dibuja todos los objetos interactivos
        for (GameObject obj : objects) {
            obj.draw(g);
        }

        // dibuja al personaje con su imagen y tamaño actual
        Image playerImg = player.getCurrentImage();
        int size = player.getCurrentSize();
        if (playerImg != null) {
            g.drawImage(playerImg, player.getX(), player.getY(), size, size, this);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(player.getX(), player.getY(), size, size);
        }
    }

    // maneja el movimiento del personaje con las teclas wasd
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: player.moveUp(); break;
            case KeyEvent.VK_S: player.moveDown(); break;
            case KeyEvent.VK_A: player.moveLeft(); break;
            case KeyEvent.VK_D: player.moveRight(); break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}