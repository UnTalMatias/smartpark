package com.estacionamiento.inteligente.data.remote

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.realtime.Realtime

object SupabaseClient {
    private const val SUPABASE_URL = "https://zssmxeeeybqsesxebyek.supabase.co"
    private const val SUPABASE_KEY = "sb_publishable_u2BYJM9M2_dzOGT3mb2U_w_EArk30b3"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)
        install(Auth)
        install(Realtime)
    }
}
