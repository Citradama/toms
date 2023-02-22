package com.sds.toms.viewmodel;

import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.Input;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlNativeComponent;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WebApps;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.handler.RespHandler;
import com.sds.toms.model.Mcategory;
import com.sds.toms.model.Mcust;
import com.sds.toms.model.Mdosen;
import com.sds.toms.model.Muser;
import com.sds.toms.model.Tquest;
import com.sds.toms.model.Tquestanswer;
import com.sds.toms.pojo.BanksoalReq;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.pojo.QuestAnswerModel;
import com.sds.toms.pojo.SearchReq;
import com.sds.toms.util.AppData;
import com.sds.toms.util.AppUtil;
import com.sds.utils.config.ConfigUtil;

public class TquestFormVM {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();

	private Muser oUser;
	private BanksoalReq objForm;
	private Tquestanswer objAnswer;
	private Tquestanswer objAnswerEdit;
	private boolean isInsert;
	private String dosenid;
	private String dosenname;
	private List<Tquestanswer> listAnswers = new ArrayList<Tquestanswer>();
	private Boolean isSetRight;
	private Div divRowEdit;
	private boolean isDetail;

	private Mcategory mcategory;
	private Media media;
	private String questimgname;

	@Wire
	private Window winCategory;
	@Wire
	private Div divFooter, divPassword, divAnswers, divSetright;
	@Wire
	private Combobox cbCategory;
	@Wire
	private Checkbox chkRight;
	@Wire
	private Image img;
	@Wire
	private Textbox tbQuest, tbAnswer;
	@Wire
	private Button btnImage;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("obj") BanksoalReq objReq, @ExecutionArgParam("isEdit") String isEdit,
			@ExecutionArgParam("isDetail") String isDetail) {
		Selectors.wireComponents(view, this, false);
		try {
			oUser = (Muser) zkSession.getAttribute("oUser");
			doReset();

			if (objReq != null) {
				objForm = objReq;
				isInsert = false;
			}
			
			if (isDetail != null && isDetail.equals("Y")) {
				divFooter.setVisible(false);
				cbCategory.setReadonly(true);
				cbCategory.setButtonVisible(false);
				divSetright.setVisible(false);
				tbQuest.setDisabled(true);
				tbAnswer.setVisible(false);
				btnImage.setVisible(false);
				this.isDetail = true;
			}

			if ((isEdit != null && isEdit.equals("Y")) || (isDetail != null && isDetail.equals("Y"))) {
				ObjectResp Resp = null;
				String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_mcategory() + "/" + objReq.getCategoryid();

				Resp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);

				if (Resp.getCode() == 200) {
					ObjectMapper mapper = new ObjectMapper();
					mcategory = mapper.convertValue(Resp.getData(), new TypeReference<Mcategory>() {
					});

					cbCategory.setValue(mcategory.getCategory());
					for(QuestAnswerModel data : objReq.getAnswers()) {
						objAnswer.setAnswertext(data.getAnswertext());
						System.out.println(data.getAnswertext());
						doSaveAnswer();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	public void doCheck() {
		if (chkRight.isChecked()) {
			objForm.setRightanswer("Y");
		} else {
			objForm.setRightanswer("N");
		}
	}

	@Command
	@NotifyChange("objAnswer")
	public void doSaveAnswer() {
		try {
			if (objAnswerEdit != null) {
				divAnswers.removeChild(divRowEdit.getNextSibling());
				divAnswers.removeChild(divRowEdit);
				listAnswers.remove(objAnswerEdit);

				if (!chkRight.isChecked() && objAnswerEdit.getIsright().equals("Y")) {
					isSetRight = false;
				}
			}
			Label lblAnswer = new Label(objAnswer.getAnswertext());
			if (chkRight.isChecked()) {
				isSetRight = true;
				objAnswer.setIsright("Y");
				lblAnswer.setStyle("font-weight: bold");
			} else
				objAnswer.setIsright("N");
			listAnswers.add(objAnswer);

			Div divRow = new Div();
			divRow.setClass("row");

			Div divCol1 = new Div();
			divCol1.setClass("col-8");
			divCol1.appendChild(lblAnswer);
			divRow.appendChild(divCol1);

			Div divCol2 = new Div();
			divCol2.setClass("col-4");

			Div divGroup = new Div();
			divGroup.setAttribute("obj", objAnswer);
			divGroup.setClass("btn-group");
			divGroup.setAlign("right");
			Button btEdit = new Button("Edit");
			btEdit.setAutodisable("self");
			btEdit.setSclass("btn btn-default btn-sm");
			btEdit.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				@Override
				public void onEvent(Event event) throws Exception {
					divRowEdit = divRow;
					objAnswerEdit = (Tquestanswer) divGroup.getAttribute("obj");
					objAnswer = objAnswerEdit;
					if (objAnswerEdit.getIsright() != null && objAnswerEdit.getIsright().equals("Y")) {
						chkRight.setDisabled(false);
						chkRight.setChecked(true);
					} else {
						chkRight.setChecked(false);
						if (isSetRight)
							chkRight.setDisabled(true);
						else
							chkRight.setDisabled(false);
					}

					BindUtils.postNotifyChange(null, null, TquestFormVM.this, "objAnswer");
				}
			});
			divGroup.appendChild(btEdit);

			Button btDelete = new Button("Hapus");
			btDelete.setAutodisable("self");
			btDelete.setSclass("btn btn-default btn-sm");
			btDelete.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				@Override
				public void onEvent(Event event) throws Exception {
					Messagebox.show(Labels.getLabel("common.delete.confirm"), WebApps.getCurrent().getAppName(),
							Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener<Event>() {

								@Override
								public void onEvent(Event event) throws Exception {
									if (event.getName().equals("onOK")) {
										try {
											Tquestanswer objDel = (Tquestanswer) divGroup.getAttribute("obj");
											listAnswers.remove(objDel);
											divAnswers.removeChild(divRow.getNextSibling());
											divAnswers.removeChild(divRow);
											if (objDel.getIsright() != null && objDel.getIsright().equals("Y")) {
												isSetRight = false;
												chkRight.setDisabled(false);
											}
											doResetAnswer();
											BindUtils.postNotifyChange(null, null, TquestFormVM.this, "objAnswer");
											BindUtils.postNotifyChange(null, null, TquestFormVM.this, "objAnswerEdit");
										} catch (Exception e) {
											Messagebox.show(e.getMessage(), WebApps.getCurrent().getAppName(),
													Messagebox.OK, Messagebox.ERROR);
											e.printStackTrace();
										}
									}
								}

							});
				}
			});
			divGroup.appendChild(btDelete);
			divCol2.appendChild(divGroup);
			
			if(isDetail)
				divCol2.setVisible(false);
			
			divRow.appendChild(divCol2);

			divAnswers.appendChild(divRow);
			divAnswers.appendChild(new HtmlNativeComponent("hr"));
			doResetAnswer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doResetAnswer() {
		objAnswer = new Tquestanswer();
		chkRight.setChecked(false);
		if (isSetRight != null && isSetRight)
			chkRight.setDisabled(true);
		objAnswerEdit = null;
		divRowEdit = null;
	}

//	@Command
//	public void doAddAnswer() {
//		list.add(objAnswer);
//
//	}

	@NotifyChange("*")
	public void doReset() {
		oUser = (Muser) zkSession.getAttribute("oUser");
		objForm = new BanksoalReq();
		objAnswer = new Tquestanswer();
		isInsert = true;
		divAnswers.getChildren().clear();
		cbCategory.setValue(null);
		img.setSrc(null);
		isDetail = false;
		
		dosenid = oUser.getUserid();
		dosenname = oUser.getUsername();

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange("*")
	public void doSave() {
		try {
			Messagebox.show(
					isInsert == true ? Labels.getLabel("common.add.confirm") : Labels.getLabel("common.update.confirm"),
					"Confirm Dialog", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {

						public void onEvent(Event event) throws Exception {
							if (event.getName().equals("onOK")) {
								Muser oUser = (Muser) zkSession.getAttribute("oUser");
								try {
									String url = "";
									ObjectMapper mapper = new ObjectMapper();
									if (isInsert) {
										ObjectResp rsp = null;
										objForm = new BanksoalReq();
										objForm.setCategory(mcategory.getCategory());
										objForm.setCategoryid(mcategory.getId());
										objForm.setDosenid(oUser.getUserid());
										objForm.setDosenname(oUser.getUsername());
										objForm.setQuestid(new SimpleDateFormat("YYMMDDHHMMSS").format(new Date()));

										List<QuestAnswerModel> list = new ArrayList<QuestAnswerModel>();
										Integer no = 0;
										for (Tquestanswer data : listAnswers) {
											data.setAnswerno(AppUtil.ALPHABETS[no]);
											data.setUpdatedby(oUser.getUserid());
											if (data.getIsright().equals("Y"))
												objForm.setRightanswer(AppUtil.ALPHABETS[no]);

											QuestAnswerModel qam = new QuestAnswerModel();
											qam.setAnswerno(data.getAnswerno());
											qam.setAnswertext(data.getAnswertext());
											qam.setIsright(data.getIsright());
											list.add(qam);

											no++;
										}

										objForm.setAnswers(list);
										url = ConfigUtil.getConfig().getUrl_base()
												+ ConfigUtil.getConfig().getEndpoint_tquest();
										System.out.println("save : " + url);
										rsp = RespHandler.responObj(url, mapper.writeValueAsString(objForm),
												AppUtil.METHOD_POST, oUser);
										if (rsp.getCode() == 201) {
											Clients.evalJavaScript("swal.fire({" + "icon: 'success',\r\n"
													+ "  title: 'Informasi',\r\n" + "  text: '"
													+ Labels.getLabel("common.add.success") + "'," + "})");
										} else {
											Clients.evalJavaScript(
													"swal.fire({" + "icon: 'warning',\r\n" + "  title: 'Informasi',\r\n"
															+ "  text: 'Data gagal disimpan'," + "})");
										}
										doClose();

									} else {
										objForm.setUpdatedby(oUser.getUserid());
										objForm.setLastupdated(null);
										objForm.setCreatetime(null);

										url = ConfigUtil.getConfig().getUrl_base()
												+ ConfigUtil.getConfig().getEndpoint_tquest();
										System.out.println("update : " + url);

										ObjectResp respobj = new ObjectResp();
										respobj = RespHandler.responObj(url, mapper.writeValueAsString(objForm),
												AppUtil.METHOD_PUT, oUser);

										if (respobj.getCode() == 200) {
											Clients.evalJavaScript("swal.fire({" + "icon: 'success',\r\n"
													+ "  title: 'Berhasil',\r\n" + "  text: '"
													+ Labels.getLabel("common.update.success") + "'," + "})");
										}
										doClose();
									}

								} catch (Exception e) {
									if (isInsert)
										Messagebox.show("Error : " + e.getMessage(), WebApps.getCurrent().getAppName(),
												Messagebox.OK, Messagebox.ERROR);
									if (e.getCause() instanceof ConnectException) {
										Messagebox.show(e.getMessage(), WebApps.getCurrent().getAppName(),
												Messagebox.OK, Messagebox.ERROR);
									}
									e.printStackTrace();
								}
							}
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Command
	@NotifyChange("*")
	public void doUpload(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) {
		try {
			UploadEvent event = (UploadEvent) ctx.getTriggerEvent();
			Media media = event.getMedia();
			if (media instanceof org.zkoss.image.Image) {
				img.setContent((org.zkoss.image.Image) media);
			} else {
				media = null;
				Messagebox.show("Not an image: " + media, "Error", Messagebox.OK, Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	public void doClose() {
		Event closeEvent = new Event("onClose", winCategory, null);
		Events.postEvent(closeEvent);
	}

	public Validator getValidator() {
		return new AbstractValidator() {

			@Override
			public void validate(ValidationContext ctx) {

//				String category = (String) ctx.getProperties("category")[0].getValue();
//
//				if (category == null || category.isEmpty()) {
//					this.addInvalidMessage(ctx, "category", Labels.getLabel("common.validator.empty"));
//				}

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


	public String getDosenid() {
		return dosenid;
	}

	public String getDosenname() {
		return dosenname;
	}

	public void setDosenid(String dosenid) {
		this.dosenid = dosenid;
	}

	public void setDosenname(String dosenname) {
		this.dosenname = dosenname;
	}

	public Tquestanswer getObjAnswer() {
		return objAnswer;
	}

	public void setObjAnswer(Tquestanswer objAnswer) {
		this.objAnswer = objAnswer;
	}

	public Tquestanswer getObjAnswerEdit() {
		return objAnswerEdit;
	}

	public void setObjAnswerEdit(Tquestanswer objAnswerEdit) {
		this.objAnswerEdit = objAnswerEdit;
	}

	public Mcategory getMcategory() {
		return mcategory;
	}

	public void setMcategory(Mcategory mcategory) {
		this.mcategory = mcategory;
	}

	public BanksoalReq getObjForm() {
		return objForm;
	}

	public void setObjForm(BanksoalReq objForm) {
		this.objForm = objForm;
	}

}
