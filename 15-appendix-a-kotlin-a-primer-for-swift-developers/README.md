# Appendix A: Kotlin : A Primer For Swift Developers

## Kotlin And Swift Syntax Table
The following table compares the base functionalities for Swift and Kotlin:

<table>

<tr>
<td> </td> <td> Swift </td> <td> Kotlin </td>
</tr>

<tr>
<td> Package declaration </td>
<td> 

Not required.

</td>
<td>
    
```kotlin
package com.raywenderlich.learn
```

</td>
</tr>

<tr>
<td> Imports </td>
<td> 

```swift
import SharedKit
```

</td>
<td>
    
```kotlin
import com.raywenderlich.learn.YourClass
```

</td>
</tr>

<tr>
<td> Comments </td>
<td> 

```swift
//Single line

/*
 Multiline
 */
```

</td>
<td>
    
```kotlin
//Single line 

/* 
 * Multiline
 */

/*
 Code block
 */
```

</td>
</tr>

<tr>
<td> Immutable variables </td>
<td> 

```swift
let author: String = "Ray Wenderlich"
```

</td>
<td>
    
```kotlin
val author: String = "Ray Wenderlich"
```

</td>
</tr>

<tr>
<td> Mutable variables </td>
<td> 

```swift
var author: String = "Ray Wenderlich"
```

</td>
<td>
    
```kotlin
var author: String = "Ray Wenderlich"
```

</td>
</tr>

<tr>
<td> Lazy initialization </td>
<td> 

```swift
lazy var author: String = {
  return getAuthor()
}
```

</td>
<td>
    
```kotlin
val author: String by lazy {
  getAuthor() 
}
```

</td>
</tr>


<tr>
<td> Late initialization </td>
<td> 

Not available

</td>
<td>
    
```kotlin
lateinit var author: String
```

</td>
</tr>

<tr>
<td> Nullability </td>
<td> 

```swift
nil
```

</td>
<td>
    
```kotlin
null
```

</td>
</tr>

<tr>
<td> Nullability: operations </td>
<td> 

```swift
//Call a function from a variable that's not null
yourVariableName!.yourFunctionName()

//Call a function from a variable that might be null
if let yourVariableName = yourVariableName {
  yourVariableName.yourFunctionName()
}
```

</td>
<td>
    
```kotlin
//Call a function from a variable that's not null
yourVariableName!!.yourFunctionName()

//Call a function from a variable that might be null
//Example #1
yourVariableName?.yourFunctionName()

//Example #2
yourVariableName?.let {
  it.yourFunctionName()
}
```

</td>
</tr>

<tr>
<td> String interpolation </td>
<td> 

```swift
Logger.d(TAG, "The author is \(author)”)
```

</td>
<td>
    
```kotlin
Logger.d(TAG, "The author is $author”)
```

</td>
</tr>

<tr>
<td> Type inference </td>
<td> 

```swift
let author = "Ray Wenderlich"
```

</td>
<td>
    
```kotlin
val author = "Ray Wenderlich"
```

</td>
</tr>


<tr>
<td> Type check </td>
<td> 

```swift
yourVariable is String
```

</td>
<td>
    
```kotlin
yourVariable is String
```

</td>
</tr>


<tr>
<td> Cast </td>
<td> 

```swift
yourVariable as String
```

</td>
<td>
    
```kotlin
yourVariable as String
```

</td>
</tr>


<tr>
<td> Extension function </td>
<td> 

```swift
extension String {
  var toRW: String { "Ray Wenderlich" }
}
```

</td>
<td>
    
```kotlin
fun String.toRW(): String {
  return "Ray Wenderlich"
}
```

</td>
</tr>

<tr>
<td> Comparing objects </td>
<td> 

```swift
//Check if both objects have the same content
book1 == book2

//Check if both objects are the same
book1 === book2
```

</td>
<td>
    
```kotlin
//Check if both objects have the same content
book1 == book2

//Check if both objects are the same
book1 === book2
```

</td>
</tr>

<tr>
<td> 

```
if... else
```

</td>
<td> 

```swift
if (book.type == Book.Type.Android) {
  Logger().d(tag: TAG, message: "Found Android book!")
} else {
  Logger().d(tag: TAG, message: "Android book not found.")
}
```

</td>
<td>
    
```kotlin
if (book.type == Book.Type.Android) {
  Logger.d(TAG, "Found Android book!")
} else {
  Logger.d(TAG, "Android book not found.")
}
```

</td>
</tr>

<tr>
<td> 

```
switch
```

</td>
<td> 

```swift
switch(book.type) {
  case Book.Type.Android:
    Logger().d(tag: TAG, message: "Found Android book!")
  case Book.Type.Ios:
    Logger().d(tag: TAG, message: "Found iOS book!")
  default:
    Logger().w(tag: TAG, message: "Unknown book type: \(book.type)")
}
```

</td>
<td>
    
Not available.

</td>
</tr>

<tr>
<td> 

```
when
```

</td>
<td> 

Not available.

</td>
<td>
    
```kotlin
when(book.type) {
  is Book.Type.Android -> {
    AddAndroidComposable()
  }
  is Book.Type.Ios -> {
    AddIosComposable()
  }
  else -> {
    //Do Nothing
  }
}
```

</td>
</tr>

<tr>
<td> 

```
for
```

</td>
<td> 

```swift
// Iterate all elements
for book in books {
  getBookInfo(book)
}

// Iterate list indices
books.enumerated().forEach { 
    getBookInfo($0)
}

// Get current index and value in each iteration
for (index, book) in books.enumerated() {
  getBookInfo(book)
}
```

</td>
<td>
    
```kotlin
// Iterate all elements
for (book in books) {
  getBookInfo(book)
}


// Iterate list indices
for (index in books.indices) {
  val book = content[index]
  getBookInfo(book)
}

// Get current index and value in each iteration
for ((index, book) in book.withIndex()) {
  getBookInfo(book)
}
```

</td>
</tr>

<tr>
<td> 

```
while
```

</td>
<td> 

```swift
while condition {
  //Do something
}

repeat {
  //Something
} while condition
```

</td>
<td>
    
```kotlin
while (condition) {
  //Do something
}

do {
  //Something
} while (condition)
```

</td>
</tr>

<tr>
<td> Ternary operator </td>
<td> 

```swift
book.type == Book.Type.Android ? "Found Android book!" : "Keep looking for book"
```

</td>
<td>
    
Not available.

</td>
</tr>


<tr>
<td> Lists </td>
<td> 

Not applicable.

No difference between immutable and mutable list object. 

You can create an immutable array (list) by defining it with let.

</td>
<td>
    
```kotlin
val bottomNavigationItems = listOf(
  BottomNavigationScreens.Home,
  BottomNavigationScreens.Bookmark,
  BottomNavigationScreens.Latest,
  BottomNavigationScreens.Search
)
```

</td>
</tr>


<tr>
<td> Mutable lists </td>
<td> 

```swift
var bottomNavigationItems = [
  BottomNavigationScreens.Home,
  BottomNavigationScreens.Bookmark,
  BottomNavigationScreens.Latest,
  BottomNavigationScreens.Search
]
```

</td>
<td>
    
```kotlin
val bottomNavigationItems = mutableListOf(
  BottomNavigationScreens.Home,
  BottomNavigationScreens.Bookmark,
  BottomNavigationScreens.Latest,
  BottomNavigationScreens.Search
)
```

</td>
</tr>


<tr>
<td> Arrays </td>
<td> 

```swift
var bottomNavigationItems: [BottomNavigationScreens] = [
  BottomNavigationScreens.Home,
  BottomNavigationScreens.Bookmark,
  BottomNavigationScreens.Latest,
  BottomNavigationScreens.Search
]
```

</td>
<td>
    
```kotlin
val bottomNavigationItems = arrayOf(
  BottomNavigationScreens.Home,
  BottomNavigationScreens.Bookmark,
  BottomNavigationScreens.Latest,
  BottomNavigationScreens.Search
)
```

</td>
</tr>

<tr>
<td> Dictionaries (maps in Kotlin) </td>
<td> 

Not applicable.

No difference between immutable and mutable maps object. 

You can create an immutable dictionary (map) by defining it with let.

</td>
<td>
    
```kotlin
val bottomNavigationItems = mapOf(
  0 to BottomNavigationScreens.Home,
  1 to BottomNavigationScreens.Bookmark,
  2 to BottomNavigationScreens.Latest,
  3 to BottomNavigationScreens.Search
)
```

</td>
</tr>


<tr>
<td> Mutable dictionaries (maps in Kotlin) </td>
<td> 


```swift
var bottomNavigationItems = [
  0 : BottomNavigationScreens.Home,
  1 : BottomNavigationScreens.Bookmark,
  2 : BottomNavigationScreens.Latest,
  3 : BottomNavigationScreens.Search
]
```

</td>
<td>
    
```kotlin
val bottomNavigationItems = mutableMapOf(
  0 to BottomNavigationScreens.Home,
  1 to BottomNavigationScreens.Bookmark,
  2 to BottomNavigationScreens.Latest,
  3 to BottomNavigationScreens.Search
)
```

</td>
</tr>


<tr>
<td> Classes </td>
<td> 

```swift
class YourClassName {
  var arg: YourArgumentType
}
```

</td>
<td>
    
```kotlin
class YourClassName(private val arg: YourArgumentType)
```

</td>
</tr>


<tr>
<td> Data classes </td>
<td> 

```swift
struct RWBook { 
  let platform: PLATFORM
  let url: String
  let image: String
}
```

</td>
<td>
    
```kotlin
data class RWBook(
  val platform: PLATFORM,
  val url: String,
  val image: String
)
```

</td>
</tr>


<tr>
<td> Sealed classes </td>
<td> 

```swift
enum BottomNavigationScreens {
  struct Content {
    let route: String
    let stringResId: Int
    let drawResId: Int
  }

  case home(route: String, stringResId: Int, drawResId: Int)

  case search(route: String, stringResId: Int, drawResId: Int)
}
```

</td>
<td>
    
```kotlin
sealed class BottomNavigationScreens(
  val route: String,
  @StringRes val stringResId: Int,
  @DrawableRes val drawResId: Int
) {

  object Home : BottomNavigationScreens("Home", R.string.navigation_home, R.drawable.ic_home)

  object Search :
    BottomNavigationScreens("Search", R.string.navigation_search, R.drawable.ic_search)
```

</td>
</tr>


<tr>
<td> Default arguments </td>
<td> 

```swift
class RWBook(
  let platform: PLATFORM = PLATFORM.ALL,
  let url: String,
  let image: String = ""
)
```

</td>
<td>
    
```kotlin
data class RWBook(
  val platform: PLATFORM = PLATFORM.ALL,
  val url: String,
  val image: String = ""
)
```

</td>
</tr>


<tr>
<td> Singletons </td>
<td> 

```swift
class YourSingletonName {
  private init() {
    //Your code goes here
  }

  static let shared = YourSingletonName()

  func yourFunctionName() {
    //Your code goes here
  }
}

//Access to YourFunctionName:
YourSingletonName.shared.yourFunctionName()
```

</td>
<td>
    
```kotlin
public object YourSingletonName {
  
  public fun yourFunctionName() {
    //Your code goes here
  }
}

//Access to YourFunctionName:
YourSingletonName.yourFunctionName()
```

</td>
</tr>

<tr>
<td> Interfaces </td>
<td> 

```swift
protocol YourInterfaceName {
  func onDataReceived(items: RWBooks[])
}
```

</td>
<td>
    
```kotlin
public interface YourInterfaceName {

  public fun onDataReceived(items: List<RWBook>)
}
```

</td>
</tr>

<tr>
<td> Functions </td>
<td> 

```swift
public func getBookInfo(book: RWBook) {

  //Do something
}
```

</td>
<td>
    
```kotlin
public fun getBookInfo(book: RWBook) {

  //Do something
}
```

</td>
</tr>

<tr>
<td> Suspend functions </td>
<td> 

```swift
public func fetch(url: String) async -> HttpResponse { 
  return await withCheckedContinuation {
    //Your request
  }
}
```

</td>
<td>
    
```kotlin
public suspend fun fetch(url: String): HttpResponse { 
  return //Your request
}
```

</td>
</tr>

</table>