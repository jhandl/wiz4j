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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.log4j.Logger;

import com.flaptor.wizard.Action;
import com.flaptor.wizard.InputPageElement;
import com.flaptor.wizard.OptionPageElement;
import com.flaptor.wizard.Page;
import com.flaptor.wizard.PageElement;
import com.flaptor.wizard.ProgressPageElement;
import com.flaptor.wizard.TextPageElement;
import com.flaptor.wizard.YesNoPageElement;

/**
 * GUI interface for wizards
 * 
 * @author Martin Massera
 */
public class GUI extends AbstractUI{
    
    private static Logger logger = Logger.getLogger(new Throwable().getStackTrace()[1].getClassName());

    private Action action;
    private JFrame frame;
    private JButton nextFinishButton;

    private Box textPanelBox = new Box(BoxLayout.Y_AXIS);
    private JPanel buttonBox = new JPanel();
    private Page page;
    
    public GUI(String title) {
        frame = new JFrame(title);
        
        Dimension minimumSize = new Dimension(400,200);
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        frame.setLocation((screenSize.width - minimumSize.width)/2, (screenSize.height - minimumSize.height)/ 2 );
        frame.setMinimumSize(minimumSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(textPanelBox, BorderLayout.NORTH);
        frame.getContentPane().add(buttonBox, BorderLayout.SOUTH);
        buttonBox.setLayout(new BorderLayout());
    }
       
    public Action doPageInternal(Page page, boolean withBack) {
        this.page = page;

        render(page, withBack);

        boolean lastCanAdavance = true;
        action = null;
        while(action == null) {
            boolean canAdvance = true;
            if (page.getValidateInputCallback() != null) {
                try {
                    canAdvance = page.getValidateInputCallback().call();
                } catch (Exception e) {
                    logger.error(e);
                    canAdvance = false;
                }
            }
            canAdvance = canAdvance && page.isReadyToAdvance();
            if (canAdvance != lastCanAdavance) {
                nextFinishButton.setEnabled(canAdvance);
                nextFinishButton.repaint();
                lastCanAdavance = canAdvance;
            }
            
            if (canAdvance && page.isAutomaticAdvance()) {
                action = page.isLast() ? Action.finish : Action.next;
            }
            
            try {Thread.sleep(50);} catch (InterruptedException e) {}
        }
        
        if (action == Action.finish || action == Action.cancel) {
            frame.dispose();
        }
        return action;
    }

    /**
     * renders the page, once for doPageInternal
     */
    private void render(Page page, boolean withBack) {
        frame.setVisible(true);
        addButtons(page,withBack);
        renderElements(page);
    }

    /**
     * renders the page elements, may be called various times for the same page
     */
    private void renderElements(Page page) {
        textPanelBox.removeAll();
        textPanelBox.add(new JLabel(" "));        
        for (PageElement element : page.getElements()) {
            render(element);
        }
        frame.pack();
        frame.getContentPane().repaint();
    }

    
    /**
     * renders an element
     */
    private void render(PageElement element) {
        textPanelBox.add(new JLabel(element.getText()));
        if (element.getExplanation() != null){
            JLabel label = new JLabel(element.getExplanation());
            Font font = label.getFont().deriveFont(Font.ITALIC);
            label.setFont(font);
            textPanelBox.add(label);
        }
        
        if (element instanceof ProgressPageElement) {
            ProgressPageElement prog = (ProgressPageElement)element;
            JProgressBar prgBar = new JProgressBar(0, 100);
            prgBar.setValue(prog.getProgress());
            prgBar.setStringPainted(true);
            textPanelBox.add(prgBar);
        }
        //else textPanelBox.add(new JLabel(" "));

        if (element instanceof InputPageElement) {
        	if (element instanceof OptionPageElement) {
                OptionPageElement option = (OptionPageElement) element;
                ButtonGroup group = new ButtonGroup();
                int i = 0;
                for (String opt : option.getOptions()) {
                    addRadio(group, textPanelBox, option, opt, String.valueOf(i), option.getAnswer());
                    i++;
                }
            } else if (element instanceof YesNoPageElement) {

                YesNoPageElement option = (YesNoPageElement) element;
                ButtonGroup group = new ButtonGroup();
                
                addRadio(group, textPanelBox, option, "yes", "y", option.getAnswer());
                addRadio(group, textPanelBox, option, "no", "n", option.getAnswer());
            } else if (element instanceof TextPageElement) {
                TextPageElement q = (TextPageElement) element;
                final JTextArea questionArea = new JTextArea(q.getAnswer());
                textPanelBox.add(questionArea);
                questionArea.addKeyListener(new SetFieldValueActionListener(q, questionArea));
                questionArea.setEnabled(!q.isReadOnly());   
            } else {
                InputPageElement q = (InputPageElement) element;
                final JTextField questionField = new JTextField(q.getAnswer());
                textPanelBox.add(questionField);
                questionField.addKeyListener(new SetFieldValueActionListener(q, questionField));
                questionField.setEnabled(!q.isReadOnly());
            }
        }
        textPanelBox.add(new JLabel(" "));
    }

    /**
     * add a radio button
     */
    private void addRadio(ButtonGroup group, Box textPanelBox, InputPageElement input, String text, String value, String defaultValue) {
        JRadioButton radio = new JRadioButton(text, value.equalsIgnoreCase(defaultValue));
        radio.addActionListener(new SetValueActionListener(input, value));
        group.add(radio);
        textPanelBox.add(radio);
        radio.setEnabled(!input.isReadOnly());
    }
    
    /**
     * adds the bottom buttons
     */
    private void addButtons(Page page, boolean withBack) {
    	buttonBox.removeAll();
    	Box backNextBox = new Box(BoxLayout.X_AXIS);
    	
        if (page.isCanCancelOrBack()) {
            JButton button = new JButton("cancel");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {action = Action.cancel;}
            });
            buttonBox.add(button,BorderLayout.WEST);
        }
        if (withBack && page.isCanCancelOrBack()) {
            JButton button = new JButton("< back");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {action = Action.back;}
            });
            backNextBox.add(button);
            backNextBox.add(new JLabel(" "));
        }
        if (!page.isAutomaticAdvance()) {
            if (page.isLast()) {
                nextFinishButton = new JButton("finish!");
                nextFinishButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {action = Action.finish;}
                });
            } else {
                nextFinishButton = new JButton("next >");
                nextFinishButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {action = Action.next;}
                });
            }
            backNextBox.add(nextFinishButton);
        }
        buttonBox.add(backNextBox, BorderLayout.EAST);
    }
        
    private static class SetValueActionListener implements ActionListener {
        InputPageElement element;
        String value;
        SetValueActionListener(InputPageElement element, String value) {
            this.element = element;
            this.value = value;
        }
        public void actionPerformed(ActionEvent e) {
            element.setAnswer(value);
        }
    }

    private static class SetFieldValueActionListener implements KeyListener {
        InputPageElement element;
        JTextComponent field;
        SetFieldValueActionListener(InputPageElement element, JTextComponent field) {
            this.element = element;
            this.field = field;
        }
        public void keyPressed(KeyEvent e) {element.setAnswer(field.getText());}
        public void keyReleased(KeyEvent e) {element.setAnswer(field.getText());}
        public void keyTyped(KeyEvent e) {element.setAnswer(field.getText());}
    }

    public void elementUpdated(PageElement element) {
        renderElements(page);
    }
}
