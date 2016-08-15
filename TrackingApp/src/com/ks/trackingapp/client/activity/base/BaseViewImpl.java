package com.ks.trackingapp.client.activity.base;
import java.util.Comparator;
import java.util.List;

import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.gwt.CellTable;

import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.RootFlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollEndEvent;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollEndEvent.Handler;
import com.ks.trackingapp.client.view.BhHeaderPanel;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.model.IBasic;

public class BaseViewImpl implements BaseView {

	private static BaseViewImplUiBinder baseUIBinder = GWT
			.create(BaseViewImplUiBinder.class);

	interface BaseViewImplUiBinder extends UiBinder<Widget, Layout> {
	}

	protected Layout layout;

	public BaseViewImpl() {
		this.layout = new Layout(this);
		baseUIBinder.createAndBindUi(this.layout);
		this.layout.getScrollPanel().addScrollEndHandler(new Handler() {
			@Override
			public void onScrollEnd(ScrollEndEvent event) {
				layout.getScrollPanel().refresh();
			}
		});
	}

	public static class Layout {
		private BaseViewImpl baseView;
		@UiField protected HTMLPanel mainPanel;
		@UiField protected ScrollPanel scrollPanel;
		@UiField protected FlowPanel centerPanel;
		@UiField protected AnchorButton tabApps,tabComment;
		
		public Layout(BaseViewImpl basicView) {
			this.baseView = basicView;
		}

		/**
		 * @return the mainPanel
		 */
		public HTMLPanel getMainPanel() {
			return mainPanel;
		}

		public FlowPanel getCenterPanel(){
			return centerPanel;
		}
		/**
		 * @return the headerPanel
		 */

		/**
		 * @return the basicView
		 */
		public BaseViewImpl getBaseView() {
			return baseView;
		}

		public ScrollPanel getScrollPanel() {
			return scrollPanel;
		}
		
		public AnchorButton getTapApps(){
			return tabApps;
		}
		public AnchorButton getTapComments(){
			return tabComment;
		}
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
	@Override
	public Widget asWidget() {
		return layout.getMainPanel();
	}

	@Override
	public FlowPanel getContentPanel() {
		return null;
	}

	@Override
	public void refreshView() {

	}

	@Override
	public int getViewId() {
		return 0;
	}

	@Override
	public Layout getLayout() {
		return layout;
	}

}
