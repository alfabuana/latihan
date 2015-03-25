package latihan

import grails.transaction.Transactional

@Transactional
class FacilityValidationService {

    def serviceMethod() {

    }
	
	def nameNotNull(def object){
		
		if (object.name == "" || object.name == null)
		{
			object.errors.rejectValue('name','null','Nama tidak boleh kosong')
		}
		return object
	}
	
	def createObjectValidation(def object)
	{
		object = nameNotNull(object)
		if (object.errors.hasErrors()) return object
		return object
	}
	
	def updateObjectValidation(def object)
	{
		object = nameNotNull(object)
		if (object.errors.hasErrors()) return object
		return object
	}
	
	def softdeletedObjectValidation(def object)
	{
		return object
	}
}
