package com.nru.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

import com.nru.util.HookWin32;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.SystemColor;
import java.util.Vector;

import javax.swing.SwingConstants;
import java.awt.Font;

public class Main extends JFrame {
	private JPanel contentPane;
	JLabel lblTimer = new JLabel("00:00:00");
	int no_event_count = 0;
	String last_event_str="", current_event_str="";
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 272, 321);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);


		lblTimer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTimer.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimer.setForeground(SystemColor.window);
		lblTimer.setBackground(SystemColor.windowBorder);
		lblTimer.setBounds(68, 11, 101, 31);
		lblTimer.setOpaque(true);
		contentPane.add(lblTimer);
		StartProcess startProcess = new StartProcess();
		Thread thread = new Thread(startProcess,"startProcess");
		thread.start();
	}

	private class StartProcess implements Runnable, NativeKeyListener, NativeMouseInputListener, NativeMouseWheelListener{
		boolean stop = false;
		public StartProcess() {
			GlobalScreen.setEventDispatcher(new SwingDispatchService());
			try{
				GlobalScreen.registerNativeHook();
				GlobalScreen.addNativeKeyListener(this);
				GlobalScreen.addNativeMouseListener(this);
				GlobalScreen.addNativeMouseMotionListener(this);
				GlobalScreen.addNativeMouseWheelListener(this);
			}catch(Exception exp){exp.printStackTrace();}
		}

		@Override
		public void run() {
			long startTime =System.currentTimeMillis();
			while(!stop){
				try {
					long currentTime =System.currentTimeMillis();
					//System.out.println("___________: "+last_event_str);
					if(last_event_str.trim().equals("") || last_event_str==null || last_event_str.trim().equals(current_event_str)){
						no_event_count++;
					}else{
						no_event_count=0;
						current_event_str = last_event_str;
					}
					System.out.println("-->count down: "+no_event_count);
					if(no_event_count>10){
						JOptionPane.showMessageDialog(null, "You are not active since last 10 seconds !!!");
					}
					startTimer(startTime, currentTime);
					Thread.sleep(1000);
				} catch (InterruptedException e) {e.printStackTrace();}
			}
		}

		public void stop(){

			stop = true;
		}

		private void startTimer(long startTime, long currentTime){
			String elapsedTime= "00:00:00";
			long diffTime_millisecs=currentTime - startTime;
			long diffTime_secs=diffTime_millisecs/1000;

			int _mins=(int)diffTime_secs/60;
			int _secs=(int)diffTime_secs%60;
			int _hours=0;
			if(_mins>59){
				_hours=_mins/60;
				_mins=_mins%60;
			}else{
				_hours=0;
			}
			String hourStr = (_hours<10)?("0"+_hours):(""+_hours);
			String minStr = (_mins<10)?("0"+_mins):(""+_mins);
			String secStr = (_secs<10)?("0"+_secs):(""+_secs);
			StringBuilder sb = new StringBuilder(hourStr);
			sb.append(":");
			sb.append(minStr);
			sb.append(":");
			sb.append(secStr);
			elapsedTime = sb.toString();
			//elapsedTime = hourStr+":"+minStr+":"+secStr;
			lblTimer.setText(elapsedTime);

		}
		@Override
		public void nativeMouseClicked(NativeMouseEvent e) {
			getEventInfo(e);

		}
		@Override
		public void nativeMousePressed(NativeMouseEvent e) {
			getEventInfo(e);

		}
		@Override
		public void nativeMouseReleased(NativeMouseEvent e) {
			getEventInfo(e);

		}
		@Override
		public void nativeMouseDragged(NativeMouseEvent e) {
			getEventInfo(e);

		}
		@Override
		public void nativeMouseMoved(NativeMouseEvent e) {
			getEventInfo(e);

		}
		@Override
		public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
			getEventInfo(e);

		}
		@Override
		public void nativeKeyPressed(NativeKeyEvent e) {
			getEventInfo(e);

		}
		@Override
		public void nativeKeyReleased(NativeKeyEvent e) {
			getEventInfo(e);

		}
		@Override
		public void nativeKeyTyped(NativeKeyEvent e) {
			getEventInfo(e);

		}

		public String getEventInfo(final NativeInputEvent e) {
			last_event_str = e.paramString();
			return last_event_str;
		}
	}
}
