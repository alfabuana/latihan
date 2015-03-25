package latihan

import grails.test.spock.IntegrationSpec

class FacilityIntegrationSpec extends IntegrationSpec {
	def facilityService

    def setup() {
    }

    def cleanup() {
    }

    void "Test Create new Facility"() {
		setup: 'setting new Facility'
		Facility facility = new Facility()
		facility.name = "newFacility"
		facility.description = "newDeskripsi"
		
		when: 'createObject is called'
		facility = facilityService.createObject(facility)
		
		then: 'check has errors'
		facility.hasErrors() == false
		facility.isDeleted == false
    }
	
	void "Test Facility Validation Name Not Null"(){
		setup: 'setting new Facility'
		Facility facility = new Facility()
		facility.name = ""
		facility.description = "newDeskripsi"
		
		when:'createObject is called'
		facility = facilityService.createObject(facility)
		
		then:'check has errors'
		facility.hasErrors() == true
		println facility.errors.getFieldError().defaultMessage 
	}
	
	void "Test Update New Facility"(){
		setup:'setting new Facility'
		Facility facility = new Facility()
		facility.name = "newFacility"
		facility.description = "newDeskripsi"
		facility = facilityService.createObject(facility)
		
		and:'setting data for Update'
		Facility facility2 = new Facility()
		facility2.id = facility.id
		facility2.name = "newTest"
		facility2.description = "newDeskripsi2"
		
		when:'updateObject is called'
		facility = facilityService.updateObject(facility2)
		
		then:'check has errors'
		facility.hasErrors() == false
		facility.name == facility.name
		facility.description == facility.description
				
	}
	
	void "Test Update Validation nama Not Null"(){
		setup:'setting new Facility'
		Facility facility = new Facility()
		facility.name = "newFacility"
		facility.description = "newDeskripsi"
		facility = facilityService.createObject(facility)
		
		and:'setting data for Update'
		Facility facility2 = new Facility()
		facility2.id = facility.id
		facility2.name = ""
		facility2.description = "newDeskripsi2"
		
		when:'updateObject is called'
		facility = facilityService.updateObject(facility2)
		
		then:'check has errors'
		facility.hasErrors() == true
		println "Validasi sukses dengan error message : " + facility.errors.getFieldError().defaultMessage
		
	}
	
	void "Test SoftDeleted Facility"(){
		setup:'setting new Facility'
		Facility facility = new Facility()
		facility.name = "newFacility"
		facility.description = "newDeskripsi"
		facility = facilityService.createObject(facility)
		
		when:'createObject is called'
		facility = facilityService.softDeletedObject(facility)
		
		then:'check has errors'
		facility.hasErrors() == false
		facility.isDeleted == true
	}
}
