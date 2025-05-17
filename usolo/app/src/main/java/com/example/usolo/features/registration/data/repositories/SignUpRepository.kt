package com.example.usolo.features.registration.data.repositories

import android.util.Log
import com.example.authclass10.config.RetrofitConfig
import com.example.usolo.features.registration.data.dto.ProfileCreateDTO
import com.example.usolo.features.registration.data.dto.SignUpRequestDTO
import com.example.usolo.features.registration.data.dto.UserCreateDTO
import com.example.usolo.features.registration.data.dto.UserWithProfileDTO
import com.example.usolo.features.registration.data.sources.SignUpApi
import retrofit2.Response
import java.lang.Exception


class SignUpRepository {
    private val apiService = RetrofitConfig.directusRetrofit.create(SignUpApi::class.java)

    suspend fun signUp(signUpRequest: SignUpRequestDTO): Result<UserWithProfileDTO> {
        return try {
            // Paso 1: Crear usuario en Directus
            val userCreateDTO = UserCreateDTO(
                first_name = signUpRequest.first_name,
                email = signUpRequest.email,
                password = signUpRequest.password,
                role = "11111111-1111-1111-1111-111111111111" // ID del rol "Usuario"
            )

            val userResponse = apiService.createUser(
                user = userCreateDTO)
            if(!userResponse.isSuccessful) {
                return Result.failure(Exception("Error al crear usuario: ${userResponse.errorBody()?.string()}"))
            }

            val userContainer = userResponse.body() ?: return Result.failure(Exception("Respuesta de usuario vacía"))
            val user = userContainer.data
            Log.e(">>>", user.toString() )

            // Paso 2: Crear perfil de usuario asociado
            val profileCreateDTO = ProfileCreateDTO(
                address = signUpRequest.address,
                profile_photo = signUpRequest.profile_photo,
                user_id = user.id
            )
            val profileResponse = apiService.createProfile(
                profile = profileCreateDTO)


            if (!profileResponse.isSuccessful) {
                // Si falla la creación del perfil, deberíamos considerar eliminar el usuario
                // para mantener la consistencia, pero esto requeriría permisos adicionales
                return Result.failure(Exception("Error al crear perfil: ${profileResponse.errorBody()?.string()}"))
            }

            val profileContainer = profileResponse.body() ?: return Result.failure(Exception("Respuesta de perfil vacía"))
            val profile = profileContainer.data

            // Retornar objeto combinado usuario+perfil
            Result.success(
                UserWithProfileDTO(
                    id = user.id,
                    first_name = user.first_name,
                    email = user.email,
                    address = profile.address,
                    profile_photo = profile.profile_photo
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

}
