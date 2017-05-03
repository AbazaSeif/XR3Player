/*
 * 
 */
package disc;


import javafx.geometry.Orientation;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

// TODO: Auto-generated Javadoc
/**
 * The Class VolumeSlider.
 */
public class VolumeSlider extends Canvas{

	
	/** The image 1. */
	Image image1 = null;
	
	/** The image 2. */
	Image image2 = null;
	
	/** The image 3. */
	Image image3 = null;
	
	/** The image 4. */
	Image image4 = null;
	
	/**
	 * 0=Horizontal <br>
	 * 1=Vertical*.
	 */
	Orientation orientation = Orientation.HORIZONTAL;
	
	    
	/** The bar width. */
	// �� ����� ���� Horizontal ���� Vertical ��� ������ ��� ����� ���� drag
	private int barWidth = 8;
	
	/** The value. */
	private int value = 0;

	
	/** The background. */
	// Colors
	private Color background = Color.BLACK;
     
	
	/** The maximum volume. */
	private int maximumVolume = 0;
	
	/** The gc. */
	GraphicsContext gc = getGraphicsContext2D();
	
	
	/**
	 * Instantiates a new volume slider.
	 *
	 * @param layoutX the layout X
	 * @param layoutY the layout Y
	 * @param width the width
	 * @param height the height
	 * @param orientation the orientation
	 * @param currentValue the current value
	 * @param maximumValue the maximum value
	 */
	//Constructor 1
	public VolumeSlider(int layoutX,int layoutY,int width,int height,Orientation orientation,int currentValue,int maximumValue){		
		
		 setLayoutX(layoutX);
		 setLayoutY(layoutY);
		
		 init(width,height,orientation,currentValue,maximumValue);
	}
	
	/**
	 * Instantiates a new volume slider.
	 *
	 * @param width the width
	 * @param height the height
	 * @param orientation the orientation
	 * @param currentValue the current value
	 * @param maximumValue the maximum value
	 */
	//Constructor 2
	public VolumeSlider(int width,int height,Orientation orientation,int currentValue,int maximumValue){		

		 init(width,height,orientation,currentValue,maximumValue);
	}
	
	
	/**
	 * Inits the.
	 *
	 * @param width the width
	 * @param height the height
	 * @param orientation the orientation
	 * @param currentValue the current value
	 * @param maximumValue the maximum value
	 */
	//Make it
	private void init(int width,int height,Orientation orientation,int currentValue,int maximumValue){
		
		this.orientation = orientation; // ��� �� ����� �� Oriantation
		value = currentValue;
		maximumVolume = maximumValue;
		
		 setWidth(orientation==Orientation.VERTICAL?width+barWidth:width);
		 setHeight(orientation==Orientation.VERTICAL?height:height+barWidth);
		 
		 image1 = new Image(getClass().getResourceAsStream((orientation==Orientation.HORIZONTAL?"h1":"v1")+".png"));
		 image2 = new Image(getClass().getResourceAsStream((orientation==Orientation.HORIZONTAL?"h2":"v2")+".png"));
		 image3 = new Image(getClass().getResourceAsStream((orientation==Orientation.HORIZONTAL?"h3":"v3")+".png"));
		 image4 = new Image(getClass().getResourceAsStream((orientation==Orientation.HORIZONTAL?"h4":"v4")+".png"));
	 
	
		 setOnMouseDragged(this::onMouseDragged);
		 setOnScroll(this::onScroll);
		 
		 paintBar();
	}
	

/**
 * Paint bar.
 */
//TODO paintBar	
	private void paintBar(){
				
		gc.clearRect(0, 0, getWidth(), getHeight());
		
		//
		if (orientation == Orientation.HORIZONTAL) { 					       		//HORIZONTAL
			
			// �������� �� Background
			gc.setFill(background);
			gc.fillRect(0, 0, getWidth(), getHeight());
			
			
			// �������� ���� ���� ��������
			gc.setFill(Color.ORANGE);
			gc.fillRect(0, 5, value, getHeight() - 10);
		
			 //������� �� Orange �� ��������
			gc.setFill(Color.BLACK);
			 for(int k=0; k<value; k+=10)
				gc.fillRect(k,0,6,28);
					
			
			//��������� ��� �����
			if(getVolume()<=17) gc.drawImage(image1,value,0);
			else if(getVolume()<=35)  gc.drawImage(image2,value,0);
			else if(getVolume()<=60) gc.drawImage(image3,value,0);
			else if(getVolume()>60)  gc.drawImage(image4,value,0);
			

			
		}else{                                     			  	//VERTICAL		
			
			// �������� �� Background
			
			//gc.setFill(Color.CORNFLOWERBLUE);
			if(getVolume()<=17) gc.setFill(Color.GREENYELLOW);
		     else if(getVolume()<=35)  gc.setFill(Color.CORNFLOWERBLUE);
		     else if(getVolume()<=85) gc.setFill(Color.YELLOW);
		     else if(getVolume()>85)  gc.setFill(Color.RED);
			for(int i=(int)getHeight() - value - barWidth; i<(int)getHeight(); i+=5)
				gc.fillRoundRect(2, i, getWidth()-4, 4, 15, 15);
				
			
			//�������� ��� ������� �������
			 gc.setStroke(Color.CYAN);
		    // gc.strokeLine(1.5,getHeight(),1.5,getHeight()-value);
		    // gc.strokeLine(getWidth()-1.5,getHeight(),getWidth()-1.5,getHeight()-value);
		     
			
		   //��������� ��� �����	
		     if(getVolume()<=17) gc.drawImage(image1,0,getHeight()-value-barWidth);
		     else if(getVolume()<=35)  gc.drawImage(image2,0,getHeight()-value-barWidth);
		     else if(getVolume()<=85) gc.drawImage(image3,0,getHeight()-value-barWidth);
		     else if(getVolume()>85)  gc.drawImage(image4,0,getHeight()-value-barWidth);
		
			gc.setStroke(Color.WHITE);
			gc.strokeText(getVolume() + "", getVolume() < 10 ? 10 : getVolume() < 100 ? 8 : 4,
					getHeight() - value - barWidth > 10 ? getHeight() - value - barWidth - 1: getHeight() - value - barWidth + 11);

		}
	}
	
	
	
	
	
//////������ �� ���� ����������------------------(SET)	
	
	/**
 * Set Current Value of Slider*.
 *
 * @param newValue the new v
 */
	private void setV(int newValue) {
		
		//������ �� value �� ����� ������� ��� Maximum ��� ��� Minimum
		if (newValue <getMaximum()+1 && newValue > -1) {
			value = newValue;
			paintBar();
		}
	}
		
		
	/**
	 * ����� ��� ���� �� ���� �� ������� ����������� ������(%,per 75 , per
	 * 200,etc .. ) ��� ����� � �������*
	 *
	 * @param newValue the new volume
	 */
    public void setVolume(int newValue){
  	
  	  //��� ����� �� ���� ���� �� ������ �������� ��������
  	  if(newValue==0){ value=0; paintBar(); return; }
  	  if(newValue == maximumVolume) { value = getMaximum(); paintBar(); return;} 
  	 
  	  //������
  	   if(newValue<maximumVolume && newValue>0){
  		  if(getMaximum()/maximumVolume==0)  //��� ����� ����������� ��� Systemal
  		   value = newValue*getMaximum()/maximumVolume;
  		  else                              //��� ��� ����� ����������� ��� Systemal
  			value = (newValue*getMaximum()/maximumVolume) +1;
  			  
  		   paintBar();
  	   }	
  		  
	  }
		  	
	/**
	 * ������ �� ���� �� drag ��� ���������*.
	 *
	 * @param m the m
	 */
    public void onMouseDragged(MouseEvent m){
    	
    	//���� ��� ��� ���� �� mo.getX
		if(orientation==Orientation.HORIZONTAL){                 //Horizontal				
			//�������� ���������������� ���� ��� ��� ��������� �� �� mouse
			if(m.getX()>getWidth()-barWidth)  
				setV(getMaximum());	
			else if(m.getX()<0)
				setV(0);
			else 
				setV((int) m.getX());
		}else{                              //Vertical				
			//�������� ���������������� ���� ��� ��� ��������� �� �� mouse
			if(m.getY()>getMaximum())  
				setV(0);	
			else if(m.getY()<0)
				setV(getMaximum());
			else 
				setV((int) (getHeight()-m.getY()));
		}	
		
    }
    
    
    /**
	 * ������ �� ���� �� containerScroller ��� ���������.
	 *
	 * @param scroll the scroll
	 */
    public void onScroll(ScrollEvent scroll){
    	
   	 int rotation=scroll.getDeltaY()<1?1:-1;
		//������� �� ����� Rolling
		   if(value-rotation*2>-1 && value-rotation*2<getMaximum()+1)
			   setV(value-rotation*2);
		   else if(value-rotation>-1 && value-rotation<getMaximum()+1)
			   setV(value-rotation);
	   
    }
	
//////����������� ��� ����---------------(GET)
	
	
	/**
 * Get Maximum Value of Slider.
 *
 * @return the maximum
 */
	public int getMaximum() {
		
		if(orientation==Orientation.HORIZONTAL)
			return (int) (getWidth()-barWidth);
		else 
			return (int) (getHeight()-barWidth);	
	}
	
	
	/**
	 * ������� ���������� �� ���� �� ������� ����������� ������(%,per 75 , per
	 * 200,etc .. ) ��� ����� � �������*
	 *
	 * @return the volume
	 */
	public int getVolume() {
		if(getMaximum()<=maximumVolume)   //��� �������� ������� �� �� �������
			return value;
		else              //��� ��������� ���������			
			return value*maximumVolume/getMaximum();	
	}
	
	
}
