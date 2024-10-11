package com.org.framelt.portfolio.adapter.`in`

import com.org.framelt.config.auth.Authorization
import com.org.framelt.portfolio.application.port.`in`.PortfolioCreateUseCase
import com.org.framelt.portfolio.application.service.PortfolioDetailResponse
import com.org.framelt.portfolio.application.service.PortfolioResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/portfolios")
class PortfolioController(
    private val portfolioCreateUseCase: PortfolioCreateUseCase,
) {

    @PostMapping("/portfolio")
    fun create(
        @Authorization userId: Long,
        @RequestParam("photos") photos: List<MultipartFile>,
        @RequestParam("title") title: String,
        @RequestParam("description") description: String,
        @RequestParam("hashtags") hashtags: List<String>,
        @RequestParam("togethers") togethers: List<Long>,
    ): ResponseEntity<Long> {
        val command = PortfolioCreateCommend(userId, photos, title, description, hashtags, togethers)
        val portfolioId = portfolioCreateUseCase.create(command)
        return ResponseEntity.ok(portfolioId)
    }

    @GetMapping("portfolio/{id}")
    fun read(
        @PathVariable id: Long,
    ): ResponseEntity<PortfolioDetailResponse> {
        val command = PortfolioReadCommend(id)
        val response = portfolioCreateUseCase.read(command)
        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun readAll(
        pageable: Pageable,
    ): ResponseEntity<Page<PortfolioResponse>> {
        val response = portfolioCreateUseCase.readAll(pageable)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/me")
    fun readAllByMe(
        @Authorization userId: Long,
        pageable: Pageable,
    ): ResponseEntity<Page<PortfolioResponse>> {
        val command = PortfolioReadAllCommend(userId)
        val response = portfolioCreateUseCase.readAllByMe(command, pageable)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/user/{id}")
    fun readByUserId(
        @PathVariable id: Long,
        pageable: Pageable,
    ): ResponseEntity<Page<PortfolioResponse>> {
        val command = PortfolioReadAllCommend(id)
        val response = portfolioCreateUseCase.readByUserId(command, pageable)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/model")
    fun readAllByModel(
        pageable: Pageable,
    ): ResponseEntity<Page<PortfolioResponse>> {
        val response = portfolioCreateUseCase.readAllByModel(pageable)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/photography")
    fun readAllByPhotographer(
        pageable: Pageable,
    ): ResponseEntity<Page<PortfolioResponse>> {
        val response = portfolioCreateUseCase.readAllByPhotographer(pageable)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    fun update(
        @Authorization userId: Long,
        @PathVariable id: Long,
        @RequestParam("photos") photos: List<MultipartFile>,
        @RequestParam("title") title: String,
        @RequestParam("description") description: String,
        @RequestParam("hashtags") hashtags: List<String>,
        @RequestParam("togethers") togethers: List<Long>,
    ): ResponseEntity<Void> {
        val command = PortfolioUpdateCommend(userId, photos, title, description, hashtags, togethers)
        portfolioCreateUseCase.update(command)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}")
    fun delete(
        @Authorization userId: Long,
        @PathVariable id: Long,
    ): ResponseEntity<Void> {
        val command = PortfolioDeleteCommend(id)
        portfolioCreateUseCase.delete(command)
        return ResponseEntity.noContent().build()
    }
}
