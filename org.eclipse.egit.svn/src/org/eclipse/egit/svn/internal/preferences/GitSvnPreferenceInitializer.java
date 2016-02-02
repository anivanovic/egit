package org.eclipse.egit.svn.internal.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.egit.svn.Activator;
import org.eclipse.jface.preference.IPreferenceStore;

public class GitSvnPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();
		preferences.setDefault("", "");

	}
}
