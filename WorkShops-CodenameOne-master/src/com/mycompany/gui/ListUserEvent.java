/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.services.ServiceEvent;

/**
 *
 * @author fediKh
 */
public class ListUserEvent extends BaseForm {
	Container container1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

	public ListUserEvent(Form previous) {
		setTitle("List tasks");

		SpanLabel sp = new SpanLabel();
		// sp.setText(ServiceEvent.getInstance().getAllTasks().toString());
		// add(sp);
		container1 = ServiceEvent.getInstance().getEventsUser();

		getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

		add(container1);

	}

}
