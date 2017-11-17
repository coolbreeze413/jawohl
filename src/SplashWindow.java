
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;


public class SplashWindow {

	private int splashPos = 0;

	private final int SPLASH_MAX = 10;

	public SplashWindow(Display display)
	{
//		final Image image = new Image(display,
//				"." + CommonConstants.IMAGES_PATH
//				+ CommonConstants.FILE_NAME_SEPERATOR + "splash2_300px.jpg");
		
		// https://stackoverflow.com/questions/573679/open-resource-with-relative-path-in-java
		// need to add the dir to the build path, so that we can reference the relative path
		// as below to work from eclipse
		// we also need to ensure that the same path is present in the jar file
		// as we use ANT, the build.xml ensures this.
		// also note that root project dir cannot be added to build path !!!
		// hence what we do:
		// we create a "res" directory. add this to build path. so paths when relative, will be evaluated
		// with this as reference.
		// in ANT build.xml, add zipfileset to copy from "res" to jar
		// so the same path will work in eclipse and jar.
		
		final Image image = new Image(display,
				getClass().getClassLoader().getResourceAsStream("images/splash2_300px.jpg"));
		

		final Shell splash = new Shell(SWT.NONE);
		final ProgressBar bar = new ProgressBar(splash, SWT.NONE);
		bar.setMaximum(SPLASH_MAX);

		Label label = new Label(splash, SWT.NONE);
		label.setImage(image);
		

		FormLayout layout = new FormLayout();
		splash.setLayout(layout);

		FormData labelData = new FormData();
		labelData.right = new FormAttachment(100, 0);
		labelData.bottom = new FormAttachment(100, 0);
		label.setLayoutData(labelData);

		FormData progressData = new FormData();
		progressData.left = new FormAttachment(0, -5);
		progressData.right = new FormAttachment(100, 0);
		progressData.bottom = new FormAttachment(100, 0);
		bar.setLayoutData(progressData);
		splash.pack();

		Rectangle splashRect = splash.getBounds();
		Rectangle displayRect = display.getBounds();
		int x = (displayRect.width - splashRect.width) / 2;
		int y = (displayRect.height - splashRect.height) / 2;
		splash.setLocation(x, y);
		splash.open();

		display.asyncExec(new Runnable()
		{
			public void run()
			{

				for(splashPos = 0; splashPos < SPLASH_MAX; splashPos++)
				{
					try 
					{
						Thread.sleep(100);
					}
					catch(InterruptedException e) 
					{
						e.printStackTrace();
					}
					bar.setSelection(splashPos);
				}
				
				splash.close();
				image.dispose();
			}
		});

//		while(splashPos != SPLASH_MAX)
//		{
//			if(!display.readAndDispatch())
//			{
//				display.sleep();
//			}
//		}
	}
}
