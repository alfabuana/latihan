package latihan

class Booking {
	
	Date startDate
	Date startTime
	Integer duration
	Date startUsing
	Date endUsing
	Date dateStartUsing
	Date dateEndUsing
	Date dateCreated
	Date lastUpdated
	Boolean isDeleted
	
	Customer customer1
	Facility facility1

	

    static constraints = {
		startUsing (nullable : true)
		endUsing (nullable : true)
		dateStartUsing (nullable : true)
		dateEndUsing (nullable : true)
    }
}
