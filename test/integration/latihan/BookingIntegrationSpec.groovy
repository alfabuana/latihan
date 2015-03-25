package latihan

import grails.test.spock.IntegrationSpec

class BookingIntegrationSpec extends IntegrationSpec {
	def bookingService
	def customerService
	def facilityService

	def setup() {
	}

	def cleanup() {
	}

	void "Test Create new Booking"() {
		setup: 'setting new Customer'
		Customer customer = new Customer()
		customer.name = "newCustomer"
		customer.bbPin = "b98912"
		customer.noHp = "0899200231"
		customer.otherContact = "newKontak"
		customer = customerService.createObject(customer)
		println customer.id

		and: 'setting new Facility'
		Facility facility = new Facility()
		facility.name = "newFacility"
		facility.description = "newDeskripsi"
		facility = facilityService.createObject(facility)


		and: 'setting new Booking'
		def booking = [
			waktuMulai: new Date (2015,3,23),
			jamMulai:3,
			durasi:10,
			customer1:customer,
			facility1:facility
		]

		when : 'createObject is called'
		booking = bookingService.createObject(booking)

		then: 'check has errors'
		booking.hasErrors() == false
		booking.isDeleted == false
	}

	void "Test Create Booking Validation Waktu Not Null"(){
		setup: 'setting new Customer'
		Customer customer = new Customer()
		customer.name = "newCustomer"
		customer.bbPin = "b98912"
		customer.noHp = "0899200231"
		customer.otherContact = "newKontak"
		customer = customerService.createObject(customer)

		and: 'setting new Facility'
		Facility facility = new Facility()
		facility.name = "newFacility"
		facility.description = "newDeskripsi"
		facility = facilityService.createObject(facility)


		and: 'setting new Booking'
		def booking = [
			//			waktuMulai: null,
			jamMulai:0,
			durasi:10,
			customer1:customer,
			facility1:facility
		]

		when:'createObject is called'
		booking = bookingService.createObject(booking)

		then:'check has errors'
		booking.hasErrors() == true
		println booking.errors.getFieldError().defaultMessage
	}

	void "Test Update New Booking"(){
		setup: 'setting new Customer'
		Customer customer = new Customer()
		customer.name = "newCustomer"
		customer.bbPin = "b98912"
		customer.noHp = "0899200231"
		customer.otherContact = "newKontak"
		customer = customerService.createObject(customer)

		and: 'setting new Facility'
		Facility facility = new Facility()
		facility.name = "newFacility"
		facility.description = "newDeskripsi"
		facility = facilityService.createObject(facility)


		and: 'setting new Booking'
		def booking = [
			waktuMulai: new Date (2015,3,23),
			jamMulai:2,
			durasi:10,
			customer1:customer,
			facility1:facility
		]

		booking= bookingService.createObject(booking)

		and:'setting data for Update'
		def booking2 = [
			id: booking.id,
			waktuMulai: new Date (2015,3,23),
			jamMulai:1,
			durasi:9,
			customer1:customer,
			facility1:facility
		]

		when:'updateObject is called'
		booking = bookingService.updateObject(booking2)

		then:'check has errors'
		booking.hasErrors() == false
		booking.waktuMulai == booking.waktuMulai
		booking.jamMulai == booking.jamMulai
		booking.durasi == booking.durasi
		booking.customer1 == booking.customer1
		booking.facility1 == booking.facility1

	}
	void "Test Update Validation waktu Not Null"(){
		setup: 'setting new Customer'
		Customer customer = new Customer()
		customer.name = "newCustomer"
		customer.bbPin = "b98912"
		customer.noHp = "0899200231"
		customer.otherContact = "newKontak"
		customer = customerService.createObject(customer)

		and: 'setting new Facility'
		Facility facility = new Facility()
		facility.name = "newFacility"
		facility.description = "newDeskripsi"
		facility = facilityService.createObject(facility)

		and: 'setting new Booking'
		def booking = [
			waktuMulai: new Date (2015,3,23),
			jamMulai:3,
			durasi:10,
			customer1:customer,
			facility1:facility
		]
		booking = bookingService.createObject(booking)

		and:'setting data for Update'
		def booking2 = [
			id: booking.id,
			waktuMulai: null,
			jamMulai:0,
			durasi:10,
			customer1:customer,
			facility1:facility
		]

		when:'updateObject is called'
		booking = bookingService.updateObject(booking2)

		then:'check has errors'
		booking.hasErrors() == true
		println "Validasi sukses dengan error message : " + booking.errors.getFieldError().defaultMessage

	}
	void "Test Start Using"() {
		setup: 'setting new Customer'
		Customer customer = new Customer()
		customer.name = "newCustomer"
		customer.bbPin = "b98912"
		customer.noHp = "0899200231"
		customer.otherContact = "newKontak"
		customer = customerService.createObject(customer)

		and: 'setting new Facility'
		Facility facility = new Facility()
		facility.name = "newFacility"
		facility.description = "newDeskripsi"
		facility = facilityService.createObject(facility)

		and: 'setting new Booking'
		def booking = [
			
			waktuMulai: new Date (2015,3,23),
			jamMulai:3,
			durasi:10,
			customer1:customer,
			facility1:facility,
		]
		booking = bookingService.createObject(booking)
		
		and : 'setting update Booking Data'
		def booking2 =
				[
					id: booking.id,
					dateStartUsing: new Date (2015,3,23),
					startUsing:"11"
				]

		when : 'startUsing is called'
		booking = bookingService.startUsing(booking2)

		then: 'check has errors'
		booking.hasErrors() == false
		booking.isDeleted == false
	}
	
	void "Test End Using"() {
		setup: 'setting new Customer'
		Customer customer = new Customer()
		customer.name = "newCustomer"
		customer.bbPin = "b98912"
		customer.noHp = "0899200231"
		customer.otherContact = "newKontak"
		customer = customerService.createObject(customer)
		println customer.id

		and: 'setting new Facility'
		Facility facility = new Facility()
		facility.name = "newFacility"
		facility.description = "newDeskripsi"
		facility = facilityService.createObject(facility)


		and: 'setting new Booking'
		def booking = [
			waktuMulai: new Date (2015,3,23),
			jamMulai:3,
			durasi:10,
			customer1:customer,
			facility1:facility
		]
		booking = bookingService.createObject(booking)
		
		and: 'setting update Booking Data'
		def booking2 = [
			id: booking.id,
			dateEndUsing: new Date (2015,3,23),
			endUsing:"11",
			]
		
		when : 'endUsing is called'
		booking = bookingService.endUsing(booking2)

		then: 'check has errors'
		booking.hasErrors() == false
		booking.isDeleted == false
	}
	void "Test Start Validation Start Using Not Null"(){
		setup: 'setting new Customer'
		Customer customer = new Customer()
		customer.name = "newCustomer"
		customer.bbPin = "b98912"
		customer.noHp = "0899200231"
		customer.otherContact = "newKontak"
		customer = customerService.createObject(customer)

		and: 'setting new Facility'
		Facility facility = new Facility()
		facility.name = "newFacility"
		facility.description = "newDeskripsi"
		facility = facilityService.createObject(facility)


		and: 'setting new Booking'
		def booking = [
			waktuMulai: new Date (2015,3,23),
			jamMulai:3,
			durasi:10,
			customer1:customer,
			facility1:facility
		]
		booking = bookingService.createObject(booking)
		
		and: 'setting update Booking Data'
		def booking2 = [
			id: booking.id,
			dateStartUsing: new Date (2015,3,23),
			startUsing:null,
			]
		
		when:'startUsing is called'
		booking = bookingService.startUsing(booking2)

		then:'check has errors'
		booking.hasErrors() == true
		println "Validasi sukses dengan error message : " + booking.errors.getFieldError().defaultMessage

	}
	void "Test Start Validation Date Start Using Not Null"(){
		setup: 'setting new Customer'
		Customer customer = new Customer()
		customer.name = "newCustomer"
		customer.bbPin = "b98912"
		customer.noHp = "0899200231"
		customer.otherContact = "newKontak"
		customer = customerService.createObject(customer)

		and: 'setting new Facility'
		Facility facility = new Facility()
		facility.name = "newFacility"
		facility.description = "newDeskripsi"
		facility = facilityService.createObject(facility)


		and: 'setting new Booking'
		def booking = [
			waktuMulai: new Date (2015,3,23),
			jamMulai:3,
			durasi:10,
			customer1:customer,
			facility1:facility
		]
		booking = bookingService.createObject(booking)

		and: 'setting update Booking Data'
		def booking2 = [
			id: booking.id,
			dateStartUsing: null,
			startUsing:"11",
			]
		
		when:'startUsing is called'
		booking = bookingService.startUsing(booking2)

		then:'check has errors'
		booking.hasErrors() == true
		println "Validasi sukses dengan error message : " + booking.errors.getFieldError().defaultMessage

	}
	void "Test End Using Validation End Using Not Null"(){
		setup: 'setting new Customer'
		Customer customer = new Customer()
		customer.name = "newCustomer"
		customer.bbPin = "b98912"
		customer.noHp = "0899200231"
		customer.otherContact = "newKontak"
		customer = customerService.createObject(customer)

		and: 'setting new Facility'
		Facility facility = new Facility()
		facility.name = "newFacility"
		facility.description = "newDeskripsi"
		facility = facilityService.createObject(facility)


		and: 'setting new Booking'
		def booking = [
			waktuMulai: new Date (2015,3,23),
			jamMulai:3,
			durasi:10,
			customer1:customer,
			facility1:facility
			]
		booking = bookingService.createObject(booking)
		
		and: 'setting update Booking Data'
		def booking2 = [
			id: booking.id,
			endUsing: null,
			dateEndUsing: new Date (2015,3,23),
			]

		when:'endUsing is called'
		booking = bookingService.endUsing(booking2)

		then:'check has errors'
		booking.hasErrors() == true
		println booking.errors.getFieldError().defaultMessage
	}
	void "Test End Using Validation Date End Using Not Null"(){
		setup: 'setting new Customer'
		Customer customer = new Customer()
		customer.name = "newCustomer"
		customer.bbPin = "b98912"
		customer.noHp = "0899200231"
		customer.otherContact = "newKontak"
		customer = customerService.createObject(customer)

		and: 'setting new Facility'
		Facility facility = new Facility()
		facility.name = "newFacility"
		facility.description = "newDeskripsi"
		facility = facilityService.createObject(facility)

		and: 'setting new Booking'
		def booking = [
			waktuMulai: new Date (2015,3,23),
			jamMulai:3,
			durasi:10,
			customer1:customer,
			facility1:facility
		]
		booking = bookingService.createObject(booking)

		and:'setting update Booking Data'
		def booking2 = [
			id: booking.id,
			endUsing: "12",
			dateEndUsing: null,
			]
		when:'endUsing is called'
		booking = bookingService.endUsing(booking2)

		then:'check has errors'
		booking.hasErrors() == true
		println booking.errors.getFieldError().defaultMessage
	}

}
