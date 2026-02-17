package rubrica;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
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

        TableRowSorter<PersonaTableModel> sorter = new TableRowSorter<>(personaTableModel);
        table.setRowSorter(sorter);

        setLayout(new BorderLayout(8,8));
        add(new JScrollPane(table),BorderLayout.CENTER);

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton nuovo = new JButton("Nuovo");
        JButton modifica = new JButton("Modifica");
        JButton elimina = new JButton("Elimina");
        toolBar.add(nuovo);
        toolBar.add(modifica);
        toolBar.add(elimina);

        toolBar.addSeparator();
        toolBar.add(new JLabel("Ricerca: "));
        JTextField txtSearch = new JTextField(18);
        toolBar.add(txtSearch);

        add(toolBar, BorderLayout.NORTH);

        nuovo.addActionListener(e -> nuovoContatto());
        modifica.addActionListener(e -> modificaContatto());
        elimina.addActionListener(e -> eliminaContatto());

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            private void updateFilter() {
                String text = txtSearch.getText();
                if (text == null || text.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text.trim()));
                }
            }

            @Override public void insertUpdate(DocumentEvent e) { updateFilter(); }
            @Override public void removeUpdate(DocumentEvent e) { updateFilter(); }
            @Override public void changedUpdate(DocumentEvent e) { updateFilter(); }
        });

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

        int modelRow = table.convertRowIndexToModel(row);
        Persona p = personaTableModel.getPersonaAt(modelRow);

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
