
public class GameObject {
    int x, y, w, h;    //(x,y) y tamaño (ancho, alto) del cuadrito
    String message;    // el mensajito que sale cuando le das click

    // constructor: le metemos los datos del objeto
    public GameObject(int x, int y, int w, int h, String msg) {
        this.x = x; 
        this.y = y; 
        this.w = w; 
        this.h = h;
        this.message = msg;
    }

    // revisa si el click (px, py) cayó adentro del cuadrito
    public boolean contains(int px, int py) {
        return (px >= x && px <= x + w && py >= y && py <= y + h);
    }

    // dibuja el cuadrito en pantalla (azul nomas, como lo quiso santi)
    public void draw(java.awt.Graphics g) {
        g.setColor(java.awt.Color.BLUE); // color azul
        g.fillRect(x, y, w, h);          // pinta el rectangulito
    }
}
