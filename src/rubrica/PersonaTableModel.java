package rubrica;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PersonaTableModel extends AbstractTableModel {

    private final List<Persona> elencoPersone;
    private final String[] columns = {"Nome","Cognome","Telefono"};

    public PersonaTableModel(List<Persona> elencoPersone){
        this.elencoPersone = elencoPersone;
    }

    @Override
    public int getRowCount() {
        return elencoPersone.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Persona persona = elencoPersone.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return persona.getNome();
            case 1:
                return persona.getCognome();
            case 2:
                return persona.getTelefono();
            default:
                return "";
        }
    }

    public Persona getPersonaAt(int row) {
        return elencoPersone.get(row);
    }

    public void refresh(){
        fireTableDataChanged();
    }
}
