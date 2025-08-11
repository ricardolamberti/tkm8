package pss.common.terminals.drivers.PDFFile;

public class JRectangulo {
	
	float left;
	float top;
	float width;
	float height;
	
	public JRectangulo(float x, float y, float w, float h) {
		this.left=x;
		this.top=y;
		this.width=w;
		this.height=h;
	}
	
	public float getLeft() {
		return this.left;
	}
	public float getTop() {
		return this.top;
	}
	public float getWidth() {
		return this.width;
	}
	public float getHeight() {
		return this.height;
	}
	public float getAllWidth() {
		return this.left+this.width;
	}
	public float getAllHeight() {
		return this.top+this.height;
	}

}
