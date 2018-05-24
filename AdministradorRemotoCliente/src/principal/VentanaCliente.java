/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Estudiantes mis Cuvalles.
 */
public class VentanaCliente extends JFrame {

    JLabel LabelNombre;
    JLabel LabelApellidos;
    JLabel LabelCodigo;
    JLabel LabelIpServidor;
    JLabel LabelStatus;
    JLabel LabelPuerto;
    
    int puerto = 9090;

    public VentanaCliente() {
        LabelNombre = new JLabel();
        LabelNombre.setText("Nombre:           ");
        //JTextField TextoNombre = new JTextField(15);
        JTextField TextoNombre = new JTextField("ALUMNO", 15);
        LabelApellidos = new JLabel();
        LabelApellidos.setText("Apellidos:         ");
        //JTextField TextoApellidos = new JTextField(15);
        JTextField TextoApellidos = new JTextField("APELLIDO", 15);
        LabelCodigo = new JLabel();
        LabelCodigo.setText("Código:              ");
        //JTextField TextoCodigo = new JTextField(15);
        JTextField TextoCodigo = new JTextField("I03030075", 15);
        LabelIpServidor = new JLabel();
        LabelIpServidor.setText("IP del servidor:");
        //JTextField TextoIpServidor = new JTextField("192.168.1.xx",15);
        JTextField TextoIpServidor = new JTextField("192.168.1.80", 15);
        LabelPuerto = new JLabel();
        /*LabelPuerto.setText("Puerto del servidor: ");
        JTextField TextoPuerto = new JTextField(String.valueOf(this.puerto), 12);*/
        LabelStatus = new JLabel();
        LabelStatus.setText("Estado: Pendiente del servidor");

        LabelStatus.setHorizontalAlignment(JTextField.CENTER);

        //TextStatus.setText("Pendiente del servidor");
        JButton ButtonIniciar = new JButton();
        ButtonIniciar.setText("Iniciar");
        ButtonIniciar.setLocation(200, 200);
        add(LabelNombre);
        add(TextoNombre);
        add(LabelApellidos);
        add(TextoApellidos);
        add(LabelCodigo);
        add(TextoCodigo);
        add(LabelIpServidor);
        add(TextoIpServidor);
        /*add(LabelPuerto);
        add(TextoPuerto);*/
        add(ButtonIniciar);

        VentanaCliente YoMismo = this;
        /* Hecho para poder utilizarno en el ActionListener */

        ButtonIniciar.addActionListener(
                new ActionListener() {

            public void actionPerformed(
                    ActionEvent e) {
                if (TextoNombre.getText().isEmpty() || TextoApellidos.getText().isEmpty() || TextoCodigo.getText().isEmpty() || TextoIpServidor.getText().isEmpty() )   {
                    JOptionPane.showMessageDialog(null, "no llenaste Todos los campos!");
                    return;
                }  
                
                
                try {

                    //JOptionPane.showMessageDialog(null, "no llenaste los datos correctamente!");
                    new Conexion(
                            TextoIpServidor.getText(),
                            /*Integer.valueOf(TextoPuerto.getText())*/YoMismo.puerto,
                            TextoNombre.getText(),
                            TextoApellidos.getText(),
                            TextoCodigo.getText(),
                            YoMismo
                    );

                } catch (InputMismatchException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString(), "Error al conectarse al server",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, exc.toString(), "Error al conectarse al server",
                            JOptionPane.ERROR_MESSAGE);
                    System.out.println("Error general: " + exc.getMessage());
                }

            }

        }
        );
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        this.setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void mostrarVentana(boolean mostrar) {

        this.setVisible(mostrar);

    }
}
