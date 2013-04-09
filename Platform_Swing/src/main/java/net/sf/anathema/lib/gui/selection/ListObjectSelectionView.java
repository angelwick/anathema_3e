package net.sf.anathema.lib.gui.selection;

import net.sf.anathema.lib.control.ObjectValueListener;
import net.sf.anathema.lib.gui.ConfigurableSwingUI;
import net.sf.anathema.lib.gui.TechnologyAgnosticUIConfiguration;
import net.sf.anathema.lib.gui.list.SmartJList;
import net.sf.anathema.lib.gui.list.VetoableListSelectionModel;
import net.sf.anathema.lib.gui.list.veto.IVetor;
import net.sf.anathema.lib.gui.ui.ObjectUiListCellRenderer;

import javax.swing.JComponent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import static net.sf.anathema.lib.gui.list.ListSelectionMode.SingleSelection;

public class ListObjectSelectionView<V> implements IListObjectSelectionView<V> {

	private final SmartJList<V> smartList;
	private final VetoableListSelectionModel selectionModel;

	public ListObjectSelectionView(Class<V> contentClazz) {
		this.smartList = new SmartJList<>(contentClazz);
		this.selectionModel = new VetoableListSelectionModel(SingleSelection);
		this.smartList.setSelectionModel(selectionModel);
	}

	@Override
    public void setEnabled(boolean enabled) {
		smartList.setEnabled(enabled);
	}

	@Override
    public void setCellRenderer(TechnologyAgnosticUIConfiguration<V> renderer) {
		smartList.setCellRenderer(new ObjectUiListCellRenderer(new ConfigurableSwingUI<>(renderer)));

	}

	@Override
    public void addObjectSelectionChangedListener(
			final ObjectValueListener<V> listener) {
		smartList.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
                    public void valueChanged(ListSelectionEvent e) {
						listener.valueChanged(smartList.getSelectedValue());
					}
				});
	}

	@Override
    public void setObjects(V[] objects) {
		smartList.setObjects(objects);
	}

	@Override
    @SuppressWarnings("unchecked")
	public void setSelectedObject(V object) {
		smartList.setSelectedObjects(object);
	}

  @Override
    public JComponent getComponent() {
		return smartList;
	}

	@Override
    public V getSelectedObject() {
		return smartList.getSelectedValue();
	}

  @Override
    public boolean isObjectSelected() {
		return getSelectedObject() != null;
	}

	@Override
    public void addSelectionVetor(IVetor vetor) {
		selectionModel.addVetor(vetor);
	}

	@Override
    public void removeSelectionVetor(IVetor vetor) {
		selectionModel.removeVetor(vetor);
	}
}