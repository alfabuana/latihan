package latihan

import grails.transaction.Transactional

@Transactional
class BookingValidationService {

    def serviceMethod() {

    }
	
	def waktuNotNull(def object){
		if (object.startDate == null)
		{
			object.errors.rejectValue('startDate','null','Waktu mulai tidak boleh kosong')
		}
		return object
	}
	def jamNotNull(def object){
		if (object.startTime == null)
		{
			object.errors.rejectValue('startTime','null','Jam Mulai tidak boleh kosong')
		}
		return object
	}
	
	def createObjectValidation(def object)
	{
		object = waktuNotNull(object)
		if (object.errors.hasErrors()) return object
		object = jamNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = waktuNotNull(object)
		if (object.errors.hasErrors()) return object
		object = jamNotNull(object)
		return object
	}
	
	def softdeleteObjectValidation(object)
	{
		return object
	}
	def startUsingNotNull(def object){
		if (object.startUsing == null)
		{
			object.errors.rejectValue('startUsing','null','Jam Mulai tidak boleh kosong')
		}
		return object
	}
	def dateStartUsingNotNull(def object){
		if (object.dateStartUsing == null)
		{
			object.errors.rejectValue('dateStartUsing','null','Waktu Mulai tidak boleh kosong')
		}
		return object
	}
	def startUsingValidation(def object)
	{
		object = startUsingNotNull(object)
		if (object.errors.hasErrors()) return object
		object = dateStartUsingNotNull(object)
		return object
	}
	def endUsingNotNull(def object){
		if (object.endUsing == null)
		{
			object.errors.rejectValue('endUsing','null','Jam Selesai tidak boleh kosong')
		}
		return object
	}
	def dateEndUsingNotNull(def object){
		if (object.dateEndUsing == null)
		{
			object.errors.rejectValue('dateEndUsing','null','Waktu Selesai tidak boleh kosong')
		}
		return object
	}
	def endUsingValidation(def object)
	{
		object = endUsingNotNull(object)
		if (object.errors.hasErrors()) return object
		object = dateEndUsingNotNull(object)
		return object
	}

}
