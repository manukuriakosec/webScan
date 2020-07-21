package com.ecorp.webscan.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
@Embeddable
public class Word implements Serializable,Comparable<Word>{

  
	private static final long serialVersionUID = 1L;

	private String words;

    private Integer frequency;

  

	public String getWords() {
		return words;
	}



	public void setWords(String words) {
		this.words = words;
	}



	public Integer getFrequency() {
		return frequency;
	}



	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((frequency == null) ? 0 : frequency.hashCode());
		result = prime * result + ((words == null) ? 0 : words.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (frequency == null) {
			if (other.frequency != null)
				return false;
		} else if (!frequency.equals(other.frequency))
			return false;
		if (words == null) {
			if (other.words != null)
				return false;
		} else if (!words.equals(other.words))
			return false;
		return true;
	}



	@Override
	public int compareTo(Word arg0) {
		if (!arg0.getFrequency().equals(frequency)) {
			return frequency.compareTo(arg0.getFrequency());
		} else {
			return 0;
		}
	}
}
