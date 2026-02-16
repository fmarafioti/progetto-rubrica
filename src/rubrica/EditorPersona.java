package rubrica;

import javax.swing.*;
import java.awt.*;

public class EditorPersona extends JDialog {

    private final JTextField jtxtNome = new JTextField(20);
    private final JTextField jtxtCognome = new JTextField(20);
    private final JTextField jtxtIndirizzo = new JTextField(20);
    private final JTextField jtxtTelefono = new JTextField(20);
    private final JTextField jtxtEta = new JTextField(5);

    private boolean saved = false;
    private Persona persona;

    public EditorPersona(Frame frame,Persona persona){
        super(frame,"Editor Contatto",true);

        this.persona = persona;

        if (persona != null) {
            jtxtNome.setText(persona.getNome());
            jtxtCognome.setText(persona.getCognome());
            jtxtIndirizzo.setText(persona.getIndirizzo());
            jtxtTelefono.setText(persona.getTelefono());
            jtxtEta.setText(String.valueOf(persona.getEta()));
        }

        setLayout(new BorderLayout(10, 10));

        JPanel center = new JPanel(new GridLayout(5, 2, 5, 5));
        center.add(new JLabel("Nome:"));
        center.add(jtxtNome);
        center.add(new JLabel("Cognome:"));
        center.add(jtxtCognome);
        center.add(new JLabel("Indirizzo:"));
        center.add(jtxtIndirizzo);
        center.add(new JLabel("Telefono:"));
        center.add(jtxtTelefono);
        center.add(new JLabel("Età:"));
        center.add(jtxtEta);

        add(center, BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton salva = new JButton("Salva");
        JButton annulla = new JButton("Annulla");
        south.add(salva);
        south.add(annulla);

        add(south, BorderLayout.SOUTH);

        salva.addActionListener(e -> salva());
        annulla.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(frame);
    }

    private void salva(){
        String nome = jtxtNome.getText().trim();
        String cognome = jtxtCognome.getText().trim();
        String indirizzo = jtxtIndirizzo.getText().trim();
        String telefono = jtxtTelefono.getText().trim();
        String etaStr = jtxtEta.getText().trim();

        if (nome.isEmpty() || cognome.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nome, Cognome e Telefono sono campi obbligatori.",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int eta;
        try {
            eta = Integer.parseInt(etaStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Il campo età deve contenere un valore numerico",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (persona == null) {
            persona = new Persona(nome, cognome, indirizzo, telefono, eta);
        } else {
            persona.setNome(nome);
            persona.setCognome(cognome);
            persona.setIndirizzo(indirizzo);
            persona.setTelefono(telefono);
            persona.setEta(eta);
        }

        saved = true;
        dispose();
    }

    public boolean isSaved() {
        return saved;
    }

    public Persona getPersona() {
        return persona;
    }

}
