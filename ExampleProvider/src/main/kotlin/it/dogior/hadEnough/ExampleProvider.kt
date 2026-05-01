package com.lagradost

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*

class FilmModu : MainAPI() { 
    override var name = "FilmModu"
    override var mainUrl = "https://www.filmmodu.one"
    override val hasMainPage = true
    override var lang = "tr"

    override suspend fun getMainPage(page: Int, request: HomePageRequest): HomePageResponse {
        val document = app.get(mainUrl).document
        val home = document.select("div.movie-item").mapNotNull {
            val title = it.selectFirst("h3")?.text() ?: return@mapNotNull null
            val href = it.selectFirst("a")?.attr("href") ?: return@mapNotNull null
            val poster = it.selectFirst("img")?.attr("src")
            newMovieSearchResponse(title, href, TvType.Movie) { this.posterUrl = poster }
        }
        return newHomePageResponse("Ana Sayfa", home)
    }
}
