package com.raywenderlich.organize.data

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RemindersRepositoryTest {
  private lateinit var repository: RemindersRepository

  @BeforeTest
  fun setup() {
    repository = RemindersRepository()
  }

  @Test
  fun testCreatingReminder() {
    val title = "New Title"
    repository.createReminder(title)

    val count = repository.reminders.count {
      it.title == title
    }

    assertTrue(
      actual = count == 1,
      message = "Reminder with title: $title wasn't created.",
    )
  }

  @Test
  fun testMarkingExistingReminderAsTrue() {
    val title = "New Title"
    repository.createReminder(title)

    val reminder = repository.reminders[0]
    assertEquals(reminder.title, title)

    repository.markReminder(reminder.id, true)
    assertTrue(repository.reminders[0].isCompleted)
  }
}