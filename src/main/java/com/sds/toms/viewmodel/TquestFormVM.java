package com.sds.toms.viewmodel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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
import com.sds.toms.model.Muser;
import com.sds.toms.pojo.BanksoalReq;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.pojo.QuestAnswerModel;
import com.sds.toms.util.AppData;
import com.sds.toms.util.AppUtil;
import com.sds.utils.config.ConfigUtil;

public class TquestFormVM {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();

	private Muser oUser;
	private BanksoalReq objForm;
	private QuestAnswerModel objAnswer;
	private QuestAnswerModel objAnswerEdit;
	private boolean isInsert;
	private String dosenid;
	private String dosenname;
	private List<QuestAnswerModel> listAnswers = new ArrayList<QuestAnswerModel>();
	private List<QuestAnswerModel> objAnswerList = new ArrayList<QuestAnswerModel>();
	private Boolean isSetRight;
	private Div divRowEdit;
	private boolean isDetail;

	private Mcategory mcategory;
	private Media media;
	private String reason;

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
				String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_mcategory() + "/"
						+ objReq.getCategoryid();

				Resp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);

				if (Resp.getCode() == 200) {
					ObjectMapper mapper = new ObjectMapper();
					mcategory = mapper.convertValue(Resp.getData(), new TypeReference<Mcategory>() {
					});

					if (objForm.getQuestimglink() != null) {
						String linkimg = objForm.getQuestimglink().replace("\\", "/");
						System.out.println("LINK IMG : " + linkimg);
						img.setSrc(linkimg);
						img.setWidth("30%");
					}

					cbCategory.setValue(mcategory.getCategory());
					for (QuestAnswerModel data : objReq.getAnswers()) {
						objAnswer = data;
						doSaveAnswer();
						objAnswerList.add(data);
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

	@SuppressWarnings("deprecation")
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
			
			if (chkRight.isChecked() || (objAnswer.getIsright() != null && objAnswer.getIsright().equals("Y"))) {
				objAnswer.setIsright("Y");
			} else
				objAnswer.setIsright("N");
			listAnswers.add(objAnswer);
			
			Integer no = 0;
			divAnswers.getChildren().clear();
			for (QuestAnswerModel data : listAnswers) {
				objAnswer = data;
				Label lblAnswer = new Label(AppUtil.ALPHABETS[no] + ". " + objAnswer.getAnswertext());
				if(objAnswer.getIsright().equals("Y")) {
					isSetRight = true;
					lblAnswer.setStyle("font-weight: bold");
				}
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
						objAnswerEdit = (QuestAnswerModel) divGroup.getAttribute("obj");
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
												QuestAnswerModel objDel = (QuestAnswerModel) divGroup.getAttribute("obj");
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

				if (isDetail)
					divCol2.setVisible(false);

				divRow.appendChild(divCol2);

				divAnswers.appendChild(divRow);
				divAnswers.appendChild(new HtmlNativeComponent("hr"));
				no++;
			}
			
			doResetAnswer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doResetAnswer() {
		objAnswer = new QuestAnswerModel();
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
		objAnswer = new QuestAnswerModel();
		isInsert = true;
		divAnswers.getChildren().clear();
		cbCategory.setValue(null);
		img.setSrc(null);
		isDetail = false;
		media = null;

		dosenid = oUser.getUserid();
		dosenname = oUser.getUsername();
		reason = "";

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

									ObjectResp rsp = null;
									objForm.setCategory(mcategory.getCategory());
									objForm.setCategoryid(mcategory.getId());
									objForm.setDosenid(oUser.getUserid());
									objForm.setDosenname(oUser.getUsername());
									if (isInsert)
										objForm.setQuestid(new SimpleDateFormat("YYMMDDHHMMSS").format(new Date()));

									Integer no = 0;
									for (QuestAnswerModel data : listAnswers) {
										data.setAnswerno(AppUtil.ALPHABETS[no]);
										if (data.getIsright().equals("Y"))
											objForm.setRightanswer(AppUtil.ALPHABETS[no]);
										no++;
									}

									if (!isInsert) {
										for (QuestAnswerModel data : objAnswerList) {
											url = ConfigUtil.getConfig().getUrl_base()
													+ ConfigUtil.getConfig().getEndpoint_tquest() + "answer/"
													+ data.getId();
											rsp = RespHandler.responObj(url, mapper.writeValueAsString(data),
													AppUtil.METHOD_DEL, oUser);
											if (rsp.getCode() == 200) {
												System.out.println("UPDATE JAWABAN");
											}
										}
									}

									objForm.setAnswers(listAnswers);
									url = ConfigUtil.getConfig().getUrl_base()
											+ ConfigUtil.getConfig().getEndpoint_tquest();
									System.out.println("save : " + url);
									if (isInsert) {
										rsp = RespHandler.responObj(url, mapper.writeValueAsString(objForm),
												AppUtil.METHOD_POST, oUser);
									} else {
										rsp = RespHandler.responObj(url, mapper.writeValueAsString(objForm),
												AppUtil.METHOD_PUT, oUser);
									}
									String label = "";
									if (rsp.getCode() == 201 || rsp.getCode() == 200) {
										if (media != null) {
											objForm = mapper.convertValue(rsp.getData(),
													new TypeReference<BanksoalReq>() {
													});
											if (media.isBinary()) {
												Files.copy(new File(objForm.getQuestimglink()), media.getStreamData());
											} else {
												BufferedWriter writer = new BufferedWriter(
														new FileWriter(objForm.getQuestimglink()));
												Files.copy(writer, media.getReaderData());
												writer.close();
											}

											url = ConfigUtil.getConfig().getUrl_base()
													+ ConfigUtil.getConfig().getEndpoint_tquest() + "/uploadimg/"
													+ objForm.getId();
											rsp = RespHandler.postMedia(url, objForm.getQuestimglink(), media.getName(),
													oUser);
											if (rsp.getCode() == 201 || rsp.getCode() == 200) {
												if (rsp.getCode() == 201)
													label = "common.add.success";
												else
													label = "common.update.success";
												Clients.evalJavaScript("swal.fire({" + "icon: 'success',\r\n"
														+ "  title: 'Berhasil',\r\n" + "  text: '"
														+ Labels.getLabel(label) + "'," + "})");
											} else {
												Clients.evalJavaScript("swal.fire({" + "icon: 'warning',\r\n"
														+ "  title: 'Informasi',\r\n" + "  text: 'Data gagal disimpan',"
														+ "})");
											}
										} else {
											if (rsp.getCode() == 201)
												label = "common.add.success";
											else
												label = "common.update.success";
											Clients.evalJavaScript(
													"swal.fire({" + "icon: 'success',\r\n" + "  title: 'Berhasil',\r\n"
															+ "  text: '" + Labels.getLabel(label) + "'," + "})");
										}
									} else {
										Clients.evalJavaScript(
												"swal.fire({" + "icon: 'warning',\r\n" + "  title: 'Informasi',\r\n"
														+ "  text: 'Data gagal disimpan'," + "})");
									}
									doClose();

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
			media = event.getMedia();
			String path = Executions.getCurrent().getDesktop().getWebApp()
					.getRealPath(AppUtil.FILES_ROOT_PATH + AppUtil.IMAGE_PATH);
			if (media instanceof org.zkoss.image.Image) {
				img.setContent((org.zkoss.image.Image) media);
				img.setWidth("30%");
				objForm.setQuestimglink(path + media.getName());
			} else {
				media = null;
				img.setWidth(null);
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

	public QuestAnswerModel getObjAnswer() {
		return objAnswer;
	}

	public void setObjAnswer(QuestAnswerModel objAnswer) {
		this.objAnswer = objAnswer;
	}

	public QuestAnswerModel getObjAnswerEdit() {
		return objAnswerEdit;
	}

	public void setObjAnswerEdit(QuestAnswerModel objAnswerEdit) {
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
