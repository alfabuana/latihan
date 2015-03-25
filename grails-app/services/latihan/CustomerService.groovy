package latihan

import grails.transaction.Transactional

@Transactional
class CustomerService {
	CustomerValidationService customerValidationService
	
    def serviceMethod() {

    }
	def getObjectById(def object){
		return Customer.get(object)
	}
	def getList(){
		return Customer.getAll()
	}
	
	def createObject(object){
		
		object.isDeleted = false
//		object.nama = 0
//		object.bbPin = 0
//		object.nomorHp = 0
//		object.kontakLain = 0
		object = customerValidationService.createObjectValidation(object as Customer)
		if (object.errors.getErrorCount() == 0)
		{
			object = object.save()
		}
		return object
	}
	
	def updateObject(def object){
		def valObject = Customer.read(object.id)
		valObject.name = object.name
		valObject.bbPin = object.bbPin
		valObject.noHp = object.noHp
		valObject.otherContact = object.otherContact
		valObject = customerValidationService.updateObjectValidation(valObject)
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
		def newObject = Customer.get(object.id)
		newObject = customerValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
		
	}
	
}
