package PacotePrincipal;

import javax.swing.table.AbstractTableModel;

public class MeuTableModel extends AbstractTableModel {
    private Object[][] dados;
    private String[] nomesColunas;

    public MeuTableModel(Object[][] dados, String[] nomesColunas) {
        this.dados = dados;
        this.nomesColunas = nomesColunas;
    }

    @Override
    public int getColumnCount() {
        return nomesColunas.length;
    }

    @Override
    public int getRowCount() {
        return dados.length;
    }

    @Override
    public String getColumnName(int column) {
        return nomesColunas[column];
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    @Override
    public Object getValueAt(int row, int column) {
        return dados[row][column];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        // Defina se a célula é editável ou não
        return true;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        dados[row][column] = value;
        fireTableCellUpdated(row, column);
    }
}
