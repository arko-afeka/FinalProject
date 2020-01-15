package org.afeka.project.validation.plugin.sqli.libinjection;

import com.google.common.collect.Maps;
import org.ahocorasick.trie.Trie;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Keyword {
  Trie fingerprints;
  Map<String, Character> keywordMap = Maps.newHashMap();

  Keyword(InputStream stream) {
    String word;
    char type;
    Pattern wordpattern, typepattern;
    Matcher matchedword, matchedtype;

    Scanner in = new Scanner(new InputStreamReader(stream));
    String line;

    Trie.TrieBuilder builder = Trie.builder();

    while (in.hasNextLine()) {
      line = in.nextLine();
      wordpattern = Pattern.compile("\\{\"(.*)\"");
      typepattern = Pattern.compile("\'(.*)\'");
      matchedword = wordpattern.matcher(line);
      matchedtype = typepattern.matcher(line);

      while (matchedword.find() && matchedtype.find()) {
        word = matchedword.group(1);
        type = matchedtype.group(1).charAt(0);

        keywordMap.put(word, type);
        if (type == Libinjection.TYPE_FINGERPRINT) {
          builder.addKeyword(word);
        }
      }
    }
    in.close();
    fingerprints = builder.build();
  }

//  void printKeywordMap() {
//    for (String keyword : keywordMap.keySet()) {
//      String keytype = keywordMap.get(keyword).toString();
//      System.out.println("word: " + keyword + " type: " + keytype);
//    }
//    System.out.println("table size: " + keywordMap.size());
//  }
}
