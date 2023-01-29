package com.sds.utils;

import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.WebAppCleanup;
import org.zkoss.zk.ui.util.WebAppInit;


public class UserLoginRelease implements WebAppInit, WebAppCleanup {

	@Override
	public void init(WebApp wapp) throws Exception {
		try {
//			new MuserDAO().doRelease();
			System.out.println("Timeout..");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override 
	public void cleanup(WebApp wapp) throws Exception {
		System.out.println("Timeout...");
	}

}
