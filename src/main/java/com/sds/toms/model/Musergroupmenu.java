package com.sds.toms.model;

public class Musergroupmenu {

	private Integer id;
	private Musergroup usergroup;
	private Mmenu menu;

	public Integer getId() {
		return id;
	}

	public Musergroup getUsergroup() {
		return usergroup;
	}

	public Mmenu getMenu() {
		return menu;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUsergroup(Musergroup usergroup) {
		this.usergroup = usergroup;
	}

	public void setMenu(Mmenu menu) {
		this.menu = menu;
	}

}
