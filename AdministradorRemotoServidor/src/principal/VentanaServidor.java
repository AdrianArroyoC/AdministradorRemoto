package principal;

import com.sun.awt.AWTUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

/**
 *
 * @author Carlos González <adrianarroyoceja at gmail.com>
 */

public class VentanaServidor extends JFrame implements MouseListener, KeyListener {
    private Dimension Pantalla = Toolkit.getDefaultToolkit().getScreenSize();
    private int LargoPantalla = (int) Pantalla.getHeight();
    private int AnchoPantalla = (int) Pantalla.getWidth();
    private final ImageIcon ImgCerrar = new ImageIcon(getClass().getResource("..\\imagenes\\cerrar.png"));
    private final ImageIcon ImgOcultar = new ImageIcon(getClass().getResource("..\\imagenes\\ocultar.png"));
    private final ImageIcon ImgArriba = new ImageIcon(getClass().getResource("..\\imagenes\\arriba.png"));
    private final ImageIcon ImgDerecha = new ImageIcon(getClass().getResource("..\\imagenes\\derecha.png"));
    private final ImageIcon ImgAbajo = new ImageIcon(getClass().getResource("..\\imagenes\\abajo.png"));
    private final ImageIcon ImgIzquierda = new ImageIcon(getClass().getResource("..\\imagenes\\izquierda.png"));
    private JButton BtnCerrar = new JButton(ImgCerrar);
    private JButton BtnOcultar = new JButton(ImgOcultar);
    private boolean Horizontal = false;
    private JPanel PnlFlechas = new JPanel();
    private JButton BtnArriba = new JButton(ImgArriba);
    private JButton BtnDerecha = new JButton(ImgDerecha);
    private JButton BtnAbajo = new JButton(ImgAbajo);
    private JButton BtnIzquierda = new JButton(ImgIzquierda);
    private JPanel PnlConexiones = new JPanel();
    //private JScrollPane ScrllConexiones = new JScrollPane(PnlConexiones);
    private int Conexiones = 0;
    private ButtonGroup AgrupacionConexiones = new ButtonGroup();
    private JToggleButton[] ArregloConexiones;
    
    public VentanaServidor(){
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); 
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setAlwaysOnTop(true);
        this.setUndecorated(true);
        this.establecerUbicacion(0);
        this.PnlConexiones.setLayout(null);
        //this.PnlConexiones.setAutoscrolls(true);
        AWTUtilities.setWindowOpaque(this, false);
        this.PnlFlechas.setOpaque(false);
        this.PnlConexiones.setOpaque(false);
        this.add(this.BtnCerrar);
        this.add(this.BtnOcultar);
        this.add(this.PnlFlechas);
        this.add(this.PnlConexiones);
        //this.add(this.ScrllConexiones);
        this.PnlFlechas.setLayout(null);
        this.PnlFlechas.add(this.BtnArriba);
        this.PnlFlechas.add(this.BtnDerecha);
        this.PnlFlechas.add(this.BtnAbajo);
        this.PnlFlechas.add(this.BtnIzquierda);
        this.BtnCerrar.addMouseListener(this);
        this.BtnOcultar.addMouseListener(this);
        this.BtnArriba.addMouseListener(this);
        this.BtnDerecha.addMouseListener(this);
        this.BtnAbajo.addMouseListener(this);
        this.BtnIzquierda.addMouseListener(this);
        this.BtnArriba.setBounds(33, 0, 33, 33);   
        this.BtnDerecha.setBounds(66, 33, 33, 33);
        this.BtnAbajo.setBounds(33, 66, 33, 33);
        this.BtnIzquierda.setBounds(0, 33, 33, 33);
        this.addKeyListener(this);
        this.setVisible(true);
        this.agregarQuitarBotones();
    }
    
    public void establecerUbicacion(int posicion) {
        if (posicion == 0) {
            this.Horizontal = true;
            this.cambiarOrientacion();
            this.setLocation(this.calcularMitad(this.AnchoPantalla, this.getSize().width),0);
        }
        else if (posicion == 1) {
            this.Horizontal = false;
            this.cambiarOrientacion();
            this.setLocation(this.AnchoPantalla - this.getSize().width,this.calcularMitad(this.LargoPantalla, this.getSize().height));
        }
        else if (posicion == 2) {
            this.Horizontal = true;
            this.cambiarOrientacion();
            this.setLocation(this.calcularMitad(this.AnchoPantalla, this.getSize().width), this.LargoPantalla - this.getSize().height);
        }
        else if (posicion == 3) {
            this.Horizontal = false;
            this.cambiarOrientacion();
            this.setLocation(0, this.calcularMitad(this.LargoPantalla, this.getSize().height));
        }
    }
    
    private int calcularMitad(int medTot, int medEl) {
        return medTot / 2 - medEl / 2;
    }
    
    private void cambiarOrientacion() {
        
        if (this.Horizontal) {
            this.setSize(800, 100);
            this.BtnCerrar.setBounds(21, 11, 33, 33);
            this.BtnOcultar.setBounds(21, 56, 33, 33);
            this.PnlFlechas.setBounds(65, 0, 100, 100);
            this.PnlConexiones.setBounds(176, 0, 600, 100);
            //this.PnlConexiones.setLocation(176, 0);
            //this.PnlConexiones.setPreferredSize(new Dimension(600, 278));
        }
        else {
            this.setSize(100, 800);
            this.BtnCerrar.setBounds(11, 21, 33, 33);
            this.BtnOcultar.setBounds(56, 21, 33, 33);
            this.PnlFlechas.setBounds(0, 65, 100, 100);
            this.PnlConexiones.setBounds(0, 176, 100, 600);
            //this.PnlConexiones.setLocation(0, 176);
            //this.PnlConexiones.setPreferredSize(new Dimension(414, 600));
        }
        agregarQuitarBotones();
    }
    
    private void ocultarDesocultar(boolean mostrar) {
        this.BtnCerrar.setVisible(mostrar);
        this.BtnOcultar.setVisible(mostrar);
        this.PnlFlechas.setVisible(mostrar);
    }
    
    private void agregarQuitarBotones() {
        this.ArregloConexiones = new JToggleButton[Conexiones];
        int x = 5;
        int y = 5;
        if (this.Horizontal) {
            y = 16;   
        }
        for (int i = 0; i < this.Conexiones; i++) {
            this.ArregloConexiones[i] = new JToggleButton(""+i);
            this.AgrupacionConexiones.add(this.ArregloConexiones[i]);
            this.ArregloConexiones[i].setToolTipText("Mas información");
            this.ArregloConexiones[i].addMouseListener(this);
            if (i == 6) {
                if (this.Horizontal) {
                    y = 49;
                    x = 5;
                }
            }
            if (i == 12) {
                if (this.Horizontal) {
                    y = 71;
                    x = 5;
                }
                else {
                    //
                }
            }
            if (this.Horizontal) {
                this.ArregloConexiones[i].setBounds(x, y, 90, 30);
                x += 95;
            }
            else {
                this.ArregloConexiones[i].setBounds(x, y, 90, 30);
                y += 33;
            }
            this.PnlConexiones.add(this.ArregloConexiones[i]);
        } 
        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.BtnCerrar) {
            System.exit(0);
        }
        else if (e.getSource() == this.BtnOcultar) {
            this.ocultarDesocultar(false);
            this.requestFocus();
        }
        else if (e.getSource() == this.BtnArriba) {
            this.establecerUbicacion(0);
        }
        else if (e.getSource() == this.BtnDerecha) {
            this.establecerUbicacion(1);
        }
        else if (e.getSource() == this.BtnAbajo) {
            this.establecerUbicacion(2);
        }
        else if (e.getSource() == this.BtnIzquierda) {
            this.establecerUbicacion(3);
        }
        else {
            int conexion = -1;
            for (int i = 0; i < this.Conexiones; i++) {
                this.ArregloConexiones[i].setSelected(false);
                if (e.getSource() == this.ArregloConexiones[i]) {
                    this.ArregloConexiones[i].setSelected(true);
                    conexion = i;
                }
            }
            //Metodo para realizar la conexion
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for (int i = 0; i < this.Conexiones; i++) {
            if (e.getSource() == this.ArregloConexiones[i]) {
                
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_M) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
            ocultarDesocultar(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
