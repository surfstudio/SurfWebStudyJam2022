package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*
import javax.persistence.*

@Table(name = "accounts")
@Entity
class Account(

    @Id
    @Column(name = "id")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "email")
    var email: String = "",

    @Column(name = "password")
    var password: String? = null,

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    val role: Role? = null,


    ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , email = $email , password = $password )"
    }

}