# WordHelp

#### A set of tools including a word ladder, anagram generator, and wildcard filler for use in word games such as Scrabble and crossword puzzles.

#### _By: Oliver Calder_

### Tools:
- Ladder
> A word ladder between two words
- Anagram
> An anagram generator
- Wildcard
> A wildcard filler

## Ladder

Builds the shortest ladder between two words by changing one letter at a time.

#### Usage:

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

## Anagram

Generates anagrams for a given word.

#### Usage:

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

#### Usage:

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
