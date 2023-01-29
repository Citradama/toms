package com.sds.utils.config;

import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.WebAppCleanup;
import org.zkoss.zk.ui.util.WebAppInit;

public class ConfigListeners implements WebAppInit, WebAppCleanup  {

	@Override
	public void init(WebApp wapp) throws Exception {
		ConfigUtil.getConfig();
		System.out.println(ConfigUtil.getConfig().getUrl_base());
	}
	
	@Override
	public void cleanup(WebApp wapp) throws Exception {
				
	}	

}
