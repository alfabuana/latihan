package latihan

import grails.transaction.Transactional

@Transactional
class FacilityService {
	FacilityValidationService facilityValidationService

    def serviceMethod() {

    }
	
	def getObjectById(def object){
		return Facility.get(object)
	}
	
	def getList(){
		return Facility.getAll()
	}
	
	def createObject(object){
		
		object.isDeleted = false
//		object.nama = 0
//		object.deskripsi = 0
		object = facilityValidationService.createObjectValidation(object as Facility)
		if (object.errors.getErrorCount() == 0)
		{
			object = object.save()
		}
		
		return object
	}
	
	def updateObject(def object){
		Facility valObject = Facility.read(object.id)
		valObject.name = object.name
		valObject.description = object.description
		valObject = facilityValidationService.updateObjectValidation(valObject)
		if (valObject.errors.getErrorCount() == 0)
		{
			valObject.save()
		}
		else
		{
			valObject.discard()
		}
		
		return valObject
	}
	
	def softDeletedObject(def object){
		def newObject = Facility.get(object.id)
		newObject = facilityValidationService.softdeletedObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
		
	}
}
