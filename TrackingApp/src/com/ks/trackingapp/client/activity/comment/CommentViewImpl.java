package com.ks.trackingapp.client.activity.comment;

import java.util.Date;
import java.util.List;

import org.gwtbootstrap3.client.ui.gwt.CellTable;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.ks.trackingapp.client.TrackingApp;
import com.ks.trackingapp.client.activity.base.BaseViewImpl;
import com.ks.trackingapp.client.event.ReloadCommentEvent;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.model.AndroidItem;
import com.ks.trackingapp.shared.model.IBasic;
import com.ks.trackingapp.shared.model.IOSItem;

public class CommentViewImpl extends BaseViewImpl implements CommentView {

	private static CommentViewImplUiBinder uiBinder = GWT
			.create(CommentViewImplUiBinder.class);
	
	DateTimeFormat formatDate =  DateTimeFormat.getFormat("dd/MM/yyyy");


	interface CommentViewImplUiBinder extends UiBinder<Widget, CommentViewImpl> {
	}
	private final String COLUM_APPNAME = "App's Name";
	private final String COLUM_APPCOMMENT = "App's Comment";
	private final String COLUM_PLATFORM = "Platform";
	private final String COLUM_DATE = "Date";
	private final String COLUM_RATING = "Rating";
	@UiField(provided = true) CellTable<IBasic> tableCustomer;
	@UiField(provided = true) SimplePager pagerTableCustomer;
	@UiField HTMLPanel panelTableCustomer;
	AsyncDataProvider<IBasic> provider;
	
	@UiField ListBox lbPlatform;
	private int start = 0;

	public CommentViewImpl() {
		tableCustomer = new CellTable<IBasic>();
		tableCustomer.setPageSize(Config.PAGER_SIZE);
		addColumnTable();
		provider = new AsyncDataProvider<IBasic>() {
		      @Override
		      protected void onRangeChanged(HasData<IBasic> display) {
		    	  start = display.getVisibleRange().getStart();
		    	  TrackingApp.clientFactory.getEventBus().fireEvent(new ReloadCommentEvent());
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
		addTypePlatform();
	}
	
	private void addTypePlatform() {
		lbPlatform.addItem(Config.PLATFORM_IOS, String.valueOf(Config.PLATFORM_IOS_VALUE));
		lbPlatform.addItem(Config.PLATFORM_ANDROID, String.valueOf(Config.PLATFORM_ANDROID_VALUE));
	}
	private void addColumnTable() {
		 //add column platform
		 addColumn(tableCustomer, 20, new TextCell(), COLUM_PLATFORM, new GetValue<String>() {
		      @Override
		      public String getValue(IBasic item) {
		    	  if(item instanceof IOSItem){
		    		  return "IOS";
		    	  }
		    	  if(item instanceof AndroidItem){
		    		  return "<font color=\"red\">"+"Android" + "</font>";
		    	  }
		        return "";
		      }
		    }, new FieldUpdater<IBasic, String>() {
		      @Override
		      public void update(int index, IBasic object, String value) {
		    	 
		      }
		      
		    });
		//add column your app
		 addColumn(tableCustomer, 30, new TextCell(), COLUM_APPNAME, new GetValue<String>() {
		      @Override
		      public String getValue(IBasic item) {
		    	  if(item instanceof IOSItem){
		    		  return ((IOSItem)item).getAppName();
		    	  }
		    	  if(item instanceof AndroidItem){
		    		  return ((AndroidItem)item).getAppName();
		    	  }
		        return "";
		      }
		    }, new FieldUpdater<IBasic, String>() {
		      @Override
		      public void update(int index, IBasic object, String value) {
		    	 
		      }
		    });
		//add column your comment
		 addColumn(tableCustomer, 200, new TextCell(), COLUM_APPCOMMENT, new GetValue<String>() {
		      @Override
		      public String getValue(IBasic item) {
		    	  if(item instanceof IOSItem){
		    		  return ((IOSItem)item).getComment();
		    	  }
		    	  if(item instanceof AndroidItem){
		    		  return ((AndroidItem)item).getComment();
		    	  }
		        return "";
		      }
		    }, new FieldUpdater<IBasic, String>() {
		      @Override
		      public void update(int index, IBasic object, String value) {
		    	 
		      }
		    });
		 //add column date
		 addColumn(tableCustomer, 20, new TextCell(), COLUM_DATE, new GetValue<String>() {
		      @Override
		      public String getValue(IBasic item) {
		    	  if(item instanceof IOSItem){
		    		  Date date = ((IOSItem)item).getDate();
		    		  return formatDate.format(date);
		    	  }
		    	  if(item instanceof AndroidItem){
		    		  Date date = ((AndroidItem)item).getDate();
		    		  return  formatDate.format(date);
		    	  }
		        return "";
		      }
		    }, new FieldUpdater<IBasic, String>() {
		      @Override
		      public void update(int index, IBasic object, String value) {
		    	 
		      }
		    });
		 //add column rating
		 addColumn(tableCustomer, 20, new TextCell(), COLUM_RATING, new GetValue<String>() {
		      @Override
		      public String getValue(IBasic item) {
		    	  if(item instanceof IOSItem){
		    		  return ((IOSItem)item).getRating()+"";
		    	  }
		    	  if(item instanceof AndroidItem){
		    		  return ((AndroidItem)item).getRating()+"";
		    	  }
		        return "";
		      }
		    }, new FieldUpdater<IBasic, String>() {
		      @Override
		      public void update(int index, IBasic object, String value) {
		    	 
		      }
		    });
	}

	@Override
	public ListBox getListboxPlatform() {
		return lbPlatform;
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
	public void resetTable() {
		provider.getDataDisplays().clear();
	}

}
