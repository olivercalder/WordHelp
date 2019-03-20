# WordHelp

#### A set of tools including a word ladder, anagram generator, and wildcard filler for use in word games such as Scrabble and crossword puzzles.

#### _By: Oliver Calder_

### Tools:
- __Check:__ Checks whether a word is in the dictionary
  - [Description](https://github.com/olivercalder/WordHelp#Check)
  - [Usage](https://github.com/olivercalder/WordHelp#Usage)
  - [Implementation](https://github.com/olivercalder/WordHelp#Implementation)
- __Ladder:__ A word ladder between two words
  - [Description](https://github.com/olivercalder/WordHelp#Ladder)
  - [Usage](https://github.com/olivercalder/WordHelp#Usage-1)
  - [Implementation](https://github.com/olivercalder/WordHelp#Implementation-1)
- __Anagram:__ An anagram generator
  - [Description](https://github.com/olivercalder/WordHelp#Anagram)
  - [Usage](https://github.com/olivercalder/WordHelp#Usage-2)
  - [Implementation](https://github.com/olivercalder/WordHelp#Implementation-2)
- __Wildcard:__ A wildcard filler
  - [Description](https://github.com/olivercalder/WordHelp$#Wildcard)
  - [Usage](https://github.com/olivercalder/WordHelp#Usage-3)
  - [Implementation](https://github.com/olivercalder/WordHelp#Implementation-3)

## Check

Checks whether a word is in the dictionary.

### Usage:

`java Check word [--dictFile=fileName]`

#### _Example:_

```
$ java Check concatenate

CONCATENATE is a valid word in the dictionary.
```

### Implementation:

`Check` builds a `HashSet` of words from a given dictionary file using `Loader`.
- _Sample dictionaries can be found in the Dictionaries folder_

Then, the program checks whether the given word appears in that `HashSet`.

## Ladder

Builds the shortest ladder between two words by changing one letter at a time.

### Usage:

`java Ladder startWord endWord [--dictFile=fileName] [--alphabet=ABCDEFGHIJKLMNOPQRSTUVWXYZ]`

#### _Example:_

```
$ java Ladder Brain Heart --dictFile=Dictionaries/english_words.txt --ignore=deaws,bruit --ignore=braws

Start word: BRAIN
End word: HEART
Ignored words: DEAWS BRUIT BRAWS

BRAIN
BRAID
BRAND
BRANS
BEANS
BEARS
HEARS
HEART
```

### Implementation:

`Ladder` uses breadth first search to find the shortest path between two words
by traversing through words which differ by only one letter from the parent word.

`Ladder` uses a custom `GraphNode` class which stores the following:
```
String word
boolean visited
GraphNode parent
```
Each possible neighbor word is checked against a `HashMap` of `GraphNode`s built
by `Loader` from a text file containing a list of 'valid' words.
- _Sample dictionaries can be found in the Dictionaries folder_

The `startWord` is added to a custom `LadderQueue` class based on a linked list.

The program takes the first item off the queue, calls it `currentWord`,
and checks its neighbors by doing the following:

If the neighbor is in the `HashMap`:
- it is added to the `LadderQueue`
- its `visited` boolean is set to true
- its `parent` is set to the `GraphNode` of `currentWord`

This process is repeated until either:
1. the `LadderQueue` is empty, or 
2. `currentWord` is the specified `endWord`

Then, starting with the last item in the `LadderQueue` (the node for `endWord`),
the ladder is climbed backwards from word to parent, adding each at the beginning
of `ArrayList path`, until the parent is `null`. The path is then printed.

## Anagram

Generates anagrams for a given word.

### Usage:

`java Anagram word [--dictFile=fileName] [--recursive=true/false]`

#### _Example:_

```
$ java Anagram faire --dictFile=Dictionaries/mots_francais.txt --recursive=false

Iterative Anagrams for FAIRE:

FERAI
FRAIE
FAIRE
FIERA
```

### Implementation:

`Anagram` uses recursion or iteration to generate possible anagrams for a given word.

If the type has not been specified, the program will first decide whether to use
iteration or recursion by comparing the length of the given word to the size of the
dictionary. If the size of the dictionary is greater than the length of the word to
the power of itself (length**length), then the program will use recursion. Otherwise,
it will use iteration.

#### Recursion method:

The program first splits the word into its individual characters and then
uses the `subAnagram` method to build possible anagrams with the given letters.

The `subAnagram` method does the following:
1. chooses one of the characters
2. passes the remaining characters again through the `subAnagram` method
3. builds possible words by concatenating the chosen character with the
generated anagrams of the other characters.

This process is repeated for every character in the original
word, thus generating every possible combination of the letters of that
word.

Finally, each possible anagram is checked against a `HashSet` built
by `Loader` from a text file containing a list of 'valid' words.
- _Sample dictionaries can be found in the Dictionaries folder_

If a word is valid, it is added to a new `HashSet` of valid anagrams, which is then
printed.
- A `HashSet` is used, rather than an `ArrayList`, so as to avoid duplicates
created by words in which one or more letters occur multiple times.

#### Iteration method:

The program first splits the word into its individual characters and then sorts them,
thus generating an alphabetized list of characters. Any word which undergoes the same
sorting process will have an identical list of characters.

The program then loads a dictionary HashSet built by Loader from a text file containing 
a list of 'valid' words.
- _Sample dictionaries can be found in the Dictionaries folder_

Each of these words is then split into characters by the same
process as the input word, its characters are sorted, and, if it matches the characters
of the given word, it is added (as it originally was) to the `HashSet` of valid anagrams.

## Wildcard

Generates words which fulfill the wildcards of a given word.

### Usage:

`java Wildcard word [--dictFile=fileName] [--alphabet=ABCDEFGHIJKLMNOPQRSTUVWXYZ]`

Wildcard characters include `#`, `%`, and `*`
- If `*` is used, word must be surrounded by single quotes (ie. 'word') such that
the shell does not interpret it as a command line wildcard

#### _Example:_

```
$ java Wildcard 'ап*****' --dictFile=Dictionaries/russian_words.txt --alphabet=АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ

Possible words which fulfill wildcards of АП*****:

АПОКРИФ
АПОЛЛОН
АПОФЕОЗ
АППЕТИТ
```

### Implementation:

`Wildcard` uses iteration to generate a list of words which fit with the wildcard
characters by replacing each wildcard character with every possible letter.

The program first creates a list of the indices which contain wildcard characters,
and then, one at a time, replaces the characters at those indices with every valid
character, thus constructing a list of words with one fewer wildcard. This process
is repeated until no wildcard characters remain.

The result is a list of possible words which fulfill the combination of characters
and wildcards found in the original word.

Finally, each possible word is checked against a `HashSet` built
by `Loader` from a text file containing a list of 'valid' words.
- _Sample dictionaries can be found in the Dictionaries folder_

If a word is valid, it is added to a list of valid words, which is then printed.
