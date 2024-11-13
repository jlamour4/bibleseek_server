package com.jlam.bibleseek.controller

import com.jlam.bibleseek.entity.User
import com.jlam.bibleseek.repository.UsersRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UsersController(val usersRepository: UsersRepository) {

    @GetMapping
    fun getAllUsers(): List<User> = usersRepository.findAll()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> =
        usersRepository.findById(id).map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @PostMapping
    fun saveUser(@RequestBody user: User): ResponseEntity<String> {
        // Check if the user already exists by authProviderId (Google UID)
        val existingUser = usersRepository.findByAuthProviderId(user.authProviderId)

        return if (existingUser.isPresent) {
            // User exists, update their details if necessary
            val updatedUser = existingUser.get().copy(
                name = user.name,
                profilePictureUrl = user.profilePictureUrl
            )
            usersRepository.save(updatedUser)
            // Return 200 OK (no conflict)
            ResponseEntity("User exists, details updated", HttpStatus.OK)
        } else {
            // User does not exist, create a new one
            val newUser = User(
                username = user.username,
                name = user.name,
                email = user.email,
                profilePictureUrl = user.profilePictureUrl,
                authProvider = user.authProvider,
                authProviderId = user.authProviderId
            )
            usersRepository.save(newUser)
            // Return 200 OK with "User created" message
            ResponseEntity("User created", HttpStatus.OK)
        }
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody newUser: User): ResponseEntity<User> {
        return usersRepository.findById(id).map { existingUser ->
            val updatedUser = existingUser.copy(
                name = newUser.name,
                username = newUser.username,
                email = newUser.email,
                profilePictureUrl = newUser.profilePictureUrl,
                authProvider = newUser.authProvider,
                authProviderId = newUser.authProviderId,
                createdAt = newUser.createdAt
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