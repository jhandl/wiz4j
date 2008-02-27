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
 * PageElement for representing progress of a process, especially for progress bars in GUI
 * 
 * @author Martin Massera
 */
public class ProgressPageElement extends PageElement{

	private int progress = 0;
	
	public ProgressPageElement(String text, String explanation) {
		super(text, explanation);
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}
}
