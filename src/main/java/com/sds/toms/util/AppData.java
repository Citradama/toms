package com.sds.toms.util;

public class AppData {

	public static String getStatusLabel(String status) {
		String label = "";
		if (status.equals("Y"))
			label = "Aktif";
		else if (status.equals("N"))
			label = "Tidak Aktif";

		return label;
	}

}
