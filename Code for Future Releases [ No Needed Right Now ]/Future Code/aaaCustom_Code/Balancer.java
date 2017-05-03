/*
 * 
 */
package disc;

import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

// TODO: Auto-generated Javadoc
/**
 * The Class Balancer.
 */
public class Balancer extends Canvas {

	/** The image 1. */
	Image image1 =  new Image(getClass().getResourceAsStream("circle.png"));
	
	/**
	 * 0=Horizontal <br>
	 * 1=Vertical.
	 */
	Orientation orientation = Orientation.HORIZONTAL;

	/** The bar width. */
	// �� ����� ���� Horizontal ���� Vertical ��� ������ ��� ����� ���� drag
	private int barWidth = 8;
	
	/** The value. */
	private int value = 0;

	/** The maximum volume. */
	// Colors
	final int maximumVolume;
	
	/** The color 1. */
	Color color1 = Color.rgb(53, 144, 255);

	/** The gc. */
	GraphicsContext gc = getGraphicsContext2D();

	/**
	 * Instantiates a new balancer.
	 *
	 * @param d the d
	 * @param y the y
	 * @param width the width
	 * @param height the height
	 * @param currentValue the current value
	 * @param maximum the maximum
	 */
	public Balancer(double d, int y, int width, int height, int currentValue, int maximum) {
		value = currentValue;
		maximumVolume = maximum;

		setLayoutX(d);
		setLayoutY(y);
		setWidth(width+image1.getWidth()/2);
		setHeight(height+8);

		setCursor(Cursor.NONE);
		paintBalancer();
	}

	/**
	 * Paint balancer.
	 */
	private void paintBalancer() {

		if (orientation == Orientation.HORIZONTAL) { // HORIZONTAL
			gc.clearRect(0, 0, getWidth(), getHeight());

			// �������� �� Background
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, getWidth(), getHeight());

			// �������� ���� ���� ��������
			gc.setFill(color1);
			gc.fillRect(0, 5, value, getHeight() - 10);
			gc.setFill(Color.RED);
			gc.fillRect(value, 5, getWidth() - getVolume(), getHeight() - 10);

			// ��������� ��� �����
			gc.drawImage(image1, value, 0);
			/*if (getVolume() < 100)
				gc.drawImage(image3, value, 0);
			else if (getVolume() == 100)
				gc.drawImage(image2, value, 0);
			else if (getVolume() > 100)
				gc.drawImage(image1, value, 0);*/

			gc.setStroke(Color.BLACK);
			gc.strokeText(String.valueOf(getVolume()), getWidth() / 2, getHeight() / 2 + 5);
		}

	}

	/**
	 * On mouse dragged.
	 *
	 * @param m the m
	 */
	public void onMouseDragged(MouseEvent m) {
		// ���� ��� ��� ���� �� mo.getX
		if (orientation == Orientation.HORIZONTAL) { // Horizontal
			// �������� ���������������� ���� ��� ��� ��������� �� �� mouse
			if (m.getX() > getMaximum())
				setV(getMaximum());
			else if (m.getX() < 0)
				setV(0);
			else
				setV((int) m.getX());
		} else { // Vertical
			// �������� ���������������� ���� ��� ��� ��������� �� �� mouse
			if (m.getY() > getMaximum())
				setV(0);
			else if (m.getY() < 0)
				setV(getMaximum());
			else
				setV(getHeight() - m.getY());
		}

	}

	/**
	 * On scroll.
	 *
	 * @param sc the sc
	 */
	public void onScroll(ScrollEvent sc) {

		int rotation = sc.getDeltaY() < 1 ? 1 : -1;
		// ������� �� ����� Rolling
		if (value - rotation * 2 > -1 && value - rotation * 2 < getMaximum() + 1)
			setV(value - rotation * 2);
		else if (value - rotation > -1 && value - rotation < getMaximum() + 1)
			setV(value - rotation);
	}

	////// ������ �� ���� ����������------------------(SET)

	/**
	 * Set Current Value of Slider *.
	 *
	 * @param d the new v
	 */
	private void setV(double d) {

		// ������ �� value �� ����� ������� ��� Maximum ��� ��� Minimum
		if (d < getMaximum() + 1 && d > -1) {
			value = (int) d;
			paintBalancer();
		}
	}

	/**
	 * ����� ��� ���� �� ���� �� ������� ����������� ������(%,per 75 , per
	 * 200,etc .. ) ��� ����� � �������
	 *
	 * @param newValue the new volume
	 */
	public void setVolume(int newValue) {

		// ��� ����� �� ���� ���� �� ������ �������� ��������
		if (newValue == 0) {
			value = 0;
			paintBalancer();
			return;
		}
		if (newValue == maximumVolume) {
			value = getMaximum();
			paintBalancer();
			return;
		}

		// ������
		if (newValue < maximumVolume && newValue > 0) {
			if (getMaximum() / maximumVolume == 0) // ��� ����� ����������� ���
													// Systemal
				value = newValue * getMaximum() / maximumVolume;
			else // ��� ��� ����� ����������� ��� Systemal
				value = (newValue * getMaximum() / maximumVolume) + 1;

			paintBalancer();
		}

	}

	////// ����������� ��� ����---------------(GET)

	/**
	 * Get Maximum Value of Slider.
	 *
	 * @return the maximum
	 */
	public int getMaximum() {

		if (orientation == Orientation.HORIZONTAL)
			return (int) (getWidth() - barWidth);
		else
			return (int) (getHeight() - barWidth);
	}

	/**
	 * ������� ���������� �� ���� �� ������� ����������� ������(%,per 75 , per
	 * 200,etc .. ) ��� ����� � �������
	 *
	 * @return the volume
	 */
	public int getVolume() {
		if (getMaximum() <= maximumVolume) // ��� �������� ������� �� �� �������
			return value;
		else // ��� ��������� ���������
			return value * maximumVolume / getMaximum();
	}

}
