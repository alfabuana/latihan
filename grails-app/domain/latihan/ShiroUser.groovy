package latihan

import java.util.Date;

class ShiroUser {
    String username
    String passwordHash
	String email
	Date dateCreated
	Date lastUpdated
    boolean	isDeleted
    static hasMany = [ roles: ShiroRole, permissions: String ]

    static constraints = {
        username(nullable: false, blank: false, unique: true)
		email(nullable : true)

    }
}
