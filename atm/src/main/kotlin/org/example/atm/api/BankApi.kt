package org.example.atm.api

import org.example.atm.api.dto.BalanceResponseDTO
import org.example.atm.api.dto.ModifyBalanceDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class BankApi(
    @Value("\${atm.id}")
    val atmId: String,
    private val wabClient: WebClient = WebClient.builder().baseUrl("http://localhost:8081")
        .defaultHeaders { it.set("X-ATM", "value") }.build(),
) {
    fun sendBalanceRequest(userName: String): Int { //TODO Balazzsal megbeszelni az error handlinget
        try {
            val response: BalanceResponseDTO? = wabClient.get()
                .uri("/api/v1/user-balance/$userName")
                .retrieve()
                .bodyToMono<BalanceResponseDTO>()
                .block()
            if (response != null) {
                return response.balance
            }
            throw Exception()
        } catch (e: WebClientResponseException.NotFound) {
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, e.message?:"Can't find user: $userName")
        }
    }

    fun modifyUserBalance(amount: Int, userName: String) {
        val request: ModifyBalanceDTO = ModifyBalanceDTO( userName, amount)
        try {
            val response = wabClient.post()
                .uri("/api/v1/modify-balance")
                .bodyValue(request)
                .retrieve()
                .bodyToMono<String>()
                .block()
        }catch (e: WebClientResponseException) {
            throw HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went Wrong")
        }
    }
}
