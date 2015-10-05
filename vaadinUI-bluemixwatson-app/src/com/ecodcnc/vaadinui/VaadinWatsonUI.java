package com.ecodcnc.vaadinui;

import java.io.File;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import org.json.JSONException;


import com.ecodcnc.vaadinui.utils.format.Answer;
import com.ecodcnc.vaadinui.utils.format.QuestionAnswer;
import com.ecodcnc.vaadinui.utils.format.WatsonResponse;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;

import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


@PreserveOnRefresh
@Title("VaadinUI")
@Theme("vaadinui")

public class VaadinWatsonUI extends UI {

	TextField postQuestionText = new TextField();
	Button postQ = new Button("Ask Watson about a Health Symptom?");
	String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	// Show the image in the application
	FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/iconNoCommunityPhoto155.png"));
	Image image = new Image(null, resource);
	WatsonService ws = new WatsonService();
	WatsonResponse wresp = new WatsonResponse();
	VerticalLayout vlayout = null;
	Table table1;
	Table table2;
	
	
	@Override
	protected void init(VaadinRequest request) {
		FormLayout formLayout = new FormLayout();
		postQ.setStyleName(ValoTheme.BUTTON_PRIMARY);
		postQ.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		HorizontalLayout hlayout = new HorizontalLayout(postQ);
		HorizontalLayout head = new HorizontalLayout();
		head.addComponent(new Label("WELCOME TO IBM VAADIN CHALLENGE"));
		head.setWidth("500px");
		HorizontalLayout head1 = new HorizontalLayout();
		head1.addComponent(new Label("VAADIN BLUEMIX WATSON HEALTHCARE SERVICES SAMPLE PPLLICATION"));
		head1.setWidth("700px");
		hlayout.setSpacing(true);
		postQuestionText.setImmediate(true);
		postQuestionText.setInputPrompt("Example: Depression");
		postQuestionText.setWidth("500px");
		formLayout.addComponents(image, head, head1, postQuestionText, hlayout);
		setVisible(true);
		
		postQ.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

				if (vlayout != null) {
					//formLayout.removeAllComponents();
					vlayout.removeAllComponents();
					table1.removeAllItems();
					table2.removeAllItems();
				}
				vlayout = new VerticalLayout();
				BeanItemContainer<Symptoms> beans1 = new BeanItemContainer<Symptoms>(Symptoms.class);
				BeanItemContainer<Symptoms> beans2 = new BeanItemContainer<Symptoms>(Symptoms.class);
				beans1.removeAllItems();
				beans2.removeAllItems();

				String postedQuestion = postQuestionText.getValue();
				if (postedQuestion != null && !postedQuestion.isEmpty()) {
					String myjsonRespone = ws.getResponseFromBluemixWatson(postedQuestion);
					//System.out.println("In From Response is" + myjsonRespone);
					try {
						ArrayList<QuestionAnswer> results = new ArrayList<QuestionAnswer>();
						wresp = WatsonResponse.formatResponse(myjsonRespone, postedQuestion);
						results = wresp.getQAList();
						//System.out.println("ArrayList from Results" + results.size());
						String table1Header = null;
						String table2Header = null;
						int count = 1;
						for (QuestionAnswer result : results) {
						
							if (count == 1) {
								table1Header = result.getQuestion().toString();
							} else {
								table2Header = result.getQuestion().toString();
							}

							int m = 1;
							for (Answer answer : result.getAnswerList()) {
								//System.out.println("Question Count---" + count + "---Answer---" + m + "---" + answer.getAnswer());
								if (count == 1) {
									beans1.addBean(new Symptoms(new Integer(m), answer.getAnswer()));
									m++;
								} else {
									beans2.addBean(new Symptoms(new Integer(m), answer.getAnswer()));
									m++;
								}
							}
							count++;
						}

						table1 = new Table(table1Header, beans1);
						table1.setHeight("100%");
					    table1.setWidth("100%");
					    table1.setPageLength(0);
					    table1.setColumnWidth("id", 10);
					    table1.setVisibleColumns(new Object[] { "id", "answer" });
						vlayout.addComponent(table1);
						
						table2 = new Table(table2Header, beans2);
						table2.setHeight("100%");
					    table2.setWidth("100%");
					    table2.setPageLength(0);
					    table2.setColumnWidth("id", 10);
					    table2.setVisibleColumns(new Object[] { "id", "answer" });
						vlayout.addComponent(table2);

						formLayout.addComponent(vlayout);
					
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					postQuestionText.setInputPrompt("Example : Depression");
					formLayout.addComponent(postQuestionText);
					Notification.show("NO RESULTS FOUND, PLEASE ENTER A VALUE",Type.TRAY_NOTIFICATION);
				}
			}
		});
		setContent(formLayout);

	}
	
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaadinWatsonUI.class)
	public static class Servlet extends VaadinServlet {
	}

}
