package ru.surf.core.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.surf.core.entity.Account
import ru.surf.core.repository.AccountRepository
import ru.surf.core.service.AccountService
import java.util.*

@Service
class AccountServiceImpl(
        @Autowired
        private val accountRepository: AccountRepository
) : AccountService {

    override fun getAccountByEmail(email: String): Account? {
        return accountRepository.findByEmail(email).orElse(null)
    }

    override fun getAccountById(id: UUID): Account? {
        return accountRepository.findById(id).orElse(null)
    }

}