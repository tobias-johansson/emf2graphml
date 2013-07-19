package tojo.emf.graphml;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.sphinx.platform.ui.util.SelectionUtil;
import org.eclipse.ui.navigator.CommonActionProvider;

public class ActionProvider extends CommonActionProvider {

	public ActionProvider() {
		System.out.println("created");
	}


	@Override public void fillContextMenu(IMenuManager menu) {
		IStructuredSelection selection = SelectionUtil.getStructuredSelection(getContext().getSelection());
		if (selection.size() != 1) return;
		if (!(selection.getFirstElement() instanceof EObject)) return;
		final EObject eo = (EObject) selection.getFirstElement();
		menu.add(new Action("HELLO"){
			@Override public void run() {
				System.out.println("apa");
				Graphml.traverse(eo);
			}
		});
	}
}
