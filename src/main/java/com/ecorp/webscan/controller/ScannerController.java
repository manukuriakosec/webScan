package com.ecorp.webscan.controller;

import com.ecorp.webscan.domain.Page;
import com.ecorp.webscan.domain.Word;
import com.ecorp.webscan.service.PageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ScannerController responsible for 10 most frequent word and most 10 frequent word pair from a web page
 */
@RestController
public class ScannerController {

    /** The page service. */
    private PageService pageService;

    /**
     * Instantiates a new scanner controller.
     *
     * @param pageService the page service
     */
    public ScannerController(PageService pageService) {
        this.pageService = pageService;
    }

    /**
     * Gets the info from the web page url.
     *
     * @param url the url
     * @param recompute the recompute
     * @return the info
     */
    @GetMapping(value = "/pages/scan",produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getInfo(
    		@RequestParam String url,
    		@RequestParam(required = false) Boolean recompute) {
    	
    	if(StringUtils.isEmpty(url)) 
    		return new ResponseEntity<String>("URL Required", HttpStatus.OK);

        Page page = pageService.findByUrl(url);
        if(page !=null)

        if(StringUtils.isEmpty(recompute))
            recompute = false;

        if (Boolean.TRUE.equals(recompute) || StringUtils.isEmpty(page)) {
            String pageInfoInString = getPageInfoIntoStingFromUrl(url);
            String[] words = splitStingIntoStringArray(pageInfoInString);
            page = get10FrequentWords(words, page, url);
            page = get10FrequentWordsPairs(words, page);
            pageService.save(page);
        }
        return new ResponseEntity<Page>(page, HttpStatus.OK);
    }

    /**
     * Gets the 10 frequent words pairs.
     *
     * @param words the words
     * @param page the page
     * @return the 10 frequent words pairs
     */
    private Page get10FrequentWordsPairs(String[] words, Page page) {
        //Map holds words as keys and their frequency as values
        HashMap<String, Integer> wordCountMap = new HashMap<String, Integer>();
        for(int i=0;i<words.length-1;i++){
            String word1 = new StringBuffer(words[i]).toString().toLowerCase();
            String word2 = new StringBuffer(words[i+1]).toString().toLowerCase();
            StringBuffer word = new StringBuffer();
            word = word.append(word1).append(" ").append(word2);
            String key = word.toString().toLowerCase();

            wordCountMap.put(key, wordCountMap.getOrDefault(key, 0) + 1);
            word1 = word2 = null;
        }
        Set<Word> frequentWords = new HashSet<>();
        HashMap<String, Integer> sortedwordCountMap = sortByValues(wordCountMap);
        int i =0;
        for (Map.Entry<String, Integer> entry : sortedwordCountMap.entrySet()) {
        	 if(i<=9){
                 Word word = new Word();
                 word.setWords((String) entry.getKey());
                 word.setFrequency((Integer) entry.getValue());
                 frequentWords.add(word);
             }
             i++;           
        }
        
        page.setFrequentWordPair(frequentWords);

        return page;
    }

    /**
     * Gets the 10 frequent words.
     *
     * @param words the words
     * @param page the page
     * @param url the url
     * @return the 10 frequent words
     */
    private Page get10FrequentWords(String[] words, Page page, String url) {

        //Map holds words as keys and their frequency as values
        HashMap<String, Integer> wordCountMap = new HashMap<String, Integer>();
        wordCountMap = createWordCountMap(words);
        HashMap<String, Integer> sortedwordCountMap = sortByValues(wordCountMap);
        if(page == null){
            page = new Page();
            page.setUrl(url);
        }  
        Set<Word> frequentWords = new HashSet<>();
        int i =0;
        for (Map.Entry<String, Integer> entry : sortedwordCountMap.entrySet()) {
        	 if(i<=9){
                 Word word = new Word();
                 word.setWords((String) entry.getKey());
                 word.setFrequency((Integer) entry.getValue());
                 frequentWords.add(word);
                 
             }
             i++;
           
        }
        page.setFrequentWords(frequentWords);
        return page;

    }

    /**
     * Sort wordCountMap by its frequency.
     *
     * @param wordCountMap the word count map
     * @return the hash map
     */
    private HashMap<String, Integer> sortByValues(HashMap<String, Integer> wordCountMap) {

    	// Create a list from elements of HashMap 
    	// Because its required for sorting
        List<Map.Entry<String, Integer> > list = 
               new LinkedList<Map.Entry<String, Integer> >(wordCountMap.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() { 
            public int compare(Map.Entry<String, Integer> o1,  
                               Map.Entry<String, Integer> o2) 
            { 
                return (o2.getValue()).compareTo(o1.getValue()); 
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); 
        for (Map.Entry<String, Integer> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    }

    /**
     * Creates the word count map.
     *
     * @param words the words
     * @return the hash map
     */
    private HashMap<String, Integer> createWordCountMap(String[] words) {
        HashMap<String, Integer> wordCountMap = new HashMap<String, Integer>();
        for (String word : words)
        {
            //if word is already present in wordCountMap, updating its count

            if(wordCountMap.containsKey(word))
            {
                wordCountMap.put(word, wordCountMap.get(word)+1);
            }

            //otherwise inserting the word as key and 1 as its value
            else
            {
                wordCountMap.put(word, 1);
            }
        }


        return wordCountMap;

    }

    /**
     * Split sting into string array.
     *
     * @param pageInfoInString the page info in string
     * @return the string[]
     */
    private String[] splitStingIntoStringArray(String pageInfoInString) {
        String[] words = null;
          if(!StringUtils.isEmpty(pageInfoInString))
        //splitting the string into words
            words = pageInfoInString.toLowerCase().split(" ");

        return words;
    }

    /**
     * Gets the page info into sting from url.
     *
     * @param stringUrl the string url
     * @return the page info into sting from url
     */
    private String getPageInfoIntoStingFromUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Scanner sc = null;
        try {
            sc = new Scanner(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //StringBuffer class to hold the result
        StringBuffer sb = new StringBuffer();
        while(sc.hasNext()) {
            sb.append(sc.next());
        }
        //Retrieving the String from the String Buffer
        String result = sb.toString();
        //Removing the HTML tags
        result = result.replaceAll("<[^>]*>", "");
        result = result.replaceAll("[-+.^:,&$#@!~;_`'{}|\\/><\"()%=?\\t\\r\\n0-9–\\[\\]]"," ");
        result = result.replace("\\", "");
        return result;
    }
}