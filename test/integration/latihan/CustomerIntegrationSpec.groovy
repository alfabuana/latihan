package latihan

import grails.test.spock.IntegrationSpec
//test-app -echoOut -integration
class CustomerIntegrationSpec extends IntegrationSpec {
	def customerService
	
    def setup() {
    }

    def cleanup() {
    }

    void "Test Create new Customer"() {
		setup:'setting new Customer'
		Customer customer = new Customer()
		customer.name = "newCustomer"
		customer.bbPin = "b98912"
		customer.noHp = "0899200231"
		customer.otherContact = "newKontak"
		 
		when:'createObject is called'
		customer = customerService.createObject(customer)
		
		then:'check has errors'
		customer.hasErrors() == false
		customer.isDeleted == false
    }
	
	void "Test Customer Validation Name Not Null"() {
		setup:'setting new Customer'
		Customer customer = new Customer()
		customer.name = ""
		customer.bbPin = "b98912"
		customer.noHp = "0899200231"
		customer.otherContact = "newKontak"
		 
		when:'createObject is called'
		customer = customerService.createObject(customer)
		
		then:'check has errors'
		customer.hasErrors() == true
		println customer.errors.getFieldError().defaultMessage
	}

	void "Test Update new Customer"() {
		setup:'setting new Customer'
		Customer customer = new Customer()
		customer.name = "newCustomer"
		customer.bbPin = "b98912"
		customer.noHp = "0899200231"
		customer.otherContact = "newKontak"
		customer = customerService.createObject(customer)
		
		and: 'setting data for Update'
		Customer customer2 = new Customer()
		customer2.id = customer.id
		customer2.name = "updateCustomer"
		customer2.bbPin = "zi09782"
		customer2.noHp = "1227653"
		customer2.otherContact = "newKontak2"
		 
		when:'updateObject is called'
		customer = customerService.updateObject(customer2)
		
		then:'check has errors'
		customer.hasErrors() == false
		customer.name == customer2.name
		customer.bbPin == customer2.bbPin
		customer.noHp == customer2.noHp
		customer.otherContact == customer2.otherContact
	}
	
	void "Test Update Validation nama Not Null"() {
		setup:'setting new Customer'
		Customer customer = new Customer()
		customer.name = "newCustomer"
		customer.bbPin = "b98912"
		customer.noHp = "0899200231"
		customer.otherContact = "newKontak"
		customer = customerService.createObject(customer)
		
		and: 'setting data for Update'
		Customer customer2 = new Customer()
		customer2.id = customer.id
		customer2.name = ""
		customer2.bbPin = "zi09782"
		customer2.noHp = "1227653"
		customer2.otherContact = "newKontak2"
		 
		when:'updateObject is called'
		customer = customerService.updateObject(customer2)
		
		then:'check has errors'
		customer.hasErrors() == true
		println "Validasi sukses dengan error message : " + customer.errors.getFieldError().defaultMessage
	}
		
	void "Test SoftDelete Customer"() {
		setup:'setting new Customer'
		Customer customer = new Customer()
		customer.name = "newCustomer"
		customer.bbPin = "b98912"
		customer.noHp = "0899200231"
		customer.otherContact = "newKontak"
		customer = customerService.createObject(customer)
		
		when:'createObject is called'
		customer = customerService.softDeletedObject(customer)
		
		then:'check has errors'
		customer.hasErrors() == false
		customer.isDeleted == true
	}
	
	
	
}
