package com.ks.trackingapp.client.view;

import java.util.Comparator;
import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.ModalFooter;
import org.gwtbootstrap3.client.ui.ModalHeader;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.ModalBackdrop;
import org.gwtbootstrap3.client.ui.gwt.CellTable;
import org.gwtbootstrap3.extras.bootbox.client.callback.ConfirmCallback;

import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;
import com.ks.trackingapp.client.view.KSButtonLoading.BUTTONLOADING_STATE;
import com.ks.trackingapp.shared.Config;
/* 
	 * Customize KSDialogPanel 
	 * author:7buon
	 */
import com.ks.trackingapp.shared.model.IBasic;

public class KSDialogPanel {
   private Modal modal = new Modal();
   private ModalHeader modalHeader = new ModalHeader();
   private ModalBody modalBody = new ModalBody();
   private ModalFooter modalFooter = new ModalFooter();
   private HTML htmlHeader = new HTML();
   private FlowPanel panelBody = new FlowPanel();
   private KSButtonLoading confirmButton = new KSButtonLoading("Confirm");
   private Button closeButton = new Button("Cancel");
   private ConfirmCallback confirmCallback;
   private boolean isShowing = false;
   private boolean isAutoHide = true;
   private Button buttonprint = new Button("Print");
   ButtonType PRIMARY,DANGER;
   
   public KSDialogPanel(){
	   modal.setClosable(false);
	   modal.setFade(true);
	   modal.setHideOtherModals(false);
	   modal.setDataKeyboard(true);
	   this.setEnableHideOverClick(false);
	   modal.setId("myModal");
	   modal.add(modalHeader);
	   modal.add(modalBody);
	   modal.add(modalFooter);
	   buttonprint.getElement().getStyle().setBackgroundColor("#253c65");
	   buttonprint.setStyleName("closebutton",true);
	   buttonprint.setType(PRIMARY);
	   buttonprint.setVisible(false);
	   modalHeader.add(htmlHeader);
	   modalBody.add(panelBody);
	   modalFooter.add(buttonprint);
	   modalFooter.add(confirmButton);
	   modalFooter.add(closeButton);
	   confirmButton.getElement().getStyle().setBackgroundColor("#253c65");
	   confirmButton.setText("Đăng nhập");
	   confirmButton.setDefaultStyle("btn-login");
	   confirmButton.updateState(BUTTONLOADING_STATE.NONE);
	   closeButton.addStyleName("closebutton");
	   confirmButton.setType(PRIMARY);
	   closeButton.setType(PRIMARY);
	   bind();
   }
   public void bind(){
//	   confirmButton.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				if(isAutoHide){
//					onConfirm();
//				}
//			}
//		});
	   confirmButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(isAutoHide){
					onConfirm();
				}
			}
		});
	   closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onCancel();
			}
		});
   }
   
   protected void onCancel() {
		if(confirmCallback != null)
			confirmCallback.callback(false);
		confirmButton.setEnabled(false);
		closeButton.setEnabled(false);
		hide();
	}

	protected void onConfirm() {
		if(confirmCallback != null)
			confirmCallback.callback(true);
		confirmButton.setEnabled(false);
		closeButton.setEnabled(false);
		hide();
	}

	public KSDialogPanel(String title, Widget contentWidget, String okText, String cancelText, ConfirmCallback callback) {
		this();
		setConfirmCallback(callback);
		updateInfo(title, contentWidget, okText, cancelText);
	}
	
	public void updateInfo(String title, Widget contentWidget, String okText, String cancelText) {
		modalHeader.setTitle(title);
		if(contentWidget != null){
			panelBody.clear();
			panelBody.add(contentWidget);
		}
		if(okText == null || okText.isEmpty()) {
			confirmButton.setVisible(false);
		} else{
			confirmButton.setVisible(true);
			confirmButton.setText(okText);
		}
		if(cancelText == null || cancelText.isEmpty()) {
			closeButton.setVisible(false);
		} else{
			closeButton.setVisible(true);
			closeButton.setText(cancelText);
		}
	}
	
	public void show(String title, Widget contentWidget, String okText, String cancelText, ConfirmCallback callback) {
		setConfirmCallback(callback);
		updateInfo(title, contentWidget, okText, cancelText);
		show();
		
	}
	
	public void show(IBasic iBasic, String title, Widget contentWidget, String okText, String cancelText, ConfirmCallback callback) {
		this.confirmCallback = callback;
		setConfirmCallback(callback);
		updateInfo(title, contentWidget, okText, cancelText);
		updateData(iBasic);
		show();
		
	}
	
	public void show(List<IBasic> iBasics, String title, Widget contentWidget, String okText, String cancelText, ConfirmCallback callback) {
		setConfirmCallback(callback);
		updateInfo(title, contentWidget, okText, cancelText);
		updateData(iBasics);
		show();
		
	}
	
	public void show() {
		modal.show();
		getConfirmButton().setEnabled(true);
		getCloseButton().setEnabled(true);
		isShowing = true;
	}
	
	public Button getButtonprint() {
		return buttonprint;
	}
	public Modal getModal() {
		return modal;
	}
	
	public ModalHeader getModalHeaderPanel() {
		return modalHeader;
	}
	
	public ModalFooter getModalFooterPanel() {
		return modalFooter;
	}
	
	public ModalHeader getModalHeader() {
		return modalHeader;
	}
	public ModalBody getModalBody() {
		return modalBody;
	}
	public ModalFooter getModalFooter() {
		return modalFooter;
	}
	public HTML getHtmlHeader() {
		return htmlHeader;
	}
	public FlowPanel getPanelBody() {
		return panelBody;
	}
	public KSButtonLoading getConfirmButton() {
		return confirmButton;
	}
	public Button getCloseButton() {
		return closeButton;
	}
	public ConfirmCallback getConfirmCallback() {
		return confirmCallback;
	}
	public void hide() {
		modal.hide();
		isShowing = false;
	}
	
	public boolean isAutoHide() {
		return isAutoHide;
	}
	public void setAutoHide(boolean isAutoHide) {
		this.isAutoHide = isAutoHide;
	}
	public void setConfirmCallback(ConfirmCallback confirmCallback) {
		this.confirmCallback = confirmCallback;
	}
	public void setEnableHideOverClick(boolean enableHideClick) {
		modal.setClosable(enableHideClick);
		modal.setDataBackdrop(enableHideClick ? ModalBackdrop.TRUE : ModalBackdrop.STATIC);
	}
	
	public boolean isShowing() {
		return isShowing;
	}
	public void updateData(IBasic object) {
		
	}
	
	public void updateData(List<IBasic> objects) {
		
	}
	

	protected ListHandler<IBasic> sortHandler;
	protected List<AbstractEditableCell<?, ?>> editableCells;
	
	public static final ProvidesKey<IBasic> KEY_PROVIDER = new ProvidesKey<IBasic>() {
	      @Override
	      public Object getKey(IBasic item) {
	        return item == null ? null : item.getId() == null ? Config.NULL_ID : item.getId();
	      }
	};
	
	protected CellTable<IBasic> initCellTable(ListDataProvider<IBasic> dataProvider) {
		CellTable<IBasic> cellTable = new CellTable<IBasic>(KEY_PROVIDER);
        cellTable.setWidth("100%", true);

        // Do not refresh the headers and footers every time the data is updated.
        cellTable.setAutoHeaderRefreshDisabled(true);
        cellTable.setAutoFooterRefreshDisabled(true);

        // Attach a column sort handler to the ListDataProvider to sort the list.
        sortHandler = new ListHandler<IBasic>(dataProvider.getList());
        cellTable.addColumnSortHandler(sortHandler);

        // Add a selection model so we can select cells.
        final SelectionModel<IBasic> selectionModel = new MultiSelectionModel<IBasic>(KEY_PROVIDER);
        cellTable.setSelectionModel(selectionModel,
            DefaultSelectionEventManager.<IBasic> createCheckboxManager());

        // Initialize the columns.
        // Add the CellList to the adapter in the database.
        dataProvider.addDataDisplay(cellTable);
        return cellTable;
	}
	
	 /**
	   * Get a cell value from a record.
	   * 
	   * @param <C> the cell type
	   */
	protected static interface GetValue<C> {
	    C getValue(IBasic contact);
	  }
	  
	protected <C> Column<IBasic, C> addColumn(CellTable<IBasic> cellTable, int width, Cell<C> cell, String headerText,
		      final GetValue<C> getter, FieldUpdater<IBasic, C> fieldUpdater) {
		  return this.addColumn(cellTable, width, null, cell, headerText, getter, fieldUpdater, null);
	  }
	  
	protected <C> Column<IBasic, C> addColumn(CellTable<IBasic> cellTable, int width, Cell<C> cell, String headerText,
		      final GetValue<C> getter, FieldUpdater<IBasic, C> fieldUpdater, Comparator<IBasic> comparator) {
		  return this.addColumn(cellTable, width, null, cell, headerText, getter, fieldUpdater, comparator);
	  }
	
	/**
	   * Add a column with a header.
	   * 
	   * @param <C> the cell type
	   * @param cell the cell used to render the column
	   * @param headerText the header string
	   * @param getter the value getter for the cell
	   */
	protected <C> Column<IBasic, C> addColumn(CellTable<IBasic> cellTable, int width, HorizontalAlignmentConstant align, Cell<C> cell, String headerText,
	      final GetValue<C> getter, FieldUpdater<IBasic, C> fieldUpdater, Comparator<IBasic> comparator) {
	    Column<IBasic, C> column = new Column<IBasic, C>(cell) {
	      @Override
	      public C getValue(IBasic object) {
	        return getter.getValue(object);
	      }
	    };
	    if (comparator != null) {
	    	column.setSortable(true);
	    	sortHandler.setComparator(column, comparator);
	    }
	    column.setFieldUpdater(fieldUpdater);
	    if (cell instanceof AbstractEditableCell<?, ?>) {
	      editableCells.add((AbstractEditableCell<?, ?>) cell);
	    }
	    cellTable.addColumn(column, headerText);
	    if (width > 0)
	    	cellTable.setColumnWidth(column, width, Unit.PX);
	    if (align != null)
	    	column.setHorizontalAlignment(align);
	    return column;
	  }
}
