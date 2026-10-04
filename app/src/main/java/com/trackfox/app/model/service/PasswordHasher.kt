package com.trackfox.app.model.service

import org.mindrot.jbcrypt.BCrypt

fun hashPassword(password : String) : String =
    BCrypt.hashpw(password, BCrypt.gensalt(12))

fun isCorrectPassword(password : String, hash : String) : Boolean =
    BCrypt.checkpw(password, hash)