package rubrica;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class RubricaFrame extends JFrame {

    private final RubricaRepository rubricaRepository;
    private final PersonaTableModel personaTableModel;

    private final JTable table;

    public RubricaFrame(){
        super("Rubrica");

        rubricaRepository = new RubricaRepository(new File("informazioni.txt"));
        rubricaRepository.load();

        personaTableModel = new PersonaTableModel(rubricaRepository.getElencoPersone());
        table = new JTable(personaTableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setLayout(new BorderLayout(8,8));
        add(new JScrollPane(table),BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton nuovo = new JButton("Nuovo");
        JButton modifica = new JButton("Modifica");
        JButton elimina = new JButton("Elimina");
        buttons.add(nuovo);
        buttons.add(modifica);
        buttons.add(elimina);
        add(buttons, BorderLayout.SOUTH);

        nuovo.addActionListener(e -> nuovoContatto());
        modifica.addActionListener(e -> modificaContatto());
        elimina.addActionListener(e -> eliminaContatto());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700,400);
        setLocationRelativeTo(null);
    }

    private void nuovoContatto(){
        EditorPersona editor = new EditorPersona(this,null);
        editor.setVisible(true);

        if (editor.isSaved()) {
            rubricaRepository.getElencoPersone().add(editor.getPersona());
            rubricaRepository.save();
            personaTableModel.refresh();
        }
    }

    private void modificaContatto(){
        int row = table.getSelectedRow();
        if(row<0){
            JOptionPane.showMessageDialog(
                    this,
                    "Seleziona prima un contatto da modificare",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Persona p = personaTableModel.getPersonaAt(row);

        EditorPersona editor = new EditorPersona(this, p);
        editor.setVisible(true);

        if (editor.isSaved()) {
            rubricaRepository.save();
            personaTableModel.refresh();
        }
    }

    private void eliminaContatto(){
        int row = table.getSelectedRow();
        if(row<0){
            JOptionPane.showMessageDialog(
                    this,
                    "Seleziona prima un contatto da eliminare",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Persona p = personaTableModel.getPersonaAt(row);
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Vuoi davvero eliminare " + p.getNome() + " " + p.getCognome() + " dalla tua rubrica?",
                "Conferma Eliminazione",
                JOptionPane.YES_NO_OPTION
        );

        if(choice == JOptionPane.YES_OPTION){
            rubricaRepository.getElencoPersone().remove(row);
            rubricaRepository.save();
            personaTableModel.refresh();
        }
    }

}
