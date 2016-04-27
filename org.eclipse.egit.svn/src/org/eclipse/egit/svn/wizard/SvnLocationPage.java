package org.eclipse.egit.svn.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SvnLocationPage extends WizardPage {

	protected SvnLocationPage(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.BORDER);
		setControl(parent);
		
		comp.setLayout(new GridLayout(2, false));
		
		Label svnUrl = new Label(comp, SWT.NONE);
		svnUrl.setText("Svn url");
		GridData gData = new GridData();
		gData.horizontalAlignment = SWT.BEGINNING;
		svnUrl.setData(gData);
		
		Text svnUrlField = new Text(comp, SWT.BORDER);
		gData = new GridData();
		gData.horizontalAlignment = SWT.FILL;
		svnUrlField.setLayoutData(gData);
	}
	
	

}
