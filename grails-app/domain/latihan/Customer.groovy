package latihan

import java.util.Date;

class Customer {
	
	String name
	String bbPin
	String noHp
	String otherContact
	Date dateCreated
	Date lastUpdated
	boolean isDeleted

    static constraints = {
		otherContact (nullable : true)

    }
}
