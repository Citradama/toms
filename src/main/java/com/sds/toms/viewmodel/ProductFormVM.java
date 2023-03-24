package com.sds.toms.viewmodel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.json.JSONObject;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.handler.RespHandler;
import com.sds.toms.model.Mcategory;
import com.sds.toms.model.Muser;
import com.sds.toms.model.Tbook;
import com.sds.toms.model.Tproduct;
import com.sds.toms.model.Tquest;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.pojo.ProductReq;
import com.sds.toms.pojo.QuestModel;
import com.sds.toms.util.AppData;
import com.sds.toms.util.AppUtil;
import com.sds.utils.config.ConfigUtil;

public class ProductFormVM {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();

	private Muser oUser;
	private Tproduct objForm;
	private Tbook book;
	private QuestModel quest;
	private boolean isInsert;
	private List<String> listQuest = new ArrayList<String>();
	private List<String> listBook = new ArrayList<String>();
	private List<Tbook> books = new ArrayList<Tbook>();
	private List<QuestModel> quests = new ArrayList<QuestModel>();

	@Wire
	private Window winProduct;
	@Wire
	private Div divFooter;
	@Wire
	private Radio rbGenerate, rbManual;
	@Wire
	private Combobox cbCategory, cbBook, cbQuest;
	@Wire
	private Grid gridGenerate, gridManual, gridQuest, gridMateri, gridQs;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("objForm") Tproduct objForm, @ExecutionArgParam("isEdit") String isEdit,
			@ExecutionArgParam("isDetail") String isDetail) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		doReset();
		if (objForm != null) {
			this.objForm = objForm;
			isInsert = false;
		}

	}

	@Command
	public void doReset() {
		objForm = new Tproduct();
		isInsert = true;
		listQuest = new ArrayList<String>();
		listBook = new ArrayList<String>();

		try {
			String url = "";
			ObjectResp rsp = new ObjectResp();

//			-------Get Combobox Mcategory-------
			url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_mcategory();
			rsp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);

			if (rsp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				List<Mcategory> objList = mapper.convertValue(rsp.getData(), new TypeReference<List<Mcategory>>() {
				});

				Comboitem comboitem = null;
				for (Mcategory category : objList) {
					comboitem = new Comboitem();
					comboitem.setLabel(category.getCategory());
					comboitem.setValue(category);
					cbCategory.appendChild(comboitem);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Command
	@NotifyChange({ "book", "quest" })
	public void doSelectCategory() {
		String url = "";
		ObjectResp rsp = new ObjectResp();

//		-------Get Combobox Tbook-------
		url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_tbook() + "/category/"
				+ objForm.getCategory().getId();
		try {
			rsp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);

			if (rsp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				List<Tbook> objList = mapper.convertValue(rsp.getData(), new TypeReference<List<Tbook>>() {
				});

				cbBook.getChildren().clear();
				Comboitem comboitem = null;
				for (Tbook book : objList) {
					comboitem = new Comboitem();
					comboitem.setLabel(book.getBookname());
					comboitem.setValue(book);
					cbBook.appendChild(comboitem);
				}
			}

			rbGenerate.setDisabled(false);
			rbManual.setDisabled(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("book")
	public void addBook() {
		if (book != null) {
			books.add(book);

			listBook.add(String.valueOf(book.getId()));
			doAddGridBook(books);
		}
	}

	private void doAddGridBook(List<Tbook> list) {
		gridMateri.setModel(new ListModelList<>(list));

		gridMateri.setRowRenderer(new RowRenderer<Tbook>() {

			@Override
			public void render(Row row, Tbook data, int index) throws Exception {

				row.getChildren().add(new Label(data.getBookname() != null ? data.getBookname() : ""));
				row.getChildren().add(new Label(data.getBookdesc() != null ? data.getBookdesc() : ""));

				A a = new A();
				Label label = new Label(data.getBooklink());
				a.appendChild(label);
//				a.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//
//					@Override
//					public void onEvent(Event event) throws Exception {
//						Map<String, Object> map = new HashMap<String, Object>();
//						map.put("fileid", data.getDocfileid());
//						Window win = (Window) Executions.createComponents("/view/docviewer.zul", null, map);
//						win.setWidth("90%");
//						win.setClosable(true);
//						win.doModal();
//						win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {
//
//							@Override
//							public void onEvent(Event event) throws Exception {
//								BindUtils.postNotifyChange(null, null, VerificationOtsVM.this, "*");
//							}
//						});
//					}
//				});

				row.appendChild(a);

				Button btnDelete = new Button(Labels.getLabel("common.delete"));
				btnDelete.setStyle("margin: 3px");
				btnDelete.setSclass("btn btn-danger btn-sm");
				btnDelete.setAutodisable("self");
				btnDelete.setTooltiptext("Delete");
				btnDelete.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
//						list.remove(data);
//						otsList.remove(data);
//
//						Tapxdoc doc = new TapxdocDAO().findByPk(data.getTapxdocpk());
//						if (doc != null) {
//							listDel.add(data);
//						}
//						gridDocVerif.setModel(new ListModelList<>(otsList));
					}
				});
//				row.getChildren().add(btnDelete);
			}
		});

	}

	@Command
	@NotifyChange("quest")
	public void autoGenerateQuest() {
		if (objForm.getTotalquest() != null && objForm.getTotalquest() == listQuest.size()) {

		} else {
			if (objForm.getTotalquest() != null) {
				String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_tquest()
						+ "/category/" + objForm.getCategory().getId();

				try {
					ObjectResp rsp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);

					if (rsp.getCode() == 200) {
						ObjectMapper mapper = new ObjectMapper();
						List<QuestModel> objList = mapper.convertValue(rsp.getData(),
								new TypeReference<List<QuestModel>>() {
								});

						System.out.println(objList.get(0).getCategory());
						System.out.println(objForm.getTotalquest());
						for (int i = 0; i < objForm.getTotalquest(); i++) {
							System.out.println(i);
							quests.add(objList.get(i));
							listQuest.add(objList.get(i).getId().toString());
						}
						gridQs.setVisible(true);
						doAddGridQuest(quests);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Clients.evalJavaScript("swal.fire({" + "icon: 'warning',\r\n" + "  title: 'Informasi',\r\n"
						+ "  text: 'Masukkan jumlah soal yang akan di generate'," + "})");
			}
		}
	}

	@Command
	public void doGenerate(@BindingParam("arg") String arg) {
		quests.clear();
		listQuest.clear();

		if (arg.equals("Generate")) {
			gridGenerate.setVisible(true);
			gridManual.setVisible(false);
			gridQs.setVisible(false);
		} else {
			gridGenerate.setVisible(false);
			gridManual.setVisible(true);
			gridQs.setVisible(true);
			String url = "";
			ObjectResp rsp = new ObjectResp();

//			-------Get Combobox Tquest-------
			url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_tquest() + "/category/"
					+ objForm.getCategory().getId();
			try {
				rsp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);

				if (rsp.getCode() == 200) {
					ObjectMapper mapper = new ObjectMapper();
					List<QuestModel> objList = mapper.convertValue(rsp.getData(),
							new TypeReference<List<QuestModel>>() {
							});

					cbQuest.getChildren().clear();
					Comboitem comboitem = null;
					for (QuestModel quest : objList) {
						comboitem = new Comboitem();
						comboitem.setLabel(quest.getQuesttext());
						comboitem.setValue(quest);
						cbQuest.appendChild(comboitem);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Command
	@NotifyChange("quest")
	public void addQuest() {
		gridQs.setVisible(true);
		if (objForm.getTotalquest() != null && objForm.getTotalquest() > 0) {
			if (gridQuest != null && gridQuest.getChildren().size() <= objForm.getTotalquest()) {
				if (quest != null) {
					quests.add(quest);
					listQuest.add(String.valueOf(quest.getId()));
					doAddGridQuest(quests);
				}
			} else {
				Clients.evalJavaScript("swal.fire({" + "icon: 'warning',\r\n" + "  title: 'Informasi',\r\n"
						+ "  text: 'Stok soal habis'," + "})");
			}
		} else {
			Clients.evalJavaScript("swal.fire({" + "icon: 'warning',\r\n" + "  title: 'Informasi',\r\n"
					+ "  text: 'Masukkan Jumlah Soal'," + "})");
		}
	}

	private void doAddGridQuest(List<QuestModel> list) {
		gridQuest.setModel(new ListModelList<>(list));

		gridQuest.setRowRenderer(new RowRenderer<QuestModel>() {

			@Override
			public void render(Row row, QuestModel data, int index) throws Exception {
				row.getChildren().add(new Label(String.valueOf(index + 1) + "."));
				row.getChildren().add(new Label(data.getQuesttext() != null ? data.getQuesttext() : ""));

				Button btnDetail = new Button();
				btnDetail.setClass("btn btn-sm btn-info");
				btnDetail.setIconSclass("z-icon-eye");
				btnDetail.setStyle("border-radius:200px; margin:3px");
				btnDetail.setTooltiptext("Detail");
				btnDetail.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
//						list.remove(data);
//						otsList.remove(data);
//
//						Tapxdoc doc = new TapxdocDAO().findByPk(data.getTapxdocpk());
//						if (doc != null) {
//							listDel.add(data);
//						}
//						gridDocVerif.setModel(new ListModelList<>(otsList));
					}
				});
				row.getChildren().add(btnDetail);
			}
		});

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange("*")
	public void doSave() {
		Messagebox.show(
				isInsert == true ? Labels.getLabel("common.add.confirm") : Labels.getLabel("common.update.confirm"),
				"Confirm Dialog", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {

					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							try {
								String url = "";
								ObjectMapper mapper = new ObjectMapper();
								ObjectResp rsp = new ObjectResp();

								objForm.setCreatedby(oUser.getUserid());

								if (isInsert) {
									url = ConfigUtil.getConfig().getUrl_base()
											+ ConfigUtil.getConfig().getEndpoint_tproduct();

									ProductReq objReq = getProductReq(objForm);

									rsp = RespHandler.responObj(url, mapper.writeValueAsString(objReq),
											AppUtil.METHOD_POST, oUser);
									if (rsp.getCode() == 201) {

										Clients.evalJavaScript("swal.fire({" + "icon: 'success',\r\n"
												+ "  title: 'Berhasil',\r\n" + "  text: '"
												+ Labels.getLabel("common.add.success") + "'," + "})");

									} else {
										if (rsp.getCode() != 201
												&& (rsp.getMessage().contains("ConstraintViolationException")
														|| rsp.getMessage().contains("duplicate"))) {
											Clients.evalJavaScript("swal.fire({" + "icon: 'warning',\r\n"
													+ "  title: 'Informasi',\r\n"
													+ "  text: 'Data gagal disimpan, kode role sudah terdaftar',"
													+ "})");
										} else {
											Clients.evalJavaScript(
													"swal.fire({" + "icon: 'warning',\r\n" + "  title: 'Informasi',\r\n"
															+ "  text: 'Data gagal disimpan'," + "})");
										}
									}
									
									Event closeEvent = new Event("onClose", winProduct, null);
									Events.postEvent(closeEvent);
								} else {
									url = ConfigUtil.getConfig().getUrl_base()
											+ ConfigUtil.getConfig().getEndpoint_tproduct();

									JSONObject jsonReq = new JSONObject();

									jsonReq.put("id", objForm.getId());
									jsonReq.put("productname", objForm.getProductimgname());
									jsonReq.put("productdesc", objForm.getProductdesc());
									jsonReq.put("duration", objForm.getDuration());
									jsonReq.put("passingscore", objForm.getPassingscore());
									jsonReq.put("price", objForm.getPrice());

									rsp = RespHandler.responObj(url, mapper.writeValueAsString(jsonReq),
											AppUtil.METHOD_PUT, oUser);
									if (rsp.getCode() == 201) {

										Clients.evalJavaScript("swal.fire({" + "icon: 'success',\r\n"
												+ "  title: 'Berhasil',\r\n" + "  text: '"
												+ Labels.getLabel("common.add.success") + "'," + "})");
										
										
										
									} else {
										if (rsp.getCode() != 201
												&& (rsp.getMessage().contains("ConstraintViolationException")
														|| rsp.getMessage().contains("duplicate"))) {
											Clients.evalJavaScript("swal.fire({" + "icon: 'warning',\r\n"
													+ "  title: 'Informasi',\r\n"
													+ "  text: 'Data gagal disimpan, kode role sudah terdaftar',"
													+ "})");
										} else {
											Clients.evalJavaScript(
													"swal.fire({" + "icon: 'warning',\r\n" + "  title: 'Informasi',\r\n"
															+ "  text: 'Data gagal disimpan'," + "})");
										}
									}
								}

								
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				});
	}

	private ProductReq getProductReq(Tproduct product) {
		ProductReq objReq = new ProductReq();
		objReq.setId(null);
		objReq.setCategoryid(product.getCategory().getId().intValue());
		objReq.setCategoryname(product.getCategory() != null ? product.getCategory().getCategory() : "");
		objReq.setProductname(product.getProductname() != null ? product.getProductname() : "");
		objReq.setProductid(product.getProductid() != null ? product.getProductid() : "");
		objReq.setProductdesc(product.getProductdesc() != null ? product.getProductdesc() : "");
		objReq.setPassingscore(product.getPassingscore() != null ? product.getPassingscore() : 0);
		objReq.setPrice(product.getPrice() != null ? product.getPrice() : null);
		objReq.setDuration(product.getDuration() != null ? product.getDuration() : 0);
		objReq.setTotalbook(product.getTotalbook() != null ? product.getTotalbook() : 0);
		objReq.setTotalquest(product.getTotalquest() != null ? product.getTotalquest() : 0);
		objReq.setBuildtype(product.getBuildtype() != null ? product.getBuildtype() : "");
		objReq.setBookids(listBook);
		objReq.setQuestids(listQuest);

		return objReq;
	}

	public Validator getValidator() {
		return new AbstractValidator() {

			@Override
			public void validate(ValidationContext ctx) {

				String productid = (String) ctx.getProperties("productid")[0].getValue();
				String productname = (String) ctx.getProperties("productname")[0].getValue();
//				Integer totalquest = (Integer) ctx.getProperties("totalquest")[0].getValue();
				Integer passingscore = (Integer) ctx.getProperties("passingscore")[0].getValue();
				BigDecimal price = (BigDecimal) ctx.getProperties("price")[0].getValue();
				String productdesc = (String) ctx.getProperties("productdesc")[0].getValue();

				if (productid == null || productid.isEmpty()) {
					this.addInvalidMessage(ctx, "productid", Labels.getLabel("common.validator.empty"));
				}

				if (productname == null || productname.isEmpty()) {
					this.addInvalidMessage(ctx, "productname", Labels.getLabel("common.validator.empty"));
				}

				if (objForm.getTotalquest() == null) {
					this.addInvalidMessage(ctx, "totalquest", Labels.getLabel("common.validator.empty"));
				}

				if (passingscore == null) {
					this.addInvalidMessage(ctx, "passingscore", Labels.getLabel("common.validator.empty"));
				}

				if (price == null) {
					this.addInvalidMessage(ctx, "price", Labels.getLabel("common.validator.empty"));
				}

				if (productdesc == null || productdesc.isEmpty()) {
					this.addInvalidMessage(ctx, "productdesc", Labels.getLabel("common.validator.empty"));
				}
			}
		};
	}

	public ListModelList<Mcategory> getMcategorymodel() {
		ListModelList<Mcategory> lm = null;
		try {
			lm = new ListModelList<Mcategory>(AppData.getMcategory());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public Muser getoUser() {
		return oUser;
	}

	public Tproduct getObjForm() {
		return objForm;
	}

	public void setoUser(Muser oUser) {
		this.oUser = oUser;
	}

	public void setObjForm(Tproduct objForm) {
		this.objForm = objForm;
	}

	public List<String> getListQuest() {
		return listQuest;
	}

	public List<String> getListBook() {
		return listBook;
	}

	public void setListQuest(List<String> listQuest) {
		this.listQuest = listQuest;
	}

	public void setListBook(List<String> listBook) {
		this.listBook = listBook;
	}

	public Tbook getBook() {
		return book;
	}

	public void setBook(Tbook book) {
		this.book = book;
	}

	public QuestModel getQuest() {
		return quest;
	}

	public void setQuest(QuestModel quest) {
		this.quest = quest;
	}
}
