package principal;

import com.sun.awt.AWTUtilities;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;

/**
 *
 * @author Carlos González <adrianarroyoceja at gmail.com>
 */
public class VentanaServidor extends JFrame{
    private Dimension Pantalla = Toolkit.getDefaultToolkit().getScreenSize();
    private int LargoPantalla = (int) Pantalla.getHeight();
    private int AnchoPantalla = (int) Pantalla.getWidth();
    private JButton BtnSalir = new JButton();
    
    public VentanaServidor(){
        this.setResizable(false);
        this.setSize(500, 100);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); //EXIT
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setAlwaysOnTop(true);
        this.setUndecorated(true);
        this.establecerUbicacion(0);
        //
        //AWTUtilities.setWindowOpaque(this, false);
        //
        //this.BtnSalir.setBounds(0, 0, 50, 50);
        //this.add(this.BtnSalir);
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
            this.setSize(500, 50);
            //this.setBounds(0,0,500,16);
        }
        else {
            this.setSize(50, 500);
            //this.setBounds(0,0,16,500); 
        }
    }
    
    
}
