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

/**
 * represents a page of the wizard
 * has a list of elements (includes questions and text)
 *  
 * if the page has to decide between various next pages, getNextPage should be overwritten
 * 
 * isLast should also be overwritten, since now it tells if there is nextPage or not 
 *  
 * @author Martin Massera
 */
public class NextChoosingPage extends Page{
    protected List<Page> nextPages = new ArrayList<Page>();
    String choosingProperty;
    
    public NextChoosingPage(String choosingProperty) {
        this.choosingProperty = choosingProperty;
    }

    public NextChoosingPage addNextPage(Page e) {
        nextPages.add(e);
        return this;
    }
    
    @Override
    public boolean isLast() {
        return nextPages.isEmpty();
    }

    @Override
    public Page getNextPage() {
        return nextPages.get(Integer.parseInt(getProperty(choosingProperty)));
    }
}
