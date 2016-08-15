package com.ks.trackingapp.client.activity.home;

import java.util.List;

import org.gwtbootstrap3.client.ui.gwt.ButtonCell;
import org.gwtbootstrap3.client.ui.gwt.CellTable;
import org.gwtbootstrap3.extras.bootbox.client.callback.ConfirmCallback;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.ks.trackingapp.client.TrackingApp;
import com.ks.trackingapp.client.activity.base.BaseViewImpl;
import com.ks.trackingapp.client.event.DeleteObjectEvent;
import com.ks.trackingapp.client.event.ReloadAppEvent;
import com.ks.trackingapp.client.view.KSDialogPanel;
import com.ks.trackingapp.shared.AppItem;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.model.IBasic;

public class HomeViewImpl extends BaseViewImpl implements HomeView {

	private static HomeViewImplUiBinder uiBinder = GWT
			.create(HomeViewImplUiBinder.class);

	interface HomeViewImplUiBinder extends UiBinder<Widget, HomeViewImpl> {
	}
	
	private final String COLUM_APPNAME = "App's Name";
	private final String COLUM_APPCOMMENT = "App's Comment";
	private final String COLUM_STT = "STT";
	private final String COLUM_PLATFORM = "Platform";
	private final String COLUM_DATE = "Date";
	private final String COLUM_PACKAGENAME = "PackageName";
	private final String COLUM_EDIT = "Edit";
	private final String COLUM_DELETE = "Delete";
	private int start = 0;
	@UiField Button btnNewApp;
	@UiField(provided = true) CellTable<IBasic> tableCustomer;
	@UiField(provided = true) SimplePager pagerTableCustomer;
	@UiField HTMLPanel panelTableCustomer;
	AsyncDataProvider<IBasic> provider;
	public HomeViewImpl() {
		// Paginaiton 
		tableCustomer = new CellTable<IBasic>();
		tableCustomer.setPageSize(Config.PAGER_SIZE);
		addColumnTable();
		provider = new AsyncDataProvider<IBasic>() {
		      @Override
		      protected void onRangeChanged(HasData<IBasic> display) {
		    	  start = display.getVisibleRange().getStart();
		    	  TrackingApp.clientFactory.getEventBus().fireEvent(new ReloadAppEvent());
		      }
		};
		
	    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    pagerTableCustomer = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		provider.addDataDisplay(tableCustomer);
		tableCustomer.addColumnStyleName(6, "xxx");
	    pagerTableCustomer.setDisplay(tableCustomer);

	    this.layout.getCenterPanel().add(uiBinder.createAndBindUi(this));
	    // End Pagination
		this.layout.getScrollPanel().refresh();
	}

	private void addColumnTable() {
		//add stt
		 addColumn(tableCustomer, 20, new TextCell(), COLUM_STT, new GetValue<String>() {
		      @Override
		      public String getValue(IBasic item) {
		    	  if(item instanceof AppItem){
		    		  return ((AppItem)item).getId()+"";
		    	  }
		        return "";
		      }
		    }, new FieldUpdater<IBasic, String>() {
		      @Override
		      public void update(int index, IBasic object, String value) {
		    	 
		      }
		    });
		//add column your app
		 addColumn(tableCustomer, 100, new TextCell(), COLUM_APPNAME, new GetValue<String>() {
		      @Override
		      public String getValue(IBasic item) {
		    	  if(item instanceof AppItem){
		    		  return ((AppItem)item).getAppName();
		    	  }
		        return "";
		      }
		    }, new FieldUpdater<IBasic, String>() {
		      @Override
		      public void update(int index, IBasic object, String value) {
		    	 
		      }
		    });
		 //add column platform
		 addColumn(tableCustomer, 100, new TextCell(), COLUM_PLATFORM, new GetValue<String>() {
		      @Override
		      public String getValue(IBasic item) {
		    	  if(item instanceof AppItem){
		    		  if(((AppItem)item).getPlatform() == Config.PLATFORM_ANDROID_VALUE){
		    			  return "Android";
		    		  }else{
		    			  return "IOS";
		    		  }
		    	  }
		        return "";
		      }
		    }, new FieldUpdater<IBasic, String>() {
		      @Override
		      public void update(int index, IBasic object, String value) {
		    	 
		      }
		    });
		
		//add column packageName
		 addColumn(tableCustomer, 100, new TextCell(), COLUM_PACKAGENAME, new GetValue<String>() {
		      @Override
		      public String getValue(IBasic item) {
		    	  if(item instanceof AppItem){
		    		  return ((AppItem)item).getPackageName();
		    	  }
		        return "";
		      }
		    }, new FieldUpdater<IBasic, String>() {
		      @Override
		      public void update(int index, IBasic object, String value) {
		    	 
		      }
		    });
		 //add columns edit
		 addColumn(tableCustomer, 100, new ButtonCell(), COLUM_EDIT, new GetValue<String>() {
		      @Override
		      public String getValue(IBasic item) {
		        return "Edit";
		      }
		    }, new FieldUpdater<IBasic, String>() {
		      @Override
		      public void update(int index, IBasic object, String value) {
		    	 Window.alert("edit");
		      }
		    });
		 //add columns delete
		 addColumn(tableCustomer, 100, new ButtonCell(), COLUM_DELETE, new GetValue<String>() {
		      @Override
		      public String getValue(IBasic item) {
		        return "Delete";
		      }
		    }, new FieldUpdater<IBasic, String>() {
		      @Override
		      public void update(int index, IBasic object, String value) {
		    	  HomeViewImpl.basic = object;
		    	  new KSDialogPanel("Xác nhận", new HTML("Bạn có chắc chắn muốn xoá?"), "Xoá", "Không", new ConfirmCallback() {
						@Override
						public void callback(boolean result) {
							if (result) {
								TrackingApp.getClientFactory().getEventBus().fireEvent(new DeleteObjectEvent(basic));
							}
						}
					}).show();

		      }
		    });
	}
	static IBasic basic;
	@Override
	public void refreshView() {
		super.refreshView();
	}

	@Override
	public Button getAddApp() {
		return btnNewApp;
	}

	@Override
	public void updateData(int start, List<IBasic> customers) {
		provider.updateRowData(start, customers);
	}

	@Override
	public int getStart() {
		return start;
	}

	@Override
	public void setStart(int start) {
		this.start = start;
	}


}
