package org.eclipse.egit.svn.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

public class SvnProjectWizard extends Wizard implements IImportWizard {

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addPages() {
		WizardPage svnLocationPage = new SvnLocationPage("Svn repo location");
		addPage(svnLocationPage);
		addPage(new SvnRepoPage("Select svn branches"));
	}

}
