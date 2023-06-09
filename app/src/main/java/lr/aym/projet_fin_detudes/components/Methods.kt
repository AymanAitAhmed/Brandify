package lr.aym.projet_fin_detudes.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import lr.aym.projet_fin_detudes.model.posting.UnreviewedPosts
import lr.aym.projet_fin_detudes.model.posting.UnreviewedPostsReceived

fun convertPosts(postsReceived: List<UnreviewedPostsReceived>): List<UnreviewedPosts> {
    return postsReceived.map { postReceived ->
        val urls = postReceived.imagesUrls?.let { extractUrls(it) }
        UnreviewedPosts(postReceived.id, postReceived.userUID, postReceived.message, urls)
    }
}

fun extractUrls(input: String): List<String> {
    val regex = Regex("""https?://[^\s\[\],]+""")
    val matches = regex.findAll(input)

    return matches.map { it.value }.toList()
}

@Composable
fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}