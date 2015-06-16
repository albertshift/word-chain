# word-chain
Word-Chain my solution with complexity O(n*log(n))

### Linguistic Chains

Write a program in Java or Scala that finds the word from which one can remove the most letters, one at a time, such
 that each resulting word is itself a valid word. For example, you can remove six letters from "starting":

starting => stating => statin => satin => sati => sat => at

assuming your dictionary is:

```
at
bat
be
bee
sat
sati
satin
starting
statin
stating
```

The program must take the path to a dictionary as input. The dictionary will contain words, one per line. The program
 must output the longest chains which can be created from the words in the dictionary.
Please note that the input is only the dictionary; the input does not include a hypothetical starting word.
  The format must be as above with one space between each word and the following "=>" and one space after the "=>". If there are multiple words that produce equal length chains, then print each chain on a line by itself.

Your program must work with large dictionaries with more than a hundred thousand words.

What is the complexity of your program?
