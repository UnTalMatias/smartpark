package com.estacionamiento.inteligente.data.remote

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.realtime.Realtime

object SupabaseClient {
    private const val SUPABASE_URL = "https://kqapyjcfmsfgsrnebzbn.supabase.co"
    private const val SUPABASE_KEY = "sb_publishable_OglH6GgUfJtfIFf1JrlpOQ_EnATFHc_"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)
        install(Auth)
        install(Realtime)
    }
}
