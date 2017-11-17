


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;


public class ApplicationLauncher {

    @SuppressWarnings("unused")
    public ApplicationLauncher()
    {
		// Displays are responsible from managing event loops and 
		// controlling communication between the UI thread and other threads
		// each application has (at least) one Display object
		Display display_mainDisplay = new Display();
		
		
		
		
		// Shell is the window in an application managed by the OS window manager
		// we need as many Shell instances as there are windows in the application.
        Shell shell_mainWindow = new Shell(display_mainDisplay);
     
        // the layout manager handle the layout
        // of the widgets in the container
        shell_mainWindow.setLayout(new FillLayout());
        shell_mainWindow.setText("cross SWT");        
        
        // create a tabfolder to contain tabs within the main window
        TabFolder mainWindowTabFolder = new TabFolder(shell_mainWindow, SWT.NONE);
        
        // Tab 1
        TabItem tab1 = new TabItem(mainWindowTabFolder, SWT.NONE);
        tab1.setText("TCP Server");
        Composite _CompositeTCPServer = createTCPServerComposite(mainWindowTabFolder);
        tab1.setControl(_CompositeTCPServer);
        
        
        // Tab 2
        TabItem tab2 = new TabItem(mainWindowTabFolder, SWT.NONE);
        tab2.setText("Tab 2");
        
        // Tab 3
        TabItem tab3 = new TabItem(mainWindowTabFolder, SWT.NONE);
        tab3.setText("Tab 3");
        
        shell_mainWindow.pack();
        shell_mainWindow.open();
        
        
        
        SplashWindow splashWindow = new SplashWindow(display_mainDisplay);
        
        
        
        while((Display.getCurrent().getShells().length != 0)
                 && !Display.getCurrent().getShells()[0].isDisposed())
        {
             if(!display_mainDisplay.readAndDispatch())
             {
            	 display_mainDisplay.sleep();
             }
        }
        
        
        display_mainDisplay.dispose();        
        
    }
    
     
    public static void main(String[] args)
    {
        new ApplicationLauncher();
    }
    
    
    public Composite createTCPServerComposite(Composite parent)
    {
    	// tcp server:
    	
    	// group Config
    	// row ip address : LABEL TEXTFIELD
    	// row interface selection ? eth/wifi... LABEL COMBOBOX/DROPDOWN
    	// row port : LABEL TEXTFIELD, number [INFO BUTTON, popup with standard TCP Port Numbers]
    	// row start listening/stop listening : BUTTON TOGGLE?
    	
    	// group Status
    	// row connection status: LABEL TEXTFIELD  BUTTON(connected to or disconnected)
    	// multi rows connection log... [conversation] TEXTBOX
    	// row log buttons BUTTON (clear log), BUTTON (save log) 
    	// row Log To File Options... TODO

    	// group Send Message
    	// (if connected, active else greyed out)
    	// row message LABEL, TEXTFIELD, SEND BUTTON
    	
    	Composite composite = new Composite(parent, SWT.NONE);    	
        composite.setLayout(new GridLayout());

    	
    	
        Group configGroup = new Group(composite, SWT.NONE);
        configGroup.setText("TCP Server Config"); 
    	GridData configGroupGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
    	configGroup.setLayoutData(configGroupGridData);
    	
       	GridLayout configGroupLayout = new GridLayout();
    	configGroupLayout.numColumns = 3;
    	configGroup.setLayout(configGroupLayout);
    	
    	
		// Network Interface
    	java.util.List<String> interfaceNameList = new java.util.ArrayList<String>();
    	try 
    	{
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();			
			
			while (interfaces.hasMoreElements())
			{
				NetworkInterface networkInterface = interfaces.nextElement();

				if(networkInterface.isUp())
				{
					String interfaceName = networkInterface.getDisplayName();
					interfaceNameList.add(interfaceName);
				}

			}
		} 
    	catch (SocketException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
		new Label(configGroup, SWT.NONE).setText("Network Interface: ");
		Combo interfaceSelectCombo = new Combo(configGroup, SWT.READ_ONLY);
		String interfaceArray[] = {};
		interfaceSelectCombo.setItems(interfaceNameList.toArray(interfaceArray));
		interfaceSelectCombo.select(0);
		GridData interfaceSelectComboGridData = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		interfaceSelectComboGridData.horizontalSpan = 2;
		interfaceSelectComboGridData.widthHint = 200;
		interfaceSelectCombo.setLayoutData(interfaceSelectComboGridData);
		
    	
    	// IP Address
    	new Label(configGroup, SWT.NONE).setText("IP Address: ");    	
    	Text ipAddressText = new Text(configGroup, SWT.SINGLE | SWT.BORDER);
		GridData ipAddressTextGridData = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		ipAddressTextGridData.horizontalSpan = 2;
		ipAddressTextGridData.widthHint = 100;
		ipAddressText.setLayoutData(ipAddressTextGridData);
		ipAddressText.setText("127.0.0.1");
				

		// Port
		// IP Address
    	new Label(configGroup, SWT.NONE).setText("Port: ");    	
    	Text portNumberText = new Text(configGroup, SWT.SINGLE | SWT.BORDER);
		GridData portNumberTextGridData = new GridData(SWT.FILL, SWT.CENTER, false, false);
		portNumberTextGridData.horizontalSpan = 1;
		//portNumberTextGridData.widthHint = 100;
		portNumberText.setLayoutData(portNumberTextGridData);
		portNumberText.setText("13131");
		Button portInfoButton = new Button(configGroup, SWT.PUSH);
		portInfoButton.setText("Ports");
		GridData portInfoButtonGridData = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		portInfoButtonGridData.horizontalSpan = 1;
		portInfoButton.setLayoutData(portInfoButtonGridData);
		
		
		// Toggle Listening:
		Button toggleListeningButton = new Button(configGroup, SWT.PUSH);
		toggleListeningButton.setText("Start Listening");
		GridData toggleListeningButtonGridData = new GridData(SWT.LEFT | SWT.FILL, SWT.CENTER, false, false);
		toggleListeningButtonGridData.horizontalSpan = 3;
		toggleListeningButton.setLayoutData(toggleListeningButtonGridData);
		
    	
    	Group statusGroup = new Group(composite, SWT.NONE);
    	statusGroup.setText("Connection Status");
    	GridLayout statusGroupLayout = new GridLayout();
    	statusGroupLayout.numColumns = 3;
    	statusGroup.setLayout(statusGroupLayout);
    	
    	new Label(statusGroup, SWT.NONE).setText("< DISCONNECTED >");
    	new Label(statusGroup, SWT.NONE).setText("");
    	Button disconnectButton = new Button(statusGroup, SWT.PUSH);
    	disconnectButton.setText("Disconnect");
		GridData disconnectButtonGridData = new GridData(SWT.FILL, SWT.CENTER, false, false);
		disconnectButtonGridData.horizontalSpan = 1;
		disconnectButton.setLayoutData(disconnectButtonGridData);
		disconnectButton.setEnabled(false);
		//disconnectButton.setVisible(false);
    	
    	Text conversationLogText = new Text(statusGroup, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData conversationLogTextGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		conversationLogTextGridData.horizontalSpan = 3;
		conversationLogTextGridData.widthHint = 400;
		conversationLogTextGridData.heightHint = 5 * conversationLogText.getLineHeight();
		conversationLogText.setLayoutData(conversationLogTextGridData);
		
		new Label(statusGroup, SWT.NONE).setText("");
		Button clearLogButton = new Button(statusGroup, SWT.PUSH);
    	clearLogButton.setText("Clear Log");
		GridData clearLogButtonGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		//clearLogButtonGridData.horizontalSpan = 1;
		clearLogButton.setLayoutData(clearLogButtonGridData);		
    	Button saveLogButton = new Button(statusGroup, SWT.PUSH);
    	saveLogButton.setText("Save Log");
		GridData saveLogButtonGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		//saveLogButtonGridData.horizontalSpan = 1;
		saveLogButton.setLayoutData(saveLogButtonGridData);
    	
    	Group sendMessageGroup = new Group(composite, SWT.NONE);
    	
    	
    	
    	
    	composite.layout();
    	composite.pack();
        return composite;
    }
	
}
