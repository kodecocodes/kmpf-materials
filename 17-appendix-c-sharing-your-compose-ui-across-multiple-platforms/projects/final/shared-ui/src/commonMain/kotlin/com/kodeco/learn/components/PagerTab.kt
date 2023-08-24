package com.kodeco.learn.components

/**
 * Code adapted from: com.google.accompanist:accompanist-pager-indicators
 */
internal interface PagerStateBridge {
    val currentPage: Int
    val currentPageOffset: Float
}