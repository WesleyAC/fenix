/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.tabstray.ext

import mozilla.components.browser.state.state.TabSessionState

private fun TabSessionState.isActive(maxActiveTime: Long): Boolean {
    val lastActiveTime = maxOf(lastAccess, createdAt)
    val now = System.currentTimeMillis()
    return (now - lastActiveTime <= maxActiveTime)
}

/**
 * Returns true if the [TabSessionState] has a search term.
 */
private fun TabSessionState.hasSearchTerm(): Boolean {
    return content.searchTerms.isNotEmpty() || !historyMetadata?.searchTerm.isNullOrBlank()
}

/**
 * Returns true if the [TabSessionState] is considered active based on the [maxActiveTime].
 */
internal fun TabSessionState.isNormalTabActive(maxActiveTime: Long): Boolean {
    return isActive(maxActiveTime) && !content.private
}

/**
 * Returns true if the [TabSessionState] is considered active based on the [maxActiveTime] and
 * does not have a search term
 */
internal fun TabSessionState.isNormalTabActiveWithoutSearchTerm(maxActiveTime: Long): Boolean {
    return isNormalTabActive(maxActiveTime) && !hasSearchTerm()
}

/**
 * Returns true if the [TabSessionState] have a search term.
 */
internal fun TabSessionState.isNormalTabActiveWithSearchTerm(maxActiveTime: Long): Boolean {
    return isNormalTabActive(maxActiveTime) && hasSearchTerm()
}

/**
 * Returns true if the [TabSessionState] has a search term but may or may not be active.
 */
internal fun TabSessionState.isNormalTabWithSearchTerm(): Boolean {
    return hasSearchTerm() && !content.private
}

/**
 * Returns true if the [TabSessionState] is considered active based on the [maxActiveTime].
 */
internal fun TabSessionState.isNormalTabInactive(maxActiveTime: Long): Boolean {
    return !isActive(maxActiveTime) && !content.private
}
