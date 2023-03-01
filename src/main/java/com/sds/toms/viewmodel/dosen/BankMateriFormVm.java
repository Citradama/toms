package com.sds.toms.viewmodel.dosen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.zkoss.bind.BindContext;
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
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.handler.RespHandler;
import com.sds.toms.model.Mcategory;
import com.sds.toms.model.Muser;
import com.sds.toms.model.Tbook;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.util.AppData;
import com.sds.toms.util.AppUtil;
import com.sds.utils.config.ConfigUtil;

public class BankMateriFormVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private Tbook objForm;
	private Mcategory mcategory;
	private String filename;

	private Media media;
	private boolean isInsert;

	@Wire
	private Window window;
	@Wire
	private Combobox cbCategory;
	@Wire
	private Div divBtn;
	@Wire
	private Row rowUpload, rowLampiran;
	@Wire
	private Textbox tbDeskripsi, tbJudul;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("obj") Tbook obj,
			@ExecutionArgParam("isEdit") String isEdit, @ExecutionArgParam("isDetail") String isDetail) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		doReset();

		if (obj != null) {
			try {
				objForm = obj;
				isInsert = false;
				filename = obj.getBookname();
				
				ObjectResp Resp = null;
				String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_mcategory() + "/"
						+ obj.getCategoryid();

				Resp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);

				if (Resp.getCode() == 200) {
					ObjectMapper mapper = new ObjectMapper();
					mcategory = mapper.convertValue(Resp.getData(), new TypeReference<Mcategory>() {
					});
					
					cbCategory.setValue(mcategory.getCategory());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		if (isDetail != null && isDetail.equals("Y")) {
			
				divBtn.setVisible(false);
				rowLampiran.setVisible(true);
				rowUpload.setVisible(false);
				cbCategory.setButtonVisible(false);
				cbCategory.setDisabled(true);
				tbDeskripsi.setDisabled(true);
				tbJudul.setDisabled(true);
			
		}
	}
	
	@Command
	public void doView() {
		try {
			Sessions.getCurrent().setAttribute("reportPath", objForm.getBooklink());
			Executions.getCurrent().sendRedirect("/view/docviewer.zul", "_blank");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("filename")
	public void doUpload(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) {
		try {
			UploadEvent event = (UploadEvent) ctx.getTriggerEvent();
			media = event.getMedia();
			filename = media.getName();
			if (media != null) {
				if (media.getFormat().toUpperCase().equals("PDF") || media.getFormat().toUpperCase().equals("DOCX")) {
					String path = Executions.getCurrent().getDesktop().getWebApp()
							.getRealPath(AppUtil.FILES_ROOT_PATH + AppUtil.BOOK_PATH) + filename;
					objForm.setBooklink(path);
				} else {
					Clients.evalJavaScript("swal.fire({" + "icon: 'warning',\r\n" + "  title: 'Informasi',\r\n"
							+ "  text: 'Format file harus berupa pdf atau docx'," + "})");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	@NotifyChange("*")
	public void doSave() {
		try {
			Messagebox.show(
					isInsert == true ? Labels.getLabel("common.add.confirm") : Labels.getLabel("common.update.confirm"),
					"Confirm Dialog", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {

						public void onEvent(Event event) throws Exception {
							if (event.getName().equals("onOK")) {
								String url = "";
								ObjectMapper mapper = new ObjectMapper();
								ObjectResp rsp = null;

								objForm.setCategory(mcategory.getCategory());
								objForm.setCategoryid(mcategory.getId());

								url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_tbook();
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
										if (media.isBinary()) {
											Files.copy(new File(objForm.getBooklink()), media.getStreamData());
										} else {
											BufferedWriter writer = new BufferedWriter(
													new FileWriter(objForm.getBooklink()));
											Files.copy(writer, media.getReaderData());
											writer.close();
										}

										Tbook obj = mapper.convertValue(rsp.getData(), new TypeReference<Tbook>() {
										});
										url = ConfigUtil.getConfig().getUrl_base()
												+ ConfigUtil.getConfig().getEndpoint_tbook() + "/uploaddoc/"
												+ obj.getId();
										rsp = RespHandler.postMedia(url, objForm.getBooklink(), media.getName(), oUser);
										if (rsp.getCode() == 201 || rsp.getCode() == 200) {
											if (rsp.getCode() == 201)
												label = "common.add.success";
											else
												label = "common.update.success";
											Clients.evalJavaScript(
													"swal.fire({" + "icon: 'success',\r\n" + "  title: 'Berhasil',\r\n"
															+ "  text: '" + Labels.getLabel(label) + "'," + "})");

											doClose();
										} else {
											Clients.evalJavaScript(
													"swal.fire({" + "icon: 'warning',\r\n" + "  title: 'Informasi',\r\n"
															+ "  text: 'Data gagal disimpan'," + "})");
										}
									}
								} else {
									Clients.evalJavaScript("swal.fire({" + "icon: 'warning',\r\n"
											+ "  title: 'Informasi',\r\n" + "  text: 'Data gagal disimpan'," + "})");
								}
							}
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@NotifyChange("*")
	public void doReset() {
		objForm = new Tbook();
		objForm.setDosenid(oUser.getUserid());
		objForm.setDosenname(oUser.getUsername());
		mcategory = null;
		cbCategory.setValue(null);
		isInsert = true;
		media = null;
	}

	public void doClose() {
		Event closeEvent = new Event("onClose", window, null);
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

	public Tbook getObjForm() {
		return objForm;
	}

	public void setObjForm(Tbook objForm) {
		this.objForm = objForm;
	}

	public Mcategory getMcategory() {
		return mcategory;
	}

	public void setMcategory(Mcategory mcategory) {
		this.mcategory = mcategory;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
