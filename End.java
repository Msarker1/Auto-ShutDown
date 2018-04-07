import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class End {
	private static long time = 0L;
	public static long getTime() {return time;}
	public static void setTime(long t) {time = t;}

	private static void killIt(String command) {
		try
		{	
			Runtime.getRuntime().exec(command);
		}
		catch (Exception e)
		{
			System.out.println("Error");
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		JFrame frame  = new JFrame("Tanvir's ShutDown"); 

		frame.setSize(700, 200);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new FlowLayout());

		JLabel l1 = new JLabel("Enter ShutDown Time In Minutes");
		JButton j1 = new JButton ("START!!!");
		JTextField f1 = new JTextField(10);
		JLabel status = new JLabel();
		JLabel cd = new JLabel();

		f1.setFont(new Font("Courier", Font.BOLD,45));
		j1.setFont(new Font("Courier", Font.BOLD,15));
		l1.setFont(new Font("Courier", Font.BOLD,15));
		status.setFont(new Font("Courier", Font.BOLD,13));
		cd.setFont(new Font("Courier", Font.BOLD,30));
		cd.setForeground(Color.BLACK);
		
		frame.add(l1);
		frame.add(f1);
		frame.add(j1);
		frame.add(status);
		frame.add(cd);

		status.setVisible(false);
		Timer timer = new Timer();

		j1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent source) {

				try {
					String command;
					String os  = System.getProperty("os.name");
					if((os.toUpperCase()).contains("Linux".toUpperCase()) || (os.toUpperCase()).contains("Mac OS X".toUpperCase())) {
						command  = "shutdown -h now";
					}
					else if (os.toUpperCase().contains("Windows".toUpperCase())) {
						command  = "shutdown.exe -s -t 0";
					}
					else
						throw new Exception();


					int n = Integer.parseInt(f1.getText());
					if(n>100000)
						throw new Exception();
					status.setText("STARTED: You Have Entered: "+n +" minute(s).");
					status.setForeground(Color.GREEN);
					setTime(n*60);
					
					TimerTask task = new TimerTask() {
						public void run() {
							cd.setText("ShutDown in : "+ Long.toString(getTime())+ " Seconds");
							setTime((getTime()-1));
							if(getTime()<0) {
								timer.cancel();
								killIt(command);
							}
						}
					};
					timer.schedule(task, 1000,1000);
					j1.setEnabled(false);
				}
				catch (Exception e ) {
					status.setText("Try Again : *** ERROR: Number Too Large / Decimal / No Input / OS Issue ***");
					status.setForeground(Color.RED);

				}}

		});

		frame.setResizable(false);
		frame.setVisible(true);
		status.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
