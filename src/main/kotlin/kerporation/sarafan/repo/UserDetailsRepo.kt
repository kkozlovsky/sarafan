package kerporation.sarafan.repo

import kerporation.sarafan.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserDetailsRepo : JpaRepository<User, String>
