package latihan

import java.text.DateFormat
import java.text.SimpleDateFormat
import javax.validation.metadata.ReturnValueDescriptor;

import grails.transaction.Transactional

@Transactional
class BookingService {
	BookingValidationService bookingValidationService

    def serviceMethod() {

    }
	def getObjectById(def object){
		return Booking.get(object)
	}
	
	def getList(Date startTime,Date endTime){
		return Booking.findAll{isDeleted == false && (startDate >= startTime || startDate <= endTime)}
	}
	
	def getList(){
		return Booking.findAll(sort:"id",order:"desc"){isDeleted == false}
	}
	
	
	def createObject(object){
		object.isDeleted = false 
		if (object.startTime != null)
		{
			object.startTime = new SimpleDateFormat("HH").parse(object.startTime)
		}
		
//		object.waktuMulai = 0
//		object.jamMulai = 0
//		object.durasi = 0
//		object.costumer1 = 0
//		object.facility1 = 0
		object = bookingValidationService.createObjectValidation(object as Booking)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		
		return object
	}
	
	def updateObject(def object){
		def valObject = Booking.read(object.id)
		valObject.startDate = object.startDate
		if (object.startTime != null)
		{
			valObject.startTime = new SimpleDateFormat("HH").parse(object.startTime)
		}
		valObject.duration = object.duration
		valObject.customer1 = object.customer1
		valObject.facility1 = object.facility1
		valObject = bookingValidationService.updateObjectValidation(valObject)
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
	def startUsing(def object){
		def valObject = Booking.read(object.id)
		valObject.dateStartUsing = object.dateStartUsing
		Date startUsing = new Date()
		if (object.startUsing != null)
		{
			valObject.startUsing = new SimpleDateFormat("HH").parse(object.startUsing)
		}
		

//		valObject.startUsing = new Date (startUsing.getYear(),startUsing.getMonth()
//			,startUsing.getDate(),object.startUsing,00)
		valObject = bookingValidationService.startUsingValidation(valObject)
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
	def endUsing(def object){
		def valObject = Booking.read(object.id)
		valObject.dateEndUsing = object.dateEndUsing
		if (object.endUsing != null)
		{
			valObject.endUsing = new SimpleDateFormat("HH").parse(object.endUsing)
		}
//		valObject.endUsing = new Date (endUsing.getYear(),endUsing.getMonth()
//			,endUsing.getDate(),object.endUsing,00)
		valObject = bookingValidationService.endUsingValidation(valObject)
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
		def newObject = Booking.get(object.id)
		newObject = bookingValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
		
	}
}
