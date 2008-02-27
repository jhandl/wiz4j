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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.flaptor.wizard.Action;
import com.flaptor.wizard.InputPageElement;
import com.flaptor.wizard.OptionPageElement;
import com.flaptor.wizard.Page;
import com.flaptor.wizard.PageElement;
import com.flaptor.wizard.ProgressPageElement;
import com.flaptor.wizard.TextPageElement;
import com.flaptor.wizard.YesNoPageElement;

/**
 * Command line interface for wizards 
 * 
 * @author Martin Massera
 */
public class CLI extends AbstractUI {

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    
    public Action doPageInternal(Page page, boolean withBack) {
        for (PageElement element : page.getElements()) {
            render(element);
        }
        System.out.println();
        while (!page.isReadyToAdvance()) {
            try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
        }
        if (page.isLast()) return Action.finish;
        else return Action.next;
    }
    
    private void render(PageElement element) {
        InputPageElement input = null;
        String answer = null;
        while (true) {
            System.out.print(element.getText());
            if (element.getExplanation() != null) System.out.print(" (" + element.getExplanation() + ")");
            if (element instanceof ProgressPageElement) {
            	ProgressPageElement progress = (ProgressPageElement)element;
            	for (int i = 0; i < progress.getProgress()/5; ++i){
            		System.out.print("*");
            	}
        		System.out.print(" " + progress.getProgress() + "%");
            }
            System.out.println();
            if (!(element instanceof InputPageElement)) return;  

            input = (InputPageElement)element;
            if (element instanceof OptionPageElement) {
                System.out.println();
                OptionPageElement option = (OptionPageElement) element;
                int i = 0;
                for (String opt : option.getOptions()) {
                    System.out.println(i + ": " + opt);
                    i++;
                }
            } else if (element instanceof YesNoPageElement) {
                System.out.print("y / n ");
            }
            if (input.isReadOnly()) {
            	System.out.println(input.getAnswer());
            	return;
            }
            
            //its not readonly
            if (element instanceof TextPageElement) {
            	System.out.println("(Enter a blank line to finish)");
            	answer = "";
            	while (true) {
                	try {
                        String line = in.readLine().trim();
                        if (line.equals("")) break;
                        else answer += line;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
            	}
            } else {
            	System.out.print("(default: "+ input.getAnswer() + "): ");
                try {
                    answer = in.readLine().trim();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            if (isGoodAnswer(answer, input)) break;
            else {
                System.out.println("\nBAD INPUT - try again\n");
            }
        }
        if (input != null && !answer.equals("")) input.setAnswer(answer);
        System.out.println();
    }
    
    private boolean isGoodAnswer(String answer, InputPageElement e) {
        if (answer.equals("")) return true;
        if (e instanceof YesNoPageElement) return answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("n");
        if (e instanceof OptionPageElement) {
            try {
                int ans = Integer.parseInt(answer);
                return 0 <= ans && ans < ((OptionPageElement)e).getOptions().length;
                
            } catch (Throwable t) {return false;}
        }
        return true;
    }

	public void elementUpdated(PageElement element) {
		render(element);
	}
}
