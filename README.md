# WordHelp

#### A set of tools including a word ladder, anagram generator, and wildcard filler for use in word games such as Scrabble and crossword puzzles.

#### _By: Oliver Calder_

## Ladder

Builds builds the shortest ladder between two words by changing one letter at a time.

#### Usage:

`java Ladder startWord endWord [--dictFile=fileName]`

___Example:___

```
$ java Ladder Brain Heart --ignore=deaws,brast

Start word: BRAIN
End word: HEART
Ignored words: DEAWS BRAST

BRAIN
BRAWN
BRAWS
BRADS
BEADS
HEADS
HEARS
HEART
```
