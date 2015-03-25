package latihan

import com.vaadin.event.ItemClickEvent
import com.vaadin.event.ItemClickEvent.ItemClickListener
import com.vaadin.navigator.Navigator
import com.vaadin.ui.Tree


class TreeMenu extends Tree{
	
   public TreeMenu(){
	   //get the current Subject
	   
	   //Book
	   //--Parent
	   addItem( "Booking" )
	   //--Child
	   addItem("Booking Order")
	   setParent("Booking Order", "Booking")
	   setChildrenAllowed( "Booking Order", false)
	   addItem("Customer")
	   setParent("Customer", "Booking")
	   setChildrenAllowed( "Customer", false)
	   //Master Date
	   //Parent
	   addItem( "Master Data" )
	   //Child
	   addItem("User")
	   setParent("User", "Master Data")
	   setChildrenAllowed( "User", false)
	   
	   addItem("Role")
	   setParent("Role", "Master Data")
	   setChildrenAllowed( "Role", false)
	   
	   addItem("Facility")
	   setParent("Facility", "Master Data")
	   setChildrenAllowed( "Facility", false)
	   //Report
	   //Parent
	   addItem( "Report" )
	   //Child
	   addItem("Booking Report")
	   setParent("Booking Report", "Report")
	   setChildrenAllowed( "Booking Report", false)
	   
//	   if (currentUser.hasRole("Administrator"))
//	   {
//		   //Parent
//		   addItem( "Settings" )
//		   setChildrenAllowed( "Settings", true)
//		   //Child
//		   addItem("Role")
//		   setParent("Role", "Settings")
//		   setChildrenAllowed( "Role", false)
//		   addItem("User")
//		   setParent("User", "Settings")
//		   setChildrenAllowed( "User", false)
//	   }
			  
   }
   
   
}
