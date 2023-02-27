package com.sds.toms.util;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Sessions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.handler.RespHandler;
import com.sds.toms.model.Mcategory;
import com.sds.toms.model.Mdosen;
import com.sds.toms.model.Muser;
import com.sds.toms.pojo.ObjectResp;
import com.sds.utils.config.ConfigUtil;

public class AppData {
	private static org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private static Muser oUser = (Muser) zkSession.getAttribute("oUser");;

	public static String getStatusLabel(String status) {
		String label = "";
		if (status.equals("Y"))
			label = "Aktif";
		else if (status.equals("N"))
			label = "Tidak Aktif";

		return label;
	}

	public static List<Mdosen> getMdosen() throws Exception {
		ObjectResp Resp = null;
		String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_mdosen();
		Resp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);
		List<Mdosen> list = new ArrayList<Mdosen>();
		if (Resp.getCode() == 200) {
			ObjectMapper mapper = new ObjectMapper();
			list = mapper.convertValue(Resp.getData(), new TypeReference<List<Mdosen>>() {
			});
		}
		return list;
	}
	
	public static List<Mcategory> getMcategory() throws Exception {
		ObjectResp Resp = null;
		String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_mcategory();
		Resp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);
		List<Mcategory> list = new ArrayList<Mcategory>();
		if (Resp.getCode() == 200) {
			ObjectMapper mapper = new ObjectMapper();
			list = mapper.convertValue(Resp.getData(), new TypeReference<List<Mcategory>>() {
			});
		}
		return list;
	}

}
