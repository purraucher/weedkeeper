package weedkeeper.ui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum Icons {
	AppIcon("app-icon.png");
	private final String name;
	private BufferedImage image;
	private Icons(String name) {
		this.name = name;
		image = null;
	}
	public BufferedImage get() {
		if (image != null) {
			return image;
		}
		try {
			return image = ImageIO.read(Icons.class.getResourceAsStream("/icons/" + name));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
