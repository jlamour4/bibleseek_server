package com.jlam.bibleseek.controller

import com.jlam.bibleseek.entity.Users
import com.jlam.bibleseek.repository.UsersRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UsersController(val usersRepository: UsersRepository) {

    @GetMapping
    fun getAllUsers(): List<Users> = usersRepository.findAll()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<Users> =
        usersRepository.findById(id).map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @PostMapping
    fun createUser(@RequestBody users: Users): Users = usersRepository.save(users)

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody newUsers: Users): ResponseEntity<Users> {
        return usersRepository.findById(id).map { existingUser ->
            val updatedUser = existingUser.copy(
                name = newUsers.name,
                username = newUsers.username,
                email = newUsers.email,
                profilePictureUrl = newUsers.profilePictureUrl,
                authProvider = newUsers.authProvider,
                authProviderId = newUsers.authProviderId,
                createdAt = newUsers.createdAt
            )
            ResponseEntity.ok(usersRepository.save(updatedUser))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        return usersRepository.findById(id).map {
            usersRepository.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
}