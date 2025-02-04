package com.example.dictionaryapplication.domain.utils

import java.math.BigInteger
import java.security.MessageDigest
import java.util.Stack

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

fun stringMatching(words: Array<String>): List<String> {
    return words.filter { word -> words.any { it.contains(word) && it != word } }
}

fun strMatching(words: Array<String>): List<String> {
    return words.filter { word ->
        words.any {
            it.contains(word) && it != word
        }
    }
}

fun twoSum(nums: IntArray, target: Int): IntArray {
    val seen = mutableMapOf<Int, Int>() // Maps value to index
    nums.forEachIndexed { index, num ->
        val x = target - num
        if (seen.containsKey(x)) {
            return intArrayOf(seen[x]!!, index)
        }
        seen[num] = index
    }
    throw IllegalArgumentException("No solution")
}


fun romanToInt(s: String): Int {
    return s.foldIndexed(0) { index, acc, c ->
        val value = when (c) {
            'I' -> if ("VX".contains(s.getOrNull(index + 1) ?: ' ')) -1 else 1
            'V' -> 5
            'X' -> if ("LC".contains(s.getOrNull(index + 1) ?: ' ')) -10 else 10
            'L' -> 50
            'C' -> if ("DM".contains(s.getOrNull(index + 1) ?: ' ')) -100 else 100
            'D' -> 500
            'M' -> 1000
            else -> 0
        }
        acc + value
    }
}

fun romanToInt2(s: String): Int {
    // 1. Create a Map for Roman Numeral Values
    val translations = mapOf(
        "I" to 1,
        "V" to 5,
        "X" to 10,
        "L" to 50,
        "C" to 100,
        "D" to 500,
        "M" to 1000
    )

    // 2. Preprocess the Input String (Subtractive Notation Handling)
    var modifiedString = s
        .replace("IV", "IIII")
        .replace("IX", "VIIII")
        .replace("XL", "XXXX")
        .replace("XC", "LXXXX")
        .replace("CD", "CCCC")
        .replace("CM", "DCCCC")

    // 3. Initialize the Result Variable
    var number = 0

    // 4. Iterate Through the String and Sum Values
    for (char in modifiedString) {
        number += translations.getValue(char.toString()) // Get value from map and add to total
    }

    // 5. Return the Final Result
    return number
}

fun longestCommonPrefix(strs: Array<String>): String {
    val example = arrayOf("flower", "flow", "flight")
    if (example.isEmpty()) return ""

    var prefix = example[0]

    for (index in 1 until example.size) {
        while (!example[index].startsWith(prefix)) {
            prefix = prefix.substring(0, prefix.length - 1)

            if (prefix.isEmpty()) return ""
        }
    }
    return prefix
}

fun isPalindrome(x: Int): Boolean {
    return x.toString() == x.toString().reversed()
}

fun longPrefix(strs: Array<String>): String {
    val word = strs[0]
    word.forEachIndexed { index, character ->
        if (strs.any { it.length == index || it[index] != character })
            return word.substring(0, index)
    }
    return word
}

class ListNode(var value: Int) {
    var next: ListNode? = null
}

fun sortList(head: ListNode?): ListNode? {
    val arr = ArrayList<Int>()
    var curr = head
    while (curr != null) {
        arr.add(curr.value)
        curr = curr.next
    }
    arr.sort()
    var i = 0
    curr = head
    while (curr != null) {
        curr.value = arr[i]
        curr = curr.next
        i++
    }
    return head
}

fun isValid(s: String): Boolean {
    if (s.isEmpty()) return true
    val simplify = s.replace("()", "").replace("{}", "").replace("[]", "")
    if (simplify == s) return false
    return isValid(simplify)
}

fun isValid2(s: String): Boolean {
    val stack = Stack<Char>()

    for (char in s) {
        if (stack.isNotEmpty() && char == ')' && stack.peek() == '(') {
            stack.pop()
            continue
        }
        if (stack.isNotEmpty() && char == ']' && stack.peek() == '[') {
            stack.pop()
            continue
        }
        if (stack.isNotEmpty() && char == '}' && stack.peek() == '{') {
            stack.pop()
            continue
        }
        stack.push(char)
    }
    return stack.isEmpty()
}