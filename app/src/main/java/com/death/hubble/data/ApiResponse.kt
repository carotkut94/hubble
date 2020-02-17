package com.death.hubble.data

import androidx.annotation.Keep

@Keep
class ApiResponse<T>(val message:String,
                     val data:T?)