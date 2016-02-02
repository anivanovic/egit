package org.eclipse.egit.svn.internal.preferences;

import org.eclipse.egit.svn.Activator;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class GitLocalInstallationPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	@Override
	protected void createFieldEditors() {
		FileFieldEditor fileFieldEditor = new FileFieldEditor(PreferencesConstants.gitInstallationDirectory,
															Messages.label,
															getFieldEditorParent());
		
		addField(fileFieldEditor);
	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		// TODO Auto-generated method stub
		super.propertyChange(event);
	}
}
