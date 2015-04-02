package latihan

import java.awt.event.ItemEvent;
import latihan.widget.GeneralFunction




import org.vaadin.dialogs.ConfirmDialog


import com.vaadin.data.Property
import com.vaadin.data.Property.ValueChangeEvent
import com.vaadin.data.fieldgroup.BeanFieldGroup
import com.vaadin.data.fieldgroup.FieldGroup
import com.vaadin.data.fieldgroup.FieldGroup.CommitException
import com.vaadin.data.util.BeanItem
import com.vaadin.data.util.BeanItemContainer
import com.vaadin.event.Action
import com.vaadin.event.ItemClickEvent
import com.vaadin.event.Action.Handler
import com.vaadin.event.ItemClickEvent.ItemClickListener
import com.vaadin.event.MouseEvents.ClickEvent
import com.vaadin.event.MouseEvents.ClickListener
import com.vaadin.server.DefaultErrorHandler
import com.vaadin.server.UserError
import com.vaadin.ui.Button
import com.vaadin.ui.ComboBox
import com.vaadin.ui.Component
import com.vaadin.shared.ui.datefield.Resolution
import com.vaadin.ui.DateField
import com.vaadin.ui.Field
import com.vaadin.ui.FormLayout
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Label
import com.vaadin.ui.MenuBar
import com.vaadin.ui.Notification
import com.vaadin.ui.Table
import com.vaadin.ui.TextArea
import com.vaadin.ui.TextField
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Window
import com.vaadin.ui.MenuBar.MenuItem
import latihan.BookingService





import com.vaadin.grails.Grails

class MasterBooking extends VerticalLayout{
	def selectedRow
	def itemlist
	GeneralFunction generalFunction = new GeneralFunction()
	private MenuBar menuBar
	private Window window
	private TextField textId
	private TextField textSKU
	private TextField textDescription
	
	//==============================
	private ComboBox cmbFacility
	private ComboBox cmbCustomer
	private DateField textStartDate
	private ComboBox cmbStartTime
	private TextField textDuration
	private TextField textCustomer
	private DateField textDateStartUsing
	private ComboBox cmbStartUsing
	private DateField textDateEndUsing
	private ComboBox cmbEndUsing
	
	//==============================
	
	private Table table = new Table();
	private BeanItemContainer<Booking> tableContainer;
	private FieldGroup fieldGroup;
	private FormLayout layout
	private Action actionDelete = new Action("Delete");
	private int code = 1;
	String Title = "Facility"
	
	public MasterBooking() {
//		currentUser = SecurityUtils.getSubject();
		
		initTable();
		setSizeFull()
		HorizontalLayout menu = new HorizontalLayout()
		menu.setWidth("100%")
//		menu.addComponent(createAddButton())
//		menu.addComponent(createUpdateButton())
//		menu.addComponent(createDeleteButton())
//		
		addComponent(menu)
		
		
//		EVENT CLICK MENUBAR
		menuBar = new MenuBar()
		MenuBar.Command mycommand = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				switch(selectedItem.getText())
				{
					case "Add":
						def item = new BeanItem<Booking>(tableContainer);
						windowAdd("Add");
					break
					case "Edit":
						if (table.getValue() != null)
						windowEdit(tableContainer.getItem(table.getValue()),"Edit");
					break;
					case "Mulai":
						if (table.getValue() != null)
						windowMulai(tableContainer.getItem(table.getValue()),"Mulai Pemakaian");
					break
					case "Selesai":
					if (table.getValue() != null)
					windowSelesai(tableContainer.getItem(table.getValue()),"Selesai Pemakaian");
					break
					case "Delete":
						if (table.getValue() != null)
						windowDelete("Delete");
					break;
				break;
				}
			}
		};
//	END EVENT CLICK
	
	// UNTUK BUTTON MENU 
		MenuItem saveMenu =  menuBar.addItem("Add",mycommand)
		MenuItem updateMenu = menuBar.addItem("Edit", mycommand)
		MenuItem deleteMenu = menuBar.addItem("Delete", mycommand)
		MenuItem mulaiMenu = menuBar.addItem("Mulai", mycommand)
		MenuItem selesaiMenu = menuBar.addItem("Selesai", mycommand)
		menu.addComponent(menuBar)
		menuBar.setWidth("100%")	
		//	END BUTTON MENU
	
		addComponent(table)
		setExpandRatio(menu,0)
		setExpandRatio(table,20)
//		table.setPageLength(table.size())
	}
	
	
	private Button createCancelButton() {
		def saveButton = new Button("Cancel", new Button.ClickListener() {
			void buttonClick(Button.ClickEvent event) {
//				window.setCaption(textSKU.getValue())
//				textSKU.discard()
				window.close()
			}
		  })
	}
	
	private Button createSaveButton() {
		def saveButton = new Button("Save", new Button.ClickListener() {
			
			void buttonClick(Button.ClickEvent event) {
				try{
					def object = [id:textId.getValue(),
								  facility1:cmbFacility.getValue(),
								  customer1:cmbCustomer.getValue(),
								  startDate:textStartDate.getValue(),
								  startTime:cmbStartTime.getValue().toString(),
								  duration:textDuration.getValue().toString(),
								  ]
					
					if (object.id == "")
					{
						object =  Grails.get(BookingService).createObject(object)
					}
					else
					{
						object =  Grails.get(BookingService).updateObject(object)
					}
					
					
					if (object.errors.hasErrors())
					{
						cmbFacility.setData("facility1")
						cmbCustomer.setData("customer1")
						textStartDate.setData("startDate")
						cmbStartTime.setData("startTime")
						textDuration.setData("duration")
						Object[] tv = [cmbFacility,cmbCustomer,textStartDate,cmbStartTime,textDuration]
						generalFunction.setErrorUI(tv,object)
					}
					else
					{
						window.close()
					}
					initTable()
				}catch (Exception e)
				{
					Notification.show("Error\n",
						e.getMessage(),
						Notification.Type.ERROR_MESSAGE);
				}
				 
				
			}
		  })
	}
	
	private Button saveStartButton() {
		def saveStartButton = new Button("Save", new Button.ClickListener() {
			
			void buttonClick(Button.ClickEvent event) {
				try{
					def object = [id:textId.getValue(),
								  dateStartUsing:textDateStartUsing.getValue(),
								  startUsing:cmbStartUsing.getValue().toString(),
								  ]
					
					
						object =  Grails.get(BookingService).startUsing(object)
				
					
					
					if (object.errors.hasErrors())
					{
						textDateStartUsing.setData("dateStartUsing")
						cmbStartUsing.setData("startUsing")
						Object[] tv = [textDateStartUsing,cmbStartUsing]
						generalFunction.setErrorUI(tv,object)
					}
					else
					{
						window.close()
					}
					initTable()
				}catch (Exception e)
				{
					Notification.show("Error\n",
						e.getMessage(),
						Notification.Type.ERROR_MESSAGE);
				}
				 
				
			}
		  })
	}
	
	private Button saveEndButton() {
		def saveEndButton = new Button("Save", new Button.ClickListener() {
			
			void buttonClick(Button.ClickEvent event) {
				try{
					def object = [id:textId.getValue(),
								  dateEndUsing:textDateEndUsing.getValue(),
								  endUsing:cmbEndUsing.getValue().toString(),
								  ]
					
					
						object =  Grails.get(BookingService).endUsing(object)
				
					
					
					if (object.errors.hasErrors())
					{
						textDateEndUsing.setData("dateEndUsing")
						cmbEndUsing.setData("endUsing")
						Object[] tv = [textDateEndUsing,cmbEndUsing]
						generalFunction.setErrorUI(tv,object)
					}
					else
					{
						window.close()
					}
					initTable()
				}catch (Exception e)
				{
					Notification.show("Error\n",
						e.getMessage(),
						Notification.Type.ERROR_MESSAGE);
				}
				 
				
			}
		  })
	}
//	===========================================
//	WINDOW DELETE
//	===========================================
	
	//@RequiresPermissions("Master:Item:Delete")
	private void windowDelete(String caption) {
//		if (currentUser.isPermitted(Title + Constant.AccessType.Delete)) {
			ConfirmDialog.show(this.getUI(), caption + " ID:" + tableContainer.getItem(table.getValue()).getItemProperty("id") + " ? ",
			new ConfirmDialog.Listener() {
				public void onClose(ConfirmDialog dialog) {
					if (dialog.isConfirmed()) {
						def object = [id:tableContainer.getItem(table.getValue()).getItemProperty("id").toString()]
						Grails.get(BookingService).softDeletedObject(object)
						initTable()
					} else {
								
					}
				}
			})
//		} else {
//			Notification.show("Access Denied\n",
//				"Anda tidak memiliki izin untuk Menghapus Record",
//				Notification.Type.ERROR_MESSAGE);
//		}
	}
//	========================================
	//WINDOW EDIT
//	========================================
	//@RequiresPermissions("Master:Item:Edit")
	private void windowEdit(def item,String caption) {
//		if (currentUser.isPermitted(Title + Constant.AccessType.Edit)) {
			window = new Window(caption);
			window.setModal(true);
			layout = new FormLayout();
			layout.setMargin(true);
			window.setContent(layout);
			textId = new TextField("Id:");
			textId.setPropertyDataSource(item.getItemProperty("id"))
			textId.setReadOnly(true)
			layout.addComponent(textId)
			cmbFacility = new ComboBox("Facility:");
			def beanFacility = new BeanItemContainer<Facility>(Facility.class)
			def facilityList = Grails.get(FacilityService).getList()
			beanFacility.addAll(facilityList)
			cmbFacility.setContainerDataSource(beanFacility)
			cmbFacility.setItemCaptionPropertyId("name")
			cmbFacility.select(cmbFacility.getItemIds().find{ it.id == item.getItemProperty("facility1.id").value})
			cmbFacility.setBuffered(true)
			cmbFacility.setImmediate(false)
			layout.addComponent(cmbFacility)
			cmbCustomer = new ComboBox("Customer:");
			def beanCustomer = new BeanItemContainer<Customer>(Customer.class)
			def customerList = Grails.get(CustomerService).getList()
			beanCustomer.addAll(customerList)
			cmbCustomer.setContainerDataSource(beanCustomer)
			cmbCustomer.setItemCaptionPropertyId("name")
			cmbCustomer.select(cmbCustomer.getItemIds().find{ it.id == item.getItemProperty("customer1.id").value})
			cmbCustomer.setBuffered(true)
			cmbCustomer.setImmediate(false)
			layout.addComponent(cmbCustomer)
			textStartDate = new DateField("Waktu Mulai:");
			textStartDate.setPropertyDataSource(item.getItemProperty("startDate"))
			textStartDate.setBuffered(true)
			textStartDate.setImmediate(false)
			layout.addComponent(textStartDate)
			cmbStartTime = new ComboBox("Jam Mulai:");
			for (int i = 0; i < 24; i++)
			{
				cmbStartTime.addItem(i)
				cmbStartTime.setItemCaption(i,i+":00")
			}
//			textJamMulai.select(textJamMulai.getItemIds().find{ it.id == item.getItemProperty("jamMulai").value.getHour()})
			cmbStartTime.select(item.getItemProperty("startTime").value.getHours())
			cmbStartTime.setBuffered(true)
			cmbStartTime.setImmediate(false)
			layout.addComponent(cmbStartTime)
			textDuration = new TextField("Durasi:");
			textDuration.setPropertyDataSource(item.getItemProperty("duration"))
			textDuration.setBuffered(true)
			textDuration.setImmediate(false)
			layout.addComponent(textDuration)
			layout.addComponent(createSaveButton())
			layout.addComponent(createCancelButton())
			getUI().addWindow(window);
//		} else {
//			Notification.show("Access Denied\n",
//				"Anda tidak memiliki izin untuk Mengubah Record",
//				Notification.Type.ERROR_MESSAGE);
//		}
	}
	
	//	========================================
	//WINDOW MULAI
//	========================================
	//@RequiresPermissions("Master:Item:Mulai")
	private void windowMulai(def item,String caption) {
//		if (currentUser.isPermitted(Title + Constant.AccessType.Edit)) {
			window = new Window(caption);
			window.setModal(true);
			layout = new FormLayout();
			layout.setMargin(true);
			window.setContent(layout);
			textId = new TextField("Id:");
			textId.setPropertyDataSource(item.getItemProperty("id"))
			textId.setReadOnly(true)
			layout.addComponent(textId)
			textCustomer = new TextField("Customer:");
			textCustomer.setPropertyDataSource(item.getItemProperty("customer1.name"))
//			def beanCustomer = new BeanItemContainer<Customer>(Customer.class)
//			def customerList = Grails.get(CustomerService).getList()
//			beanCustomer.addAll(customerList)
//			textCustomer.setContainerDataSource(beanCustomer)
//			textCustomer.setItemCaptionPropertyId("name")
//			textCustomer.select(textCustomer.getItemIds().find{ it.id == item.getItemProperty("customer1.id").value})
			textCustomer.setBuffered(true)
			textCustomer.setImmediate(false)
			textCustomer.setReadOnly(true)
			layout.addComponent(textCustomer)
			textDateStartUsing = new DateField("Waktu Mulai:");
			textDateStartUsing.setPropertyDataSource(item.getItemProperty("dateStartUsing"))
			textDateStartUsing.setBuffered(true)
			textDateStartUsing.setImmediate(false)
			layout.addComponent(textDateStartUsing)
			cmbStartUsing = new ComboBox("Jam Mulai:");
			for (int i = 0; i < 24; i++)
			{
				cmbStartUsing.addItem(i)
				cmbStartUsing.setItemCaption(i,i+":00")
			}
//			textJamMulai.select(textJamMulai.getItemIds().find{ it.id == item.getItemProperty("jamMulai").value.getHour()})
//			cmbStartUsing.select(item.getItemProperty("startUsing").value.getHours())
			cmbStartUsing.setBuffered(true)
			cmbStartUsing.setImmediate(false)
			layout.addComponent(cmbStartUsing)
			layout.addComponent(saveStartButton())
			layout.addComponent(createCancelButton())
			getUI().addWindow(window);
	}
	//	========================================
	//WINDOW SELESAI
//	========================================
	//@RequiresPermissions("Master:Item:Selesai")
	private void windowSelesai(def item,String caption) {
//		if (currentUser.isPermitted(Title + Constant.AccessType.Edit)) {
			window = new Window(caption);
			window.setModal(true);
			layout = new FormLayout();
			layout.setMargin(true);
			window.setContent(layout);
			textId = new TextField("Id:");
			textId.setPropertyDataSource(item.getItemProperty("id"))
			textId.setReadOnly(true)
			layout.addComponent(textId)
			textCustomer = new TextField("Customer:");
			textCustomer.setPropertyDataSource(item.getItemProperty("customer1.name"))
//			def beanCustomer = new BeanItemContainer<Customer>(Customer.class)
//			def customerList = Grails.get(CustomerService).getList()
//			beanCustomer.addAll(customerList)
//			textCustomer.setContainerDataSource(beanCustomer)
//			textCustomer.setItemCaptionPropertyId("name")
//			textCustomer.select(textCustomer.getItemIds().find{ it.id == item.getItemProperty("customer1.id").value})
			textCustomer.setBuffered(true)
			textCustomer.setImmediate(false)
			textCustomer.setReadOnly(true)
			layout.addComponent(textCustomer)
			textDateEndUsing = new DateField("Waktu Selesai:");
			textDateEndUsing.setPropertyDataSource(item.getItemProperty("dateEndUsing"))
			textDateEndUsing.setBuffered(true)
			textDateEndUsing.setImmediate(false)
			layout.addComponent(textDateEndUsing)
			cmbEndUsing = new ComboBox("Jam Selesai:");
			for (int i = 0; i < 24; i++)
			{
				cmbEndUsing.addItem(i)
				cmbEndUsing.setItemCaption(i,i+":00")
			}
//			textJamMulai.select(textJamMulai.getItemIds().find{ it.id == item.getItemProperty("jamMulai").value.getHour()})
//			cmbEndUsing.select(item.getItemProperty("endUsing").value.getHours())
			cmbEndUsing.setBuffered(true)
			cmbEndUsing.setImmediate(false)
			layout.addComponent(cmbEndUsing)
			layout.addComponent(saveEndButton())
			layout.addComponent(createCancelButton())
			getUI().addWindow(window);
	}

//	========================================
	//WINDOW ADD
//	========================================
	//@RequiresPermissions("Master:Item:Add")
	private void windowAdd(String caption) {
//		if (currentUser.isPermitted(Title + Constant.AccessType.Add)) {
			window = new Window(caption);
			window.setModal(true);
			def layout = new FormLayout();
			layout.setMargin(true);
			window.setContent(layout);
			textId = new TextField("Id:");
			textId.setReadOnly(true)
			layout.addComponent(textId)
			cmbFacility = new ComboBox("Facility")
			def beanFacility = new BeanItemContainer<Facility>(Facility.class)
			def facilityList = Grails.get(FacilityService).getList()
			beanFacility.addAll(facilityList)
			cmbFacility.setContainerDataSource(beanFacility)
			cmbFacility.setItemCaptionPropertyId("name")
			layout.addComponent(cmbFacility)
			cmbCustomer = new ComboBox("Customer")
			def beanCustomer = new BeanItemContainer<Customer>(Customer.class)
			def customerList = Grails.get(CustomerService).getList()
			beanCustomer.addAll(customerList)
			cmbCustomer.setContainerDataSource(beanCustomer)
			cmbCustomer.setItemCaptionPropertyId("name")
			layout.addComponent(cmbCustomer)
			textStartDate = new DateField("Waktu Mulai")
			layout.addComponent(textStartDate)
			cmbStartTime = new ComboBox("Jam Mulai")
			for (int i = 0; i < 24; i++)
			{
				cmbStartTime.addItem(i)
				cmbStartTime.setItemCaption(i,i+":00")
			}
			layout.addComponent(cmbStartTime)
			textDuration = new TextField("Durasi")
			layout.addComponent(textDuration)

//			def textArea = new TextArea("Text Area")
//			layout.addComponent(textArea)
//			def dateField = new DateField("Date Field")
//			layout.addComponent(dateField)
//			def comboBox = new ComboBox("combo Box")
//			comboBox.addItem("test")
//			comboBox.addItem("test2")
//			layout.addComponent(comboBox)
//			
//			===================
			//TOMBOL SAVE
//			===================
			layout.addComponent(createSaveButton())
//			==================
			
//			===================
//			TOMBOL CANCEL
//			===================
			layout.addComponent(createCancelButton())
			
//			===================
			getUI().addWindow(window);
//		} else {
//			Notification.show("Access Denied\n",
//				"Anda tidak memiliki izin untuk Membuat Record",
//				Notification.Type.ERROR_MESSAGE);
//		}
	}
	
	 void updateTable() {
//		if (table.size() > MAX_PAGE_LENGTH) {
//		table.setPageLength(MAX_PAGE_LENGTH);
//		} else {
//		table.setPageLength(table.size());
//		}
//		table.markAsDirtyRecursive();
		}
	 
	 void initTable() {
		tableContainer = new BeanItemContainer<Booking>(Booking.class);
		//fillTableContainer(tableContainer);
	    itemlist = Grails.get(BookingService).getList()
		tableContainer.addAll(itemlist)
		tableContainer.addNestedContainerProperty("facility1.id")
		tableContainer.addNestedContainerProperty("facility1.name")
		tableContainer.addNestedContainerProperty("customer1.id")
		tableContainer.addNestedContainerProperty("customer1.name")
		table.setContainerDataSource(tableContainer);
		table.setColumnHeader("facility1.name","Facility Name")
		table.setColumnHeader("customer1.name","Customer Name")
		table.setColumnHeader("startDate","Start Date")
		table.setColumnHeader("startTime","Start Time")
		table.setColumnHeader("durasi","Duration")
		table.setColumnHeader("dateStartUsing","Date Start Using")
		table.setColumnHeader("dateEndUsing","Date End Using")
		table.visibleColumns = ["facility1.name","customer1.name","startDate","startTime","duration","startUsing","endUsing","dateStartUsing","dateEndUsing","dateCreated","lastUpdated","isDeleted"]
		table.setSelectable(true)
		table.setImmediate(false)
//		table.setPageLength(table.size())
		
		table.setSizeFull()
		table.setHeight("100%")
//		table.addValueChangeListener(new Property.ValueChangeListener() {
//			public void valueChange(ValueChangeEvent event) {
//				selectedRow = table.getValue()
//			}
//		});

	}
	
	
	
}

