package com.jlam.bibleseek.controller

import com.jlam.bibleseek.entity.User
import com.jlam.bibleseek.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(val userRepository: UserRepository) {

    @GetMapping
    fun getAllUsers(): List<User> = userRepository.findAll()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> =
        userRepository.findById(id).map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @PostMapping
    fun createUser(@RequestBody user: User): User = userRepository.save(user)

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody newUser: User): ResponseEntity<User> {
        return userRepository.findById(id).map { existingUser ->
            val updatedUser = existingUser.copy(
                username = newUser.username,
                email = newUser.email,
                profilePicture = newUser.profilePicture
            )
            ResponseEntity.ok(userRepository.save(updatedUser))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        return userRepository.findById(id).map {
            userRepository.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
}