package latihan

import java.util.Date;

class ShiroRole {
    String name
	Date dateCreated
	Date lastUpdated
	boolean	isDeleted
    static hasMany = [ users: ShiroUser, permissions: String ]
    static belongsTo = ShiroUser

    static constraints = {
        name(nullable: false, blank: false, unique: true)

    }
}
