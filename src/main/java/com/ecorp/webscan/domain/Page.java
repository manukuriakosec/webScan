package com.ecorp.webscan.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Page extends BaseEntity {

  
	private static final long serialVersionUID = 1L;
	@Column(unique = true)
    private String url;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "frequentWords", joinColumns = @JoinColumn(name = "id"))
    private Set<Word> frequentWords;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "frequentWordPair", joinColumns = @JoinColumn(name = "id"))
    private Set<Word> frequentWordPair;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Word> getFrequentWords() {
        return frequentWords;
    }

    public void setFrequentWords(Set<Word> frequentWords) {
        this.frequentWords = frequentWords;
    }

    public Set<Word> getFrequentWordPair() {
        return frequentWordPair;
    }

    public void setFrequentWordPair(Set<Word> frequentWordPair) {
        this.frequentWordPair = frequentWordPair;
    }

	@Override
	public String toString() {
		return "Page [url=" + url + ", frequentWords=" + frequentWords + ", frequentWordPair=" + frequentWordPair + "]";
	}
    
}
