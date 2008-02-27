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
import java.util.Stack;

import com.flaptor.wizard.ui.UI;

/**
 * class for building wizards. You create a wizard with an initial page
 * and a UI for rendering. this page must be linked with other pages.
 * It maintains a stack of pages to know how to go back.  
 * 
 * @author Martin Massera
 */
public class Wizard {
    
    Page initialPage;
    UI ui;
    Stack<Page> pages;
    
    public Wizard(Page initialPage, UI ui) {
        this.initialPage = initialPage;
        this.ui = ui;
    }
    
    /**
     * executes the wizard 
     * @return the list of pages executed or null if it was cancelled
     */
    public List<Page> startWizard() {
        Page page = initialPage;
        pages = new Stack<Page>();
        while (true) {
            Action action = ui.doPage(page, !pages.isEmpty());
            if (action ==  Action.cancel) {
                return null;
            } else if (action == Action.back) {
                page = pages.pop();
            } else if (action == Action.next) {
                pages.add(page);
                page = page.getNextPage();
            } else if (action == Action.finish) {
                pages.add(page);
                break;
            }
        }
        
        List<Page> ret = new ArrayList<Page>();
        ret.addAll(pages);
        return ret;
    }
}
