# WordHelp

#### A set of tools including a word ladder, anagram generator, and wildcard filler for use in word games such as Scrabble and crossword puzzles.

#### _By: Oliver Calder_

### Tools:
- __Ladder__
  - _A word ladder between two words_
- __Anagram__
  - _An anagram generator_
- __Wildcard__
  - _A wildcard filler_

## Ladder

Builds the shortest ladder between two words by changing one letter at a time.

### Usage:

`java Ladder startWord endWord [--dictFile=fileName] [--alphabet=ABCDEFGHIJKLMNOPQRSTUVWXYZ]`

#### _Example:_

```
$ java Ladder Brain Heart --dictFile=sowpods.txt --ignore=deaws,bruit --ignore=braws

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

`Ladder` uses a custom `MapNode` class which stores the following:
```
String word
boolean visited
MapNode parent
```
Each possible neighbor word is checked against a `HashMap` of `MapNode`s built
by `Loader` from a text file containing a list of 'valid' words.
- _A sample list can be downloaded [here](https://cs.carleton.edu/faculty/jondich/documents/sowpods.txt) by those affiliated with Carleton College_

The `startWord` is added to a custom `LadderQueue` class based on a linked list.

The program takes the first item off the queue, calls it `currentWord`,
and checks its neighbors by doing the following:

If the neighbor is in the `HashMap`:
- it is added to the `LadderQueue`
- its `visited` boolean is set to true
- its `parent` is set to the `MapNode` of `currentWord`

This process is repeated until either:
1. the `LadderQueue` is empty, or 
2. `currentWord` is the specified `endWord`

Then, starting with the last item in the `LadderQueue` (the node for `endWord`),
the ladder is climbed backwards from word to parent, adding each at the beginning
of `ArrayList path`, until the parent is `null`. The path is then printed.

## Anagram

Generates anagrams for a given word.

### Usage:

`$ java Anagram word [--dictFile=fileName]`

#### _Example:_

```
$ java Anagram Bears

Anagrams for BEARS:

BEARS
BARES
BASER
BRAES
SABER
SABRE
```

### Implementation:

`Anagram` uses recursion to generate possible anagrams for a given word.

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
- _A sample list can be downloaded [here](https://cs.carleton.edu/faculty/jondich/documents/sowpods.txt) by those affiliated with Carleton College_

If a word is valid, it is added to a list of valid anagrams, which is then
printed.

## Wildcard

Generates words which fulfill the wildcards of a given word.

### Usage:

`$ java Wildcard word [--dictFile=fileName] [--alphabet=ABCDEFGHIJKLMNOPQRSTUVWXYZ]`

Wildcard characters include `#`, `%`, and `*`
- If `*` is used, word must be surrounded by single quotes (ie. 'word') such that
the shell does not interpret it as a command line wildcard

#### _Example:_

```
$ java Wildcard S#r#t

Possible words which fulfill wildcards of S#R#T:

SCRAT
SPRAT
SPRIT
STRUT
SURAT
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
- _A sample list can be downloaded [here](https://cs.carleton.edu/faculty/jondich/documents/sowpods.txt) by those affiliated with Carleton College_

If a word is valid, it is added to a list of valid words, which is then printed.
