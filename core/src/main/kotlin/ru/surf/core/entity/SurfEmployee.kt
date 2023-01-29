package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*
import javax.persistence.*

@Table(name = "surf_employees")
@Entity
class SurfEmployee(

    @Id
    @Column(name = "id")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "name")
    var name: String = "",

    @OneToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    var account: Account = Account(),

    ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , account = $account )"
    }

}