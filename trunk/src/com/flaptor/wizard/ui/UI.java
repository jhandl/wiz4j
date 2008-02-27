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
package com.flaptor.wizard.ui;

import com.flaptor.wizard.Action;
import com.flaptor.wizard.Page;
import com.flaptor.wizard.PageElement;

/**
 * Interface for the UI of a wizard
 * 
 * @author Martin Massera
 */
public interface UI {
    /**
     * execute a page
     * @param page the page to be shown
     * @return the action to be done
     */
    Action doPage(Page page, boolean withBack);
    
    /**
     * for updating an element in the UI
     * @param element the element to be updated
     */
    public void elementUpdated(PageElement element);
}
