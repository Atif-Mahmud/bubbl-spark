package tech.gaggle.extapi.search

trait PagedSearchEngine[PageIdentifier] extends TextSearchEngine {
  def performSearch(searchQuery: String, page: PageIdentifier): List[SearchResult]
}
