package com.example.ktnotes.util

import java.text.SimpleDateFormat
import java.util.*

fun Date.toDateString() : String = SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.ROOT).format(this)