package org.eclipse.egit.svn.handlers;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.egit.svn.GitSvnRunner;
import org.eclipse.egit.ui.internal.selection.SelectionUtils;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jgit.lib.Repository;

public class SvnDcommit extends AbstractHandler {

	private IEvaluationContext evaluationContext;
	private IStructuredSelection mySelection;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Repository repo = getRepository();
			GitSvnRunner.dcommit(repo.getWorkTree());
		
		return null;
	}
		

	@Override
	public boolean isEnabled() {
		Repository repository = getRepository();
		return isEnabled(repository);
	}

	static boolean isEnabled(Repository repo) {
		boolean isEnabled = false;
			
		isEnabled = repo != null
				    && GitSvnRunner.isGitSvnRepo(repo.getDirectory());
		return isEnabled;
	}
	
	@Override
	public void setEnabled(Object evaluationContext) {
		this.evaluationContext = (IEvaluationContext) evaluationContext;
		setBaseEnabled();
	}
	
	protected void setBaseEnabled() {
		
	}
	
	protected Repository getRepository() {
		IStructuredSelection selection = getSelection();
		return SelectionUtils.getRepository(selection);
	}
	
	protected IStructuredSelection getSelection() {
		// if the selection was set explicitly, use it
		if (mySelection != null)
			return mySelection;
		return SelectionUtils.getSelection(evaluationContext);
	}


}
