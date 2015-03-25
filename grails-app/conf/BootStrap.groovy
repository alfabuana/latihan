import org.apache.shiro.crypto.hash.Sha256Hash
import latihan.ShiroRole
import latihan.ShiroUser

class BootStrap {
	def itemService
    def init = { servletContext ->
		
		//Shiro
		def adminRole = ShiroRole.findByNameAndIsDeleted("Administrator",false)
		if(adminRole==null){
			adminRole = new ShiroRole(name: "Administrator")
			adminRole.addToPermissions("*:*")
			//adminRole.addToPermissions("admin")
			adminRole.save(flush:true, failOnError:true)
		}
		if (ShiroUser.findAllByUsernameAndIsDeleted("ADMIN",false).isEmpty()) {
			def user = new ShiroUser(username: "ADMIN", passwordHash: new Sha256Hash("sysadmin").toHex())
			user.addToRoles(adminRole)
			//		user.addToPermissions("*:*")
			//		user.addToPermissions("admin")
			user.save(flush:true, failOnError:true)
		}
		if (ShiroUser.findAllByUsernameAndIsDeleted("GUEST",false).isEmpty()) {
			def user = new ShiroUser(username: "GUEST", passwordHash: new Sha256Hash("guest").toHex())
			//user.addToPermissions("Master:Item:Add")
			user.save(flush:true, failOnError:true)
		}
    }
	
    def destroy = {
    }
}
