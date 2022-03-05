/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.learn.domain.dao

import com.raywenderlich.learn.data.model.RWEntry
import data.AppDb

public class RWEntryDAO(database: AppDb) {

  private val db = database.rWEntryModelQueries

  public fun insertOrReplace(entries: List<RWEntry>) {
    for (entry in entries) {
      insertOrReplace(entry)
    }
  }

  public fun insertOrReplace(entry: RWEntry) {
    db.insertOrReplaceRWEntry(
      id = entry.id,
      link = entry.link,
      title = entry.title,
      summary = entry.summary,
      updated = entry.updated,
      bookmarked = if (entry.bookmarked) 1 else 0
    )
  }

  public fun remove(entry: RWEntry) {
    db.removeRWEntry(
      id = entry.id
    )
  }

  public fun getAllEntries(): List<RWEntry> {
    val data = db.selectAllRWEntries().executeAsList()

    val entries = mutableListOf<RWEntry>()
    for (item in data) {
      entries += RWEntry(
        id = item.id,
        link = item.link,
        title = item.title,
        summary = item.summary,
        updated = item.updated,
        bookmarked = item.bookmarked == 1
      )
    }

    return entries
  }

  public fun clearAllEntries() {
    db.clearAllRWEntries()
  }
}