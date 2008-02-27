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
 * represents a page that chooses the next page according to a yes no answer
 * yes = first next
 * no = second next
 * 
 * @author Martin Massera
 */
public class YesNoNextChoosingPage extends NextChoosingPage {

	public YesNoNextChoosingPage(String choosingProperty) {
		super(choosingProperty);
	}

	@Override
    public Page getNextPage() {
        return nextPages.get(getProperty(choosingProperty).equalsIgnoreCase("y") ? 0 : 1);
    }
}
