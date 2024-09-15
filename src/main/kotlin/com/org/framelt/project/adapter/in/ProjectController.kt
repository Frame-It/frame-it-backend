package com.org.framelt.project.adapter.`in`

import com.org.framelt.project.adapter.`in`.request.ProjectApplicationCancelRequest
import com.org.framelt.project.adapter.`in`.request.ProjectApplyRequest
import com.org.framelt.project.adapter.`in`.request.ProjectCreateRequest
import com.org.framelt.project.adapter.`in`.request.ProjectUpdateRequest
import com.org.framelt.project.adapter.`in`.response.ProjectAnnouncementDetailResponse
import com.org.framelt.project.adapter.`in`.response.ProjectAnnouncementItemResponse
import com.org.framelt.project.adapter.`in`.response.ProjectApplyResponse
import com.org.framelt.project.adapter.`in`.response.ProjectCreateResponse
import com.org.framelt.project.adapter.`in`.response.ProjectUpdateResponse
import com.org.framelt.project.application.port.`in`.ProjectAnnouncementDetailCommand
import com.org.framelt.project.application.port.`in`.ProjectApplicantAcceptCommand
import com.org.framelt.project.application.port.`in`.ProjectApplyUseCase
import com.org.framelt.project.application.port.`in`.ProjectCompleteCommand
import com.org.framelt.project.application.port.`in`.ProjectCompleteUseCase
import com.org.framelt.project.application.port.`in`.ProjectCreateUseCase
import com.org.framelt.project.application.port.`in`.ProjectReadUseCase
import com.org.framelt.project.application.port.`in`.ProjectUpdateUseCase
import com.org.framelt.project.common.ProjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class ProjectController(
    val projectCreateUseCase: ProjectCreateUseCase,
    val projectReadUseCase: ProjectReadUseCase,
    val projectCompleteUseCase: ProjectCompleteUseCase,
    val projectUpdateUseCase: ProjectUpdateUseCase,
    val projectApplyUseCase: ProjectApplyUseCase,
) {
    @PostMapping("/projects")
    fun create(
        @RequestBody projectCreateRequest: ProjectCreateRequest,
    ): ResponseEntity<ProjectCreateResponse> {
        val createCommand = ProjectMapper.toCommand(projectCreateRequest)
        val createdProjectId = projectCreateUseCase.create(createCommand)
        val response =
            ProjectCreateResponse(
                projectId = createdProjectId,
                title = projectCreateRequest.title,
            )
        return ResponseEntity.ok(response)
    }

    @GetMapping("/projects/announcement")
    fun showAnnouncementList(
        @RequestParam(required = false) recruitmentRole: String?,
        @RequestParam(required = false) startDate: LocalDate?,
        @RequestParam(required = false) endDate: LocalDate?,
        @RequestParam(required = false) timeOption: String?,
        @RequestParam(required = false) spot: String?,
        @RequestParam(required = false) locationType: String?,
        @RequestParam(required = false) concepts: String?,
        @RequestParam(required = true) userId: Long,
    ): ResponseEntity<List<ProjectAnnouncementItemResponse>> {
        val projectFilterCommand =
            ProjectMapper.toCommand(
                recruitmentRole = recruitmentRole,
                startDate = startDate,
                endDate = endDate,
                timeOption = timeOption,
                spot = spot,
                locationType = locationType,
                concepts = concepts,
                userId = userId,
            )
        val projectItems = projectReadUseCase.getProjectAnnouncementList(projectFilterCommand)
        val response = projectItems.map { ProjectMapper.toResponse(it) }
        return ResponseEntity.ok(response)
    }

    @GetMapping("/projects/{projectId}/announcement")
    fun showAnnouncementDetail(
        @PathVariable projectId: Long,
        @RequestParam userId: Long,
    ): ResponseEntity<ProjectAnnouncementDetailResponse> {
        val projectAnnouncementDetailCommand = ProjectAnnouncementDetailCommand(projectId, userId)
        val projectDetail = projectReadUseCase.getProjectAnnouncementDetail(projectAnnouncementDetailCommand)
        val response = ProjectMapper.toResponse(projectDetail)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/projects")
    fun update(
        @RequestBody projectUpdateRequest: ProjectUpdateRequest,
    ): ResponseEntity<ProjectUpdateResponse> {
        val updateCommand = ProjectMapper.toCommand(projectUpdateRequest)
        val updatedProjectId = projectUpdateUseCase.update(updateCommand)
        val response = ProjectUpdateResponse(projectId = updatedProjectId)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/projects/{projectId}/apply")
    fun apply(
        @PathVariable projectId: Long,
        @RequestBody projectApplyRequest: ProjectApplyRequest,
    ): ResponseEntity<ProjectApplyResponse> {
        val projectApplyCommand = ProjectMapper.toCommand(projectId, projectApplyRequest)
        val applyResult = projectApplyUseCase.applyProject(projectApplyCommand)
        val response = ProjectApplyResponse(projectTitle = applyResult.projectTitle)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/projects/{projectId}/applicants/{applicantId}/cancel")
    fun cancelApplication(
        @PathVariable projectId: Long,
        @PathVariable applicantId: Long,
        @RequestBody projectApplicationCancelRequest: ProjectApplicationCancelRequest,
    ): ResponseEntity<Unit> {
        val projectApplicantCancelCommand = ProjectMapper.toCommand(projectId, applicantId, projectApplicationCancelRequest)
        projectApplyUseCase.cancelApplication(projectApplicantCancelCommand)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/projects/{projectId}/applicants/{applicantId}/accept")
    fun acceptApplicant(
        @PathVariable projectId: Long,
        @PathVariable applicantId: Long,
    ): ResponseEntity<Unit> {
        // TODO: 프로젝트 호스트 권한 체크
        val projectApplicantAcceptCommand = ProjectApplicantAcceptCommand(projectId, applicantId)
        projectApplyUseCase.acceptApplicant(projectApplicantAcceptCommand)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/projects/{projectId}/complete")
    fun complete(
        @RequestParam memberId: Long,
        @PathVariable projectId: Long,
    ): ResponseEntity<Unit> {
        val projectCompleteCommand = ProjectCompleteCommand(projectId, memberId)
        projectCompleteUseCase.complete(projectCompleteCommand)
        return ResponseEntity.ok().build()
    }
}
