package org.eclipse.egit.svn.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SvnLocationPage extends WizardPage {

	protected SvnLocationPage(String pageName) {
		super(pageName);
		setTitle("Enter SVN repository location");
		setDescription("SVN repository informations");
	}

	@Override
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NULL);
		comp.setLayout(new GridLayout());
		comp.setLayoutData(createFilGD());
		
		createUrlFieldSet(comp);
		createRevisionFieldSet(comp);
		createRepoLayoutFieldSet(comp);
		createAuthFieldSet(comp);
		
		setControl(comp);
	}

	private void createRevisionFieldSet(Composite comp) {
		Group g = createGroup(comp, "Clone from revision", 4);
		new Label(g, SWT.NONE).setText("Clone from revision");
		final Button fromRevision = new Button(g, SWT.CHECK);
		fromRevision.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		
		final Label fromL = new Label(g, SWT.NONE);
		fromL.setText("Revision from: ");
		final Text fromRev = new Text(g, SWT.NONE);
		fromRev.setEnabled(false);
		
		final Label toL = new Label(g, SWT.NONE);
		toL.setText("to: ");
		final Text toRev = new Text(g, SWT.NONE);
		toRev.setEnabled(false);
		
		fromRevision.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean selection = fromRevision.getSelection();
				fromL.setEnabled(selection);
				fromRev.setEnabled(selection);
				toL.setEnabled(selection);
				toRev.setEnabled(selection);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private GridData createFilGD() {
		final GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		return gd;
	}

	private void createRepoLayoutFieldSet(Composite comp) {
		
		Group g = createGroup(comp, "folders", 2);
		
		new Label(g, SWT.NONE).setText("Trunk:");
		Text trunk = new Text(g, SWT.NULL);
		trunk.setText("trunk");
		trunk.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		
		new Label(g, SWT.NONE).setText("Branches:");
		Text branches = new Text(g, SWT.NULL);
		branches.setText("branches");
		branches.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		
		new Label(g, SWT.NONE).setText("Tags:");
		Text tags = new Text(g, SWT.NULL);
		tags.setText("tags");
		tags.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
	}

	private void createAuthFieldSet(Composite comp) {
		Group g = createGroup(comp, "Authentication", 2);
		Label label = new Label(g, SWT.NONE);
		label.setText("Username:");
		Text username = new Text(g, SWT.BORDER);
		username.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		
		Label psLabel = new Label(g, SWT.NONE);
		psLabel.setText("Password:");
		Text password = new Text(g, SWT.BORDER | SWT.PASSWORD);
		password.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
	}

	private void createUrlFieldSet(Composite comp) {
		Group g = createGroup(comp, "Svn location", 2);
		Label svnUrl = new Label(g, SWT.NONE);
		svnUrl.setText("Url:");
		GridData gData = new GridData();
		gData.horizontalAlignment = SWT.BEGINNING;
		svnUrl.setData(gData);
		
		Text svnUrlField = new Text(g, SWT.BORDER);
		svnUrlField.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
	}
	
	private Group createGroup(final Composite parent, final String text, int columns) {
		final Group g = new Group(parent, SWT.NONE);
		final GridLayout layout = new GridLayout();
		layout.numColumns = columns;
		g.setLayout(layout);
		g.setText(text);
		final GridData gd = createFilGD();
		g.setLayoutData(gd);
		return g;
	}

}
