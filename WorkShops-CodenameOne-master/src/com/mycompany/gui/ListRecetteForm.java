/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.services.RecetteService;
import entity.Recettes;
import java.util.ArrayList;

/**
 *
 * @author timou
 */
public class ListRecetteForm extends BaseForm{
Form     current = this;
Form prev;
 public ListRecetteForm(Resources res) {
        super("Newsfeed",BoxLayout.y());
        prev=this;
      Toolbar tb=new Toolbar(true);
      
      setToolbar(tb);
      getTitleArea().setUIID("Container");
      setTitle(" Recette");
      getContentPane().setScrollVisible(false);
      
        super.addSideMenu(res);
      tb.addSearchCommand(e->{
          
      });
      Tabs swipe =new Tabs();
      Label sw1=new Label();
      Label sw2=new Label();
      Image img=res.getImage("back-logo.jpg");
     //   System.out.println(img);
      
    addTab(swipe,sw1,res.getImage("back-logo.jpg"),"" ,res);
      
       addTab(swipe,sw2,res.getImage("back-logo.jpg"),"" ,res);
      
      
      
      ///////////////////////////////////
      swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, sw1, sw2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Mes Recettes", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Statistiques", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Ajouter Recette ", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");


    mesListes.addActionListener((e) -> {
    refreshTheme();
        new ListRecetteForm(res).show();
            
        });

        liste.addActionListener((e) -> {
    refreshTheme();
        new RecetteStatForm( prev ).show();
            
        });
        partage.addActionListener((e) -> {
            refreshTheme();
        new AjouterRecetteForm(res).show();
            
        });
        
        
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        
        ArrayList <Recettes> list=RecetteService.getInstance().getAllTasks();
        for (Recettes rec:list){
            String urlimg="Logo.png";
            Image plch=Image.createImage(120, 90);
            System.out.println(plch+"yyyyyyyyyyyy");
            EncodedImage e=EncodedImage.createFromImage(plch, false);
            URLImage urlim=URLImage.createToStorage(e, urlimg, urlimg, URLImage.RESIZE_SCALE);
            addButton(urlim,rec,res);
            ScaleImageLabel image=new ScaleImageLabel(urlim);
            Container cimg=new Container();
            image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
            
            
        }     
      //////////////////////////////////////
      
    }
    
    
    
    
    
    
    
    
  private void addStringValue(String s,Component v){
   add(BorderLayout.west(new Label(s,"PaddedLabel"))
           .add(BorderLayout.CENTER,v));
add(createLineSeparator(0xeeeeee));   
  }  

    private void addTab(Tabs swipe, Label spacer, Image image,   String text, Resources res) {
        int size=Math.min(Display.getInstance().getDisplayWidth(),Display.getInstance().getDisplayHeight());
        if(image.getHeight()<size){
            image=image.scaledHeight(size);
        }
        if(image.getHeight()> Display.getInstance().getDisplayHeight()/2){
            image=image.scaledHeight(Display.getInstance().getDisplayHeight()/2);
        }
        
        ScaleImageLabel imagesc=new ScaleImageLabel(image);
        imagesc.setUIID("Container");
        imagesc.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        Label overl=new Label("","ImageOverlay");
        Container page1=
                LayeredLayout.encloseIn(
                imagesc,
                        overl,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                new SpanLabel(text,"LargeWhiteText"),
                                      FlowLayout.encloseIn( ),
                                        spacer
                                        )));
        swipe.addTab("",res.getImage("logo.png"),page1);
        
    }
    
    public void bindButtonSelection(Button btn,Label l){
        btn.addActionListener(e->{if(btn.isSelected()) {
            updateArrowPosition(btn,l);
        }
    });
}
    
 

    private void updateArrowPosition(Button btn, Label l) {
       l.getUnselectedStyle().setMargin(LEFT, btn.getX()+btn.getWidth()/2 -l.getWidth()/2);
       l.getParent().repaint();
    }

    private void addButton( Image img,Recettes rec,Resources res) {
 int height=Display.getInstance().convertToPixels(11.5f);
 EncodedImage enc = EncodedImage.createFromImage(res.getImage("back-logo.jpg"), false);
             Image i = URLImage.createToStorage(enc, "http://127.0.0.1/pidev/public/uploads/" + rec.getImg(), "http://127.0.0.1/pidev/public/uploads/" + rec.getImg(), URLImage.RESIZE_SCALE);
int height2 = Display.getInstance().convertToPixels(11.5f);
           int width2 = Display.getInstance().convertToPixels(14f);
           Button img2 = new Button(i.fill(width2, height2));
           img2.setUIID("Label");
           img2.getStyle().setBgColor(0xFBFBEF);
           img2.getStyle().setBackgroundGradientEndColor(0xFBFBEF);
           img2.getStyle().setFgColor(0xFBFBEF);
           
           
         int width=Display.getInstance().convertToPixels(14f);
         Button image =new Button (img.fill(width, height));
         image.setUIID("Label");
        Container c=BorderLayout.west(image);
        TextArea t=new TextArea(rec.getNom());
        t.setUIID("NewsTopLine");
        t.setEditable(false);
        TextArea t2=new TextArea(rec.getAbout());
        t2.setUIID("NewsTopLine");
        t2.setEditable(false);
 DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
       
        System.out.println(rec.getNom());
        System.out.println(rec.getAbout());
//                String strDate = dateFormat.format(rec.getDate_creation());  
    //    TextArea t3=new TextArea( strDate);
 //        System.out.println(strDate+"uuuuuuuuuu");
      //  t.setUIID("NewsTopLine");
      //  t.setEditable(false);
         
        
        
        
        
        
        Label delete=new Label("");
        delete.setUIID("NewsTopLine");
        Style delstyle=new Style(delete.getUnselectedStyle());
        delstyle.setFgColor(0xf21f1f1);
        FontImage delimg=FontImage.createMaterial(FontImage.MATERIAL_DELETE,delstyle);
        delete.setIcon(delimg);
        delete.setTextPosition(RIGHT);
        delete.addPointerPressedListener((e)->{
        Dialog dig=new Dialog("Suppression");
            if (dig.show("Suppression","Voulez Supprimer cette Catégorie?!","Annuler","Ok")){
               dig.dispose();
                
            }else{
                 dig.dispose();
                 
                 if( RecetteService.getInstance().deleteCat(rec)){
                     new ListRecetteForm(res).show();
                 }
                 
        }
    }); 
        
        
        
        
        
        Label modif=new Label("");
        modif.setUIID("NewsTopLine");
        Style modstyle=new Style(modif.getUnselectedStyle());
        modstyle.setFgColor(0x53bfa4);
        FontImage modimg=FontImage.createMaterial(FontImage.MATERIAL_UPDATE,modstyle);
        modif.setIcon(modimg);
        modif.setTextPosition(RIGHT);
        modif.addPointerPressedListener((e)->{
        Dialog dig=new Dialog("Modification");
            if (dig.show("Modification","Voulez Modifier cette Catégorie?!","Annuler","Ok")){
               dig.dispose();
                
            }else{
                 dig.dispose();
                 new UpdateRecetteForm(res, rec).show();
      }
    }); 
        c.add(BorderLayout.CENTER,BoxLayout.encloseY(BoxLayout.encloseX(img2,t,modif,delete),t2));
        add(c);
 
    }    
}
