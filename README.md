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

`java Ladder startWord endWord [--dictFile=fileName]`

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

### Implementation

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

If a word is in the `HashMap`, it is added to custom `LadderQueue` class based
on a linked list, its `visited` boolean is set to true, and its `parent` is
set to the `MapNode` from which it is being visited.

The program 

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

## Wildcard

Generates words which fulfill the wildcards of a given word.

### Usage:

`$ java Wildcard word [--dictFile=fileName]`

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
