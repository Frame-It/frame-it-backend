package com.org.framelt.portfolio.adapter.`in`

import com.org.framelt.portfolio.application.port.`in`.*
import com.org.framelt.portfolio.application.service.PortfolioDetailResponse
import com.org.framelt.portfolio.application.service.PortfolioResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/portfolio")
class PortfolioController(
    private val portfolioCreateUseCase: PortfolioCreateUseCase,
) {

    @PostMapping
    fun create(
        @AuthenticationPrincipal userId: Long,
        @RequestParam("photos") photos: List<MultipartFile>,
        @RequestParam("title") title: String,
        @RequestParam("description") description: String,
        @RequestParam("hashtags") hashtags: List<String>,
        @RequestParam("togethers") togethers: List<Long>
    ): ResponseEntity<Long> {
        val command = PortfolioCreateCommend(userId, photos, title, description, hashtags, togethers)
        val portfolioId = portfolioCreateUseCase.create(command)
        return ResponseEntity.ok(portfolioId)
    }

    @GetMapping("/{id}")
    fun read(
        @AuthenticationPrincipal userId: Long,
        @PathVariable id: Long
    ): ResponseEntity<PortfolioDetailResponse> {
        val command = PortfolioReadCommend(id, userId)
        val response = portfolioCreateUseCase.read(command)
        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun readAll(
        @AuthenticationPrincipal userId: Long
    ): ResponseEntity<List<PortfolioResponse>> {
        val command = PortfolioReadAllCommend(userId)
        val response = portfolioCreateUseCase.readAll(command)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    fun update(
        @AuthenticationPrincipal userId: Long,
        @PathVariable id: Long,
        @RequestParam("photos") photos: List<MultipartFile>,
        @RequestParam("title") title: String,
        @RequestParam("description") description: String,
        @RequestParam("hashtags") hashtags: List<String>,
        @RequestParam("togethers") togethers: List<Long>
    ): ResponseEntity<Void> {
        val command = PortfolioUpdateCommend(userId, photos, title, description, hashtags, togethers)
        portfolioCreateUseCase.update(command)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}")
    fun delete(
        @AuthenticationPrincipal userId: Long,
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        val command = PortfolioDeleteCommend(id)
        portfolioCreateUseCase.delete(command)
        return ResponseEntity.noContent().build()
    }
}
