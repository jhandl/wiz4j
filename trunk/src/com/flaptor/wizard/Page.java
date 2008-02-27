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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * represents a page of the wizard
 * has a list of elements (includes questions and text)
 *  
 * if the page has to decide between various next pages, getNextPage should be overwritten
 * 
 * isLast should also be overwritten, since now it tells if there is nextPage or not
 * 
 * the boolean readyToAdvance indicates whether the page is ready to advance or should wait
 * 
 * @author Martin Massera
 */
public class Page {
    private List<PageElement> elements;
    private Page nextPage;
    private boolean canCancelOrBack = true;
    private boolean readyToAdvance = true;
    private boolean automaticAdvance = false;
    
    private Runnable preRenderCallback;
    private Runnable preBackCallback;
    private Callable<Boolean> validateInputCallback;
    private Runnable preNextCallback;
    
    public Page() {
        this.elements = new ArrayList<PageElement>();
    }
    public Page(List<PageElement> elements) {
        this.elements = elements;
    }

    public List<PageElement> getElements() {
        return elements;
    }

    public void clearElements() {
    	elements.clear();
    }
    
    public Page add(PageElement e) {
        elements.add(e);
        return this;
    }
    
    public boolean isLast() {
        return nextPage == null;
    }

    /**
     * @param nextPage
     * @return nextPage, so as to write chained setNextPages
     */
    public Page setNextPage(Page nextPage) {
        this.nextPage = nextPage;
        return nextPage;
    }

    public Page getNextPage() {
        return nextPage;
    }

    /**
     * @param propertyName
     * @return the input page element that holds the propertyname
     */
    public InputPageElement getElementForProperty(String propertyName) {
        for (PageElement element : elements) {
            if (element instanceof InputPageElement) {
                InputPageElement input = (InputPageElement) element;
                if (input.getPropertyName().equals(propertyName)) return input;
            }
        }
        return null;
    }

    /**
     * looks in all the input PageElements to find the value of the property
     * @param propertyName the property to look for
     * @return the value of the property
     */
    public String getProperty(String propertyName) {
        InputPageElement element = getElementForProperty(propertyName);
        if (element != null) return element.getAnswer();
        else return null;
    }

    /**
     * looks in all the input PageElements to find the element that holds the property and sets the value
     * @param propertyName the property to look for
     * @param value new the value
     */
    public void setProperty(String propertyName, String value) {
        InputPageElement element = getElementForProperty(propertyName);
        if (element != null) element.setAnswer(value);
    }
       
    public boolean isCanCancelOrBack() {
        return canCancelOrBack;
    }
    public void setCanCancelOrBack(boolean canCancelOrBack) {
        this.canCancelOrBack = canCancelOrBack;
    }
    public Runnable getPreRenderCallback() {
        return preRenderCallback;
    }
    public void setPreRenderCallback(Runnable preRenderCallback) {
        this.preRenderCallback = preRenderCallback;
    }
    public Runnable getPreBackCallback() {
        return preBackCallback;
    }
    public void setPreBackCallback(Runnable preBackCallback) {
        this.preBackCallback = preBackCallback;
    }
    public Runnable getPreNextCallback() {
        return preNextCallback;
    }
    public void setPreNextCallback(Runnable preNextCallback) {
        this.preNextCallback = preNextCallback;
    }
    public Callable<Boolean> getValidateInputCallback() {
        return validateInputCallback;
    }
    public void setValidateInputCallback(Callable<Boolean> validateInputCallback) {
        this.validateInputCallback = validateInputCallback;
    }
    public boolean isReadyToAdvance() {
        return readyToAdvance;
    }
    public void setReadyToAdvance(boolean readyToAdvance) {
        this.readyToAdvance = readyToAdvance;
    }
    public boolean isAutomaticAdvance() {
        return automaticAdvance;
    }
    public void setAutomaticAdvance(boolean automaticAdvance) {
        this.automaticAdvance = automaticAdvance;
    }
}
