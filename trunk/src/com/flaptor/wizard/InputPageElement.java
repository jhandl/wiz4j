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
 * Page element for input. You can use it directly as a text input or subclass it
 * for more specific input 
 *  
 * @author Martin Massera
 */
public class InputPageElement extends PageElement{
    private String propertyName;
    private String answer;
    private boolean readOnly;
    
    public InputPageElement(String text, String explanation, String propertyName, String defaultValue) {
        super(text, explanation);
        this.answer = defaultValue;
        this.propertyName = propertyName;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    public String getPropertyName() {
        return propertyName;
    }

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
}
