package menuPackage;

import java.awt.EventQueue;
import java.io.*;
import java.nio.file.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.CardLayout;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.ScrollPaneConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextPane;

public class NewsScreen extends JFrame {
	
	JFrame frame = new JFrame();
	JPanel containerPane = new JPanel();
	JPanel mainNews = new JPanel();
	JPanel specificNews = new JPanel();
	CardLayout cardLayout = new CardLayout();
	JButton back = new JButton();
	private final JTextArea newsOne;
	private final JTextArea newsTwo;
	private final JTextArea newsThree;
	private final JTextArea newsFour;
	private final JTextArea newsFive;
	private final JTextArea newsSix;
	private final JTextArea specificNewsArea;
	private JScrollPane scrollPane;
	private JLabel lblImage;
	private JPanel panelNextBackImage;
	private JButton btnPreviousImage;
	private JTextPane lblImageText;
	private JButton btnNextImage;
	private static List<String> imageTextList = new ArrayList<String>();
	private static List<String> imageLinksList = new ArrayList<String>();
	private static List<String> imageDirectoryLinks = new ArrayList<String>();
	private int imageTextCount = 0;
	private int imageCount = 0;
	
	
	//Function for scaling the image so it fits the panel
	private void getScaledImage(int ImageNumber) {
		
		int maxWidth = 900;
		int maxHeight = 410;
		int newHeight = 0, newWidth = 0;        // Variables for the new height and width
	    int priorHeight = 0, priorWidth = 0;
	    BufferedImage image = null;
		ImageIcon imageIcon;
		System.out.println(imageDirectoryLinks);
		
		try {
			
			File file = new File(System.getProperty("user.dir")+"\\bin\\Resources\\Images\\Image"+ImageNumber+".jpg");
			image = ImageIO.read(file);
//			image = ImageIO.read(NewsScreen.class.getResource("/Resources/Images/Image"+ImageNumber+".jpg"));
			
			
		}
		catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "The following error has occured: "+e, "Invalid image error", JOptionPane.ERROR_MESSAGE);
			
		}
		
		imageIcon = new ImageIcon(image);
		if(imageIcon != null)
	    {
	        priorHeight = imageIcon.getIconHeight(); 
	        priorWidth = imageIcon.getIconWidth();
	    }

	    // Calculate the correct new height and width
	    if((float)priorHeight/(float)priorWidth > (float)maxHeight/(float)maxWidth)
	    {
	        newHeight = maxHeight;
	        newWidth = (int)(((float)priorWidth/(float)priorHeight)*(float)newHeight);
	    }
	    else 
	    {
	        newWidth = maxWidth;
	        newHeight = (int)(((float)priorHeight/(float)priorWidth)*(float)newWidth);
	    }
	    
	 // 1. Create a new Buffered Image and Graphic2D object
	    BufferedImage resizedImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    // 2. Use the Graphic object to draw a new image to the image in the buffer
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(image, 0, 0, newWidth, newHeight, null);
	    g2.dispose();
		lblImage.setIcon(new ImageIcon(resizedImg));
	    
	}
	
	
	//A function which deletes all of the old images on startup and upon clicking the back button
	private static void deleteOldImages() {

		
		try {
			final String directory = System.getProperty("user.dir")+"\\bin\\Resources\\Images\\";
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directory));
			List<String> fileNames = new ArrayList<>();
			
			
			for(Path path : directoryStream) {
				fileNames.add(path.toString());
				
			}
			
			for(int firstCount = 0; firstCount < fileNames.size(); firstCount++) {
				
				Files.deleteIfExists(Paths.get(directory+"Image"+firstCount+".jpg"));
			}
			
		}
		catch(NoSuchFileException e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "The following error has occured: "+e, "Nonexistant file error", JOptionPane.ERROR_MESSAGE);
		}
		catch(DirectoryNotEmptyException e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "The following error has occured: "+e, "Directory error", JOptionPane.ERROR_MESSAGE);
		}
		catch(IOException e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "The following error has occured: "+e, "Input/Output error", JOptionPane.ERROR_MESSAGE);
		} 
		
	}
	
	
	//Downloads the image files, from their respective links, to the Images folder
	private void getImages() {
		
		
		try {
			final String folderPath = System.getProperty("user.dir")+"\\bin\\Resources\\Images\\";
			
			for(int count=0; count < imageLinksList.size(); count++) {
				
				URL imageURL = new URL(imageLinksList.get(count).toString());
				InputStream in = imageURL.openStream();
				OutputStream out = new BufferedOutputStream(new FileOutputStream(folderPath+"Image"+count+".jpg"));
				
				 for (int b; (b = in.read()) != -1;) {
					 out.write(b);				
				 }
				 
				 out.close();
			     in.close();
			     imageDirectoryLinks.add(folderPath+"Image"+count+".jpg");
			     
			}
			
		}
		catch(MalformedURLException e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "The following error has occured: "+e, "Malformed URL Exception error", JOptionPane.ERROR_MESSAGE);
			
		}
		catch(IOException ex) {
			
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "The following error has occured: "+ex, "Input/Output error", JOptionPane.ERROR_MESSAGE);
			
		}
		catch(NullPointerException exx) {
			
			exx.printStackTrace();
			JOptionPane.showMessageDialog(null, "The following error has occured: "+exx, "Null Pointer Exception error", JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
	
	//The functions below are the main logic used when the user flips between the images and their respective text or quote.
	//The idea is that both the image and text lists are able to loop on themselves when the user reaches the end and clicks
	//the next image button or is at the beginning and clicks the previous image button.
	private void getNextImage() {
		
		imageCount = imageCount + 1;
		
		if(imageCount > imageDirectoryLinks.size() - 1) {
			imageCount = 0;
			getScaledImage(imageCount);
		}
		else {
			getScaledImage(imageCount);
		}
	}
	
	private void getPreviousImage() {
		
		if(imageCount == 0) {
			
			imageCount = imageDirectoryLinks.size() - 1;
			getScaledImage(imageCount);
		}
		else {
			
			imageCount = imageCount - 1;
			getScaledImage(imageCount);
		}
	
	}
	
	private void getNextImageText() {
		
		if(!imageTextList.isEmpty()) {
			
			imageTextCount = imageTextCount + 1;
			
			if(imageTextCount > imageTextList.size() - 1) { 
				
				imageTextCount = 0;
				lblImageText.setText(imageTextList.get(imageTextCount));
			}
			else {
				
				lblImageText.setText(imageTextList.get(imageTextCount));			
			}
		}
		 
		
	
	}
	
	private void getPreviousImageText() {
		
		if(!imageTextList.isEmpty()) {
			
			if(imageTextCount == 0) {
				
				imageTextCount = imageTextList.size() - 1;
				lblImageText.setText(imageTextList.get(imageTextCount));
			}
			else {
				
				imageTextCount = imageTextCount - 1;
				lblImageText.setText(imageTextList.get(imageTextCount));
			}
		}
	}
	
	
	//Removes both buttons if there is only one image and quote and if both are empty. 
	//Enables them if there are images, but no quotes.
	private void removeLeftRightButtons() {
		
		if(imageTextList.size() == 1 && imageLinksList.size() == 1) {
			
//			panelNextBackImage.remove(btnNextImage);
//			panelNextBackImage.remove(btnPreviousImage);
			btnPreviousImage.setEnabled(false);
			btnNextImage.setEnabled(false);
		}
		else if(imageTextList.isEmpty() && imageLinksList.isEmpty()) {
			
//			panelNextBackImage.remove(btnNextImage);
//			panelNextBackImage.remove(btnPreviousImage);
			btnPreviousImage.setEnabled(false);
			btnNextImage.setEnabled(false);
		}
		else if(imageTextList.isEmpty() && !imageLinksList.isEmpty()) {
			
			btnPreviousImage.setEnabled(true);
			btnNextImage.setEnabled(true);
		}
		else {
			
			btnPreviousImage.setEnabled(true);
			btnNextImage.setEnabled(true);
		}
	}
	
	//Function which fills out a list with each image's web link.
	public static void getSpecificNewsImagesLinks(Document specificNewsDoc) {
		
		String newLink;
		Document doc = specificNewsDoc;
		Elements diffImageLinks = doc.select("div.story-body__inner div.js-delayed-image-load");
		
		for(Element diffImageLink : diffImageLinks) {
			
			newLink = (diffImageLink.attr("data-src").replaceAll("/320/", "/900/"));
			if(!newLink.contains("_98950366_presentational_grey_line464-nc") && !newLink.contains("_007_in_numbers_624") && !newLink.contains("_line")) {
				
				imageLinksList.add(newLink);
			}
			
		}
		
		System.out.println(imageLinksList.toString());
		
	}
	
	//Fills out a list with each image text or quote
	public static void getSpecificNewsImageText(Document specificNewsDoc) {
		
		Document doc = specificNewsDoc;
		Elements imageLinks = doc.select("div.story-body__inner div.js-delayed-image-load");
		
		for(Element imageLink : imageLinks) {
			
			if(!imageLink.attr("data-src").contains("_98950366_presentational_grey_line464-nc") && !imageLink.attr("data-src").contains("_007_in_numbers_624") && !imageLink.attr("data-src").contains("_line")) {
				
				imageTextList.add(imageLink.attr("data-alt"));
			}
			
		}
		System.out.println(imageTextList.toString());
		
	}
	
	
	//Function used to write on the main titles screen.
	private void fillHubNewsInfo(String msg,int count) {
		
		switch(count) {
    	
    	case 0:
    		newsOne.append(msg);
    		count++;
    		break;
    		
    	case 1:
    		newsTwo.append(msg);
    		count++;
    		break;
    		
    	case 2:
    		newsThree.append(msg);
    		count++;
    		break;
    		
    	case 3:
    		newsFour.append(msg);
    		count++;
    		break;
    		
    	case 4:
    		newsFive.append(msg);
    		count++;
    		break;
    		
    	case 5:
    		newsSix.append(msg);
    		count++;
    		break;
    		
    	default:
    		break;
    	
    	}
    }
	
	//Used when the user clicks on a specific article, to write the main text of that article.
	private Document getSpecificNews(Document doc, int choice) {
		try {
			
			List<String> linksList = new ArrayList<String>();
			URL bbcBaseURL = new URL("http://www.bbc.com");
			URL completeURL;
			Document news;
			Elements links = doc.select("#comp-candy-asset-munger-9.distinct-component-group  div.sparrow-item__body > a");
			Elements paragraphs;
			
			for(Element link : links) {
				
				linksList.add(link.attr("href"));
			}
			
			System.out.println(linksList);
			switch(choice) {
			
			case 1:
				completeURL = new URL(bbcBaseURL, linksList.get(0).toString());
				news = Jsoup.connect(completeURL.toString()).get();
				paragraphs = news.select("div.story-body__inner p");
				
				for(Element para : paragraphs) {
					
					specificNewsArea.append(para.text()+"\n\n");
				}
	
				return news;
				
			case 2:
				completeURL = new URL(bbcBaseURL, linksList.get(1).toString());
				news = Jsoup.connect(completeURL.toString()).get();
				paragraphs = news.select("div.story-body__inner p");
				
				for(Element para : paragraphs) {
					
					specificNewsArea.append(para.text()+"\n\n");
				}
				return news;
				
				
			case 3:
				completeURL = new URL(bbcBaseURL, linksList.get(2).toString());
				news = Jsoup.connect(completeURL.toString()).get();
				paragraphs = news.select("div.story-body__inner p");
				
				for(Element para : paragraphs) {
					
					specificNewsArea.append(para.text()+"\n\n");
				}
				
				return news;
				
			case 4:
				completeURL = new URL(bbcBaseURL, linksList.get(3).toString());
				news = Jsoup.connect(completeURL.toString()).get();
				paragraphs = news.select("div.story-body__inner p");
				
				for(Element para : paragraphs) {
					
					specificNewsArea.append(para.text()+"\n\n");
				}
				
				return news;
				
			case 5:
				completeURL = new URL(bbcBaseURL, linksList.get(4).toString());
				news = Jsoup.connect(completeURL.toString()).get();
				paragraphs = news.select("div.story-body__inner p");
				
				for(Element para : paragraphs) {
					
					specificNewsArea.append(para.text()+"\n\n");
				}
				
				return news;
				
			case 6:
				completeURL = new URL(bbcBaseURL, linksList.get(5).toString());
				news = Jsoup.connect(completeURL.toString()).get();
				paragraphs = news.select("div.story-body__inner p");
				
				for(Element para : paragraphs) {
					
					specificNewsArea.append(para.text()+"\n\n");
				}
				
				return news;
				
				default:
					break;
			
			}
			
		}
		catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "The following error has occured: "+e, "Input/Output error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return null;
		
	}
	
	//Returns the main file of the BBC World hub website.
	private Document getSiteInfo(){
		
		try {
			 
			Document doc = Jsoup.connect("http://www.bbc.com/news/world").get();     
	        return doc;
	       
		}
		catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "The following error has occured: "+e, "Input/Output error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
	}
	
	//This function writes the date, title and section(if given) of each article.
	private void fillTextAreas(Document doc) {
		
		
		try {
			
			Elements dates = doc.select("#comp-candy-asset-munger-9.distinct-component-group   .date--v2");
	        Elements titles = doc.select("#comp-candy-asset-munger-9.distinct-component-group  .title-link__title-text");
	        Elements types = doc.select("#comp-candy-asset-munger-9.distinct-component-group  .mini-info-list__item--section a");
	        int count;
	        for(count = 0; count < dates.size(); count++) {
	        	
	        	fillHubNewsInfo(dates.get(count).text(), count);
	        	fillHubNewsInfo("\n" +titles.get(count).text(), count);
	        	
	        	if(count >= types.size()) {
	        		
	        		fillHubNewsInfo("\nFrom - BBC Unknown!" , count);
	        	}
	        	else {
	        		
	        		fillHubNewsInfo("\nFrom - BBC " +types.get(count).text(), count);
	        	}
	        	
	        	
	        	System.out.println(titles.get(count).text());
	        }
	        
		}
		catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "The following error has occured: "+e, "Text area error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new NewsScreen();
					deleteOldImages();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NewsScreen() {
		frame.getContentPane().add(containerPane);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setTitle("News Hub");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 668, 928);
		
		containerPane.setLayout(cardLayout);
		containerPane.add(mainNews, "Hub");
		containerPane.add(specificNews, "Spec");
		containerPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		containerPane.setBackground(Color.WHITE);
		cardLayout.show(containerPane, "Hub");
		
		
		
		mainNews.setBackground(Color.WHITE);
		mainNews.setLayout(new GridLayout(6, 1, 0, 0));
		
		Document doc = getSiteInfo();
		
		
		specificNews.setBackground(Color.WHITE);
		GridBagLayout gbl_specificNews = new GridBagLayout();
		gbl_specificNews.columnWidths = new int[] {430, 0};
		gbl_specificNews.rowHeights = new int[]{410, 30, 259, 0, 0};
		gbl_specificNews.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_specificNews.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		specificNews.setLayout(gbl_specificNews);
		
		
		lblImage = new JLabel("");
		lblImage.setVerticalAlignment(SwingConstants.TOP);
		lblImage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblImage.setBackground(Color.WHITE);
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblImage = new GridBagConstraints();
		gbc_lblImage.anchor = GridBagConstraints.NORTH;
		gbc_lblImage.gridwidth = 1;
		gbc_lblImage.gridheight = 1;
		gbc_lblImage.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblImage.gridx = 0;
		gbc_lblImage.gridy = 0;
		specificNews.add(lblImage, gbc_lblImage);
		
		
		//Creating the panel that houses the buttons which select between the images and the exact image text
		panelNextBackImage = new JPanel();
		panelNextBackImage.setBackground(Color.WHITE);
		GridBagConstraints gbc_panelNextBackImage = new GridBagConstraints();
		gbc_panelNextBackImage.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelNextBackImage.gridx = 0;
		gbc_panelNextBackImage.gridy = 1;
		specificNews.add(panelNextBackImage, gbc_panelNextBackImage);
		panelNextBackImage.setLayout(new MigLayout("", "[100px][410px][100px]", "[21px]"));
		
		btnPreviousImage = new JButton("");
		btnPreviousImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!imageLinksList.isEmpty() && !imageTextList.isEmpty()) {
					
					getPreviousImage();
					getPreviousImageText();
				}
				else {
					
					getPreviousImage();
				}
			}
		});
		btnPreviousImage.setBackground(Color.WHITE);
		btnPreviousImage.setIcon(new ImageIcon(NewsScreen.class.getResource("/Resources/Icons/icons8-back-32.png")));
		panelNextBackImage.add(btnPreviousImage, "cell 0 0,alignx left,aligny center");
		
		lblImageText = new JTextPane();
		lblImageText.setEditable(false);
		lblImageText.setFont(new Font("Tahoma", Font.PLAIN, 19));
		StyledDocument lblDoc = lblImageText.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		lblDoc.setParagraphAttributes(0, lblDoc.getLength(), center, false);
		panelNextBackImage.add(lblImageText, "cell 1 0,grow");
		
		btnNextImage = new JButton("");
		btnNextImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(!imageLinksList.isEmpty() && !imageTextList.isEmpty()) {
					
					getNextImage();
					getNextImageText();
				}
				else {
					
					getNextImage();
				}
				
			}
		});
		btnNextImage.setForeground(Color.WHITE);
		btnNextImage.setBackground(Color.WHITE);
		btnNextImage.setIcon(new ImageIcon(NewsScreen.class.getResource("/Resources/Icons/icons8-forward-32.png")));
		panelNextBackImage.add(btnNextImage, "cell 2 0,alignx right,aligny center");
		
		
		//Creating the main text area for the paragraphs from the news article
		scrollPane = new JScrollPane();
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setViewportBorder(null);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		specificNews.add(scrollPane, gbc_scrollPane);
		
		specificNewsArea = new JTextArea();
		specificNewsArea.setBackground(Color.WHITE);
		scrollPane.setViewportView(specificNewsArea);
		specificNewsArea.setWrapStyleWord(true);
		specificNewsArea.setLineWrap(true);
		specificNewsArea.setRows(1);
		specificNewsArea.setColumns(1);
		specificNewsArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
		specificNewsArea.setEditable(false);
		GridBagConstraints gbc_back = new GridBagConstraints();
		gbc_back.fill = GridBagConstraints.VERTICAL;
		gbc_back.gridx = 0;
		gbc_back.gridy = 3;
		specificNews.add(back, gbc_back);
		back.setText("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				specificNewsArea.setText(null);
				lblImage.setIcon(null);
				deleteOldImages();
				imageLinksList.clear();
				imageTextList.clear();
				imageDirectoryLinks.clear();
				imageCount = 0;
				imageTextCount = 0;
				btnPreviousImage.setEnabled(true);
				btnNextImage.setEnabled(true);
				cardLayout.show(containerPane, "Hub");
			}
		});
		
		//Creating the six rows for the titles and their details, along with their mouse listeners
		newsOne = new JTextArea();
		mainNews.add(newsOne);
		newsOne.setFont(new Font("Monospaced", Font.PLAIN, 20));
		newsOne.setRows(2);
		newsOne.setEditable(false);
		newsOne.setLineWrap(true);
		newsOne.setWrapStyleWord(true);
		newsOne.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				Document specificDoc = getSpecificNews(doc, 1);
				getSpecificNewsImagesLinks(specificDoc);
				getSpecificNewsImageText(specificDoc);
				
				if(!imageLinksList.isEmpty() && !imageTextList.isEmpty()) {
					
					getImages();
					getScaledImage(imageCount);
					lblImageText.setText(imageTextList.get(0));
					removeLeftRightButtons();
				}
				else if(imageTextList.isEmpty() && !imageLinksList.isEmpty()) {
					
					getImages();
					getScaledImage(imageCount);
					removeLeftRightButtons();
				}
				else {
					
					lblImage.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\bin\\Resources\\Images\\WhoopsNoImagesError.jpg"));
					lblImageText.setText("Whoops! No images appear to have been found!");
					removeLeftRightButtons();
					if(specificNewsArea.getText() == "") {
						
						specificNewsArea.append("No text from the article has been found!");
					}
				}
				cardLayout.show(containerPane, "Spec");
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
				newsOne.setFont(new Font("Monospaced", Font.PLAIN, 22));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				
				newsOne.setFont(new Font("Monospaced", Font.PLAIN, 20));
			}
		});
		
		newsTwo = new JTextArea();
		mainNews.add(newsTwo);
		newsTwo.setFont(new Font("Monospaced", Font.PLAIN, 20));
		newsTwo.setRows(2);
		newsTwo.setEditable(false);
		newsTwo.setLineWrap(true);
		newsTwo.setWrapStyleWord(true);
		newsTwo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				Document specificDoc = getSpecificNews(doc, 2);
				getSpecificNewsImagesLinks(specificDoc);
				getSpecificNewsImageText(specificDoc);
				
				if(!imageLinksList.isEmpty() && !imageTextList.isEmpty()) {
					
					getImages();
					getScaledImage(imageCount);
					lblImageText.setText(imageTextList.get(0));
					removeLeftRightButtons();
				}
				else if(imageTextList.isEmpty() && !imageLinksList.isEmpty()) {
					
					getImages();
					getScaledImage(imageCount);
					removeLeftRightButtons();
				}
				else {
					
					lblImage.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\bin\\Resources\\Images\\WhoopsNoImagesError.jpg"));
					lblImageText.setText("Whoops! No images appear to have been found!");
					removeLeftRightButtons();
					if(specificNewsArea.getText() == "") {
						
						specificNewsArea.append("No text from the article has been found!");
					}
				}
				cardLayout.show(containerPane, "Spec");
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
				newsTwo.setFont(new Font("Monospaced", Font.PLAIN, 22));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				
				newsTwo.setFont(new Font("Monospaced", Font.PLAIN, 20));
			}
		});
		
		newsThree = new JTextArea();
		mainNews.add(newsThree);
		newsThree.setFont(new Font("Monospaced", Font.PLAIN, 20));
		newsThree.setRows(2);
		newsThree.setEditable(false);
		newsThree.setLineWrap(true);
		newsThree.setWrapStyleWord(true);
		newsThree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				Document specificDoc = getSpecificNews(doc, 3);
				getSpecificNewsImagesLinks(specificDoc);
				getSpecificNewsImageText(specificDoc);
				
				if(!imageLinksList.isEmpty() && !imageTextList.isEmpty()) {
					
					getImages();
					getScaledImage(imageCount);
					lblImageText.setText(imageTextList.get(0));
					removeLeftRightButtons();
				}
				else if(imageTextList.isEmpty() && !imageLinksList.isEmpty()) {
					
					getImages();
					getScaledImage(imageCount);
					removeLeftRightButtons();
				}
				else {
					
					lblImage.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\bin\\Resources\\Images\\WhoopsNoImagesError.jpg"));
					lblImageText.setText("Whoops! No images appear to have been found!");
					removeLeftRightButtons();
					if(specificNewsArea.getText() == "") {
						
						specificNewsArea.append("No text from the article has been found!");
					}
				}
				cardLayout.show(containerPane, "Spec");
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
				newsThree.setFont(new Font("Monospaced", Font.PLAIN, 22));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				
				newsThree.setFont(new Font("Monospaced", Font.PLAIN, 20));
			}
		});
		
		newsFour = new JTextArea();
		mainNews.add(newsFour);
		newsFour.setFont(new Font("Monospaced", Font.PLAIN, 20));
		newsFour.setRows(2);
		newsFour.setEditable(false);
		newsFour.setLineWrap(true);
		newsFour.setWrapStyleWord(true);
		newsFour.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				Document specificDoc = getSpecificNews(doc, 4);
				getSpecificNewsImagesLinks(specificDoc);
				getSpecificNewsImageText(specificDoc);
				
				if(!imageLinksList.isEmpty() && !imageTextList.isEmpty()) {
					
					getImages();
					getScaledImage(imageCount);
					lblImageText.setText(imageTextList.get(0));
					removeLeftRightButtons();
				}
				else if(imageTextList.isEmpty() && !imageLinksList.isEmpty()) {
					
					getImages();
					getScaledImage(imageCount);
					removeLeftRightButtons();
				}
				else {
					
					lblImage.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\bin\\Resources\\Images\\WhoopsNoImagesError.jpg"));
					lblImageText.setText("Whoops! No images appear to have been found!");
					removeLeftRightButtons();
					if(specificNewsArea.getText() == "") {
						
						specificNewsArea.append("No text from the article has been found!");
					}
				}
				cardLayout.show(containerPane, "Spec");
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
				newsFour.setFont(new Font("Monospaced", Font.PLAIN, 22));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				
				newsFour.setFont(new Font("Monospaced", Font.PLAIN, 20));
			}
		});
		
		newsFive = new JTextArea();
		mainNews.add(newsFive);
		newsFive.setFont(new Font("Monospaced", Font.PLAIN, 20));
		newsFive.setRows(2);
		newsFive.setEditable(false);
		newsFive.setLineWrap(true);
		newsFive.setWrapStyleWord(true);
		newsFive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				Document specificDoc = getSpecificNews(doc, 5);
				getSpecificNewsImagesLinks(specificDoc);
				getSpecificNewsImageText(specificDoc);
				
				if(!imageLinksList.isEmpty() && !imageTextList.isEmpty()) {
					
					getImages();
					getScaledImage(imageCount);
					lblImageText.setText(imageTextList.get(0));
					removeLeftRightButtons();
				}
				else if(imageTextList.isEmpty() && !imageLinksList.isEmpty()) {
					
					getImages();
					getScaledImage(imageCount);
					removeLeftRightButtons();
				}
				else {
					
					lblImage.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\bin\\Resources\\Images\\WhoopsNoImagesError.jpg"));
					lblImageText.setText("Whoops! No images appear to have been found!");
					removeLeftRightButtons();
					if(specificNewsArea.getText() == "") {
						
						specificNewsArea.append("No text from the article has been found!");
					}
				}
				cardLayout.show(containerPane, "Spec");
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
				newsFive.setFont(new Font("Monospaced", Font.PLAIN, 22));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				
				newsFive.setFont(new Font("Monospaced", Font.PLAIN, 20));
			}
		});
		
		newsSix = new JTextArea();
		mainNews.add(newsSix);
		newsSix.setFont(new Font("Monospaced", Font.PLAIN, 20));
		newsSix.setRows(2);
		newsSix.setEditable(false);
		newsSix.setLineWrap(true);
		newsSix.setWrapStyleWord(true);
		newsSix.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				Document specificDoc = getSpecificNews(doc, 6);
				getSpecificNewsImagesLinks(specificDoc);
				getSpecificNewsImageText(specificDoc);
				
				if(!imageLinksList.isEmpty() && !imageTextList.isEmpty()) {
					
					getImages();
					getScaledImage(imageCount);
					lblImageText.setText(imageTextList.get(0));
					removeLeftRightButtons();
				}
				else if(imageTextList.isEmpty() && !imageLinksList.isEmpty()) {
					
					getImages();
					getScaledImage(imageCount);
					removeLeftRightButtons();
				}
				else {
					
					lblImage.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\bin\\Resources\\Images\\WhoopsNoImagesError.jpg"));
					lblImageText.setText("Whoops! No images appear to have been found!");
					removeLeftRightButtons();
					if(specificNewsArea.getText() == "") {
						
						specificNewsArea.append("No text from the article has been found!");
					}
				}
				cardLayout.show(containerPane, "Spec");
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
				newsSix.setFont(new Font("Monospaced", Font.PLAIN, 22));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				
				newsSix.setFont(new Font("Monospaced", Font.PLAIN, 20));
			}
		});
		//Inserts the article headlines, dates and categories
		fillTextAreas(doc);

	}

}
