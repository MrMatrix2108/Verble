import com.verbleGame.verble.VerbModel

fun charListToSpacedString(charList: List<Char>): String { //this method simply adds a space between characters for display and readability purposes
        val builder = StringBuilder()
        for (char in charList) {
            builder.append("$char ")
        }
        return builder.toString().trim() //trim() gets rid of the last pace added to the string by the builder
    }

    fun validateGuess(string: String): Boolean { //this method is used to validate the users guess before processing
        val regex = Regex("^[a-zA-Z]$") //regex that checks string for letter with length of 1
        return regex.matches(string)
    }

object LsGlobal {
    val lsVerbs =
        mutableListOf<VerbModel>() //holds a list of objects that contain a verb, hint, and an
}

