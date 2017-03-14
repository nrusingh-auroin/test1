package com.nru.util;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

public class HookWin32 implements NativeKeyListener, NativeMouseInputListener, NativeMouseWheelListener {
	private void startHook(){
		GlobalScreen.setEventDispatcher(new SwingDispatchService());
		try{
			GlobalScreen.registerNativeHook();
			GlobalScreen.addNativeKeyListener(this);
			GlobalScreen.addNativeMouseListener(this);
			GlobalScreen.addNativeMouseMotionListener(this);
			GlobalScreen.addNativeMouseWheelListener(this);
		}catch(Exception exp){exp.printStackTrace();}
	}

	public static void main(String[] args) {
		HookWin32 hook = new HookWin32();
		hook.startHook();
	}

	public void nativeMouseClicked(NativeMouseEvent e) {
		getEventInfo(e);
	}

	public void nativeMousePressed(NativeMouseEvent e) {
		getEventInfo(e);
	}

	public void nativeMouseReleased(NativeMouseEvent e) {
		getEventInfo(e);
	}

	public void nativeMouseDragged(NativeMouseEvent e) {
		getEventInfo(e);
	}

	public void nativeMouseMoved(NativeMouseEvent e) {
		getEventInfo(e);
	}

	public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
		getEventInfo(e);
	}

	public void nativeKeyPressed(NativeKeyEvent e) {
		getEventInfo(e);
	}

	public void nativeKeyReleased(NativeKeyEvent e) {
		getEventInfo(e);
	}

	public void nativeKeyTyped(NativeKeyEvent e) {
		getEventInfo(e);
	}

	private void getEventInfo(final NativeInputEvent e) {
		System.out.println("-->: "+e.paramString());
	}
}
