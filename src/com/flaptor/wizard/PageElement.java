/*
Copyright 2008 Flaptor (flaptor.com) 

Licensed under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License. 
You may obtain a copy of the License at 

    http://www.apache.org/licenses/LICENSE-2.0 

Unless required by applicable law or agreed to in writing, software 
distributed under the License is distributed on an "AS IS" BASIS, 
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
See the License for the specific language governing permissions and 
limitations under the License.
*/
package com.flaptor.wizard;

/**
 * Represents an element in a wizard page
 * 
 * @author Martin Massera
 */
public class PageElement {
    private String text;
    private String explanation;
    
    public PageElement(String text) {
        this.text= text;
    }

    public PageElement(String text, String explanation) {
        this.text= text;
        this.explanation = explanation;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getText() {
        return text;
    }

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public void setText(String text) {
		this.text = text;
	}
    
    
}
