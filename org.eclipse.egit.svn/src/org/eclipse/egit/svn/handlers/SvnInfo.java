package org.eclipse.egit.svn.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.svn.Activator;
import org.eclipse.egit.svn.GitSvnRunner;
import org.eclipse.egit.svn.ProcessResult;
import org.eclipse.egit.ui.internal.selection.SelectionUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class SvnInfo extends AbstractHandler {
	
	private IEvaluationContext evaluationContext;
	private IStructuredSelection mySelection;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Repository repo = getRepository();
		if (repo == null) return null;

		Job job = new Job("svn info") {
			
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask("Git svn info", 1);
				ProcessResult result = GitSvnRunner.info(repo.getWorkTree());
				monitor.worked(1);
				monitor.done();
				
				if (result != null) {
					if (result.getExitCode() == 0) {
						showInfo(result);
					} else {
						return new Status(IStatus.ERROR, Activator.getPluginId(),
											result.getMessage(), new Throwable("Git command finished with exit code " + result.getExitCode()));
					}
				}
				
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		return null;
	}
	
	private static void showInfo(final ProcessResult result) {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				Shell shell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				MessageDialog.openInformation(shell,
						"Svn info",
						result.getMessage());
			}
		});
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
	
	@Override
	public void setEnabled(Object evaluationContext) {
		this.evaluationContext = (IEvaluationContext) evaluationContext;
	}

}
