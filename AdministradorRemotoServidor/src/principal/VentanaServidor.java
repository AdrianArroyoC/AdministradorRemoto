package principal;

import com.sun.awt.AWTUtilities;
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
import javax.swing.JToggleButton;

/**
 *
 * @author Adrián Arroyo <adrianarroyoceja at gmail.com>
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
    private ButtonGroup AgrupacionConexiones = new ButtonGroup();
    private JToggleButton[] ArregloConexiones;
    
    /* Manejador de conexiones */
    private ManejadorConexiones CarteraClientes;
    
    public VentanaServidor(){
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); 
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setAlwaysOnTop(true);
        this.setUndecorated(true);
        this.establecerUbicacion(0);
        this.PnlConexiones.setLayout(null);
        this.PnlConexiones.setAutoscrolls(true);
        AWTUtilities.setWindowOpaque(this, false);
        this.PnlFlechas.setOpaque(false);
        this.PnlConexiones.setOpaque(false);
        this.add(this.BtnCerrar);
        this.add(this.BtnOcultar);
        this.add(this.PnlFlechas);
        this.add(this.PnlConexiones);
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
        this.setAlwaysOnTop(true);
        this.CarteraClientes = new ManejadorConexiones(this);
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
            this.setSize(800, 211);
            this.BtnCerrar.setBounds(21, 11, 33, 33);
            this.BtnOcultar.setBounds(21, 56, 33, 33);
            this.PnlFlechas.setBounds(65, 0, 100, 100);
            this.PnlConexiones.setBounds(176, 0, 600, 211);
        }
        else {
            this.setSize(100, 800);
            this.BtnCerrar.setBounds(11, 21, 33, 33);
            this.BtnOcultar.setBounds(56, 21, 33, 33);
            this.PnlFlechas.setBounds(0, 65, 100, 100);
            this.PnlConexiones.setBounds(0, 176, 100, 600);
        }
        agregarQuitarBotones();
    }
    
    private void ocultarDesocultar(boolean mostrar) {
        this.BtnCerrar.setVisible(mostrar);
        this.BtnOcultar.setVisible(mostrar);
        this.PnlFlechas.setVisible(mostrar);
    }
    
    public void agregarQuitarBotones() {       
        try {
            Conexion[] conexiones = CarteraClientes.getConexiones();
            PnlConexiones.removeAll();
            //Conexion[] conexiones = new Conexion[22];
            if (conexiones.length != 0) {
                this.ArregloConexiones = new JToggleButton[conexiones.length];
                int x = 5;
                int y = 5;
                if (this.Horizontal) {
                    y = 0; //16 
                }
                for (int i = 0; i < conexiones.length; i++) {
                    this.ArregloConexiones[i] = new JToggleButton(conexiones[i].getNombres());
                    this.ArregloConexiones[i].setToolTipText(conexiones[i].getNombre());
                    this.AgrupacionConexiones.add(this.ArregloConexiones[i]);
                    if (i == 6 && this.Horizontal) {
                            y = 35; //46
                            x = 5;
                    }
                    else if (i == 12 && this.Horizontal) {
                            y = 71;
                            x = 5;
                    }
                    else if (i == 12 && !this.Horizontal) {
                            y = 5;
                            x = 100;
                            this.setSize(190, 800);
                            this.PnlConexiones.setBounds(0, 176, 190, 600);
                    }
                    else if (i == 18 && this.Horizontal) {
                            y = 106;
                            x = 5;
                    }
                    else if (i == 24 && this.Horizontal) {
                            y = 141;
                            x = 5;
                    }
                    else if (i == 24 && !this.Horizontal) {
                            y = 5;
                            x = 195;
                            this.setSize(285, 800);
                            this.PnlConexiones.setBounds(0, 176, 285, 600);
                    }
                    else if (i == 30 && this.Horizontal) {
                            y = 176;
                            x = 5;
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
        } catch (Exception e) {
        }
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
        else if (this.ArregloConexiones.length != 0) {
            int conexion = -1;
            for (int i = 0; i < this.ArregloConexiones.length; i++) {
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
        try {
            if (this.ArregloConexiones.length != 0) {
                for (int i = 0; i < this.ArregloConexiones.length; i++) {
                    if (e.getSource() == this.ArregloConexiones[i]) {

                    }
                }
            }
        } catch (Exception ex) {
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
