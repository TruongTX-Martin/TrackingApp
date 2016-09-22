package com.ks.trackingapp.client.activity.newapp;

import org.gwtbootstrap3.client.ui.TextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.ks.trackingapp.client.TrackingApp;
import com.ks.trackingapp.client.activity.basic.BasicViewImpl;
import com.ks.trackingapp.client.manager.TrackingManager;
import com.ks.trackingapp.client.util.Toaster;
import com.ks.trackingapp.client.view.BHTouchImage;
import com.ks.trackingapp.shared.Config;

public class NewAppViewImpl extends BasicViewImpl implements NewAppView {

	private static NewAppViewImplUiBinder uiBinder = GWT
			.create(NewAppViewImplUiBinder.class);

	interface NewAppViewImplUiBinder extends UiBinder<Widget, NewAppViewImpl> {
	}

	private BHTouchImage btnCheck = new BHTouchImage("images/ic_check.png");

	protected @UiField
	TextBox tbAppName, tbPackageName, tbAppleId;
	protected @UiField
	CheckBox cbAndroid, cbIOS;
	protected @UiField
	Button btnCancel, btnOk, btnUpload,btnDeleteComment,btnDeleteApp;
	protected @UiField
	FlowPanel flowUpload, flowForm,flowButtonDelete;
	protected FormPanel formPanel = new FormPanel();
	protected FileUpload fileUpload = new FileUpload();

	public NewAppViewImpl() {
		this.layoutBasic.getScrollPanel().add(uiBinder.createAndBindUi(this));
		this.layoutBasic.getHeaderPanel().showNavigation(false);
		this.layoutBasic.getHeaderPanel().setCenter(Config.ITEMSCREEN_ADDAPP);
		btnCheck.setPixelSize(40, 40);
		initFileUpload();
	}

	private void initFileUpload() {
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_POST);
		formPanel.setAction(GWT.getModuleBaseURL() +"uploadfile");
		// form.setAction(/* WHAT SHOULD I PUT HERE */);

		VerticalPanel holder = new VerticalPanel();

		fileUpload.setName("upload");
		holder.add(fileUpload);
		Button button = new Button("Submit");
		button.addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent event) {
				formPanel.submit();
			}
		});

		formPanel.addSubmitHandler(new FormPanel.SubmitHandler() {
			public void onSubmit(SubmitEvent event) {
				if (fileUpload.getFilename().length() > 7) {
					TrackingApp.getClientFactory().getLoadingDialog().show();
					Toaster.showToast("Upload" + GWT.getHostPageBaseURL() +"uploadfile");
					//http://tracking-dot-qt3men.appspot.com/
//					formPanel.setAction("http://test-dot-trackingapp-144102.appspot.com/" +"uploadfile?userId="+TrackingManager.newInstance().getCurrentUser().getId());
					formPanel.setAction("http://127.0.0.1:8888/" +"uploadfile?userId="+TrackingManager.newInstance().getCurrentUser().getId());
				} else {
					
					Toaster.showToast("Please choose file upload");
				}
			}
		});

		formPanel.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
					public void onSubmitComplete(SubmitCompleteEvent event) {
						Toaster.showToast("Success");
//						TrackingApp.getClientFactory().getLoadingDialog().hide();
					}
				});
		
		formPanel.add(holder);
		flowForm.add(formPanel);

		btnUpload.addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent event) {
				formPanel.submit();
			}
		});
	}

	@Override
	public TextBox getTextboxAppname() {
		return tbAppName;
	}

	@Override
	public TextBox getTextboxPakageName() {
		return tbPackageName;
	}

	@Override
	public TextBox getTextboxAppleId() {
		return tbAppleId;
	}

	@Override
	public CheckBox getCheckBoxAndroid() {
		return cbAndroid;
	}

	@Override
	public CheckBox getCheckBoxIOS() {
		return cbIOS;
	}

	@Override
	public BHTouchImage getButtonCheck() {
		return btnCheck;
	}

	@Override
	public Button getButtonCancel() {
		return btnCancel;
	}

	@Override
	public Button getButtonOk() {
		return btnOk;
	}

	@Override
	public Button getButtonSubmit() {
		return btnUpload;
	}

	@Override
	public FormPanel getFormPanel() {
		return formPanel;
	}

	@Override
	public FileUpload getFileUpload() {
		return fileUpload;
	}

	@Override
	public Button getButtonDeleteComment() {
		return btnDeleteComment;
	}

	@Override
	public Button getButtonDeleteApp() {
		return btnDeleteApp;
	}

	@Override
	public FlowPanel getFlowPanelUpload() {
		return flowUpload;
	}

	@Override
	public FlowPanel getFlowPanelDelete() {
		return flowButtonDelete;
	}

}
