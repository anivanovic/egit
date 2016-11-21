package org.eclipse.egit.svn.wizard;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SvnRepoPage extends WizardPage {

	private FilteredTree branchesTree;
	private Label labelMain;

	protected SvnRepoPage(String pageName) {
		super(pageName);
	}

	@Override
	public void createControl(Composite parent) {
		try {
			disableSSL();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final Composite panel = new Composite(parent, SWT.NULL);
		final GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		panel.setLayout(layout);

		Label label = new Label(panel, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		label.setText("Choose svn branches");
		branchesTree = new FilteredTree(panel, INFORMATION, new PatternFilter(), true);
		branchesTree.getViewer().setContentProvider(new ITreeContentProvider() {
			
			@Override
			public boolean hasChildren(Object element) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Object getParent(Object element) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object[] getElements(Object inputElement) {
				return ((List<String>) inputElement).toArray();
			}
			
			@Override
			public Object[] getChildren(Object parentElement) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		labelMain = new Label(panel, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		label.setText("Dohva�anje Repozitorija");
		
		setControl(panel);
	}
	
	
	private static Group createGroup(final Composite parent, final String text, int columns) {
		final Group g = new Group(parent, SWT.NONE);
		final GridLayout layout = new GridLayout();
		layout.numColumns = columns;
		g.setLayout(layout);
		g.setText(text);
		final GridData gd = createFilGD();
		g.setLayoutData(gd);
		return g;
	}
	

	private static GridData createFilGD() {
		final GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		return gd;
	}
	
	private List<String> getBranches(String url) throws IOException {
		Document html = Jsoup.connect(url).timeout(30000).get(); 
		Elements links = html.select("a");
		
		List<String> branches = new ArrayList<>();
		for (Element link : links) {
			String possibleBranche = link.html();
			if (possibleBranche.endsWith("/")) {
				String branchName = possibleBranche.replaceFirst("/", "");
				branches.add(branchName);
				System.out.println(branchName);
			}
			
		}
		
		return branches;
	}
	
	private void runTask() {
		
		final List<String> branches = new ArrayList<>();
		try {
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					try {
						
						monitor.beginTask("Geting svn branches", 1);
						branches.addAll(getBranches("https://svn.svnkit.com/repos/svnkit/branches/"));
						monitor.worked(1);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		branchesTree.getViewer().setInput(branches);
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		labelMain.getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				runTask();
			}
		});
	}
	
	private static void disableSSL() throws NoSuchAlgorithmException, KeyManagementException {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

		// Install the all-trusting trust manager
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}

}
