package bu.ac.kr.github_repository_app.data.response

import bu.ac.kr.github_repository_app.data.entity.GithubRepoEntity

data class GithubRepoSearchResponse(
    val totalCount: Int,
    val items: List<GithubRepoEntity>
)