package com.org.framelt.project.domain

class ProjectReview(
    val id: Long? = null,
    val reviewer: ProjectMember,
    val reviewee: ProjectMember,
    val tags: List<String>,
    val content: String,
) {
    init {
        require(reviewer.project == reviewee.project) { "리뷰는 같은 프로젝트를 진행한 사용자에게만 남길 수 있습니다." }
        require(reviewer.project.isCompleted()) { "프로젝트가 완료된 후에 리뷰를 남길 수 있습니다." }
        require(reviewer != reviewee) { "리뷰어와 리뷰 대상이 동일할 수 없습니다." }
        require(tags.isNotEmpty()) { "피드백 태그는 최소 1개 이상이어야 합니다." }
        require(content.trim().length >= 10) { "리뷰 내용은 최소 10자 이상이어야 합니다." }
    }

    override fun toString(): String = "ProjectReview(reviewer=$reviewer, reviewee=$reviewee, tags=$tags, content='$content')"
}