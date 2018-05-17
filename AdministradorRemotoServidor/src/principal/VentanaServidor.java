package principal;

import com.sun.awt.AWTUtilities;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Carlos González <adrianarroyoceja at gmail.com>
 */
public class VentanaServidor extends JFrame implements MouseListener{
    private Dimension Pantalla = Toolkit.getDefaultToolkit().getScreenSize();
    private int LargoPantalla = (int) Pantalla.getHeight();
    private int AnchoPantalla = (int) Pantalla.getWidth();
    private JButton BtnCerrar = new JButton();
    private JButton BtnOcultar = new JButton();
    private JPanel PnlFlechas = new JPanel();
    private JButton BtnArriba = new JButton();
    private JButton BtnDerecha = new JButton();
    private JButton BtnAbajo = new JButton();
    private JButton BtnIzquierda = new JButton();
    
    public VentanaServidor(){
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); 
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setAlwaysOnTop(true);
        this.setUndecorated(true);
        this.establecerUbicacion(0);
        AWTUtilities.setWindowOpaque(this, false);
        this.PnlFlechas.setOpaque(false); 
        this.add(this.BtnCerrar);
        this.add(this.BtnOcultar);
        this.add(this.PnlFlechas);
        this.PnlFlechas.setLayout(null);
        this.PnlFlechas.add(this.BtnArriba);
        this.PnlFlechas.add(this.BtnDerecha);
        this.PnlFlechas.add(this.BtnAbajo);
        this.PnlFlechas.add(this.BtnIzquierda);
        this.BtnArriba.addMouseListener(this);
        this.BtnDerecha.addMouseListener(this);
        this.BtnAbajo.addMouseListener(this);
        this.BtnIzquierda.addMouseListener(this);
        this.BtnArriba.setBounds(33, 0, 33, 33);   
        this.BtnDerecha.setBounds(66, 33, 33, 33);
        this.BtnAbajo.setBounds(33, 66, 33, 33);
        this.BtnIzquierda.setBounds(0, 33, 33, 33);
        this.setVisible(true);
    }
    
    public void establecerUbicacion(int posicion) {
        if (posicion == 0) {
            this.cambiarOrientacion(true);
            this.setLocation(this.calcularMitad(this.AnchoPantalla, this.getSize().width),0);
        }
        else if (posicion == 1) {
            this.cambiarOrientacion(false);
            this.setLocation(this.AnchoPantalla - this.getSize().width,this.calcularMitad(this.LargoPantalla, this.getSize().height));
        }
        else if (posicion == 2) {
            this.cambiarOrientacion(true);
            this.setLocation(this.calcularMitad(this.AnchoPantalla, this.getSize().width), this.LargoPantalla - this.getSize().height);
        }
        else if (posicion == 3) {
            this.cambiarOrientacion(false);
            this.setLocation(0, this.calcularMitad(this.LargoPantalla, this.getSize().height));
        }
    }
    
    private int calcularMitad(int medTot, int medEl) {
        return medTot / 2 - medEl / 2;
    }
    
    private void cambiarOrientacion(boolean horizontal) {
        if (horizontal) {
            this.setSize(800, 100);
            this.BtnCerrar.setBounds(11, 11, 33, 33);
            this.BtnOcultar.setBounds(11, 56, 33, 33);
            this.PnlFlechas.setBounds(89, 0, 100, 100);
        }
        else {
            this.setSize(100, 800);
            this.BtnCerrar.setBounds(11, 11, 33, 33);
            this.BtnOcultar.setBounds(56, 11, 33, 33);
            this.PnlFlechas.setBounds(0, 89, 100, 100);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.BtnCerrar) {
            System.exit(0);
        }
        else if (e.getSource() == this.BtnCerrar) {
            //
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
    }

    @Override
    public void mousePressed(MouseEvent e) {
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
    
    
}
