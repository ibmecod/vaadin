package com.ecodcnc.vaadinui;

import java.io.File;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.json.JSONException;
import org.json.JSONObject;

import com.ecodcnc.vaadinui.utils.format.Answer;
import com.ecodcnc.vaadinui.utils.format.QuestionAnswer;
import com.ecodcnc.vaadinui.utils.format.WatsonResponse;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
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
import com.vaadin.ui.themes.ValoTheme;

@PreserveOnRefresh
@Title("VaadinUI")
@Theme("vaadinui")
public class VaadinWatsonUI extends UI {

	TextField postQuestionText = new TextField();

	Button postQ = new Button("Ask Watson about a Health Symptom?");
	//Button reset = new Button("Reset");
	String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	// Show the image in the application
	FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/iconNoCommunityPhoto155.png"));
	Image image = new Image(null, resource);
	WatsonService ws = new WatsonService();
	WatsonResponse wresp = new WatsonResponse();

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaadinWatsonUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		// Panel panel = new Panel();

		FormLayout formLayout = new FormLayout();
		// setMargin(true);
		postQ.setStyleName(ValoTheme.BUTTON_PRIMARY);
		postQ.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		formLayout.setSizeUndefined();
		setVisible(true);
		//
		//HorizontalLayout hlayout = new HorizontalLayout(postQ, reset);
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

		postQ.addClickListener(new Button.ClickListener() {
			
			
			@SuppressWarnings("deprecation")
			@Override
			public void buttonClick(ClickEvent event) {
				
				Table table1 = new Table();
				Table table2 = new Table();
				// TODO Auto-generated method stub
				JSONObject jsonObject = null;
				if (event.getButton() == postQ) {
					table1.requestRepaint();
					table2.requestRepaintAll();
					//formLayout.removeComponent(table1);;
					//formLayout.removeComponent(table2);
					
					String postedQuestion = postQuestionText.getValue();
					if (postedQuestion != null && !postedQuestion.isEmpty()) {
						postQuestionText.setInputPrompt("Example: Depression");

						String myjsonRespone = ws.getResponseFromBluemixWatson(postedQuestion);
						System.out.println("In From Response is" + myjsonRespone);
						try {
							ArrayList<QuestionAnswer> results = new ArrayList<QuestionAnswer>();
							wresp = WatsonResponse.formatResponse(myjsonRespone, postedQuestion);
							results = wresp.getQAList();
							System.out.println("ArrayList from Results" + results.size());

							

							List<String> answers = new ArrayList<String>();
							int count = 1;
							for (QuestionAnswer result : results) {

								System.out.println("Question is ---" + count + "---" + result.getQuestion().toString());
								if (count == 1) {
									
									table1.addContainerProperty(result.getQuestion().toString(), String.class, null);
									table1.setColumnWidth(result.getQuestion().toString(), 1000);
								} else {
									table2.addContainerProperty(result.getQuestion().toString(), String.class, null);
									table2.setColumnWidth(result.getQuestion().toString(), 1000);
								}

								int m = 1;
								for (Answer answer : result.getAnswerList()) {
									System.out.println("Question Count---" + count + "---Answer---" + m + "---"
											+ answer.getAnswer());
									if (count == 1) {
										m++;
										table1.addItem(new Object[] { answer.getAnswer() }, new Integer(m));
										System.out.println("Added Item" + m);

									} else {
										m++;
										table2.addItem(new Object[] { answer.getAnswer() }, new Integer(m));
									}
								}
								count++;
							}
							table1.setPageLength(table1.size());
							System.out.println("Added Component");
							formLayout.addComponent(table1);
							table2.setPageLength(table2.size());
							formLayout.addComponent(table2);
							String notify = "Watson is Searching for " + postedQuestion + "....";
							Notification.show(notify, Type.TRAY_NOTIFICATION);
							// panel.setContent(formLayout);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						postQuestionText.setInputPrompt("Example : Depression");
						//formLayout.addComponent(postQuestionText);
						Notification.show("NO RESULTS FOUND", Type.TRAY_NOTIFICATION);

						
					}

				} else {
					//This will never get invoked..Place Holder for RESET
					// postQuestionText.setValue("Depression");
					postQuestionText.setInputPrompt("Example : Depression");
					//formLayout.addComponent(postQuestionText);
					Notification.show("Clicked on RESET", Type.TRAY_NOTIFICATION);

				}

			}

		});
		System.out.println("Added Layout");

		setContent(formLayout);

	}

}
